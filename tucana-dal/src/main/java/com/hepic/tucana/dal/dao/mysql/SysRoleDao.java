package com.hepic.tucana.dal.dao.mysql;

import java.util.List;

import com.hepic.tucana.dal.entity.authority.SysRole;

/**
 * 数据访问接口
 */
public interface SysRoleDao {

    List<SysRole> selectSysRoleListByModel(SysRole model);

    List<SysRole> selectRoleByUserId(Long userId);

    SysRole selectSysRoleById(Long id);

    int insertSysRole(SysRole model);

    int updateSysRole(SysRole model);

    int deleteById(Long id);
}