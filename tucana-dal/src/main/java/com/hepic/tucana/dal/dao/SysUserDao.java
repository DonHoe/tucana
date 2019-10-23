package com.hepic.tucana.dal.dao;

import com.hepic.tucana.model.shiro.SysUser;
import java.util.List;

/**
 *
 */
public interface SysUserDao {

    List<SysUser> selectSysUserListByModel(SysUser model);

    SysUser selectSysUserById(Long id);

    int insertSysUser(SysUser model);

    int updateSysUser(SysUser model);

    int deleteById(Long id);

}
