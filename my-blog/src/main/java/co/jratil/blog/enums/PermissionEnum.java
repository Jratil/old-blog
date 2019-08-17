package co.jratil.blog.enums;

import lombok.Getter;

@Getter
public enum PermissionEnum {

    ADMIN(1, "管理员"),
    ORDINARY(0, "普通");

    Integer code;

    String message;

    PermissionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
