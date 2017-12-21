package com.ants.auth.entity;

import com.ants.common.annotation.action.Entity;
import com.ants.plugin.orm.Column;
import com.ants.plugin.orm.Id;
import com.ants.plugin.orm.Table;

import java.io.Serializable;

/**
 * 用户数据字典表(sys_dict)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-11
 */
@Entity
@Table(name = "sys_dict")
public class Dict implements Serializable {

    /**
     * 字典ID
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 编号
     */
    @Column(name = "code")
    private String code;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 对应数据值
     */
    @Column(name = "val")
    private String val;

    /**
     * 排序
     */
    @Column(name = "ipx")
    private Integer ipx;

    /**
     * 0、添加 1、系统
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 父ID
     */
    @Column(name = "pid")
    private Integer pid;

    public Dict() {
    }

    public Dict(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


    public Integer getIpx() {
        return ipx;
    }

    public void setIpx(Integer ipx) {
        this.ipx = ipx;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


}