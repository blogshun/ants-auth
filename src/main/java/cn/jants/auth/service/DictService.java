package cn.jants.auth.service;

import cn.jants.auth.entity.Dict;
import cn.jants.auth.generate.QDict;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.jants.common.annotation.service.Service;
import cn.jants.common.annotation.service.Source;
import cn.jants.common.exception.TipException;
import cn.jants.common.utils.StrUtil;
import cn.jants.plugin.db.Db;
import cn.jants.plugin.orm.Criteria;
import cn.jants.plugin.orm.enums.Condition;
import cn.jants.plugin.orm.enums.OrderBy;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-08-26
 */

@Service
public class DictService {

    @Source
    private Db db;

    public List<Dict> queryList(String filters) {
        Criteria<Dict> criteria = db.createCriteria(Dict.class);
        if (StrUtil.notBlank(filters)) {
            JSONObject jsonObject = JSON.parseObject(filters);
            Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Object value = entry.getValue();
                if (StrUtil.notBlank(String.valueOf(value))) {
                    criteria.and(entry.getKey(), Condition.EQ, value);
                }
            }
        }
        criteria.orderBy(OrderBy.DESC, QDict.IPX, QDict.ID);
        return criteria.findList();
    }

    //find
    public Dict find(Integer id) {
        Criteria<Dict> criteria = db.createCriteria(Dict.class);
        return criteria.findById(id);
    }

    //save
    public int save(Dict dict) {
        String code = dict.getCode();
        List list = findCode(code);
        if (list != null && list.size() > 0) {
            return -1;
        }
        Criteria<Dict> criteria = db.createCriteria(Dict.class);
        criteria.save(dict);
        return 1;
    }

    public int update(Dict dict) {
        String code = dict.getCode();
        int id = dict.getId();
        //先查询是否存在相同的CODE
        Criteria<Dict> criteria = db.createCriteria(Dict.class);
        criteria.and(QDict.CODE, Condition.EQ, code);
        criteria.and(QDict.ID, Condition.NE, id);
        Integer count = criteria.count();
        if (count > 0) {
            return -1;
        }
        //先查询是否为系统字典
        Dict sysDict = criteria.findById(id);
        if (sysDict != null && sysDict.getType() == 1 && !code.equals(sysDict.getCode())) {
            return -2;
        }
        return criteria.update(dict);
    }

    public int delete(Integer id) {
        if (id == null) {
            throw new TipException("id 参数不能为空!");
        }
        Criteria<Dict> criteria = db.createCriteria(Dict.class);
        Dict dict = criteria.findById(id);
        if (dict != null && dict.getType() == 1) {
            return -1;
        }
        return criteria.deleteById(id);
    }

    /**
     * 根据Code查询字典集合
     *
     * @param id
     * @return
     */
    public List findCode(String id) {
        if (id == null) {
            throw new TipException("id 参数不能为空!");
        }
        Criteria criteria = db.createCriteria(Dict.class);
        criteria.label(QDict.NAME, QDict.VAL);
        criteria.and("pid = (select id from sys_dict where code = ?)", Condition.EMBED, id);
        criteria.orderBy(OrderBy.ASC, QDict.IPX);
        return criteria.findList();
    }
}
