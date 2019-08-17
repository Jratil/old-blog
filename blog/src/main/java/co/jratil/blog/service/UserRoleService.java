package co.jratil.blog.service;

import co.jratil.blog.pojo.dataobject.UserRole;

public interface UserRoleService {

    String findRole(String userId);

    boolean deleteRole(String userId);

    boolean updateRole(UserRole userRole);

    boolean addRole(UserRole userRole);
}
