package co.jratil.blog.mapper;

import co.jratil.blog.pojo.dataobject.Author;

import java.util.List;

public interface AuthorMapper {
    int deleteAuthor(String account);

    int addAuthor(Author author);

    int addAuthorSelective(Author author);

    Author findAuthorById(String authorId);

    Author findAuthorByAccount(String authorAccount);

    List<Author> findAllAuthor();

    int updateByAuthor(Author author);

    int updateByAuthorSelective(Author author);
}