package com.ants.auth.entity;


import com.ants.common.annotation.action.Entity;
import com.ants.plugin.orm.Column;
import com.ants.plugin.orm.Id;
import com.ants.plugin.orm.Table;

import java.io.Serializable;

/**
 * 系统组织机构表(sys_org)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-13
 */
@Entity
@Table(name = "sys_org")
public class Org implements Serializable {

    /**
     * 关键字
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 排序
     */
    @Column(name = "ipx")
    private Integer ipx;

    /**
     * 父ID
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * 0、添加 1、系统
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private java.util.Date createTime;

    public Org() {
    }

    public Org(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public Integer getIpx() {
        return ipx;
    }

    public void setIpx(Integer ipx) {
        this.ipx = ipx;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }


}