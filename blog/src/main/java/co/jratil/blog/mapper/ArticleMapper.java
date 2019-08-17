package co.jratil.blog.mapper;

import co.jratil.blog.pojo.dataobject.Article;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(String articleId);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String articleId);

    List<Article> selectAll(String authorId);

    Integer selectLike(String articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    int updateArticleLike(Integer articleLike);
}