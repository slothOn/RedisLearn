package com.zxc.dao;

import com.zxc.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by zxc on 16/7/6.
 */
public interface SeckillDao {
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);//减少库存
    Seckill queryById(@Param("seckillId") long seckillId);
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
