package co.jratil.blog.mapper;

import co.jratil.blog.pojo.dataobject.AuthorFollow;

public interface AuthorFollowMapper {
    int insert(AuthorFollow record);

    int insertSelective(AuthorFollow record);
}