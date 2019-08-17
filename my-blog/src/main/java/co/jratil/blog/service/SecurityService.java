package co.jratil.blog.service;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.enums.UserRoleEnum;
import co.jratil.blog.exception.AuthorityException;
import co.jratil.blog.service.impl.UserRoleServiceImpl;
import co.jratil.blog.utils.ApplicationContextProvider;
import co.jratil.blog.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 用来被继承，判断权限
 */
@Slf4j
public abstract class SecurityService {

    protected void authorityCheck(String authorId) {
        UserRoleService userRoleService = ApplicationContextProvider.getBean(UserRoleServiceImpl.class);
        String role = userRoleService.findRole(SessionUtil.getAuthorId());
        if (!authority(authorId, role)) {
            log.error("【文章服务】用户权限不够");
            throw new AuthorityException(ResultEnum.NOT_AUTHORITY);
        }
    }

    private boolean authority(String authorId, String userRole) {

        // 如果是管理员，并且已经登陆了
        // 或者已经登陆了，并且查询到需要操作的内容的 authorId所有者和登录的 authorId一样
        // 则有权限操作
        String sAuthorId = SessionUtil.getAuthorId();
        if (UserRoleEnum.ROLE_ADMIN.getUserRole().equals(userRole)
                && !StringUtils.isEmpty(sAuthorId)) {
            return true;
        } else if (!StringUtils.isEmpty(sAuthorId) && sAuthorId.equals(authorId)) {
            return true;
        }
        return false;
    }
}
