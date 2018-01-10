package cn.jants.auth.service;

import cn.jants.auth.entity.RoleRes;
import cn.jants.common.annotation.service.Service;
import cn.jants.common.annotation.service.Source;
import cn.jants.common.annotation.service.Tx;
import cn.jants.common.bean.Log;
import cn.jants.common.exception.TipException;
import cn.jants.common.utils.StrUtil;
import cn.jants.plugin.db.Db;
import cn.jants.plugin.orm.Criteria;
import cn.jants.plugin.orm.enums.Condition;
import cn.jants.plugin.orm.enums.OrderBy;
import cn.jants.auth.entity.Role;
import cn.jants.common.bean.Page;

import java.util.List;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-08-24
 */
@Service
public class RoleService {

    @Source
    private Db db;

    public Page queryPage(Integer pageIndex, Integer pageSize, String sortField, String sortOrder) {
        Criteria criteria = db.createCriteria(Role.class);
        if(StrUtil.notBlank(sortOrder)) {
            criteria.orderBy(sortField, OrderBy.valueOf(sortOrder));
        }
        return criteria.findPage(pageIndex, pageSize);
    }


    public List<Role> queryList(String filters) {
        Criteria<Role> criteria = db.createCriteria(Role.class);
        try {
            if (filters != null) {
                criteria.filters(filters);
            }
        } catch (Exception e) {
            Log.error("conditional conversion error:{}", filters);
        }
        criteria.orderBy("id", OrderBy.DESC);
        return criteria.findList();
    }

    public void save(Role role) {
        Criteria<Role> criteria = db.createCriteria(Role.class);
        criteria.save(role);
    }

    public int update(Role role) {
        Criteria<Role> criteria = db.createCriteria(Role.class);
        return criteria.update(role);
    }


    public Role find(Integer id) {
        Criteria<Role> criteria = db.createCriteria(Role.class);
        return criteria.findById(id);
    }

    public int delete(Integer[] ids) {
        if (ids == null) {
            throw new TipException("ids 参数不能为空!");
        }
        int res = 0;
        Criteria criteria = db.createCriteria(Role.class);
        for (Integer id : ids) {
            criteria.deleteById(id);
            res++;
        }
        return res;
    }

    /**
     * 设置权限，保存角色与资源对应
     *
     * @param rid
     * @param ids
     * @return
     */
    @Tx
    public int setRes(Integer rid, Integer[] ids) {
        Criteria criteria = db.createCriteria(RoleRes.class);
        //清空对应角色下面的所有资源
        criteria.deleteById(rid);
        if (ids == null || ids.length == 0) {
            return 0;
        }
        int i = 0;
        for (Integer id : ids) {
            RoleRes roleRes = new RoleRes();
            roleRes.setRoleId(rid);
            roleRes.setResId(id);
            //存储角色对应资源
            criteria.save(roleRes);
            i++;
        }
        return i;
    }

    public List findRes(Integer[] ids) {
        if (ids == null) {
            throw new TipException("ids 参数不能为空!");
        }
        Criteria criteria = db.createCriteria(RoleRes.class);
        criteria.and("roleId", Condition.IN, ids);
        return criteria.findList();
    }
}
