package cn.jants.auth.entity;


import cn.jants.common.annotation.action.Entity;
import cn.jants.plugin.orm.Column;
import cn.jants.plugin.orm.Id;
import cn.jants.plugin.orm.Table;

import java.io.Serializable;

/**
 * 角色(sys_role)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-13
 */
@Entity
@Table(name = "sys_role")
public class Role implements Serializable {

    /**  */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}