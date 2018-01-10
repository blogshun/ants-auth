package cn.jants.auth.controller;

import cn.jants.auth.common.SysConst;
import cn.jants.auth.entity.User;
import cn.jants.auth.generate.QUser;
import cn.jants.auth.service.SystemService;
import cn.jants.auth.service.UserService;
import cn.jants.auth.vo.LoginVO;
import cn.jants.common.annotation.action.Controller;
import cn.jants.common.annotation.action.GET;
import cn.jants.common.annotation.action.POST;
import cn.jants.common.annotation.action.Param;
import cn.jants.common.annotation.service.Autowired;
import cn.jants.common.utils.VerifyCodeUtil;
import cn.jants.core.holder.ClientHolder;
import cn.jants.plugin.cache.RedisTpl;
import cn.jants.restful.render.Json;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTpl redisTpl;

    /**
     * 系统用户登录
     *
     * @return
     */
    @POST("/authorize")
    public Object authorize(LoginVO loginVO) {
        int result = systemService.authorize(loginVO);
        if (result == -1) {
            return Json.fail("登录失败, 验证码输入错误!");
        } else if (result == -2) {
            return Json.fail("登录失败, 账号或密码输入错误!");
        } else if (result == -3) {
            return Json.fail("登录失败, 该账号已被锁定请联系管理员!");
        }
        return Json.success("ok");
    }

    @POST("/user/info")
    public Object getUserInfo(@Param String userToken) {
        JSONObject result = systemService.findUserTokenByUser(userToken);
        if (result == null) {
            return Json.fail(2000, "用户UserToken不存在或已过期!");
        }
        return Json.success(result);
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
     * 退出系统, 清除Cookie
     *
     * @return
     */
    @GET("/exit")
    public Object exit() {
        ClientHolder.delCookie(SysConst.LOGIN_COOKIE_NAME);
        return ClientHolder.getSessionId();
    }

    /**
     * 修改管理员头像
     *
     * @param userToken 用户信息token
     * @param userId    用户ID
     * @return
     */
    @POST("/updateUserAvatar")
    public Object updateUserAvatar(@Param String userToken, @Param Long userId, @Param String avatarPath) {
        User user = new User(userId);
        user.setAvatar(avatarPath);
        //重置用户信息
        JSONObject userMap = redisTpl.get(SysConst.REDIS_USER_INFO.concat(userToken));
        if(userMap == null){
            return Json.fail(2000, "用户UserToken不存在或已过期!");
        }
        userMap.put(QUser.AVATAR, avatarPath);
        redisTpl.set(SysConst.REDIS_USER_INFO.concat(userToken), userMap);
        return Json.success(userService.updateUserInfo(user));
    }
}
