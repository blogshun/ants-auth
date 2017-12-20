package com.ants.auth.service;

import com.ants.auth.common.SysConst;
import com.ants.auth.entity.User;
import com.ants.common.annotation.service.Service;
import com.ants.common.annotation.service.Source;
import com.ants.common.exception.TipException;
import com.ants.common.utils.StrEncryptUtil;
import com.ants.core.holder.ClientHolder;
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

    /**
     * 系统用户登录业务
     *
     * @param username 登录账号
     * @param password 登录密码
     * @param code     验证码
     * @return
     */
    public int login(String username, String password, String code) {
        Object sessionCode = ClientHolder.getSession().getAttribute(SysConst.CODE_SESSION_NAME);
        if (code == null) throw new TipException("验证码不能为空!");
        Criteria<User> criteria = db.createCriteria(User.class);
        criteria.and("account", Condition.EQ, username)
                .and("password", Condition.EQ, StrEncryptUtil.md5(password));
        User user = criteria.find();
        if (!code.equalsIgnoreCase(String.valueOf(sessionCode))) return -1;
        if (user == null) return -2;
        //判断账号是否被锁定
        if (user.getIsLock() == 1) return -3;
        //存放session
        ClientHolder.getSession().setAttribute(SysConst.USER_SESSION_NAME, user);

        //修改登录记录
        user.setLoginCount(user.getLoginCount() + 1);
        user.setLoginIp(ClientHolder.getIp());
        user.setLastLoginTime(new Date());
        return criteria.update(user);
    }
}
