package cn.jants.auth.service;


import cn.jants.plugin.orm.Criteria;
import cn.jants.auth.entity.Res;
import cn.jants.common.annotation.service.Service;
import cn.jants.common.annotation.service.Source;
import cn.jants.common.exception.TipException;
import cn.jants.common.utils.StrUtil;
import cn.jants.plugin.db.Db;
import cn.jants.plugin.orm.enums.Condition;
import cn.jants.plugin.orm.enums.OrderBy;

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
