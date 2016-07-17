package com.zxc.dao.cache;


import com.zxc.entity.Seckill;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zxc on 16/7/16.
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JedisPool jedisPool;
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port){
        jedisPool = new JedisPool(ip, port);
    }

    public String  putSeckill(Seckill seckill){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:" + seckill.getSeckillId();
            byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            String result = jedis.setex(key.getBytes(), 3600, bytes);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            jedis.close();
        }
        return null;
    }

    public Seckill getSeckill(long seckillId){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = "seckill:" + seckillId;
            byte[] bytes = jedis.get(key.getBytes());
            if(bytes != null){
                Seckill seckill = schema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                return seckill;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            jedis.close();
        }
        return null;
    }
}
