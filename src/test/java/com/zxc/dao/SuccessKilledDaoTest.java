package com.zxc.dao;

import com.zxc.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zxc on 16/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    SuccessKilledDao successKilledDao;
    @Test
    public void testInsertSuccessKilled() throws Exception {
        System.out.println(successKilledDao.insertSuccessKilled(1000, 123456));
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        List<SuccessKilled> successKilled = successKilledDao.queryByIdWithSeckill(1000);
        System.out.println(successKilled.size());
    }

    @Test
    public void testInsertSuccessKilled1() throws Exception {

    }

    @Test
    public void testQueryByIdWithSeckill1() throws Exception {

    }

    @Test
    public void testQueryByIdAndPhone() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdAndPhone(1000, 123456);
        System.out.println(successKilled);
    }
}