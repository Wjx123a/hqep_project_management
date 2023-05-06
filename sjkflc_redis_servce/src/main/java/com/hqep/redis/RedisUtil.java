package com.hqep.redis;/**
 * Created by Administrator on 2018/7/20.
 */
import com.hqep.dataSharingPlatform.common.utils.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.jms.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @program:RedisUtil
 * @description:redis工具类
 * @author:Mr.G
 * @create:2018-07-20 13:34
 */
public class RedisUtil {
    private static JedisPool pool = null;
    private static String ip ="";
    private static int port = 0;
    private static int timeout = 0;
    private static String password = "";

    public RedisUtil() {
        try {
            Properties properties = new Properties();
            properties.load(RedisUtil.class.getResourceAsStream("/conf/properties/redisConfig.properties"));
            ip = properties.getProperty("redis.ip");
            port = Integer.valueOf(properties.getProperty("redis.port"));
            timeout = Integer.valueOf(properties.getProperty("redis.timeout"));
            password = properties.getProperty("redis.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建redis连接池
     *
     * @param
     * @param
     * @return JedisPool
     */
    public static JedisPool getPool() {
        new RedisUtil();
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(500);
            config.setMaxIdle(5);
            config.setMaxWaitMillis(1000 * 100);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config,ip, port,2000,password);
           // pool = new JedisPool(config,ip, port,2000);
        }
        return pool;
    }

    /**
     * 关闭redis
     *
     * @param redis
     */
    public static void redisClose(Jedis redis) {
        if (redis != null) {
            redis.close();
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key,int indexDb) {
        String value = null;

        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            value = redis.get(key);
        } catch (Exception e) {
            // 释放redis对象
            redisClose(redis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            redisClose(redis);
        }

        return value;
    }
    public static byte[] get(byte[] key,int indexDb) {
        byte[] value = null;
        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            value = redis.get(key);
        } catch (Exception e) {
            // 释放redis对象
            redisClose(redis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            redisClose(redis);
        }

        return value;
    }

    public static void set(String key, String value, int seconds,int indexDb) {
        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            redis.setex(key,seconds,value);
        } catch (Exception e) {
            redisClose(redis);
            e.printStackTrace();
        } finally {
            redisClose(redis);
        }

    }
    public static void set(byte[] key, byte[] value, int seconds,int indexDb) {
        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            redis.setex(key,seconds, value);
        } catch (Exception e) {
            redisClose(redis);
            e.printStackTrace();
        } finally {
            redisClose(redis);
        }

    }
    public static void set(String key, String value,int indexDb) {
        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            redis.set(key, value);
        } catch (Exception e) {
            redisClose(redis);
            e.printStackTrace();
        } finally {
            redisClose(redis);
        }

    }
    public static void set(byte[] key, byte[] value,int indexDb) {
        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            redis.set(key, value);
        } catch (Exception e) {
            redisClose(redis);
            e.printStackTrace();
        } finally {
            redisClose(redis);
        }

    }
    public static void delete(int indexDb, byte[] key) throws Exception {
        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            Long result = redis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisClose(redis);
        }
    }
    public static void delete(int indexDb, String key) throws Exception {
        JedisPool pool = null;
        Jedis redis = null;
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);
            Long result = redis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisClose(redis);
        }
    }
    /**
     * 获取所有Session
     * @param indexDb
     * @param
     * @return
     */
    public static Collection<Session> AllSession(int indexDb, String keys) throws Exception {
        JedisPool pool = null;
        Jedis redis = null;
        Set<Session> sessions = new HashSet<Session>();
        try {
            pool = getPool();
            redis = pool.getResource();
            redis.select(indexDb);

            Set<byte[]> byteKeys = redis.keys(keys.getBytes());
            if (byteKeys != null && byteKeys.size() > 0) {
                for (byte[] key : byteKeys) {
                    Session obj = SerializeUtil.deserialize(redis.get(key),Session.class);
                    if(obj instanceof Session){
                        sessions.add(obj);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            redisClose(redis);
        }
        return sessions;
    }

}
