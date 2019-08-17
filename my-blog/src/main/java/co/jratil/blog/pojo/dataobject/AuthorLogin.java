package co.jratil.blog.pojo.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthorLogin implements Serializable {

    private static final long serialVersionUID = 2407206228169953267L;

    /**
     * 用户的id
     */
    private String authorId;

    /**
     * 最后登录的时间
     */
    private Date loginTime;

    /**
     * 最后登录的ip
     */
    private String loginIp;

    public AuthorLogin(String authorId, Date loginTime, String loginIp) {
        this.authorId = authorId;
        this.loginTime = loginTime;
        this.loginIp = loginIp;
    }

    public AuthorLogin() {
        super();
    }
}