package com.ants.auth.entity;


import com.ants.plugin.orm.Column;
import com.ants.plugin.orm.Id;
import com.ants.plugin.orm.Table;

import java.io.Serializable;

/**
 * 系统资源权限(sys_res)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-13
 */
@Table(name = "sys_res")
public class Res implements Serializable {

    /**
     * 关键字
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "res_name")
    private String resName;

    /**
     * 访问地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 父ID
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * 0:页面1:功能2:顶级菜单3:外窗口
     */
    @Column(name = "type")
    private String type;

    /**
     * 排序
     */
    @Column(name = "ipx")
    private Integer ipx;

    /**
     * 说明
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private java.util.Date createTime;

    public Res() {
    }

    public Res(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Integer getIpx() {
        return ipx;
    }

    public void setIpx(Integer ipx) {
        this.ipx = ipx;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }


}