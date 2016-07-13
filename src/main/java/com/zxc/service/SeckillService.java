package com.zxc.service;

import com.zxc.dto.Exposer;
import com.zxc.dto.SeckillExecution;
import com.zxc.entity.Seckill;
import com.zxc.exception.RepeatkillException;
import com.zxc.exception.SeckillCloseException;
import com.zxc.exception.SeckillException;

import java.util.List;

/**
 * Created by zxc on 16/7/12.
 */

public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId 秒杀id
     * @return
     */
    Seckill getSeckillById(long seckillId);

    /**
     * 暴露商品的秒杀接口,否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);


    /**
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatkillException 重复秒杀异常
     * @throws SeckillCloseException 秒杀关闭异常
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatkillException, SeckillCloseException;
}
