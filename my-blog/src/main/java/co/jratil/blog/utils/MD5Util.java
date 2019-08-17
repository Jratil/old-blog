package co.jratil.blog.utils;

import org.springframework.util.DigestUtils;

public class MD5Util {

    public static String md5Str(String sourceStr) {
        return DigestUtils.md5DigestAsHex(sourceStr.getBytes());
    }
}
