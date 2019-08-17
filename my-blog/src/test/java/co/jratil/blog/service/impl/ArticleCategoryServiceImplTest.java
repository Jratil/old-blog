package co.jratil.blog.service.impl;

import co.jratil.blog.pojo.dataobject.ArticleCategory;
import co.jratil.blog.service.ArticleCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleCategoryServiceImplTest {

    @Autowired
    ArticleCategoryService categoryService;

    @Test
    public void addCategory() {
//        ArticleCategory category = categoryService.addCategory("我的");

//        Assert.assertNotNull(category);
    }

    @Test
    public void updateCategory() {

        categoryService.updateCategory("我的博客", "15595631");
    }

    @Test
    public void deleteCategory() {
    }

    @Test
    public void findCategoryById() {
        ArticleCategory category = categoryService.findCategoryById("15595607");

        Assert.assertNotNull(category);
    }

    @Test
    public void findCategoryByNameAndAuthorId() {
    }

    @Test
    public void findAllCategory() {

        List<ArticleCategory> categoryList = categoryService.findAllCategory("15043234");

        Assert.assertNotNull(categoryList);
    }
}