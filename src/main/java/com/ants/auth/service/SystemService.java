package com.ants.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ants.auth.common.SysConst;
import com.ants.auth.entity.User;
import com.ants.auth.vo.LoginVO;
import com.ants.common.annotation.service.Autowired;
import com.ants.common.annotation.service.Service;
import com.ants.common.annotation.service.Source;
import com.ants.common.enums.EncType;
import com.ants.common.exception.TipException;
import com.ants.common.utils.GenUtil;
import com.ants.common.utils.StrEncryptUtil;
import com.ants.core.holder.ClientHolder;
import com.ants.plugin.cache.RedisTpl;
import com.ants.plugin.db.Db;
import com.ants.plugin.orm.Criteria;
import com.ants.plugin.orm.enums.Condition;

import java.util.Date;

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
        String password = loginVO.getPassword();
        String decryptStr = StrEncryptUtil.decrypt("_utravel12345678", EncType.AES, password);
        if (decryptStr == null) {
            return -2;
        }
        Criteria<User> criteria = db.createCriteria(User.class);
        criteria.and("account", Condition.EQ, loginVO.getUserName())
                .and("password", Condition.EQ, StrEncryptUtil.md5(decryptStr));
        User user = criteria.find();
        if (!code.equalsIgnoreCase(String.valueOf(sessionCode))) {
            return -1;
        }
        if (user == null) {
            return -2;
        }
        //判断账号是否被锁定
        if (user.getIsLock() == 1) {
            return -3;
        }

        //TODO 查询用户组织信息

        //TODO 查询用户资源信息

        //TODO 查询用户角色信息

        //存放session
        ClientHolder.getSession().setAttribute(SysConst.USER_SESSION_NAME, user);
        //修改登录记录
        user.setLoginCount(user.getLoginCount() + 1);
        user.setLoginIp(ClientHolder.getIp());
        user.setLastLoginTime(new Date());

        //存放redis
        String userTokenStr = GenUtil.makeTokenStr(user.getId(), user.getAccount()
                , user.getPassword()
                , System.currentTimeMillis());
        redisTpl.set(SysConst.REDIS_USER_INFO.concat(userTokenStr), JSON.toJSONString(user), 60 * 60 * 24 * 2);

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
