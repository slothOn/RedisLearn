package com.zxc.dao;

import com.zxc.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zxc on 16/7/8.
 * junit启动时加载spring容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//JUnit spring位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void testReduceNumber() throws Exception {
        System.out.println(seckillDao.reduceNumber(1000, new Date()));
    }

    @Test
    public void testQueryById() throws Exception {
        Seckill seckill = seckillDao.queryById(1000);
        System.out.println(seckill.getName());
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(1, 2);
        for(Seckill sec:seckillList){
            System.out.println(sec);
        }
    }
}