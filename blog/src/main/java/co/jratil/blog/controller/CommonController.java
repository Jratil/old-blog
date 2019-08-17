package co.jratil.blog.controller;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.exception.ArticleException;
import co.jratil.blog.pojo.dto.ArticleDTO;
import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.service.ArticleService;
import co.jratil.blog.utils.ResultVOUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class CommonController {

    @Autowired
    private ArticleService articleService;

    @ResponseBody
    @RequestMapping("/watch")
    public ResultVO watchArticle(String articleId) {

        if (StringUtils.isEmpty(articleId)) {
            log.error("【文章操作】查询文章出错，文章id不能为空，articleId={}", articleId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        ArticleDTO articleDTO = articleService.findArticleById(articleId);
        return ResultVOUtil.success(articleDTO);
    }

    @ResponseBody
    @RequestMapping({"/watch/{page}/{count}", "/watch/{page}"})
    public ResultVO watchAllArticle(@RequestParam("authorId") String authorId,
                                    @PathVariable("page") Integer page,
                                    @PathVariable(value = "count", required = false) Integer count) {

        if (StringUtils.isEmpty(authorId)) {
            log.error("【文章操作】查询文章出错，用户id不能为空，authorId={}", authorId);
            throw new ArticleException(ResultEnum.PARAM_ERROR);
        }

        if (count == null) {
            count = 10;
        }
        System.out.println(count);

        PageHelper.startPage(page, count);
        List<ArticleDTO> articleDTOList = articleService.findAllArticle(authorId);

        System.out.println("查询到的数量为：" + articleDTOList.size());

        return ResultVOUtil.success(articleDTOList);
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "/author/login";
    }

    @RequestMapping("/register")
    public String registerPage() {
        return "/author/register";
    }

    @RequestMapping("/")
    public String mainPage() {
        return "/article/main_page";
    }

    @RequestMapping("/write")
    public String write() {
        return "/article/write";
    }
}
