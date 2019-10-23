package com.hepic.tucana.dal.dao;

import java.util.List;

import com.hepic.tucana.model.shiro.Role;
import org.apache.ibatis.annotations.Param;

/**
 * 数据访问接口
 */
public interface SysRoleDao {

    List<Role> selectSysRoleListByModel(Role model);

    List<Role> selectRoleByUserId(Long userId);

    List<Role> selectSysRoleByUserId(Long userId);

    int deleteUserRole(Long userId);

    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    Role selectSysRoleById(Long id);

    int insertSysRole(Role model);

    int updateSysRole(Role model);

    int deleteById(Long id);
}