package com.zxc.service.impl;

import com.zxc.dao.SeckillDao;
import com.zxc.dao.SuccessKilledDao;
import com.zxc.dao.cache.RedisDao;
import com.zxc.dto.Exposer;
import com.zxc.dto.SeckillExecution;
import com.zxc.entity.Seckill;
import com.zxc.entity.SuccessKilled;
import com.zxc.enums.SeckillStatNum;
import com.zxc.exception.RepeatkillException;
import com.zxc.exception.SeckillCloseException;
import com.zxc.exception.SeckillException;
import com.zxc.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zxc on 16/7/12.
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    //MD5混淆盐值,避免客户猜到
    private final static String slat = "japoifqfja^*()*-aj ppio8f";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 100);
    }

    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null) {
                return new Exposer(false, seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();

        if(nowTime.compareTo(endTime) > 0 || nowTime.compareTo(startTime) < 0){
            return new Exposer(false, seckillId, startTime.getTime(), nowTime.getTime(), endTime.getTime());
        }

        return new Exposer(true, getMD5(seckillId), seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatkillException, SeckillCloseException {
        try {
            if(md5 == null || !md5.equals(getMD5(seckillId))){
                throw new SeckillException("seckill data rewrite");
            }
            //执行秒杀逻辑
            Date nowTime = new Date();
            //先insert再update减少行级锁持有时间,事务中任何对记录的更新与删除都会自动加入排他锁
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if(insertCount <= 0){
                throw new RepeatkillException("repeat seckill");
            }
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if(updateCount <= 0){
                //没有更新到记录
                throw new SeckillCloseException("seckill is closed");
            }

            SuccessKilled successKilled = successKilledDao.queryByIdAndPhone(seckillId, userPhone);
            return new SeckillExecution(seckillId, SeckillStatNum.SUCCESS, successKilled);
        }catch (RepeatkillException e){
            return new SeckillExecution(seckillId, SeckillStatNum.REPEAT_KILL);
        }catch (SeckillCloseException e){
            return new SeckillExecution(seckillId, SeckillStatNum.END);
        }catch (SeckillException e){
            return new SeckillExecution(seckillId, SeckillStatNum.DATA_REWRITE);
        }catch (Exception e){
            //转化为runtime exception, 方便事务rollback
            throw new RuntimeException(e);
        }
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5){
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId, SeckillStatNum.DATA_REWRITE);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", new Date());
        map.put("result", null);
        try {
            seckillDao.killByProcedure(map);
            int result = MapUtils.getInteger(map, "result", -2);
            if(result == 1){
                SuccessKilled sk = successKilledDao.queryByIdAndPhone(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatNum.SUCCESS, sk);
            }
            return new SeckillExecution(seckillId, SeckillStatNum.stateOf(result));
        }catch (Exception e){
            return new SeckillExecution(seckillId, SeckillStatNum.INNER_ERROR);
        }
    }
}
