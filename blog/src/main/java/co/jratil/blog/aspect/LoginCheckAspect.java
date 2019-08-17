package co.jratil.blog.aspect;


import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.constant.CookieConstant;
import co.jratil.blog.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@Aspect
public class LoginCheckAspect {

    @Pointcut("@within(co.jratil.blog.aspect.LoginCheck)")
    public void login() {
    }

    @Before("login()")
    public void check(JoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpServletResponse response = sra.getResponse();

        // 1、判断用户的id和登录的id是否相同
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0) {
            log.error("【拦截器】没有登录！");
            throw new LoginException(ResultEnum.NOT_LOGIN);
        }

        for (Cookie cookie : cookies) {
            // 已经登录
            if (CookieConstant.TOKEN.equals(cookie.getName()) &&
                    session.getAttribute(CookieConstant.TOKEN) != null &&
                    session.getAttribute(CookieConstant.TOKEN).equals(cookie.getValue())) {

                return;
            }
        }

        log.error("【拦截器】没有登录！");
        throw new LoginException(ResultEnum.NOT_LOGIN);
    }
}
