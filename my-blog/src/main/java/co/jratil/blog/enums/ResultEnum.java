package co.jratil.blog.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {

    SUCCESS(0, "成功"),
    LOGIN_ERROR(1, "登录失败"),
    PASSWORD_ERROR(2, "登录失败，密码错误"),
    REGISTER_ERROR(3,"注册失败"),
    UPDATE_ERROR(4, "更新失败"),
    AUTHOR_EXIST(5, "用户或昵称已经存在"),
    AUTHOR_NOT_EXIST(6, "用户不存在"),
    VERIFY_CODE_QUICKLY(7, "验证码发送次数过快"),
    VERIFY_NOT_EQUAL(8, "验证码错误"),
    PARAM_ERROR(9, "参数错误"),
    CATEGORY_NOT_EXIST(10, "类目不存在"),
    CATEGORY_EXIST(11, "类目已存在"),
    ARTICLE_NOT_EXIST(12, "文章不存在"),
    AUTHOR_NOT_ARTICLE(13, "用户没有文章"),
    ADD_ARTICLE_ERROR(14, "添加文章出错"),
    NOT_LOGIN(15, "没有登录"),
    FILE_NOT_EXIST(16, "文件为空"),
    SERVER_FILE_DELETE_ERROR(17, "删除服务器文件出错"),
    NOT_AUTHORITY(18, "没有权限"),
    ROLE_NOT_EXIST(19, "用户角色不存在");



    Integer code;

    String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
