package com.hepic.tucana.service;

import com.hepic.tucana.dal.dao.mysql.SysMenuDao;
import com.hepic.tucana.dal.dao.mysql.SysRoleDao;
import com.hepic.tucana.dal.dao.mysql.SysUserDao;
import com.hepic.tucana.dal.entity.authority.SysMenu;
import com.hepic.tucana.dal.entity.authority.SysRole;
import com.hepic.tucana.dal.entity.authority.SysUser;
import org.apache.commons.collections.CollectionUtils;
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
     * @param sysMenu
     * @return
     */
    public List<SysMenu> getMenuList(SysMenu sysMenu) {
        List<SysMenu> result = new ArrayList<>();
        List<SysMenu> list = sysMenuDao.selectSysMenuListByModel(sysMenu);
        result = list.stream().filter(p -> p.getParentId().intValue() == 0).collect(Collectors.toList());
        for (SysMenu item : result) {
            List<SysMenu> child = list.stream().filter(p -> p.getParentId().equals(item.getId())).collect(Collectors.toList());
            child.forEach(p -> {
                List<SysMenu> subChild = list.stream().filter(q -> q.getParentId().intValue() == p.getId().intValue()).collect(Collectors.toList());
                p.setChildren(subChild);
            });
            item.setChildren(child);
        }

        return result;
    }

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    @Override
    public int addMenu(SysMenu sysMenu) {
        return sysMenuDao.insertSysMenu(sysMenu);
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
     * @param sysMenu
     * @return
     */
    @Override
    public int editMenu(SysMenu sysMenu) {
        return sysMenuDao.updateSysMenu(sysMenu);
    }

    /**
     * 查询角色集合
     *
     * @param sysRole
     * @return
     */
    @Override
    public List<SysRole> getRoleList(SysRole sysRole) {
        return sysRoleDao.selectSysRoleListByModel(sysRole);
    }

    /**
     * 新增角色集合
     *
     * @param sysRole
     * @return
     */
    @Override
    public int addRole(SysRole sysRole) {
        int result = sysRoleDao.insertSysRole(sysRole);
        buildRoleMenu(sysRole.getId(), sysRole.getMenuIds());
        return result;
    }

    /**
     * 编辑角色集合
     *
     * @param sysRole
     * @return
     */
    @Override
    public int editRole(SysRole sysRole) {
        int result = sysRoleDao.updateSysRole(sysRole);
        buildRoleMenu(sysRole.getId(), sysRole.getMenuIds());
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
    public List<SysMenu> getSysMenuByRoleId(Long roleId) {
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
     * @param sysUser
     * @return
     */
    @Override
    public List<SysUser> getUserList(SysUser sysUser) {
        return sysUserDao.selectSysUserListByModel(sysUser);
    }

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    @Override
    public int addUser(SysUser sysUser) {
        int result = sysUserDao.insertSysUser(sysUser);
        buildUserRole(sysUser.getId(), sysUser.getRoleIds());
        return result;
    }

    /**
     * 编辑用户
     *
     * @param sysUser
     * @return
     */
    @Override
    public int editUser(SysUser sysUser) {
        int result = sysUserDao.updateSysUser(sysUser);
        buildUserRole(sysUser.getId(), sysUser.getRoleIds());
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
    public List<SysRole> getSysRoleByUserId(Long userId) {
        return sysRoleDao.selectRoleByUserId(userId);
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
