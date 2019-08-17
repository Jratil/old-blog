package co.jratil.blog.exception;

import co.jratil.blog.enums.ResultEnum;

public class AuthorException extends RuntimeException {

    private Integer code;

    public AuthorException() {
    }

    public AuthorException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public AuthorException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
