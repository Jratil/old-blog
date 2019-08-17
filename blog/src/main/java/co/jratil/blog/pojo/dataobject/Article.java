package co.jratil.blog.pojo.dataobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Article implements Serializable {

    private static final long serialVersionUID = -4349660067778454895L;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章创建时间
     * 默认：当前时间
     */
    private Date createTime;

    /**
     * 文章修改时间
     * 默认：数据库更新时间
     */
    private Date modifyTime;

    /**
     * 文章是否对他人可见，默认：0
     * 0：可见
     * 1：不可见
     */
    private Integer articleVisible;

    /**
     * 文章的点赞（喜欢）数
     * 默认：0
     */
    private Integer articleLike;

    /**
     * 作者的id
     */
    private String authorId;

    /**
     * 作者的昵称
     */
    private String authorName;

    /**
     * 类目的id
     */
    private String categoryId;

    /**
     * 类目的名称
     */
    private String categoryName;

    /**
     * 文章内容
     */
    private String articleContent;

    public Article(String articleId, String articleTitle, Date createTime, Date modifyTime, Integer articleVisible, Integer articleLike, String authorId, String authorName, String categoryId, String categoryName, String articleContent) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.articleVisible = articleVisible;
        this.articleLike = articleLike;
        this.authorId = authorId;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.articleContent = articleContent;
    }

    public Article() {
        super();
    }
}