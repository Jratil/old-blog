package co.jratil.blog.service.impl;

import co.jratil.blog.pojo.form.AuthorForm;
import co.jratil.blog.service.AuthorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorServiceImplTest {

    @Autowired
    AuthorService authorService;

    @Test
    public void register() {
        AuthorForm authorForm = new AuthorForm();
        authorForm.setAuthorAccount("12345");
        authorForm.setAuthorName("2号猪");
        authorForm.setAuthorPassword("123123");
        boolean b = authorService.register(authorForm);
        Assert.assertTrue(b);
    }

    @Test
    public void login() {
        authorService.login("123321", "123123", "127.0.0.1");
    }
}