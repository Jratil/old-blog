package co.jratil.blog.enums;

public enum ArticleVisibleEnum {

    VISIBLE(0, "可见"),
    INVISIBLE(1, "不可见");

    Integer code;

    String message;

    ArticleVisibleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
