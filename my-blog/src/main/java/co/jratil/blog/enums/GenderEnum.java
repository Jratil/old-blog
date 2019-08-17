package co.jratil.blog.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {

    EMPTY(0, "未知"),
    MAN(1, "男"),
    WOMAN(2, "女");

    Integer code;

    String message;

    GenderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
