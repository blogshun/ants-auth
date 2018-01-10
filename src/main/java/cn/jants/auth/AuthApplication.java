package cn.jants.auth;

import cn.jants.common.annotation.action.Controller;
import cn.jants.common.annotation.action.GET;
import cn.jants.common.annotation.action.PathVariable;
import cn.jants.common.annotation.boot.PropertyConfiguration;
import cn.jants.common.annotation.plugin.EnableRedisPlugin;
import cn.jants.common.annotation.service.Application;
import cn.jants.core.holder.ClientHolder;
import cn.jants.core.startup.JTomcat;
import cn.jants.restful.render.Resource;
import cn.jants.auth.common.SysConst;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-12-20
 */
@Application
@PropertyConfiguration(debug = true, secretKey = "ants_auth_666666")
@EnableRedisPlugin
@Controller
public class AuthApplication {


    /**
     * 页面
     */
    @GET("/admin/{md}/{page}.shtml")
    public Resource assets(@PathVariable String md, @PathVariable String page) {
        return new Resource("/static/admin/".concat(md).concat("/").concat(page).concat(".html"));
    }

    @GET("/admin/{page}.shtml")
    public Resource assets(@PathVariable String page) {
        return new Resource("/static/admin/".concat(page).concat(".html"));
    }

    /**
     * css样式
     */
    @GET("/static/css/{md}/{name}")
    public Resource css(@PathVariable String md, @PathVariable String name) {
        return new Resource("/static/css/".concat(md).concat("/").concat(name));
    }

    @GET("/static/css/images/{md}/{name}")
    public Resource cssImages(@PathVariable String md, @PathVariable String name) {
        return new Resource("/static/css/images/".concat(md).concat("/").concat(name));
    }

    @GET("/static/css/{name}")
    public Resource css(@PathVariable String name) {
        return new Resource("/static/css/".concat(name));
    }

    /**
     * js脚本
     */
    @GET("/static/js/{name}")
    public Resource js(@PathVariable String name) {
        return new Resource("/static/js/".concat(name));
    }

    /**
     * 字体
     */
    @GET("/static/fonts/{name}")
    public Resource fonts(@PathVariable String name) {
        return new Resource("/static/fonts/".concat(name));
    }

    /**
     * 图片
     */
    @GET("/static/images/{name}")
    public Resource images(@PathVariable String name) {
        return new Resource("/static/images/".concat(name));
    }

    @GET("/login")
    public void login() throws IOException {
        Cookie cookie = ClientHolder.getCookie(SysConst.LOGIN_COOKIE_NAME);
        HttpServletRequest request = ClientHolder.getRequest();
        HttpServletResponse response = ClientHolder.getResponse();
        if (cookie == null) {
            Resource resource = new Resource("/static/login.html");
            resource.render(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/index.shtml");
        }
    }

    public static void main(String[] args) {
          JTomcat.run(AuthApplication.class, 80, "/auth");
    }
}
