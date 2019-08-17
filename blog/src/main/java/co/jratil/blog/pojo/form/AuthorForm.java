package co.jratil.blog.pojo.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用于接收前端=>
 * 1、注册
 * 2、忘记密码
 * 的表单输入
 */
@Data
public class AuthorForm implements Serializable {

    private static final long serialVersionUID = -7935390480768017742L;

    /**
     * 用户昵称
     */
    @JsonProperty("name")
    @Pattern(regexp = "[a-zA-Z_0-9\\u4e00-\\u9fa5=+\\-*/()%]{2,15}", message = "昵称只能3-15位的字母数字中文_=+-*/()%组成", groups = {Register.class})
    private String authorName;

    /**
     * 用户账号
     */
    @JsonProperty("account")
    @Email(message = "邮箱格式不正确", groups = {Register.class, ForgetPassword.class})
    private String authorAccount;

    /**
     * 用户密码
     */
    @JsonProperty("password")
    @Pattern(regexp = "[a-zA-Z0-9._\\-]{6,15}", message = "密码只能6-15位的字母数字._-组成", groups = {Register.class, ForgetPassword.class})
    private String authorPassword;

    /**
     * 验证码
     */
    @JsonProperty("verify-code")
    @Pattern(regexp = "[0-9]{4}", message = "请输入4位数验证码", groups = {Register.class, ForgetPassword.class})
    private String verifyCode;

    public interface Register {
    }

    public interface ForgetPassword {
    }
}
