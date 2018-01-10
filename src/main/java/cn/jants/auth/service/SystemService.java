package cn.jants.auth.service;

import cn.jants.auth.entity.User;
import cn.jants.auth.entity.UserOrg;
import cn.jants.auth.entity.UserRole;
import cn.jants.auth.generate.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.jants.auth.common.SysConst;
import cn.jants.auth.entity.RoleRes;
import cn.jants.auth.vo.LoginVO;
import cn.jants.common.annotation.service.Autowired;
import cn.jants.common.annotation.service.Service;
import cn.jants.common.annotation.service.Source;
import cn.jants.common.bean.JsonMap;
import cn.jants.common.enums.EncType;
import cn.jants.common.exception.TipException;
import cn.jants.common.utils.GenUtil;
import cn.jants.common.utils.StrEncryptUtil;
import cn.jants.core.holder.ClientHolder;
import cn.jants.plugin.cache.RedisTpl;
import cn.jants.plugin.db.Db;
import cn.jants.plugin.orm.Criteria;
import cn.jants.plugin.orm.enums.Condition;
import cn.jants.plugin.orm.enums.Relation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-09-02
 */
@Service
public class SystemService {

    @Source
    private Db db;

    @Autowired
    private RedisTpl redisTpl;

    /**
     * 系统用户登录业务
     *
     * @return
     */
    public int authorize(LoginVO loginVO) {
        Object sessionCode = ClientHolder.getSession().getAttribute(SysConst.CODE_SESSION_NAME);
        String code = loginVO.getCode();
        if (code == null) {
            throw new TipException("验证码不能为空!");
        }
        if (!code.equalsIgnoreCase(String.valueOf(sessionCode))) {
            return -1;
        }
        String password = loginVO.getPassword();
        String decryptStr = StrEncryptUtil.decrypt("_utravel12345678", EncType.AES, password);
        if (decryptStr == null) {
            return -2;
        }
        Criteria<User> criteria = db.createCriteria(User.class);
        criteria.and(QUser.ACCOUNT, Condition.EQ, loginVO.getUserName())
                .and(QUser.PASSWORD, Condition.EQ, StrEncryptUtil.md5(decryptStr));
        User user = criteria.find();
        if (user == null) {
            return -2;
        }
        //判断账号是否被锁定
        if (user.getIsLock() == 1) {
            return -3;
        }

        //存放session
        ClientHolder.getSession().setAttribute(SysConst.USER_SESSION_NAME, user);
        //修改登录记录
        user.setLoginCount(user.getLoginCount() + 1);
        user.setLoginIp(ClientHolder.getIp());
        user.setLastLoginTime(new Date());

        Long userId = user.getId();
        //存放redis
        String userTokenStr = GenUtil.makeTokenStr(userId, user.getAccount()
                , user.getPassword());
        Map userMap = JSON.parseObject(JSON.toJSONString(user), Map.class);
        //查询用户组织信息
        Criteria uoCriteria = db.createCriteria(UserOrg.class);
        uoCriteria.label(QOrg._ID, QOrg._ORG_NAME);
        uoCriteria.addRelation(Relation.lEFT, QUser.TABLE, QUserOrg.USER_ID, QUser._ID);
        uoCriteria.addRelation(Relation.lEFT, QOrg.TABLE, QUserOrg.ORG_ID, QOrg._ID);
        uoCriteria.and(QUserOrg.USER_ID, Condition.EQ, userId);
        List uoList = uoCriteria.findMapList();
        userMap.put("orgList", uoList);

        //查询用户角色信息
        Criteria urCriteria = db.createCriteria(UserRole.class);
        urCriteria.label(QRole._ID, QRole._ROLE_NAME);
        urCriteria.addRelation(Relation.lEFT, QUser.TABLE, QUserRole.USER_ID, QUser._ID);
        urCriteria.addRelation(Relation.lEFT, QRole.TABLE, QUserRole.ROLE_ID, QRole._ID);
        urCriteria.and(QUserRole.USER_ID, Condition.EQ, userId);
        List<JsonMap> urList = urCriteria.findMapList();
        userMap.put("roleList", urList);
        List resList = new ArrayList<>();
        //查询用户资源信息
        for (JsonMap ur : urList) {
            Criteria rrCriteria = db.createCriteria(RoleRes.class);
            rrCriteria.label(QRes._ID, QRes._RES_NAME, QRes._URL, QRes._ICON, QRes._PID, QRes._TYPE, QRes._IPX);
            rrCriteria.addRelation(Relation.lEFT, QRole.TABLE, QRoleRes.ROLE_ID, QRole._ID);
            rrCriteria.addRelation(Relation.lEFT, QRes.TABLE, QRoleRes.RES_ID, QRes._ID);
            rrCriteria.and(QRoleRes.ROLE_ID, Condition.EQ, ur.getInt("id"));
            List<JsonMap> mapList = rrCriteria.findMapList();
            for (JsonMap jm : mapList) {
                if (!resList.contains(jm)) {
                    resList.add(jm);
                }
            }
        }
        userMap.put("resList", resList);
        redisTpl.set(SysConst.REDIS_USER_INFO.concat(userTokenStr), userMap, 60 * 60 * 24 * 2);

        //写入信息免登陆
        ClientHolder.setCookie(SysConst.LOGIN_COOKIE_NAME, "true", 60 * 60 * 24 * 1);

        //写入UserToken
        ClientHolder.setCookie(SysConst.USER_TOKEN, userTokenStr, 60 * 60 * 24 * 1);
        return criteria.update(user);
    }

    /**
     * 根据本地token查询用户信息
     *
     * @param userToken
     * @return
     */
    public JSONObject findUserTokenByUser(String userToken) {
        return redisTpl.get(SysConst.REDIS_USER_INFO.concat(userToken));
    }
}
