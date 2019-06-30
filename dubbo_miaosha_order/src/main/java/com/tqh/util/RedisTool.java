package com.tqh.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mcorleon
 * @Date 2019/4/14 19:26
 */
@Service
public class RedisTool {

    //未初始化的库存
    public static final long UNINITIALIZED_STOCK = -1L;

    @Autowired
    private RedisTemplate redisTemplate;

    // 执行扣库存的脚本
    private static final String STOCK_LUA;
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        sb.append("    if (stock-ARGV[1] >= 0) then");
        sb.append("        redis.call('incrby', KEYS[1], -ARGV[1]);");
        sb.append("    end;");
        sb.append("    return stock;");
        sb.append("end;");
        sb.append("return -1;");
        STOCK_LUA = sb.toString();
    }
    /**
     * 扣库存
     * @param key 库存key arg 扣除个数
     * @return 扣减之前剩余的库存【 -1:库存未初始化; 其他:扣减库存之前的剩余库存】
     */
    public Long decreaseStock(String key,String arg) {
        // 脚本里的KEYS参数
        List<String> keys = new ArrayList<>();
        keys.add(key);
        // 脚本里的ARGV参数
        List<String> args = new ArrayList<>();
        args.add(arg);
        long result = (long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(STOCK_LUA, keys, args);
                }
                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(STOCK_LUA, keys, args);
                }
                return UNINITIALIZED_STOCK;
            }
        });

        return result;
    }

}
