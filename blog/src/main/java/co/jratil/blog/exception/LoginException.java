package co.jratil.blog.exception;

import co.jratil.blog.enums.ResultEnum;

public class LoginException extends RuntimeException {

    private Integer code;
    private String message;

    public LoginException() {
    }

    public LoginException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public LoginException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
