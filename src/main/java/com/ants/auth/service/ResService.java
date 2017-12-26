package com.ants.auth.service;


import com.ants.auth.entity.Res;
import com.ants.common.annotation.service.Service;
import com.ants.common.annotation.service.Source;
import com.ants.common.exception.TipException;
import com.ants.common.utils.StrUtil;
import com.ants.plugin.db.Db;
import com.ants.plugin.orm.Criteria;
import com.ants.plugin.orm.enums.Condition;
import com.ants.plugin.orm.enums.OrderBy;

import java.util.Date;
import java.util.List;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-08-24
 */
@Service
public class ResService {

    @Source
    private Db db;

    public List<Res> queryList(String tjKey, String keyValue) {
        Criteria criteria = db.createCriteria(Res.class);
        if (StrUtil.notBlank(tjKey, keyValue)) {
            criteria.and(tjKey, Condition.LIKE, "%".concat(keyValue).concat("%"));
        }
        criteria.orderBy("ipx", OrderBy.ASC);
        return criteria.findList();
    }

    public List<Res> queryList() {
        return queryList(null, null);
    }

    public void save(Res res) {
        Criteria criteria = db.createCriteria(Res.class);
        res.setCreateTime(new Date());
        criteria.save(res);
    }

    public int update(Res res) {
        Criteria criteria = db.createCriteria(Res.class);
        res.setCreateTime(new Date());
        return criteria.update(res);
    }


    public Res find(Integer id) {
        Criteria<Res> criteria = db.createCriteria(Res.class);
        return criteria.findById(id);
    }

    public int delete(Integer[] ids) {
        if (ids == null) {
            throw new TipException("ids 参数不能为空!");
        }
        int i = 0;
        Criteria<Res> criteria = db.createCriteria(Res.class);
        for (Integer id : ids) {
            criteria.deleteById(id);
            i++;
        }
        return i;
    }

}
