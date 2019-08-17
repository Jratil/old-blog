package co.jratil.blog.service;

import co.jratil.blog.pojo.dataobject.ArticleCategory;

import java.util.List;

public interface ArticleCategoryService {

    ArticleCategory addCategory(String categoryName, String authorId);

    void updateCategory(String categoryName, String categoryId);

    void deleteCategory(String categoryId);

    ArticleCategory findCategoryById(String categoryId);

    ArticleCategory findCategoryByNameAndAuthorId(String categoryName, String authorId);

    List<ArticleCategory> findAllCategory(String authorId);
}
