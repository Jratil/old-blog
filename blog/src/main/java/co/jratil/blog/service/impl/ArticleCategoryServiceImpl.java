package co.jratil.blog.service.impl;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.mapper.ArticleCategoryMapper;
import co.jratil.blog.mapper.AuthorMapper;
import co.jratil.blog.pojo.dataobject.ArticleCategory;
import co.jratil.blog.service.ArticleCategoryService;
import co.jratil.blog.service.SecurityService;
import co.jratil.blog.utils.IdGenerateUtil;
import co.jratil.blog.constant.RedisConstant;
import co.jratil.blog.exception.ArticleException;
import co.jratil.blog.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class ArticleCategoryServiceImpl extends SecurityService implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper categoryMapper;

    @Transactional
    @Override
    public ArticleCategory addCategory(String categoryName, String authorId) {
        // 权限判断
        this.authorityCheck(authorId);

        // 判断用户是否存在，不存在则抛出异常
//        Author author = authorMapper.findAuthorById(authorId);
//        if (author == null) {
//            log.error("【类目服务】添加类目出错，该用户不存在，authorId={}", authorId);
//            throw new ArticleException(ResultEnum.AUTHOR_NOT_EXIST);
//        }

        // 数据插入
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setCategoryId(IdGenerateUtil.generateId(RedisConstant.CATEGORY_ID));
        articleCategory.setCategoryName(categoryName);
        articleCategory.setAuthorId(authorId);

        // 数据库插入使用ignore，如果名字存在则会返回 0
        ArticleCategory category = categoryMapper.selectCategoryByNameAnAuthorId(categoryName, authorId);
        if (category != null) {
            log.error("【类目服务】添加类目出错，类目已存在，categoryName={}, authorId={}", categoryName, authorId);
            throw new ArticleException(ResultEnum.CATEGORY_EXIST);
        }
        categoryMapper.insert(articleCategory);

        return articleCategory;
    }

    @Transactional
    @CacheEvict(value = "category", key = "'category_'+#categoryId")
    @Override
    public void updateCategory(String categoryName, String categoryId) {

        ArticleCategory articleCategory = this.findCategoryById(categoryId);
        // 权限判断
        authorityCheck(articleCategory.getAuthorId());

        articleCategory.setCategoryName(categoryName);
        categoryMapper.updateByPrimaryKeySelective(articleCategory);
    }

    @CacheEvict(value = "category", key = "'category_'+#categoryId")
    @Transactional
    @Override
    public void deleteCategory(String categoryId) {
        ArticleCategory category = this.findCategoryById(categoryId);
        // 权限判断
        authorityCheck(category.getAuthorId());

        categoryMapper.deleteByPrimaryKey(categoryId);
    }

    @Cacheable(value = "category", key = "'category_'+#categoryId")
    @Override
    public ArticleCategory findCategoryById(String categoryId) {
        ArticleCategory category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null) {
            log.error("【类目管理】查询类目不存在，categoryId={}", categoryId);
            throw new ArticleException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        return category;
    }

    @Override
    public ArticleCategory findCategoryByNameAndAuthorId(String categoryName, String authorId) {
        ArticleCategory category = categoryMapper.selectCategoryByNameAnAuthorId(categoryName, authorId);
        if (category == null) {
            log.error("【类目管理】查询类目不存在，categoryName={}", categoryName);
            throw new ArticleException(ResultEnum.CATEGORY_NOT_EXIST);
        }

        return category;
    }

    @Override
    public List<ArticleCategory> findAllCategory(String authorId) {
        List<ArticleCategory> categoryList = categoryMapper.selectAllCategory(authorId);
        if (CollectionUtils.isEmpty(categoryList)) {
            log.error("【类目管理】查询所有类目出错, 不存在，authorId={}", authorId);
            throw new ArticleException(ResultEnum.CATEGORY_NOT_EXIST);
        }

        return categoryList;
    }
}
