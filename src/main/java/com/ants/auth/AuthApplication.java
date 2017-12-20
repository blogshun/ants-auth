package com.ants.auth;

import com.ants.common.annotation.action.Controller;
import com.ants.common.annotation.action.GET;
import com.ants.common.annotation.action.PathVariable;
import com.ants.common.annotation.boot.PropertyConfiguration;
import com.ants.common.annotation.service.Application;
import com.ants.core.startup.JTomcat;
import com.ants.restful.render.Resource;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-12-20
 */
@Application
@PropertyConfiguration(debug = true)
@Controller
public class AuthApplication {


    /**
     *页面
     */
    @GET("/admin/{md}/{page}")
    public Resource assets(@PathVariable String md, @PathVariable String page) {
        return new Resource("/static/admin/".concat(md).concat("/").concat(page));
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
    public Resource login(@PathVariable String name) {
        return new Resource("/static/login.html");
    }

    public static void main(String[] args) {
        JTomcat.run(AuthApplication.class, 80, "/auth");
    }
}
