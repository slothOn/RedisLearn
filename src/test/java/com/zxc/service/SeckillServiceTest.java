package com.zxc.service;

import com.zxc.dto.Exposer;
import com.zxc.dto.SeckillExecution;
import com.zxc.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zxc on 16/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        System.out.println(seckillList.size());
        for (Seckill seckill : seckillList){
            logger.info("{} \n", seckill);
        }
    }

    @Test
    public void testGetSeckillById() throws Exception {
        Seckill seckill = seckillService.getSeckillById(1000);
        logger.info("{} \n", seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        Exposer iphoneurl = seckillService.exportSeckillUrl(1000);
        logger.info("{} \n", iphoneurl);
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long seckillId = 1000;
        long phonenum = 123546;
        Exposer iphoneurl = seckillService.exportSeckillUrl(seckillId);
        SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phonenum, iphoneurl.getMd5());
        logger.info("{} \n", seckillExecution);
    }
}