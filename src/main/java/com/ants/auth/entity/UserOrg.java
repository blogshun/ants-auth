package com.ants.auth.entity;


import com.ants.common.annotation.action.Entity;
import com.ants.plugin.orm.Column;
import com.ants.plugin.orm.Table;

import java.io.Serializable;

/**
 * (sys_user_org)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-13
 */
@Entity
@Table(name = "sys_user_org")
public class UserOrg implements Serializable {

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 组织ID
     */
    @Column(name = "org_id")
    private Integer orgId;


    private String orgName;

    public UserOrg() {
    }

    public UserOrg(Long id) {
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}