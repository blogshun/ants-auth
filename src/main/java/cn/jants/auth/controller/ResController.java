package cn.jants.auth.controller;


import cn.jants.auth.service.ResService;
import cn.jants.common.annotation.action.GET;
import cn.jants.common.annotation.action.POST;
import cn.jants.common.annotation.service.Autowired;
import cn.jants.auth.entity.Res;
import cn.jants.common.annotation.action.Controller;
import cn.jants.common.annotation.action.Param;
import cn.jants.restful.render.Json;

import java.util.List;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-08-24
 */
@Controller("res")
public class ResController {

    @Autowired
    private ResService resService;

    @POST("/list")
    public Object list(String tjKey, String keyValue) {
        List list = resService.queryList(tjKey, keyValue);
        return list;
    }

    @POST("/all")
    public Object all() {
        List list = resService.queryList();
        return list;
    }

    @POST("/delete")
    public Object delete(Integer[] ids) {
        int res = resService.delete(ids);
        return Json.success(res);
    }

    @GET("/find")
    public Object find(@Param Integer id) {
        Res result = resService.find(id);
        return Json.success(result);
    }

    @POST("/save")
    public Object save(Res res) {
        resService.save(res);
        return Json.success("ok");
    }

    @POST("/update")
    public Object update(Res res) {
        int result = resService.update(res);
        return Json.success(result);
    }
}
