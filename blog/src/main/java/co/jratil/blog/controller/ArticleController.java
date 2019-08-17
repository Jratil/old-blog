package co.jratil.blog.controller;

import co.jratil.blog.aspect.LoginCheck;
import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.exception.ArticleException;
import co.jratil.blog.exception.LoginException;
import co.jratil.blog.pojo.dto.ArticleDTO;
import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.service.ArticleService;
import co.jratil.blog.utils.ResultVOUtil;
import co.jratil.blog.utils.SessionUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@LoginCheck
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/main_page")
    public ResultVO mainPage(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = SessionUtil.getHttpSession();
        String authorId = (String) session.getAttribute("authorId");
        // TODO 测试数据，把authorId写明了
        authorId = "15626630";

        PageHelper.startPage(1, 10);
        List<ArticleDTO> allArticle = articleService.findAllArticle(authorId);

        return ResultVOUtil.success(allArticle);
    }
    
    @PostMapping("/write")
    public ResultVO write(@RequestBody @Validated ArticleDTO articleDTO, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            log.error("【文章操作】添加文章，文章参数不正确，articleDTO={}", articleDTO);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        articleService.addArticle(articleDTO);

        return ResultVOUtil.success();
    }

    @DeleteMapping("/del")
    public ResultVO del(@RequestParam("articleId") String articleId) {

        if (StringUtils.isEmpty(articleId)) {
            log.error("【文章操作】删除文章出错，文章id不能为空，articleId={}", articleId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        articleService.deleteArticle(articleId);
        return ResultVOUtil.success();
    }

    @RequestMapping("/find")
    public ResultVO findArticle(@RequestParam("articleId") String articleId) {
        if (StringUtils.isEmpty(articleId)) {
            log.error("【文章操作】查询文章出错，文章id不能为空，articleId={}", articleId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        ArticleDTO articleDTO = articleService.findArticleById(articleId);

        return ResultVOUtil.success(articleDTO);
    }

    @RequestMapping({"/find/{page}/{count}", "/find/{page}"})
    public ResultVO findAllArticle(@RequestParam("authorId") String authorId,
                                   @PathVariable("page") Integer page,
                                   @PathVariable(value = "count", required = false) Integer count) {
        System.out.println(authorId + page + count);

        if (StringUtils.isEmpty(authorId)) {
            log.error("【文章操作】查询文章出错，用户id不能为空，authorId={}", authorId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        if (count == null) {
            count = 10;
        }

        PageHelper.startPage(page, count);
        List<ArticleDTO> articleDTOList = articleService.findAllArticle(authorId);

        System.out.println("查询到的数量为：" + articleDTOList.size());

        return ResultVOUtil.success(articleDTOList);
    }

    @PostMapping("/like/add")
    public ResultVO addLike(String articleId) {
        if (StringUtils.isEmpty(articleId)) {
            log.error("【文章操作】增加喜欢出错，文章id不能为空，articleId={}", articleId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        Integer articleLike = articleService.addArticleLike(articleId);

        return ResultVOUtil.success(articleLike);
    }

    @DeleteMapping("/like/reduce")
    public ResultVO reduceLike(String articleId) {
        if (StringUtils.isEmpty(articleId)) {
            log.error("【文章操作】增加喜欢出错，文章id不能为空，articleId={}", articleId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        Integer articleLike = articleService.reduceArticleLike(articleId);

        return ResultVOUtil.success(articleLike);
    }
}

