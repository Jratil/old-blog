package co.jratil.blog.controller;

import co.jratil.blog.constant.CookieConstant;
import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.exception.AuthorException;
import co.jratil.blog.pojo.dto.AuthorDTO;
import co.jratil.blog.pojo.form.AuthorForm;
import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.service.AuthorService;
import co.jratil.blog.service.UserRoleService;
import co.jratil.blog.utils.ResultVOUtil;
import co.jratil.blog.utils.SessionUtil;
import com.alibaba.druid.util.DruidWebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthorService authorService;

    @PostMapping("/register")
    public ResultVO register(@Validated(AuthorForm.Register.class) @RequestBody AuthorForm authorForm,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【用户注册】参数不正确，authorForm={}", authorForm);
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        authorService.register(authorForm);

        return ResultVOUtil.success();
    }

    @PostMapping("/login")
    public ResultVO login(@RequestParam String account, @RequestParam String password,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            log.error("【用户登录】输入不能为空, account={}, password={}", account, password);
            throw new AuthorException(ResultEnum.PARAM_ERROR);
        }

        AuthorDTO authorDTO = authorService.login(account, password, DruidWebUtils.getRemoteAddr(request));
        // 客户端不存在cookie或者cookie和session中的不相等，则登录
        String token = UUID.randomUUID().toString().replace("-", "");

        HttpSession session = SessionUtil.getHttpSession();
        session.setAttribute(CookieConstant.TOKEN, token);
        session.setAttribute("authorId", authorDTO.getAuthorId());
        session.setMaxInactiveInterval(CookieConstant.EXPIRE);

        Cookie cookie = new Cookie(CookieConstant.TOKEN, token);
        cookie.setMaxAge(CookieConstant.EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResultVOUtil.success(authorDTO);
    }

    @RequestMapping("/logout")
    public ResultVO logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = SessionUtil.getHttpSession();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (CookieConstant.TOKEN.equals(cookie.getName())) {
                cookie.setMaxAge(0);
            }
            if (!StringUtils.isEmpty(session.getAttribute(CookieConstant.TOKEN))) {
                session.removeAttribute(CookieConstant.TOKEN);
                session.removeAttribute("authorId");
            }
            response.addCookie(cookie);
        }
        return ResultVOUtil.success();
    }

    @PostMapping("/modify")
    public ResultVO updateAuthor(@RequestBody AuthorDTO authorDTO) {
        if (authorDTO == null || authorDTO.getAuthorAccount() == null) {
            log.error("【用户修改信息】参数为空");
            throw new AuthorException(ResultEnum.PARAM_ERROR);
        }

        authorService.updateAuthor(authorDTO);
        return ResultVOUtil.success();
    }

    @PostMapping("/find_password")
    public ResultVO forgetPassword(@Validated(AuthorForm.ForgetPassword.class) @RequestBody AuthorForm authorForm,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("【用户注册】参数不正确，authorForm={}", authorForm);
            throw new AuthorException(ResultEnum.PARAM_ERROR);
        }

        authorService.forgetPassword(authorForm);

        return ResultVOUtil.success();
    }
}
