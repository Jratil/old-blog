package co.jratil.blog.handle;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.exception.AuthorityException;
import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.utils.ResultVOUtil;
import co.jratil.blog.exception.AuthorException;
import co.jratil.blog.exception.EmailException;
import co.jratil.blog.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandle {

    @ResponseBody
    @ExceptionHandler(AuthorException.class)
    public ResultVO handleAuthorException(AuthorException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(EmailException.class)
    public ResultVO handleEmailException(EmailException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(LoginException.class)
    public ResultVO handleLoginException(LoginException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(AuthorityException.class)
    public ResultVO handleAuthorityException(AuthorityException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
