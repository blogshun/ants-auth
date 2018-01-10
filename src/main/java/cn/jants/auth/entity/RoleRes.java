package cn.jants.auth.entity;

import cn.jants.plugin.orm.Column;
import cn.jants.plugin.orm.Table;

import java.io.Serializable;

/**
 * (sys_role_res)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-13
 */
@Table(name = "sys_role_res")
public class RoleRes implements Serializable {

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 资源ID
     */
    @Column(name = "res_id")
    private Integer resId;

    public RoleRes() {
    }

    public RoleRes(Integer id) {
        this.roleId = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }


}