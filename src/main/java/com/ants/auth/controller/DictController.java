//package com.ants.auth.controller;
//
//import com.ants.auth.entity.Dict;
//import com.ants.auth.service.DictService;
//import com.ants.common.annotation.action.Controller;
//import com.ants.common.annotation.action.GET;
//import com.ants.common.annotation.action.POST;
//import com.ants.common.annotation.action.Param;
//import com.ants.common.annotation.service.Autowired;
//import com.ants.restful.render.Json;
//
//import java.util.List;
//
//
///**
// * @author MrShun
// * @version 1.0
// * @Date 2017-08-26
// */
//@Controller("dict")
//public class DictController {
//
//    @Autowired
//    private DictService dictService;
//
//    @POST("/list")
//    public Object list() {
//        return dictService.queryList(null);
//    }
//
//    @GET("/all")
//    public Object all(String filters) {
//        return dictService.queryList(filters);
//    }
//
//    @POST("/delete")
//    public Object delete(Integer[] ids) {
//        int result = dictService.delete(ids[0]);
//        if (result == -1) return Json.fail("失败, 不能删除系统类型字典!");
//        return Json.success(result);
//    }
//
//    @GET("/find")
//    public Object find(@Param(regex = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") Integer id) {
//        Dict result = dictService.find(id);
//        return Json.success(result);
//    }
//
//    @GET("/code")
//    public List code(String id) {
//        List result = dictService.findCode(id);
//        return result;
//    }
//
//    @POST("/save")
//    public Object save(Dict dict) {
//        int res = dictService.save(dict);
//        if (res == -1) return Json.fail("Code已经存在!");
//        ;
//        return Json.success("ok");
//    }
//
//    @POST("/update")
//    public Object update(Dict dict) {
//        int res = dictService.update(dict);
//        if (res == -1) return Json.fail("Code已经存在!");
//        ;
//        if (res == -2) return Json.fail("失败, 不能修改系统类型字典CODE!");
//        return Json.success(res);
//    }
//}
