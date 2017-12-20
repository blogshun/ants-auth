//package com.ants.auth.service;
//
//import com.acxy.ants.common.enums.DataSourceType;
//import com.acxy.ants.common.kit.StrKit;
//import com.acxy.ants.core.annotation.Service;
//import com.acxy.ants.core.annotation.Source;
//import com.acxy.ants.core.exception.TipException;
//import com.acxy.ants.core.kit.LogKit;
//import com.acxy.ants.core.plugin.db.Db;
//import com.acxy.ants.core.plugin.entity.Symbol;
//import com.acxy.ants.resful.bean.Page;
//import com.acxy.auth.entity.Org;
//import com.acxy.auth.entity.User;
//import com.alibaba.fastjson.JSON;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * @author MrShun
// * @version 1.0
// * @Date 2017-08-24
// */
//@Service
//public class OrgService {
//
//    @Source(DataSourceType.DRUID)
//    private Db db;
//
//
//    public Page queryPage(Integer pageIndex, Integer pageSize, String filters) {
//        User user = null;
//        try {
//            if (filters != null)
//                user = JSON.parseObject(filters, User.class);
//            else
//                user = new User();
//            if (StrKit.notBlank(user.getOrgs()))
//                user.where("o.org_id in(" + user.getOrgs() + ")");
//        } catch (Exception e) {
//            LogKit.error("conditional conversion error:{}", filters);
//        }
//        Page page = user.leftJoin("left join sys_user_org o on o.user_id = id").orderBy("create_time, id", Symbol.DESC).page(db, pageIndex, pageSize);
//        return page;
//    }
//
//    public List<Org> queryList(String filters) {
//        Org org = new Org();
//        try {
//            if (filters != null)
//                org = JSON.parseObject(filters, Org.class);
//        } catch (Exception e) {
//            LogKit.error("conditional conversion error:{}", filters);
//        }
//        return org.orderBy("ipx,id", Symbol.ASC).list(db);
//    }
//
//    public void save(Org org) {
//        org.setCreateTime(new Date());
//        org.save(db);
//    }
//
//    public int update(Org org) {
//        return org.update(db);
//    }
//
//
//    public Org find(Integer id) {
//        return new Org(id).find(db);
//    }
//
//    public int delete(Integer id) {
//        if (id == null) throw new TipException("id 参数不能为空!");
//        Org org = new Org(id).find(db);
//        if (org != null && org.getType() == 1) return -1;
//        return org.deleteById(db, id);
//    }
//
//}
