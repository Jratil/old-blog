package co.jratil.blog.aspect;

import java.lang.annotation.*;

/**
 * @date 2019-08-13 22:26
 * @author wenjj
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityManager {

    String value() default "ROLE_USER";
}
