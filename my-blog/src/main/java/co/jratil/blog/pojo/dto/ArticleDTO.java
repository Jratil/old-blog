package co.jratil.blog.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = 2518198230132210040L;

    /**
     * 文章id
     */
    private String articleId;

    /**
     * 文章标题
     */
    @Length(min = 1, max = 20, message = "标题字数不符合")
    private String articleTitle;

    /**
     * 文章是否对他人可见，默认：0
     * 0：可见
     * 1：不可见
     */
    @JsonIgnore
    private Integer articleVisible;

    /**
     * 作者的id
     */
    private String authorId;

    /**
     * 类目的id
     */
    @NotNull
    private String categoryId;


    /**
     * 文章内容
     */
    @NotNull
    private String articleContent;
}
