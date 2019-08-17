package co.jratil.blog.exception;

import co.jratil.blog.enums.ResultEnum;

public class ArticleException extends RuntimeException {

    private Integer code;

    public ArticleException(String message) {
        super(message);
    }

    public ArticleException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
