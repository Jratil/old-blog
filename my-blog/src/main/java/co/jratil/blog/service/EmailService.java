package co.jratil.blog.service;

import javax.servlet.http.HttpSession;

public interface EmailService {

    /**
     * 发送普通文本邮件
     * @param toMail 接收邮箱
     * @param title 标题
     * @param content 内容
     */
    void sendTextMail(String toMail, String title, String content);

    /**
     * 发送 html 邮件
     * @param toMail 接收邮箱
     * @param title 标题
     * @param content 内容
     */
    void sendHtmlMail(String toMail, String title, String content);

    /**
     * 发送验证码，标题和内容已经在配置文件定义，只要到时候生成验证码发送
     * @param toMail 接收邮箱
     */
    void sendVerifyCode(String toMail);
}
