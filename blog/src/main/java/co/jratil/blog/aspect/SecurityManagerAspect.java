package co.jratil.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @date 2019-08-13 22:29
 */
@Slf4j
@Component
@Aspect
public class SecurityManagerAspect {

    @Pointcut("@annotation(co.jratil.blog.aspect.SecurityManager)")
    public void point() { }

    @Before("point()")
    public void before(JoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        HttpSession session = request.getSession();

        String authorId = (String) session.getAttribute("authorId");
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature ms = (MethodSignature) signature;
        String[] parameterNames = ms.getParameterNames();

        for (String param: parameterNames) {
            if ("articleId".equals(param)) {

            }
        }
        for (Object object: args) {
            if(object instanceof String) {

            }
        }

    }
}
