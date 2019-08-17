package co.jratil.blog.service.impl;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.mapper.ArticleMapper;
import co.jratil.blog.pojo.dataobject.Article;
import co.jratil.blog.pojo.dataobject.ArticleCategory;
import co.jratil.blog.pojo.dataobject.Author;
import co.jratil.blog.pojo.dto.ArticleDTO;
import co.jratil.blog.service.*;
import co.jratil.blog.utils.IdGenerateUtil;
import co.jratil.blog.constant.RedisConstant;
import co.jratil.blog.exception.ArticleException;
import co.jratil.blog.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl extends SecurityService implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @Transactional
    @Override
    public void addArticle(ArticleDTO articleDTO) {
        articleDTO.setAuthorId(SessionUtil.getAuthorId());
        // 判断权限
        this.authorityCheck(articleDTO.getAuthorId());

        // 根据id获取到用户和类目的名字，service中可以判断该用户或者类目是否存在
        ArticleCategory category = categoryService.findCategoryById(articleDTO.getCategoryId());
        Author author = authorService.findAuthorById(articleDTO.getAuthorId());

        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setArticleId(IdGenerateUtil.generateId(RedisConstant.ARTICLE_ID));
        article.setCategoryName(category.getCategoryName());
        article.setAuthorName(author.getAuthorName());

        int result = articleMapper.insertSelective(article);
        if (result != 1) {
            log.error("【文章服务】添加文章出错");
            throw new ArticleException(ResultEnum.ADD_ARTICLE_ERROR);
        }
    }

    @CacheEvict(value = "article", key = "'article_'+#articleId")
    @Transactional
    @Override
    public void deleteArticle(String articleId) {
        // 判断权限
        ArticleDTO articleDTO = findArticleById(articleId);
        this.authorityCheck(articleDTO.getAuthorId());

        int result = articleMapper.deleteByPrimaryKey(articleId);
        if (result == 0) {
            log.error("【文章服务】删除文章出错，文章不存在，articleId={}", articleId);
            throw new ArticleException(ResultEnum.ARTICLE_NOT_EXIST);
        }
    }

    @CacheEvict(value = "article", key = "'article_'+#articleDTO.articleId")
    @Transactional
    @Override
    public void modifyArticle(ArticleDTO articleDTO) {
        // 判断权限
        this.authorityCheck(articleDTO.getAuthorId());

        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);

    }

    @Cacheable(value = "article", key = "'article_'+#articleId")
    @Override
    public ArticleDTO findArticleById(String articleId) {

        Article article = articleMapper.selectByPrimaryKey(articleId);

        if (article == null) {
            log.error("【文章服务】查询文章出错，文章不存在，articleId={}", articleId);
            throw new ArticleException(ResultEnum.ARTICLE_NOT_EXIST);
        }

        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);

        return articleDTO;
    }

    /**
     * 不反悔文章的内容，只返回所有文章的标题所属类目等
     *
     * @param authorId 用户id
     * @return 查询到的类目
     */
    @Override
    public List<ArticleDTO> findAllArticle(String authorId) {

        List<Article> articleList = articleMapper.selectAll(authorId);
        System.out.println(articleList.get(0).getArticleContent());
        List<ArticleDTO> articleDTOList = articleList.stream()
                .map(item -> {
                    ArticleDTO dto = new ArticleDTO();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                })
                .collect(Collectors.toList());

//        if (CollectionUtils.isEmpty(articleList)) {
//            log.error("【文章服务】查询文章出错，该用户没有文章，authorId={}", authorId);
//            throw new ArticleException(ResultEnum.AUTHOR_NOT_ARTICLE);
//        }

        return articleDTOList;
    }

    /**
     * 增加文章喜欢数
     *
     * @param articleId 文章id
     * @return 点击之后的喜欢的数量
     */
    @Cacheable(value = "article", key = "'article_'+#articleId")
    @Transactional
    @Override
    public synchronized Integer addArticleLike(String articleId) {

        Integer articleLike = selectLike(articleId);
        articleLike = articleLike + 1;

        articleMapper.updateArticleLike(articleLike);

        return articleLike;
    }


    /**
     * 减少文章喜欢数
     *
     * @param articleId 文章id
     * @return 点击之后的喜欢的数量
     */
    @Cacheable(value = "article", key = "'article_'+#articleId")
    @Transactional
    @Override
    public synchronized Integer reduceArticleLike(String articleId) {

        Integer articleLike = selectLike(articleId);
        articleLike = articleLike - 1;

        articleMapper.updateArticleLike(articleLike);

        return articleLike;
    }

    private Integer selectLike(String articleId) {
        Integer articleLike = articleMapper.selectLike(articleId);
        if (articleLike == null) {
            log.error("【文章服务】增加喜欢出错，文章不存在，articleId={}", articleId);
            throw new ArticleException(ResultEnum.ARTICLE_NOT_EXIST);
        }
        return articleLike;
    }
}
