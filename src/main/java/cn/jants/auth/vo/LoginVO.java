package cn.jants.auth.vo;

import cn.jants.common.annotation.action.Entity;

import java.io.Serializable;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017/12/21
 */
@Entity
public class LoginVO implements Serializable{

    /**
     * 登陆账号
     */
    private String userName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoginVO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
