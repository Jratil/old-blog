package co.jratil.blog.enums;

public enum UserRoleEnum {

    /** 权限 */
    ROLE_USER("ROLE_USER","用户"),
    ROLE_ADMIN("ROLE_ADMIN", "管理员");

    /** 权限 */
    String userRole;

    /** 权限说明 */
    String message;

    UserRoleEnum(String userRole, String message) {
        this.userRole = userRole;
        this.message = message;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getMessage() {
        return message;
    }
}
