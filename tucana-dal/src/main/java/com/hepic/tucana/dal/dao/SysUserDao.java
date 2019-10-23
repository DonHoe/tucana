package com.hepic.tucana.dal.dao;

import com.hepic.tucana.model.shiro.User;

import java.util.List;

/**
 *
 */
public interface SysUserDao {

    List<User> selectSysUserListByModel(User model);

    User selectSysUserById(Long id);

    List<String> findRoleKeyByUserId(Long userId);

    List<String> findPermitByUserId(Long userId);

    User selectSysUserByName(String userName);

    int insertSysUser(User model);

    int updateSysUser(User model);

    int deleteById(Long id);

}
