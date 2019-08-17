package co.jratil.blog.exception;

import co.jratil.blog.enums.ResultEnum;

public class EmailException extends RuntimeException {

    Integer code;

    public EmailException() {
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
