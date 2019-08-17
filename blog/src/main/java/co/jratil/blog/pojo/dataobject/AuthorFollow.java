package co.jratil.blog.pojo.dataobject;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthorFollow implements Serializable {

    private static final long serialVersionUID = 3845700596434501591L;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 被关注人的id
     */
    private String followId;

    /**
     * 被关注人的昵称
     */
    private String followName;

    public AuthorFollow(String authorId, String followId, String followName) {
        this.authorId = authorId;
        this.followId = followId;
        this.followName = followName;
    }

    public AuthorFollow() {
        super();
    }
}