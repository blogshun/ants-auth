package cn.jants.auth.controller;

import cn.jants.auth.entity.Role;
import cn.jants.auth.entity.RoleRes;
import cn.jants.auth.service.RoleService;
import cn.jants.common.annotation.action.Controller;
import cn.jants.common.annotation.action.GET;
import cn.jants.common.annotation.action.POST;
import cn.jants.common.annotation.action.Param;
import cn.jants.common.annotation.service.Autowired;
import cn.jants.common.bean.Page;
import cn.jants.restful.render.Json;

import java.util.List;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-08-24
 */
@Controller("role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    /**
     * 角色分页
     *
     * @param pageIndex 当前页
     * @param pageSize  每页大小
     * @return
     */
    @POST("/page")
    public Object page(@Param(regex = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageIndex
            , @Param(regex = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageSize
            , String sortField, String sortOrder) {
        Page page = roleService.queryPage(pageIndex, pageSize, sortField, sortOrder);
        return Json.ui(page);
    }

    @GET("/all")
    public Object all(String filters) {
        return roleService.queryList(filters);
    }

    @POST("/save")
    public Object save(Role role) {
        roleService.save(role);
        return Json.success("ok");
    }

    @POST("/update")
    public Object update(Role role) {
        int result = roleService.update(role);
        return Json.success(result);
    }

    @GET("/find")
    public Object find(@Param Integer id) {
        Role result = roleService.find(id);
        return Json.success(result);
    }

    @POST("/delete")
    public Object delete(Integer[] ids) {
        int res = roleService.delete(ids);
        return Json.success(res);
    }

    @POST("/setRes")
    public Object authSet(@Param(regex = "^(-|\\+)?\\d+$",
            msg = "角色ID不符合规范!") Integer rid, Integer[] ids) {
        int res = roleService.setRes(rid, ids);
        return Json.success(res);
    }

    @GET("/findRes")
    public Object findRes(Integer[] ids) {
        if (ids == null) {
            return Json.success("");
        }
        List<RoleRes> result = roleService.findRes(ids);
        return Json.success(result);
    }
}
