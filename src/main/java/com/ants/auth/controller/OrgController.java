//package com.ants.auth.controller;
//
//import com.acxy.ants.core.annotation.Autowired;
//import com.acxy.ants.resful.annotation.Controller;
//import com.acxy.ants.resful.annotation.GET;
//import com.acxy.ants.resful.annotation.POST;
//import com.acxy.ants.resful.annotation.Param;
//import com.acxy.ants.resful.bean.Page;
//import com.acxy.ants.resful.render.Json;
//import com.acxy.auth.entity.Org;
//import com.acxy.auth.service.OrgService;
//
//import java.util.List;
//
///**
// * @author MrShun
// * @version 1.0
// * @Date 2017-08-24
// */
//@Controller("org")
//public class OrgController {
//
//    @Autowired
//    private OrgService orgService;
//
//    @POST("/list")
//    public Object list(String filters) {
//        return orgService.queryList(filters);
//    }
//
//    @POST("/findUsers")
//    public Object findUsers(@Param(value = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageIndex
//            , @Param(value = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageSize, String filters) {
//        Page page = orgService.queryPage(pageIndex, pageSize, filters);
//        return Json.ui(page);
//    }
//
//    @POST("/all")
//    public Object all() {
//        List list = orgService.queryList(null);
//        return list;
//    }
//
//    @POST("/update")
//    public Object update(Org org) {
//        int result = orgService.update(org);
//        return Json.success(result);
//    }
//
//    @GET("/find")
//    public Object find(@Param Integer id) {
//        Org result = orgService.find(id);
//        return Json.success(result);
//    }
//
//    @POST("/save")
//    public Object save(Org org) {
//        orgService.save(org);
//        return Json.success("ok");
//    }
//
//    @POST("/delete")
//    public Object delete(Integer[] ids) {
//        int result = orgService.delete(ids[0]);
//        if (result == -1) return Json.fail("失败, 不能删除系统组织!");
//        return Json.success(result);
//    }
//
//
//}
