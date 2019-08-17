package co.jratil.blog.utils;

import co.jratil.blog.constant.EmailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Random;

@Slf4j
public class VerifyCodeUtil {

//    public VerifyCodeUtil verifyCodeFactory() {
//        return new VerifyCodeUtil();
//    }

    public static String generateCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9000) + 1000);
    }

    public boolean isEqualVerifyCode(String account, String verifyCode) {
        RedisTemplate redisTemplate = new RedisTemplate();
        String redisVerifyCode = (String) redisTemplate.opsForValue().get(account + EmailConstant.REDIS_VERIFY_CODE_KEY);

        // redis 中没有查到该账户的验证码或者不相等
        if (StringUtils.isEmpty(redisVerifyCode) || !verifyCode.equals(redisVerifyCode)) {
            return false;
        }

        return true;
    }
}
