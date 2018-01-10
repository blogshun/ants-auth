package cn.jants.auth.entity;

import cn.jants.plugin.orm.Column;
import cn.jants.plugin.orm.Table;

import java.io.Serializable;

/**
 * (sys_user_role)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-13
 */
@Table(name = "sys_user_role")
public class UserRole implements Serializable {

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Integer roleId;

    private String roleName;

    public UserRole() {
    }

    public UserRole(Long id) {
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}