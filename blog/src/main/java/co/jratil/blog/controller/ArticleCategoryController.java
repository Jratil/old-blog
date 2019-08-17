package co.jratil.blog.controller;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.exception.ArticleException;
import co.jratil.blog.pojo.dataobject.ArticleCategory;
import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.service.ArticleCategoryService;
import co.jratil.blog.utils.ResultVOUtil;
import co.jratil.blog.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class ArticleCategoryController {

    @Autowired
    private ArticleCategoryService categoryService;

    @PostMapping("/add")
    public ResultVO addCategory(@RequestParam("name") String categoryName, HttpServletRequest request) {

        if (StringUtils.isEmpty(categoryName)) {
            log.error("【类目管理】添加类目出错，参数错误，categoryName={}", categoryName);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        String authorId = SessionUtil.getAuthorId();
        categoryService.addCategory(categoryName, authorId);

        return ResultVOUtil.success();
    }

    @PostMapping("/update")
    public ResultVO modifyCategory(@RequestParam("name") String categoryName,@RequestParam("categoryId") String categoryId) {

        if (StringUtils.isEmpty(categoryName) || StringUtils.isEmpty(categoryId)) {
            log.error("【类目管理】修改类目出错，参数错误，categoryName={}, categoryId={}", categoryName, categoryId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        categoryService.updateCategory(categoryName, categoryId);

        return ResultVOUtil.success();
    }

    @DeleteMapping("/delete")
    public ResultVO deleteCategory(@RequestParam("categoryId") String categoryId) {

        if(StringUtils.isEmpty(categoryId)){
            log.error("【类目管理】删除类目出错，参数错误，categoryId={}", categoryId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        categoryService.deleteCategory(categoryId);

        return ResultVOUtil.success();
    }

    @RequestMapping("/find/id")
    public ResultVO findByCategoryId(@RequestParam("categoryId") String categoryId) {

        if(StringUtils.isEmpty(categoryId)){
            log.error("【类目管理】查询类目出错，参数错误，categoryId={}", categoryId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        ArticleCategory category = categoryService.findCategoryById(categoryId);
        return ResultVOUtil.success(category);
    }


    @RequestMapping("/find/name")
    public ResultVO findByCategoryNameAndAuthorId(@RequestParam("categoryName") String categoryName,
                                                  @RequestParam("authorId") String authorId) {

        if (StringUtils.isEmpty(categoryName) || StringUtils.isEmpty(authorId)) {
            log.error("【类目管理】查询类目出错，参数错误，categoryName={}, authorId={}", categoryName, authorId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        ArticleCategory category = categoryService.findCategoryByNameAndAuthorId(categoryName, authorId);

        return ResultVOUtil.success(category);
    }

    @RequestMapping("/find/all")
    public ResultVO findAllCategory(String authorId) {

        if(StringUtils.isEmpty(authorId)){
            log.error("【类目管理】查询全部类目出错，参数错误，authorId={}", authorId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        List<ArticleCategory> categorieList = categoryService.findAllCategory(authorId);

        return ResultVOUtil.success(categorieList);
    }
}
