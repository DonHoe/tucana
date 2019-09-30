package com.hepic.tucana.service;

import com.hepic.tucana.dal.dao.mysql.SysMenuDao;
import com.hepic.tucana.dal.dao.mysql.SysRoleDao;
import com.hepic.tucana.dal.dao.mysql.SysUserDao;
import com.hepic.tucana.dal.entity.authority.SysMenu;
import com.hepic.tucana.dal.entity.authority.SysRole;
import com.hepic.tucana.dal.entity.authority.SysUser;
import com.hepic.tucana.model.shiro.Permit;
import com.hepic.tucana.model.shiro.Role;
import com.hepic.tucana.model.shiro.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/12.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysMenuDao sysMenuDao;

    /**
     * 登录
     *
     * @param name     用户名
     * @param password 密码
     * @return 用户实体
     */
    @Override
    public SysUser login(String name, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(name);
        sysUser.setPassword(password);
        List<SysUser> list = sysUserDao.selectSysUserListByModel(sysUser);
        SysUser result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
        }
        return result;
    }

    /**
     * 获取用户登录相关信息
     *
     * @param id
     * @return
     */
    public User getUserInfo(Long id) {
        User userInfo = new User();
        SysUser sysUser = sysUserDao.selectSysUserById(id);
        if (sysUser == null) {
            return null;
        }
        userInfo.setId(sysUser.getId());
        userInfo.setLoginName(sysUser.getLoginName());
        userInfo.setName(sysUser.getUserName());
        List<SysRole> sysRoles = sysRoleDao.selectRoleByUserId(sysUser.getId());
        List<Role> roleList = sysRoles.stream().map(p -> {
            Role role = new Role();
            role.setId(p.getId());
            role.setKey(p.getKey());
            role.setName(p.getRoleName());
            return role;
        }).collect(Collectors.toList());
        for (Role roleItem : roleList) {
            List<SysMenu> sysMenus = sysMenuDao.selectSysMenuByRoleId(roleItem.getId());
            List<Permit> permitList = sysMenus.stream().map(p -> {
                Permit permit = new Permit();
                permit.setKey(p.getPermit());
                permit.setPath(p.getUrl());
                return permit;
            }).collect(Collectors.toList());
            roleItem.setPermitList(permitList);
        }
        userInfo.setRoleList(roleList);
        return userInfo;
    }
}
