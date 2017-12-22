package com.ants.auth.service;

import com.ants.auth.entity.Org;
import com.ants.auth.entity.User;
import com.ants.common.annotation.service.Service;
import com.ants.common.annotation.service.Source;
import com.ants.common.bean.Log;
import com.ants.common.bean.Page;
import com.ants.common.exception.TipException;
import com.ants.plugin.db.Db;
import com.ants.plugin.orm.Criteria;
import com.ants.plugin.orm.enums.OrderBy;

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
//        Criteria<Org> criteria = db.createCriteria(Org.class);
//        try {
//            if (filters != null) {
//                criteria.filters(filters);
//            } else
//                user = new User();
//            if (StrUtil.notBlank(user.getOrgs()))
//                criteria.and("orgId", Condition.IN, user.getOrgs());
//
//        } catch (Exception e) {
//            Log.error("conditional conversion error:{}", filters);
//        }
//        Page page = user.leftJoin("left join sys_user_org o on o.user_id = id").orderBy("create_time, id", Symbol.DESC).page(db, pageIndex, pageSize);
        return null;
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
