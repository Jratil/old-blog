package co.jratil.blog.mapper;

import co.jratil.blog.pojo.dataobject.ArticleCategory;

import java.util.List;

public interface ArticleCategoryMapper {
    int deleteByPrimaryKey(String categoryId);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(String categoryId);

    ArticleCategory selectCategoryByNameAnAuthorId(String categoryName, String authorId);

    List<ArticleCategory> selectAllCategory(String authorId);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);
}