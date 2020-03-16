package com.zk.device.service;

import com.zk.device.model.shiro.Menu;
import com.zk.device.model.shiro.Role;
import com.zk.device.model.shiro.User;

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

    /**
     * 获取用户的角色键
     *
     * @param userId
     * @return
     */
    List<String> findRoleKeyByUserId(Long userId);

    /**
     * 获取用户的菜单键
     *
     * @param userId
     * @return
     */
    List<String> findPermitByUserId(Long userId);

    /**
     * 根据用户名获取用户资料
     *
     * @param userName
     * @return
     */
    User selectSysUserByLoginName(String userName);

    /**
     * 加密密码
     *
     * @param userName 用户名
     * @param password 密码
     * @param salt     加密盐
     * @return
     */
    String encryptPassword(String userName, String password, String salt);
}
