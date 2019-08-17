package co.jratil.blog.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class AuthorVO {

    /**
     * 用户昵称
     */
    @Pattern(regexp = "[a-zA-Z_\\u4e00-\\u9fa5=+\\-*/()%]{3,15}", message = "只能3-15位的字母数字中文_=+-*/()%组成")
    private String authorName;

    /**
     * 用户账号
     */
    @Email(message = "邮箱格式不正确")
    private String authorAccount;

    /**
     * 用户密码
     */
    @Pattern(regexp = "[a-zA-Z0-9._\\-]{6,15}", message = "只能6-15位的字母数字._-组成")
    private String authorPassword;
}
