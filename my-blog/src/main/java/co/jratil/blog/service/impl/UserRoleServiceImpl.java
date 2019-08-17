package co.jratil.blog.service.impl;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.exception.AuthorityException;
import co.jratil.blog.mapper.UserRoleMapper;
import co.jratil.blog.pojo.dataobject.UserRole;
import co.jratil.blog.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date 2019-08-14 9:35
 */
@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Cacheable(value = "role", key = "'role_' + #userId")
    @Transactional
    @Override
    public String findRole(String userId) {
        UserRole userRole = userRoleMapper.selectByPrimaryKey(userId);
        if (userRole == null) {
            log.error("【权限服务】该用户的角色不存在");
            throw new AuthorityException(ResultEnum.ROLE_NOT_EXIST);
        }
        String role = userRole.getRole();
        return role;
    }

    @CacheEvict(value = "role", key = "'role_' + #userId")
    @Transactional
    @Override
    public boolean deleteRole(String userId) {
        findRole(userId);
        int i = userRoleMapper.deleteByPrimaryKey(userId);
        return i > 0;
    }

    @CacheEvict(value = "role", key = "'role_' + #userId")
    @Transactional
    @Override
    public boolean updateRole(UserRole userRole) {
        findRole(userRole.getAuthorId());
        int i = userRoleMapper.updateByPrimaryKey(userRole);
        return i > 0;
    }

    @Transactional
    @Override
    public boolean addRole(UserRole userRole) {
        int i = userRoleMapper.insert(userRole);
        return i > 0;
    }
}
