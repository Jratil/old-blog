package co.jratil.blog.service.impl;

import co.jratil.blog.pojo.dto.ArticleDTO;
import co.jratil.blog.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceImplTest {

    @Autowired
    ArticleService articleService;

    @Test
    public void addArticle() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setAuthorId("15626680");
        articleDTO.setArticleContent("测试内容");
        articleDTO.setArticleTitle("测试标题");
        articleService.addArticle(articleDTO);

    }

    @Test
    public void deleteArticle() {
    }

    @Test
    public void findArticleById() {
    }

    @Test
    public void findAllArticle() {
    }

    @Test
    public void addArticleLike() {
    }

    @Test
    public void reduceArticleLike() {
    }
}