package cn.jants.auth.service;

import cn.jants.auth.entity.User;
import cn.jants.auth.entity.UserOrg;
import cn.jants.auth.entity.UserRole;
import cn.jants.auth.generate.*;
import cn.jants.common.annotation.service.Service;
import cn.jants.common.annotation.service.Source;
import cn.jants.common.annotation.service.Tx;
import cn.jants.common.bean.Log;
import cn.jants.common.bean.Page;
import cn.jants.common.exception.TipException;
import cn.jants.common.utils.FileUtil;
import cn.jants.common.utils.RegexUtil;
import cn.jants.common.utils.StrEncryptUtil;
import cn.jants.common.utils.StrUtil;
import cn.jants.plugin.db.Db;
import cn.jants.plugin.orm.Criteria;
import cn.jants.plugin.orm.enums.Condition;
import cn.jants.plugin.orm.enums.OrderBy;
import cn.jants.plugin.orm.enums.Relation;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author MrShun
 * @version 1.0
 * @Date 2017-06-19
 */
@Service
public class UserService {

    @Source
    private Db db;

    public Page queryPage(Integer pageIndex, Integer pageSize, String sortField, String sortOrder, String filters, String tjKey, String keyValue) {
        try {
            Criteria criteria = db.createCriteria(User.class);
            if (filters != null) {
                criteria.filters(filters);
            }
            criteria.orderBy(OrderBy.valueOf(sortOrder), sortField);
            if (StrUtil.notBlank(tjKey, keyValue)) {
                criteria.and(tjKey, Condition.LIKE, "%".concat(keyValue).concat("%"));
            }
            return criteria.findPage(pageIndex, pageSize);
        } catch (Exception e) {
            Log.error("conditional conversion error:{}", filters);
            return null;
        }
    }

    /**
     * 根据用户ID查询用户信息包含角色，组织ID
     *
     * @param id 用户Id
     * @return
     */

    public User find(Long id) {
        Criteria<User> criteria = db.createCriteria(User.class);
        User user = criteria.findById(id);
        //填充用户角色信息
        Criteria urCriteria = db.createCriteria(UserRole.class);
        urCriteria.and(QUserRole.USER_ID, Condition.EQ, id);
        List<UserRole> userRoles = urCriteria.findList();
        String rolesStr = "", orgsStr = "";
        if (userRoles != null && userRoles.size() != 0) {
            int len = userRoles.size();
            for (int i = 0; i < len; i++) {
                rolesStr += userRoles.get(i).getRoleId();
                if (i != len - 1) {
                    rolesStr += ",";
                }
            }

        }
        user.setRoles(rolesStr);

        //查询填充用户组织信息
        Criteria uoCriteria = db.createCriteria(UserOrg.class);
        uoCriteria.and(QUserOrg.USER_ID, Condition.EQ, id);
        List<UserOrg> userOrgs = uoCriteria.findList();
        if (userOrgs != null && userOrgs.size() != 0) {
            int len = userOrgs.size();
            for (int i = 0; i < len; i++) {
                orgsStr += userOrgs.get(i).getOrgId();
                if (i != len - 1) {
                    orgsStr += ",";
                }
            }

        }
        user.setOrgs(orgsStr);
        return user;
    }

    /**
     * 根据用户ID查询角色或组织名称信息
     *
     * @param uid  用户ID
     * @param type 查询条件 1/查询角色2/查询组织/全部查询
     */
    public List findRolesOrOrgs(Long uid, int type) {
        if (uid == null) {
            throw new TipException("用户ID参数不能为空!");
        }
        if (type == 1) {
            Criteria criteria = db.createCriteria(UserRole.class);
            criteria.and(QUserRole.USER_ID, Condition.EQ, uid);
            criteria.label(QRole._ID, QRole._ROLE_NAME);
            criteria.addRelation(Relation.lEFT, QRole.TABLE, QUserRole.ROLE_ID, QRole._ID);
            return criteria.findList();
        } else if (type == 2) {
            Criteria criteria = db.createCriteria(UserOrg.class);
            criteria.and(QUserOrg.USER_ID, Condition.EQ, uid);
            criteria.label(QOrg._ID, QOrg._ORG_NAME);
            criteria.addRelation(Relation.lEFT, QOrg.TABLE, QUserOrg.ORG_ID, QOrg._ID);
            return criteria.findList();
        }
        return null;
    }

    @Tx
    public int save(User user) {
        String account = user.getAccount();
        if (account == null && "".equals(account.trim())) {
            throw new TipException("用户账号不能为空!");
        }
        if (!RegexUtil.isENG_NUM_(account)) {
            throw new TipException("用户必须是英文字母+数字+下划线组成!");
        }
        Criteria criteria = db.createCriteria(User.class);
        criteria.and(QUser.ACCOUNT, Condition.EQ, account);
        Integer count = criteria.count();
        if (count > 0) {
            return -1;
        }
        user.setLoginCount(0L);
        user.setIsLock(0);
        user.setPassword(StrEncryptUtil.md5(account));
        user.setCreateTime(new Date());
        user.setIsDelete(0);
        //存储用户
        Long uid = criteria.saveReturnKey(user);
        //先清空用户对应角色, 在存储用户角色
        String roles = user.getRoles();
        saveUserRoles(uid, roles);

        //先清空用户对应组织, 在存储用户组织
        String orgs = user.getOrgs();
        saveUserOrgs(uid, orgs);
        return 1;
    }

    @Tx
    public int update(User user) {
        Long uid = user.getId();
        if (uid == null) {
            throw new TipException("用户ID不能为空!");
        }
        String account = user.getAccount();
        if (account == null && "".equals(account.trim())) {
            throw new TipException("用户账号不能为空!");
        }
        if (!RegexUtil.isENG_NUM_(account)) {
            throw new TipException("用户必须是英文字母+数字+下划线组成!");
        }
        Criteria criteria = db.createCriteria(User.class);
        criteria.and(QUser.ACCOUNT, Condition.EQ, account);
        criteria.and(QUser.ID, Condition.NE, uid);
        Integer count = criteria.count();
        if (count > 0) {
            return -1;
        }
        user.setUpdateTime(new Date());
        criteria.update(user);
        //先清空用户对应角色, 在存储用户角色
        String roles = user.getRoles();
        saveUserRoles(uid, roles);

        //先清空用户对应组织, 在存储用户组织
        String orgs = user.getOrgs();
        saveUserOrgs(uid, orgs);
        return 1;
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return
     */
    public int updateUserInfo(User user) {
        Long uid = user.getId();
        if (uid == null) {
            throw new TipException("用户ID不能为空!");
        }
        Criteria criteria = db.createCriteria(User.class);
        criteria.and(QUser.ID, Condition.EQ, uid);
        return criteria.update(user);
    }

    /**
     * 删除用户并且删除用户头像
     *
     * @param ids
     * @param filePath
     * @return
     */
    @Tx
    public int delete(Long[] ids, String filePath) {
        if (ids == null) {
            throw new TipException("ids 参数不能为空!");
        }
        int res = 0;
        Criteria<User> criteria = db.createCriteria(User.class);
        criteria.label(QUser.AVATAR);
        for (Long id : ids) {
            //只查询用户头像信息
            User u = criteria.findById(id);
            //根据ID删除用户
            criteria.deleteById(id);
            String fileName = u.getAvatar();
            if (fileName != null && !"".equals(fileName)) {
                FileUtil.delete(filePath + File.separator + fileName);
            }
            res++;
        }
        return res;
    }

    public int checkLock(Long id, int lock) {
        User user = new User(id);
        user.setIsLock(lock);
        Criteria criteria = db.createCriteria(User.class);
        return criteria.update(user);
    }


    /**
     * 循环存储用户角色
     *
     * @param uid   用户ID
     * @param roles 角色字符串
     */
    private void saveUserRoles(Long uid, String roles) {
        if (roles == null) {
            return;
        }
        //清空用户对应角色
        Criteria criteria = db.createCriteria(UserRole.class);
        criteria.and(QUserRole.USER_ID, Condition.EQ, uid);
        criteria.delete();
        if ("".equals(roles.trim())) {
            return;
        }
        //循环存储用户角色
        for (String role : roles.split(",")) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(Integer.parseInt(role));
            userRole.setUserId(uid);
            criteria.save(userRole);
        }
    }

    /**
     * 循环存储用户组织
     *
     * @param uid  用户ID
     * @param orgs 角色字符串
     */
    @Tx
    private void saveUserOrgs(Long uid, String orgs) {
        if (orgs == null) {
            return;
        }
        //清空用户对应组织
        Criteria criteria = db.createCriteria(UserOrg.class);
        criteria.and(QUserOrg.USER_ID, Condition.EQ, uid);
        criteria.delete();
        if ("".equals(orgs.trim())) {
            return;
        }
        //循环存储用户组织
        for (String org : orgs.split(",")) {
            UserOrg userOrg = new UserOrg();
            userOrg.setOrgId(Integer.parseInt(org));
            userOrg.setUserId(uid);
            criteria.save(userOrg);
        }
    }
}
