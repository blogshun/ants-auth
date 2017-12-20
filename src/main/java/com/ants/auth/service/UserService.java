//package com.ants.auth.service;
//
//import com.acxy.ants.common.enums.DataSourceType;
//import com.acxy.ants.common.kit.FileKit;
//import com.acxy.ants.common.kit.RegKit;
//import com.acxy.ants.common.kit.StrEncryptKit;
//import com.acxy.ants.core.annotation.Service;
//import com.acxy.ants.core.annotation.Source;
//import com.acxy.ants.core.annotation.Tx;
//import com.acxy.ants.core.exception.TipException;
//import com.acxy.ants.core.kit.LogKit;
//import com.acxy.ants.core.plugin.db.Db;
//import com.acxy.ants.resful.bean.Page;
//import com.acxy.auth.entity.User;
//import com.acxy.auth.entity.UserOrg;
//import com.acxy.auth.entity.UserRole;
//import com.alibaba.fastjson.JSON;
//
//import java.io.File;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author MrShun
// * @version 1.0
// * @Date 2017-06-19
// */
//@Service
//public class UserService {
//
//    @Source(DataSourceType.DRUID)
//    private Db db;
//
//
//    public Page queryPage(Integer pageIndex, Integer pageSize, String sortField, String sortOrder, String filters, String tjKey, String keyValue) {
//        User user = null;
//        try {
//            if (filters != null)
//                user = JSON.parseObject(filters, User.class);
//            else
//                user = new User().orderBy(sortField, sortOrder);
//            if (tjKey != null && keyValue != null)
//                user.search(tjKey, keyValue);
//        } catch (Exception e) {
//            LogKit.error("conditional conversion error:{}", filters);
//        }
//        Page page = user.page(db, pageIndex, pageSize);
//        return page;
//    }
//
//    /**
//     * 根据用户ID查询用户信息包含角色，组织ID
//     *
//     * @param id 用户Id
//     * @return
//     */
//    public User find(Long id) {
//        User user = new User(id).find(db);
//        //填充用户角色信息
//        List<UserRole> userRoles = new UserRole(id).list(db);
//        String rolesStr = "", orgsStr = "";
//        if (userRoles != null && userRoles.size() != 0) {
//            int len = userRoles.size();
//            for (int i = 0; i < len; i++) {
//                rolesStr += userRoles.get(i).getRoleId();
//                if (i != len - 1)
//                    rolesStr += ",";
//            }
//
//        }
//        user.setRoles(rolesStr);
//
//        //查询填充用户组织信息
//        List<UserOrg> userOrgs = new UserOrg(id).list(db);
//        if (userOrgs != null && userOrgs.size() != 0) {
//            int len = userOrgs.size();
//            for (int i = 0; i < len; i++) {
//                orgsStr += userOrgs.get(i).getOrgId();
//                if (i != len - 1)
//                    orgsStr += ",";
//            }
//
//        }
//        user.setOrgs(orgsStr);
//        return user;
//    }
//
//    /**
//     * 根据用户ID查询角色或组织名称信息
//     *
//     * @param uid  用户ID
//     * @param type 查询条件 1/查询角色2/查询组织/全部查询
//     */
//    public List findRolesOrOrgs(Long uid, int type) {
//        if (uid == null) throw new TipException("用户ID参数不能为空!");
//        if (type == 1) {
//            return new UserRole(uid).label("role_id as roleId, r.role_name as roleName").leftJoin("left join sys_role r on r.id=role_id").list(db);
//        } else if (type == 2)
//            return new UserOrg(uid).label("org_id as orgId, o.org_name as orgName").leftJoin("left join sys_org o on o.id=org_id").list(db);
//        return null;
//    }
//
//    @Tx
//    public int save(User user) {
//        String account = user.getAccount();
//        if (account == null && "".equals(account.trim())) throw new TipException("用户账号不能为空!");
//        if (!RegKit.isENG_NUM_(account)) throw new TipException("用户必须是英文字母+数字+下划线组成!");
//        Long count = new User().and("account", account).count(db);
//        if (count > 0) return -1;
//        user.setLoginCount(0L);
//        user.setIsLock(0);
//        user.setPassword(StrEncryptKit.MD5(account));
//        user.setCreateTime(new Date());
//        user.setIsDelete(0);
//        //存储用户
//        Long uid = user.saveReturnKey(db);
//        //先清空用户对应角色, 在存储用户角色
//        String roles = user.getRoles();
//        saveUserRoles(uid, roles);
//
//        //先清空用户对应组织, 在存储用户组织
//        String orgs = user.getOrgs();
//        saveUserOrgs(uid, orgs);
//        return 1;
//    }
//
//    @Tx
//    public int update(User user) {
//        Long uid = user.getId();
//        if (uid == null) throw new TipException("用户ID不能为空!");
//        String account = user.getAccount();
//        if (account == null && "".equals(account.trim())) throw new TipException("用户账号不能为空!");
//        if (!RegKit.isENG_NUM_(account)) throw new TipException("用户必须是英文字母+数字+下划线组成!");
//        Long count = new User().and("account", account).and("id", "<>", uid).count(db);
//        if (count > 0) return -1;
//        user.setUpdateTime(new Date());
//        user.update(db);
//        //先清空用户对应角色, 在存储用户角色
//        String roles = user.getRoles();
//        saveUserRoles(uid, roles);
//
//        //先清空用户对应组织, 在存储用户组织
//        String orgs = user.getOrgs();
//        saveUserOrgs(uid, orgs);
//        return 1;
//    }
//
//    /**
//     * 删除用户并且删除用户头像
//     *
//     * @param ids
//     * @param filePath
//     * @return
//     */
//    @Tx
//    public int delete(Long[] ids, String filePath) {
//        if (ids == null) throw new TipException("ids 参数不能为空!");
//        int res = 0;
//        for (Long id : ids) {
//            //只查询用户头像信息
//            User user = new User(id).label("avatar").find(db);
//            //根据ID删除用户
//            user.deleteById(db, id);
//            String fileName = user.getAvatar();
//            if (fileName != null && !"".equals(fileName))
//                FileKit.delete(filePath + File.separator + fileName);
//            res++;
//        }
//        return res;
//    }
//
//    public int checkLock(Long id, int lock) {
//        User user = new User(id);
//        user.setIsLock(lock);
//        return user.update(db);
//    }
//
//
//    /**
//     * 循环存储用户角色
//     *
//     * @param uid   用户ID
//     * @param roles 角色字符串
//     */
//    private void saveUserRoles(Long uid, String roles) {
//        if (roles == null) return;
//        //清空用户对应角色
//        UserRole userRole = new UserRole(uid);
//        userRole.delete(db);
//        if ("".equals(roles.trim())) return;
//        //循环存储用户角色
//        for (String role : roles.split(",")) {
//            userRole.setRoleId(Integer.parseInt(role));
//            userRole.save(db);
//        }
//    }
//
//    /**
//     * 循环存储用户组织
//     *
//     * @param uid  用户ID
//     * @param orgs 角色字符串
//     */
//    private void saveUserOrgs(Long uid, String orgs) {
//        if (orgs == null) return;
//        //清空用户对应组织
//        UserOrg userOrg = new UserOrg(uid);
//        userOrg.delete(db);
//        if ("".equals(orgs.trim())) return;
//        //循环存储用户组织
//        for (String org : orgs.split(",")) {
//            userOrg.setOrgId(Integer.parseInt(org));
//            userOrg.save(db);
//        }
//    }
//}
