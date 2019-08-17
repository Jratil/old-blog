package co.jratil.blog.aspect;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCheck {
    String value() default "";
}
