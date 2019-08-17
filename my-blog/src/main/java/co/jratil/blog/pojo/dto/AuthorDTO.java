package co.jratil.blog.pojo.dto;

import co.jratil.blog.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthorDTO implements Serializable {

    private static final long serialVersionUID = 6377861544235312213L;

    @JsonProperty("id")
    private String authorId;

    /**
     * 用户昵称
     */
    @JsonProperty("name")
    private String authorName;

    /**
     * 用户账号
     */
    @JsonProperty("account")
    private String authorAccount;

    /**
     * 用户密码
     */
    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String authorPassword;

    /**
     * 用户性别
     * 0：未知
     * 1：男
     * 2：女
     */
    @JsonProperty("gender")
    private Integer authorGender;

    /**
     * 用户出生日期，默认为创建账号创建时间
     */
    @JsonProperty("birthday")
    private Date authorBirthday;

    /**
     * 用户头像，存放头像图片的url
     */
    @JsonProperty("avatar")
    private String authorAvatar;

    /**
     * 账号创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    public AuthorDTO() {
    }

    public AuthorDTO(String authorName, String authorAccount, String authorPassword, Integer authorGender, Date authorBirthday, String authorAvatar, Date createTime) {
        this.authorName = authorName;
        this.authorAccount = authorAccount;
        this.authorPassword = authorPassword;
        this.authorGender = authorGender;
        this.authorBirthday = authorBirthday;
        this.authorAvatar = authorAvatar;
        this.createTime = createTime;
    }
}
