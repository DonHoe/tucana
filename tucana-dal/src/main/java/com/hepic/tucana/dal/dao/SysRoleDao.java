package com.hepic.tucana.dal.dao;

import java.util.List;

import com.hepic.tucana.model.shiro.SysRole;
import org.apache.ibatis.annotations.Param;

/**
 * 数据访问接口
 */
public interface SysRoleDao {

    List<SysRole> selectSysRoleListByModel(SysRole model);

    List<SysRole> selectRoleByUserId(Long userId);

    List<SysRole> selectSysRoleByUserId(Long userId);

    int deleteUserRole(Long userId);

    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    SysRole selectSysRoleById(Long id);

    int insertSysRole(SysRole model);

    int updateSysRole(SysRole model);

    int deleteById(Long id);
}