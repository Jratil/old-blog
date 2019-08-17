package co.jratil.blog.pojo.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Author implements Serializable {

    private static final long serialVersionUID = 1692928029247361088L;

    /**
     * 用户id
     */
    private String authorId;

    /**
     * 用户昵称
     */
    private String authorName;

    /**
     * 用户账号
     */
    private String authorAccount;

    /**
     * 用户密码
     */
    private String authorPassword;

    /**
     * 用户性别，默认：0
     * 0：未知
     * 1：男
     * 2：女
     */
    private Integer authorGender;

    /**
     * 用户出生日期
     * 默认：当前时间
     */
    private Date authorBirthday;

    /**
     * 用户头像，存放头像图片的url
     * 默认："null"（字符串）
     */
    private String authorAvatar;

    /**
     * 用户的权限，默认：0
     * 0：普通用户
     * 1：管理员
     */
    private Integer authorPermission;

    /**
     * 账号创建时间
     * 默认：当前时间
     */
    private Date createTime;

    public Author(String authorId, String authorName, String authorAccount, String authorPassword, Integer authorGender, Date authorBirthday, String authorAvatar, Integer authorPermission, Date createTime) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorAccount = authorAccount;
        this.authorPassword = authorPassword;
        this.authorGender = authorGender;
        this.authorBirthday = authorBirthday;
        this.authorAvatar = authorAvatar;
        this.authorPermission = authorPermission;
        this.createTime = createTime;
    }

    public Author() {
        super();
    }
}