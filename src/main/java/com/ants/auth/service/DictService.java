//package com.ants.auth.service;
//
//import com.alibaba.fastjson.JSON;
//import com.ants.auth.entity.Dict;
//import com.ants.common.annotation.service.Service;
//import com.ants.common.annotation.service.Source;
//import com.ants.common.bean.Log;
//import com.ants.plugin.db.Db;
//import com.ants.plugin.orm.Criteria;
//import com.ants.plugin.orm.enums.OrderBy;
//import com.ants.plugin.orm.enums.Symbol;
//
//import java.util.List;
//
///**
// * @author MrShun
// * @version 1.0
// * @Date 2017-08-26
// */
//
//@Service
//public class DictService {
//
//    @Source
//    private Db db;
//
//    public List<Dict> queryList(String filters) {
//        Criteria<Dict> criteria = db.createCriteria(Dict.class);
//        criteria.orderBy("ipx,id", OrderBy.DESC);
//        return criteria.findList();
//    }
//
//    //find
//    public Dict find(Integer id) {
//        Criteria<Dict> criteria = db.createCriteria(Dict.class);
//        return criteria.findById(id);
//    }
//
//    //save
//    public int save(Dict dict) {
//        String code = dict.getCode();
//        List list = findCode(code);
//        if (list != null && list.size() > 0) return -1;
//        dict.save(db);
//        return 1;
//    }
//
//    public int update(Dict dict) {
//        String code = dict.getCode();
//        int id = dict.getId();
//        //先查询是否存在相同的CODE
//        Dict d1 = new Dict();
//        d1.setCode(code);
//        Long count = d1.and("id", "<>", id).count(db);
//        if (count > 0) return -1;
//        //先查询是否为系统字典
//        Dict d2 = new Dict(id).find(db);
//        if (d2 != null && d2.getType() == 1 && !code.equals(d2.getCode())) return -2;
//        return dict.update(db);
//    }
//
//    public int delete(Integer id) {
//        if (id == null) throw new TipException("id 参数不能为空!");
//        Dict dict = new Dict(id).find(db);
//        if (dict != null && dict.getType() == 1) return -1;
//        return dict.deleteById(db, id);
//    }
//
//    //根据Code查询字典集合
//    public List findCode(String id) {
//        if (id == null) throw new TipException("id 参数不能为空!");
//        Dict dict = new Dict();
//        dict.setCode(id);
//        return dict.label("name, val").where("pid = (select id from sys_dict where code = ?)").orderBy("ipx", Symbol.ASC).list(db);
//    }
//}
