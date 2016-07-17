package com.zxc.dao.cache;

import com.zxc.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by zxc on 16/7/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;
    @Test
    public void testPutSeckill() throws Exception {
        System.out.println(
                redisDao.putSeckill(new Seckill(2000, "abc", 100, new Date(), new Date(), new Date())));
    }

    @Test
    public void testGetSeckill() throws Exception {
        System.out.println(redisDao.getSeckill(2000));
    }
}