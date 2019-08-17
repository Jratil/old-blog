package co.jratil.blog.service.impl;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.service.EmailService;
import co.jratil.blog.service.RedisService;
import co.jratil.blog.utils.VerifyCodeUtil;
import co.jratil.blog.constant.EmailConstant;
import co.jratil.blog.exception.AuthorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Value("${mail.from-mail.addr}")
    private String fromMail;

    @Value("${mail.verify-code.title}")
    private String verifyCodeTitle;

    @Value("${mail.verify-code.content}")
    private String verifyCodeContent;

    /**
     * 发送普通文本邮件
     *
     * @param toMail  接收邮箱
     * @param title   标题
     * @param content 内容
     */
    @Override
    public void sendTextMail(String toMail, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(toMail);
        message.setSubject(title);
        message.setText(content);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【发送邮件】发送邮件出错,接收mail={}", toMail);
        }
    }

    /**
     * 发送 html 邮件
     *
     * @param toMail  接收邮箱
     * @param title   标题
     * @param content 内容
     */
    @Override
    public void sendHtmlMail(String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromMail);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【发送邮件】发送邮件出错,接收mail={}", toMail);
        }
    }

    /**
     * 发送验证码，标题和内容已经在配置文件定义，只要到时候生成验证码发送
     * 生成验证码后储存在redis，设置 120s 过期时间
     *
     * @param toMail 接收邮箱
     */
    @Override
    public void sendVerifyCode(String toMail) {
        String verifyCode = VerifyCodeUtil.generateCode();
        // 先从redis获取验证码，如果存在则发送频繁，不存在则将验证码存入redis并且设置过期时间
        Object result = redisService.get(EmailConstant.REDIS_VERIFY_CODE_KEY + toMail);

        // 判断是否之前已经发送过验证码并且验证码没过期
        if (result != null) {
            log.error("【邮件服务】验证码发送次数过快，email={}", toMail);
            throw new AuthorException(ResultEnum.VERIFY_CODE_QUICKLY);
        }

        sendTextMail(toMail, verifyCodeTitle, verifyCodeContent + verifyCode);
        // 验证码发送完再设置时间，如果在之前设置，发送验证码需要时间，则会倒是验证码提前过期
        redisService.set(EmailConstant.REDIS_VERIFY_CODE_KEY + toMail, verifyCode, EmailConstant.VERIFY_CODE_EXPIRE);
    }
}
