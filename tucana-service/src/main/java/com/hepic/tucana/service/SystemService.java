package com.hepic.tucana.service;

import com.hepic.tucana.model.shiro.SysMenu;
import com.hepic.tucana.model.shiro.SysRole;
import com.hepic.tucana.model.shiro.SysUser;

import java.util.List;

public interface SystemService {

    /**
     * 获取菜单列表
     *
     * @param sysMenu
     * @return
     */
    List<SysMenu> getMenuList(SysMenu sysMenu);

    /**
     * 新增菜单
     *
     * @param sysMenu
     * @return
     */
    int addMenu(SysMenu sysMenu);

    /**
     * 编辑菜单
     *
     * @param sysMenu
     * @return
     */
    int editMenu(SysMenu sysMenu);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    int deleteMenu(Long id);

    /**
     * 查询角色集合
     *
     * @param sysRole
     * @return
     */
    List<SysRole> getRoleList(SysRole sysRole);

    /**
     * 新增角色
     *
     * @param sysRole
     * @return
     */
    int addRole(SysRole sysRole);

    /**
     * 编辑角色
     *
     * @param sysRole
     * @return
     */
    int editRole(SysRole sysRole);

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    int deleteRole(Long id);

    /**
     * 获取角色下的菜单列表
     *
     * @param roleId
     * @return
     */
    List<SysMenu> getSysMenuByRoleId(Long roleId);

    /**
     * 查询用户集合
     *
     * @param sysUser
     * @return
     */
    List<SysUser> getUserList(SysUser sysUser);

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    int addUser(SysUser sysUser);

    /**
     * 编辑用户
     *
     * @param sysUser
     * @return
     */
    int editUser(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    int deleteUser(Long id);

    /**
     * 获取用户下的角色列表
     *
     * @param userId
     * @return
     */
    List<SysRole> getSysRoleByUserId(Long userId);
}
