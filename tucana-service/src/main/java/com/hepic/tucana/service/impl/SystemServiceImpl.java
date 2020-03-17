package com.hepic.tucana.service.impl;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.dao.SysMenuDao;
import com.hepic.tucana.dal.dao.SysRoleDao;
import com.hepic.tucana.dal.dao.SysUserDao;
import com.hepic.tucana.model.shiro.Menu;
import com.hepic.tucana.model.shiro.Role;
import com.hepic.tucana.model.shiro.User;
import com.hepic.tucana.service.SystemService;
import com.hepic.tucana.util.HttpClientUtil;
import com.hepic.tucana.util.model.HttpClientResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    SysMenuDao sysMenuDao;

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysUserDao sysUserDao;

    /**
     * 获取菜单列表
     *
     * @param menu
     * @return
     */
    public List<Menu> getMenuList(Menu menu) {
        List<Menu> result = new ArrayList<>();
        List<Menu> list = sysMenuDao.selectSysMenuListByModel(menu);
        result = list.stream().filter(p -> p.getParentId().intValue() == 0).collect(Collectors.toList());
        for (Menu item : result) {
            List<Menu> child = list.stream().filter(p -> p.getParentId().equals(item.getId())).collect(Collectors.toList());
            child.forEach(p -> {
                List<Menu> subChild = list.stream().filter(q -> q.getParentId().intValue() == p.getId().intValue()).collect(Collectors.toList());
                p.setChildren(subChild);
            });
            item.setChildren(child);
        }

        return result;
    }

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    @Override
    public int addMenu(Menu menu) {
        return sysMenuDao.insertSysMenu(menu);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Override
    public int deleteMenu(Long id) {
        return sysMenuDao.deleteById(id);
    }

    /**
     * 编辑菜单
     *
     * @param menu
     * @return
     */
    @Override
    public int editMenu(Menu menu) {
        return sysMenuDao.updateSysMenu(menu);
    }

    /**
     * 查询角色集合
     *
     * @param role
     * @return
     */
    @Override
    public List<Role> getRoleList(Role role) {
        return sysRoleDao.selectSysRoleListByModel(role);
    }

    /**
     * 新增角色集合
     *
     * @param role
     * @return
     */
    @Override
    public int addRole(Role role) {
        int result = sysRoleDao.insertSysRole(role);
        buildRoleMenu(role.getId(), role.getMenuIds());
        return result;
    }

    /**
     * 编辑角色集合
     *
     * @param role
     * @return
     */
    @Override
    public int editRole(Role role) {
        int result = sysRoleDao.updateSysRole(role);
        buildRoleMenu(role.getId(), role.getMenuIds());
        return result;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Override
    public int deleteRole(Long id) {
        return sysRoleDao.deleteById(id);
    }

    /**
     * 获取角色下的菜单列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Menu> getSysMenuByRoleId(Long roleId) {
        return sysMenuDao.selectSysMenuByRoleId(roleId);
    }

    /**
     * 构建角色菜单关系
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    public int buildRoleMenu(Long roleId, List<Long> menuIds) {
        int result = 0;
        sysMenuDao.deleteRoleMenu(roleId);
        if (CollectionUtils.isNotEmpty(menuIds)) {
            result = menuIds.size();
            menuIds.forEach(p -> sysMenuDao.insertRoleMenu(roleId, p));
        }
        return result;
    }

    /**
     * 查询用户集合
     *
     * @param user
     * @return
     */
    @Override
    public List<User> getUserList(User user) {
        return sysUserDao.selectSysUserListByModel(user);
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        int result = sysUserDao.insertSysUser(user);
        buildUserRole(user.getId(), user.getRoleIds());
        return result;
    }

    /**
     * 编辑用户
     *
     * @param user
     * @return
     */
    @Override
    public int editUser(User user) {
        int result = sysUserDao.updateSysUser(user);
        buildUserRole(user.getId(), user.getRoleIds());
        return result;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public int deleteUser(Long id) {
        return sysUserDao.deleteById(id);
    }

    /**
     * 获取用户下的角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> getSysRoleByUserId(Long userId) {
        return sysRoleDao.selectRoleByUserId(userId);
    }

    /**
     * 获取用户的角色键
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> findRoleKeyByUserId(Long userId) {
        return sysUserDao.findRoleKeyByUserId(userId);
    }

    /**
     * 获取用户的菜单键
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> findPermitByUserId(Long userId) {
        return sysUserDao.findPermitByUserId(userId);
    }

    /**
     * 根据用户名获取用户资料
     *
     * @param userName
     * @return
     */
    @Override
    public User selectSysUserByName(String userName) {
        return sysUserDao.selectSysUserByName(userName);
    }

    /**
     * 加密密码
     *
     * @param userName 用户名
     * @param password 密码
     * @param salt     加密盐
     * @return
     */
    public String encryptPassword(String userName, String password, String salt) {
        return new Md5Hash(userName + password + salt).toHex();
    }

    public User validateToken(String token) {
        User user = null;
        try {
            HttpClientResult httpClientResult = HttpClientUtil.get("http://a.device.com:5566/validateToken?token=" + token);
            if (httpClientResult != null && httpClientResult.getCode() == 200) {
                user = JSON.parseObject(httpClientResult.getContent(), User.class);
            }
        } catch (Exception e) {

        }
        return user;
    }

    /**
     * 构建用户角色关系
     *
     * @param userId
     * @param roleIds
     * @return
     */
    public int buildUserRole(Long userId, List<Long> roleIds) {
        int result = 0;
        sysRoleDao.deleteUserRole(userId);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            result = roleIds.size();
            roleIds.forEach(p -> sysRoleDao.insertUserRole(userId, p));
        }
        return result;
    }
}
