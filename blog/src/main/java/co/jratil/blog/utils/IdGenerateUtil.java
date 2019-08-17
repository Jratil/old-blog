package co.jratil.blog.utils;

import co.jratil.blog.service.RedisService;
import co.jratil.blog.service.impl.RedisServiceImpl;

import java.util.*;

public class IdGenerateUtil {

    /**
     * 生成用户id
     * <p>
     * 以生成author_id为例：
     * 从redis 获取最后的author_id，没有就说明是第一次运行
     * 第一次运行则将当前时间戳的前八位，再加上随机的两位数字组成的8位数字作为第一位注册用户的author_id，存入redis，并将其返回
     * 以后每位用户注册，将从redis获取这最后一位author_id，在此基础上加上两位随机数，作为用户author_id，存入redis，并返回
     *
     * @param redisKey 要从redis获取的Key名称
     * @return 生成的id
     */
    public static synchronized String generateId(String redisKey) {
        RedisService redisService = ApplicationContextProvider.getBean(RedisServiceImpl.class);

        Object cacheId = redisService.get(redisKey);
        Long authorId;
        if (cacheId == null) {
            // 获取当前时间戳并且取前8位
            authorId = System.currentTimeMillis() / 100000;
        } else {
            authorId = Long.valueOf(cacheId.toString());
        }

        // 随机生成两位数字用来相加
        Random random = new Random();
        int randomNum = random.nextInt(90) + 10;

        authorId = authorId + randomNum;

        redisService.set(redisKey, authorId);

        return String.valueOf(authorId);
    }
}
