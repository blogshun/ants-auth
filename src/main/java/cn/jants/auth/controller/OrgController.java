package cn.jants.auth.controller;

import cn.jants.auth.service.OrgService;
import cn.jants.common.annotation.action.GET;
import cn.jants.common.annotation.service.Autowired;
import cn.jants.auth.entity.Org;
import cn.jants.common.annotation.action.Controller;
import cn.jants.common.annotation.action.POST;
import cn.jants.common.annotation.action.Param;
import cn.jants.common.bean.Page;
import cn.jants.restful.render.Json;

import java.util.List;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-08-24
 */
@Controller("org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @POST("/list")
    public Object list(String filters) {
        return orgService.queryList(filters);
    }

    @POST("/findUsers")
    public Object findUsers(@Param(regex = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageIndex
            , @Param(regex = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageSize, String filters) {
        Page page = orgService.queryPage(pageIndex, pageSize, filters);
        return Json.ui(page);
    }

    @POST("/all")
    public Object all() {
        List list = orgService.queryList(null);
        return list;
    }

    @POST("/update")
    public Object update(Org org) {
        int result = orgService.update(org);
        return Json.success(result);
    }

    @GET("/find")
    public Object find(@Param Integer id) {
        Org result = orgService.find(id);
        return Json.success(result);
    }

    @POST("/save")
    public Object save(Org org) {
        orgService.save(org);
        return Json.success("ok");
    }

    @POST("/delete")
    public Object delete(Integer[] ids) {
        int result = orgService.delete(ids[0]);
        if (result == -1) {
            return Json.fail("失败, 不能删除系统组织!");
        }
        return Json.success(result);
    }


}
