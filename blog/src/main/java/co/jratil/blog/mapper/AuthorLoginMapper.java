package co.jratil.blog.mapper;

import co.jratil.blog.pojo.dataobject.AuthorLogin;

public interface AuthorLoginMapper {
    int insertOrUpdate(AuthorLogin record);

    int insertSelective(AuthorLogin record);
}