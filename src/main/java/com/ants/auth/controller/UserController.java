//package com.ants.auth.controller;
//
//import com.acxy.ants.common.kit.FileKit;
//import com.acxy.ants.common.kit.ImageKit;
//import com.acxy.ants.core.annotation.Autowired;
//import com.acxy.ants.resful.annotation.Controller;
//import com.acxy.ants.resful.annotation.GET;
//import com.acxy.ants.resful.annotation.POST;
//import com.acxy.ants.resful.annotation.Param;
//import com.acxy.ants.resful.bean.Page;
//import com.acxy.ants.resful.render.Json;
//import com.acxy.auth.entity.User;
//import com.acxy.auth.service.UserService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.Part;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * 用户操作请求层
// *
// * @author MrShun
// * @version 1.0
// * @Date 2017-06-14
// */
//@Controller("user")
//public class UserController {
//
//
//    @Autowired
//    private UserService userService;
//
//
//    /**
//     * 获取用户列表
//     *
//     * @param pageIndex 当前页
//     * @param pageSize  每页大小
//     * @param sortField 排序字段
//     * @param sortOrder 排序方式
//     * @param filters   分页条件 {}
//     * @return
//     */
//    @POST("/page")
//    public Object page(@Param(value = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageIndex
//            , @Param(value = "^(-|\\+)?\\d+$", msg = "only positive numbers can be entered!") int pageSize
//            , String sortField, String sortOrder, String filters, String tjKey, String keyValue) {
//        Page page = userService.queryPage(pageIndex, pageSize, sortField, sortOrder, filters, tjKey, keyValue);
//        return Json.ui(page);
//    }
//
//    /**
//     * 保存用户数据
//     *
//     * @param user 用户数据
//     * @return
//     */
//    @POST("/save")
//    public Object save(User user) {
//        int res = userService.save(user);
//        if (res == -1) return Json.fail("添加失败, 用户账号已存在!");
//        return Json.success("ok");
//    }
//
//    /**
//     * 根据id集合批量，删除用户含用户头像
//     *
//     * @param ids
//     * @param request
//     * @return
//     */
//    @POST("/delete")
//    public Object delete(Long[] ids, HttpServletRequest request) {
//        String filePath = request.getSession().getServletContext().getRealPath("");
//        int res = userService.delete(ids, filePath);
//        return Json.success(res);
//    }
//
//    /**
//     * 修改用户
//     *
//     * @param user 用户数据
//     * @return
//     */
//    @POST("/update")
//    public Object update(User user) {
//        int res = userService.update(user);
//        if (res == -1) return Json.fail("修改失败, 用户账号已存在!");
//        return Json.success("ok");
//    }
//
//    /**
//     * 根据用户查询用户信息
//     *
//     * @param id 用户ID
//     * @return
//     */
//    @GET(value = "/find", name = "查询用户", desc = "根据用户ID查询单条用户信息")
//    public Object find(@Param(value = "^(-|\\+)?\\d+$", remarks = "用户ID"
//            , msg = "只能输入正的长整数") Long id) {
//        User result = userService.find(id);
//        return Json.success(result);
//    }
//
//    /**
//     * 根据用户ID和查询类型查询用户关联信息
//     *
//     * @param id
//     * @param type 0/全部查询 1/查询用户角色 2/查询用户组织
//     * @return
//     */
//    @GET("/findRolesOrOrgs")
//    public Map find(@Param Long id, int type) {
//        List result = userService.findRolesOrOrgs(id, type);
//        return Json.success(result);
//    }
//
//    /**
//     * 锁定用户与解锁用户
//     *
//     * @param id   用户ID
//     * @param lock 0/解锁 1/锁定
//     * @return
//     */
//    @POST("/checkLock")
//    public Object checkLock(Long id, int lock) {
//        int res = userService.checkLock(id, lock);
//        return Json.success(res);
//    }
//
//    /**
//     * 上传用户头像
//     *
//     * @param txImg file input 名称
//     * @return
//     */
//    @POST("/uploadAvatar")
//    public Object uploadAvatar(Part txImg, HttpServletRequest request) throws IOException {
//        boolean check = ImageKit.check(txImg.getContentType());
//        if (check) {
//            long size = txImg.getSize();
//            if (size > 50000) return Json.fail("错误, 上传图片大小不能超过[50kb]!");
//            String header = txImg.getHeader("Content-Disposition");
//            String postfix = header.substring(header.lastIndexOf("."), header.length() - 1);
//            String uploadFolder = request.getSession().getServletContext().getRealPath("/upload/avatar");
//            FileKit.dirExists(uploadFolder);
//            String fileName = System.currentTimeMillis() + postfix;
//            FileKit.write(txImg.getInputStream(), uploadFolder + File.separator + fileName);
//            return Json.success("upload/avatar/" + fileName);
//        } else {
//            return Json.fail("请选择jpg 或者 png 图片文件进行上传!");
//        }
//    }
//
//}
