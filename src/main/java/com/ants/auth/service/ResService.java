//package com.ants.auth.service;
//
//import com.acxy.ants.common.enums.DataSourceType;
//import com.acxy.ants.core.annotation.Service;
//import com.acxy.ants.core.annotation.Source;
//import com.acxy.ants.core.exception.TipException;
//import com.acxy.ants.core.plugin.db.Db;
//import com.acxy.ants.core.plugin.entity.Symbol;
//import com.acxy.auth.entity.Res;
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
//public class ResService {
//
//    @Source(DataSourceType.DRUID)
//    private Db db;
//
//    public List<Res> queryList(String tjKey, String keyValue) {
//        Res res = new Res();
//        if (tjKey != null && keyValue != null)
//            res.search(tjKey, keyValue);
//        return res.orderBy("ipx", Symbol.ASC).list(db);
//    }
//
//    public List<Res> queryList() {
//        return queryList(null, null);
//    }
//
//    public void save(Res res) {
//        res.setCreateTime(new Date());
//        res.save(db);
//    }
//
//    public int update(Res res) {
//        return res.update(db);
//    }
//
//
//    public Res find(Integer id) {
//        return new Res(id).find(db);
//    }
//
//    public int delete(Integer[] ids) {
//        if (ids == null) throw new TipException("ids 参数不能为空!");
//        int i = 0;
//        Res res = new Res();
//        for (Integer id : ids) {
//            res.setId(id);
//            res.deleteById(db, id);
//            i++;
//        }
//        return i;
//    }
//
//}
