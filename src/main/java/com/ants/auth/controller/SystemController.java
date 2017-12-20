package com.ants.auth.controller;

import com.ants.auth.common.SysConst;
import com.ants.auth.service.SystemService;
import com.ants.common.annotation.action.Controller;
import com.ants.common.annotation.action.GET;
import com.ants.common.annotation.action.POST;
import com.ants.common.annotation.service.Autowired;
import com.ants.common.utils.VerifyCodeUtil;
import com.ants.restful.render.Json;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统操作层
 *
 * @author MrShun
 * @version 1.0
 * @Date 2017-09-02
 */
@Controller("sys")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 系统用户登录
     *
     * @param username 登录账号
     * @param password 登录密码
     * @param code     验证码
     * @return
     */
    @POST("/login")
    public Object login(String username, String password, String code) {
        int result = systemService.login(username, password, code);
        if (result == -1) {
            return Json.fail("登录失败, 验证码输入错误!");
        } else if (result == -2) {
            return Json.fail("登录失败, 账号或密码输入错误!");
        } else if (result == -3) {
            return Json.fail("登录失败, 该账号已被锁定请联系管理员!");
        }
        return Json.success("ok");
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @GET("/code.png")
    public void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VerifyCodeUtil.generate(request, response, SysConst.CODE_SESSION_NAME);
    }

    /**
     * 清除缓存
     *
     * @param request
     * @return
     */
    @GET("/clear")
    public Object clear(HttpServletRequest request) {
        return null;
    }

    /**
     * 退出系统
     *
     * @param request
     * @return
     */
    @GET("/exit")
    public Object exit(HttpServletRequest request) {
        return null;
    }
}
