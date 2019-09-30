package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.authority.SysUser;
import java.util.List;

/**
 *
 */
public interface SysUserDao {

    List<SysUser> selectSysUserListByModel(SysUser model);

    SysUser selectSysUserById(String id);

    int insertSysUser(SysUser model);

    int updateSysUser(SysUser model);

}
