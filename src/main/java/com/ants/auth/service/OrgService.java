package com.ants.auth.service;

import com.alibaba.fastjson.JSON;
import com.ants.auth.entity.Org;
import com.ants.auth.entity.User;
import com.ants.common.annotation.service.Service;
import com.ants.common.annotation.service.Source;
import com.ants.common.bean.Log;
import com.ants.common.bean.Page;
import com.ants.common.exception.TipException;
import com.ants.common.utils.StrUtil;
import com.ants.plugin.db.Db;
import com.ants.plugin.orm.Criteria;
import com.ants.plugin.orm.enums.Condition;
import com.ants.plugin.orm.enums.OrderBy;
import com.ants.plugin.orm.enums.Relation;

import java.util.Date;
import java.util.List;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-08-24
 */
@Service
public class OrgService {

    @Source
    private Db db;


    public Page queryPage(Integer pageIndex, Integer pageSize, String filters) {
        User user = null;
        Criteria<User> criteria = db.createCriteria(User.class);
        try {
            if (filters != null) {
                user = JSON.parseObject(filters, User.class);
            }
            criteria.addRelation(Relation.lEFT, "sys_user_org", "uo", "id", "uo.user_id");
            criteria.groupBy("_.id");
            criteria.orderBy("_.create_time, _.id", OrderBy.DESC);
            if (user != null) {
                criteria.and("uo.org_id", Condition.IN, user.getOrgs().split(","));
            }
        } catch (Exception e) {
            Log.error("conditional conversion error:{}", filters);
        }
        return criteria.findPage(pageIndex, pageSize);
    }

    public List<Org> queryList(String filters) {
        Criteria<Org> criteria = db.createCriteria(Org.class);
        try {
            if (filters != null) {
                criteria.filters(filters);
            }
        } catch (Exception e) {
            Log.error("conditional conversion error:{}", filters);
        }
        criteria.orderBy("ipx,id", OrderBy.ASC);
        return criteria.findList();
    }

    public void save(Org org) {
        org.setCreateTime(new Date());
        Criteria<Org> criteria = db.createCriteria(Org.class);
        criteria.save(org);
    }

    public int update(Org org) {
        Criteria<Org> criteria = db.createCriteria(Org.class);
        return criteria.update(org);
    }


    public Org find(Integer id) {
        Criteria<Org> criteria = db.createCriteria(Org.class);
        return criteria.findById(id);
    }

    public int delete(Integer id) {
        if (id == null) {
            throw new TipException("id 参数不能为空!");
        }
        Criteria<Org> criteria = db.createCriteria(Org.class);
        Org org = criteria.findById(id);
        if (org != null && org.getType() == 1) {
            return -1;
        }
        return criteria.deleteById(id);
    }

}
