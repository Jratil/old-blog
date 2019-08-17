package co.jratil.blog.filter;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.constant.CookieConstant;
import co.jratil.blog.exception.AuthorException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 1、判断用户的id和登录的id是否相同
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            // 没登陆
            if (CookieConstant.TOKEN.equals(cookie.getName()) &&
                    session.getAttribute(CookieConstant.TOKEN).equals(cookie.getValue())) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

        throw new AuthorException(ResultEnum.NOT_LOGIN);
    }

    @Override
    public void destroy() {

    }
}
