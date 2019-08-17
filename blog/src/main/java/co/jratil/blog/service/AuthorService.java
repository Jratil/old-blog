package co.jratil.blog.service;

import co.jratil.blog.pojo.dataobject.Author;
import co.jratil.blog.pojo.dto.AuthorDTO;
import co.jratil.blog.pojo.form.AuthorForm;

import java.util.List;

public interface AuthorService {

    AuthorDTO login(String account, String password, String ipAddr);

    boolean register(AuthorForm authorForm);

    boolean updateAuthor(AuthorDTO authorDTO);

    boolean forgetPassword(AuthorForm authorForm);

    void deleteAuthor(String account);

    Author findAuthorByAccount(String account);

    Author findAuthorById(String authorId);

    List<Author> findAllAuthor();
}
