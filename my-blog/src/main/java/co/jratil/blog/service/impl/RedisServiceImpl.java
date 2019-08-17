package co.jratil.blog.service.impl;

import co.jratil.blog.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public Object getAndSet(String key, Object value, long expire) {
        Object result = get(key);
        if (result == null) {
            set(key, value, expire);
        }
        return result;
    }

    @Override
    public boolean del(String key) {
        Boolean result = redisTemplate.delete(key);
        if (result == null) {
            return false;
        }
        return result;
    }

    @Override
    public boolean expire(String key, long expire) {
        Boolean result = redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        if (result == null) {
            return false;
        }
        return result;

    }

    @Override
    public long ttl(String key) {
        Long result = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public <T> void setList(String key, List<T> list) {

    }

    @Override
    public <T> void setList(String key, List<T> list, long expire) {

    }

    @Override
    public <T> List<T> getLis(String key) {
        return null;
    }
}
