package com.hepic.tucana.dal.dao.mysql;

import java.util.List;

import com.hepic.tucana.dal.entity.authority.SysRole;

/**
 * 数据访问接口
 */
public interface SysRoleDao {

    List<SysRole> selectSysRoleListByModel(SysRole model);

    SysRole selectSysRoleById(String id);

    int insertSysRole(SysRole model);

    int updateSysRole(SysRole model);

}