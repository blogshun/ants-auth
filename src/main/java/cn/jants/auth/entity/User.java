package cn.jants.auth.entity;


import com.alibaba.fastjson.annotation.JSONField;
import cn.jants.common.annotation.action.Entity;
import cn.jants.plugin.orm.Column;
import cn.jants.plugin.orm.Id;
import cn.jants.plugin.orm.Table;

import java.io.Serializable;

/**
 * 用户管理(sys_user)
 *
 * @author MrShun
 * @version 1.0.0 2017-09-11
 */
@Entity
@Table(name = "sys_user")
public class User implements Serializable {

    /**  */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 登陆帐户
     */
    @Column(name = "account")
    private String account;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 用户类型(1普通用户2管理员3系统管理员)
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 姓名拼音
     */
    @Column(name = "name_pinyin")
    private String namePinyin;

    /**
     * 性别(0:未知;1:男;2:女)
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 微信
     */
    @Column(name = "weixin")
    private String weixin;

    /**
     * 微博
     */
    @Column(name = "weibo")
    private String weibo;

    /**
     * QQ
     */
    @Column(name = "qq")
    private String qq;

    /**
     * 关联公众号openid
     */
    @Column(name = "openid")
    private String openid;

    /**
     * 职位
     */
    @Column(name = "position")
    private String position;

    /**
     * 登录次数
     */
    @Column(name = "login_count")
    private Long loginCount;

    /**
     * 登录IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @Column(name = "last_login_time")
    private java.util.Date lastLoginTime;

    /**
     * 账号状态 0、正常 1、锁定
     */
    @Column(name = "is_lock")
    private Integer isLock;

    /**  */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @Column(name = "create_time")
    private java.util.Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @Column(name = "update_time")
    private java.util.Date updateTime;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 是否已删除(0未删除1已删除)
     */
    @Column(name = "is_delete")
    private Integer isDelete;


    /**
     * 角色列表ID ","隔开
     */
    private String roles;

    /**
     * 组织列表ID ","隔开
     */
    private String orgs;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }


    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }


    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }


    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }


    public java.util.Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(java.util.Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
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


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    public java.util.Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }


    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOrgs() {
        return orgs;
    }

    public void setOrgs(String orgs) {
        this.orgs = orgs;
    }
}