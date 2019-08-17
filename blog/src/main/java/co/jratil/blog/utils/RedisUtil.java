package co.jratil.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

    public static RedisUtil redisUtilFactory() {
        return new RedisUtil();
    }

    public void set(String key, Object data) {
        redisTemplate.opsForValue().set(key, data);
    }

    /**
     * 设置数据和过期时间
     * @param key key
     * @param data 数据
     * @param timeout 过期时间（秒）
     */
    public void set(String key, Object data, long timeout) {
        redisTemplate.opsForValue().set(key, data, timeout, TimeUnit.SECONDS);
    }

    public Object getAndSet(String key, Object data) {
        return redisTemplate.opsForValue().getAndSet(key, data);
    }

    /**
     * 设置过期时间，单位：秒
     * @param key
     * @param timeout
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取过期时间（time to live, 单位为秒）
     * @param key
     * @return
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 删除
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
