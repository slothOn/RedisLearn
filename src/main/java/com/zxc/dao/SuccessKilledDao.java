package com.zxc.dao;

import com.zxc.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zxc on 16/7/6.
 */
public interface SuccessKilledDao {
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
    List<SuccessKilled> queryByIdWithSeckill(@Param("seckillId") long seckillId);
    SuccessKilled queryByIdAndPhone(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
