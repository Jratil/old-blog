package co.jratil.blog.controller;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.service.FileUploadService;
import co.jratil.blog.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2019-08-10 22:32
 */
@Slf4j
@RequestMapping("/server")
@RestController
public class ServerFileController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 文件上传
     * editormd-image-file 是前端editor.md穿的的默认参数名字
     *
     * @param file 前端传入的图片
     * @param request request
     * @param response respinse
     * @return 前端所需的必须的格式
     */
    @RequestMapping("/upload/pic")
    public Object uploadImage(@RequestParam(value = "editormd-image-file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String realFileName = file.getOriginalFilename();
        if (realFileName == null) {
            log.error("【上传图片错误】图片文件为null");
            return ResultVOUtil.error(ResultEnum.FILE_NOT_EXIST.getCode(), ResultEnum.FILE_NOT_EXIST.getMessage());
        }

        String suffix = realFileName.substring(realFileName.lastIndexOf("."));

        Map<String, Object> result = new HashMap<>(5);
        try {
            result = fileUploadService.uploadImage(file.getInputStream(), suffix);
        } catch (Exception e) {
            log.error("上传文件到七牛云出错");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/file/delete/{key}")
    public ResultVO deleteServerFile(@PathVariable("key") String key) {

        if (StringUtils.isEmpty(key)) {
            log.error("【删除文件错误】文件名称为空");
            return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage());
        }

        ResultVO result = fileUploadService.delete(key);
        return  result;
    }
}
