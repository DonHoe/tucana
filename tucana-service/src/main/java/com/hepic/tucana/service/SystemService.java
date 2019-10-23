package com.hepic.tucana.service;

import com.hepic.tucana.model.shiro.Menu;
import com.hepic.tucana.model.shiro.Role;
import com.hepic.tucana.model.shiro.User;

import java.util.List;

public interface SystemService {

    /**
     * 获取菜单列表
     *
     * @param menu
     * @return
     */
    List<Menu> getMenuList(Menu menu);

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    int addMenu(Menu menu);

    /**
     * 编辑菜单
     *
     * @param menu
     * @return
     */
    int editMenu(Menu menu);

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
     * @param role
     * @return
     */
    List<Role> getRoleList(Role role);

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 编辑角色
     *
     * @param role
     * @return
     */
    int editRole(Role role);

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
    List<Menu> getSysMenuByRoleId(Long roleId);

    /**
     * 查询用户集合
     *
     * @param user
     * @return
     */
    List<User> getUserList(User user);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 编辑用户
     *
     * @param user
     * @return
     */
    int editUser(User user);

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
    List<Role> getSysRoleByUserId(Long userId);
}
