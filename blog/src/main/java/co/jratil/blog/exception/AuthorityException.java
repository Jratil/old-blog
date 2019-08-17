package co.jratil.blog.exception;

import co.jratil.blog.enums.ResultEnum;

/**
 * @date 2019-08-13 23:15
 */
public class AuthorityException extends RuntimeException {

    private Integer code;
    private String message;

    public AuthorityException() {
    }

    public AuthorityException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public AuthorityException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
