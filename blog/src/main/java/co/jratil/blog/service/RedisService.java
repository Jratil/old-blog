package co.jratil.blog.service;

import java.util.List;

public interface RedisService {

    void set(String key, Object value);

    void set(String key, Object value, long expire);

    Object get(String key);

    Object getAndSet(String key, Object value);

    Object getAndSet(String key, Object value, long expire);

    boolean del(String key);

    boolean expire(String key, long expire);

    long ttl(String key);

    <T> void setList(String key, List<T> list);

    <T> void setList(String key, List<T> list, long expire);

    <T> List<T> getLis(String key);
}
