//package com.ants.auth.service;
//
//import com.acxy.ants.common.enums.DataSourceType;
//import com.acxy.ants.core.annotation.Service;
//import com.acxy.ants.core.annotation.Source;
//import com.acxy.ants.core.annotation.Tx;
//import com.acxy.ants.core.exception.TipException;
//import com.acxy.ants.core.kit.LogKit;
//import com.acxy.ants.core.plugin.db.Db;
//import com.acxy.ants.core.plugin.entity.Symbol;
//import com.acxy.ants.resful.bean.Page;
//import com.acxy.auth.entity.Role;
//import com.acxy.auth.entity.RoleRes;
//import com.alibaba.fastjson.JSON;
//
//import java.util.List;
//
///**
// * @author MrShun
// * @version 1.0
// * @Date 2017-08-24
// */
//@Service
//public class RoleService {
//
//    @Source(DataSourceType.DRUID)
//    private Db db;
//
//    public Page queryPage(Integer pageIndex, Integer pageSize, String sortField, String sortOrder) {
//        return new Role().orderBy(sortField, sortOrder).page(db, pageIndex, pageSize);
//    }
//
//
//    public List<Role> queryList(String filters) {
//        Role role = new Role();
//        try {
//            if (filters != null)
//                role = JSON.parseObject(filters, Role.class);
//        } catch (Exception e) {
//            LogKit.error("conditional conversion error:{}", filters);
//        }
//        return role.orderBy("id", Symbol.DESC).list(db);
//    }
//
//    public void save(Role role) {
//        role.save(db);
//    }
//
//    public int update(Role role) {
//        return role.update(db);
//    }
//
//
//    public Role find(Integer id) {
//        return new Role(id).find(db);
//    }
//
//    public int delete(Integer[] ids) {
//        if (ids == null) throw new TipException("ids 参数不能为空!");
//        int res = 0;
//        Role role = new Role();
//        for (Integer id : ids) {
//            role.setId(id);
//            role.deleteById(db, id);
//            res++;
//        }
//        return res;
//    }
//
//    /**
//     * 设置权限，保存角色与资源对应
//     *
//     * @param rid
//     * @param ids
//     * @return
//     */
//    @Tx
//    public int setRes(Integer rid, Integer[] ids) {
//        RoleRes roleRes = new RoleRes(rid);
//        //清空对应角色下面的所有资源
//        roleRes.delete(db);
//        if (ids == null || ids.length == 0) return 0;
//        int i = 0;
//        for (Integer id : ids) {
//            roleRes.setResId(id);
//            //存储角色对应资源
//            roleRes.save(db);
//            i++;
//        }
//        return i;
//    }
//
//    public List findRes(Integer[] ids) {
//        if (ids == null) throw new TipException("ids 参数不能为空!");
//        StringBuffer sb = new StringBuffer("role_id in(");
//        for (Integer id : ids) {
//            sb.append(id + ",");
//        }
//        sb.delete(sb.length() - 1, sb.length());
//        sb.append(")");
//        List<RoleRes> result = new RoleRes().where(sb.toString()).list(db);
//        return result;
//    }
//}
