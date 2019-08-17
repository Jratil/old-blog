package co.jratil.blog.service;

import co.jratil.blog.pojo.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    void addArticle(ArticleDTO articleDTO);

    void deleteArticle(String articleId);

    void modifyArticle(ArticleDTO articleDTO);

    ArticleDTO findArticleById(String articleId);

    /**
     * 查找用户所有文章，但不包括文章的内容
     * @param authorId 用户id
     * @return 查找到的文章
     */
    List<ArticleDTO> findAllArticle(String authorId);

    /**
     * 喜欢量增加
     * @param articleId 文章id
     * @return 返回点赞后的喜欢的数量
     */
    Integer addArticleLike(String articleId);

    /**
     * 取消喜欢
     * @param articleId 文章id
     * @return  返回取消后的喜欢的数量
     */
    Integer reduceArticleLike(String articleId);
}
