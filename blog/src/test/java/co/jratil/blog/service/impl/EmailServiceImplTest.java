package co.jratil.blog.service.impl;

import co.jratil.blog.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceImplTest {

    @Autowired
    EmailService emailService;

    private String toMail = "2283082247@qq.com";

    @Test
    public void sendTextMail() {
        emailService.sendTextMail(toMail, "这是普通邮件", "hello");
    }

    @Test
    public void sendHtmlMail() {
        String content = "" +
                "<html>" +
                "<body>" +
                "<a href='http://google.com'>html</a>" +
                "</body>" +
                "</html>";

        emailService.sendHtmlMail(toMail, "这是一个HTML邮件", content);
    }

    @Test
    public void sendVerifyCode() {
        emailService.sendVerifyCode(toMail);
    }
}