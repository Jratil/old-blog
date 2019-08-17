package co.jratil.blog.controller;

import co.jratil.blog.pojo.vo.ResultVO;
import co.jratil.blog.service.EmailService;
import co.jratil.blog.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send_code")
    public ResultVO sendVerifyCode(@RequestParam("email") String email) {
        emailService.sendVerifyCode(email);
        return ResultVOUtil.success();
    }
}
