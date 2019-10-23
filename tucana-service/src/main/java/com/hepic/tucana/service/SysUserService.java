package com.hepic.tucana.service;

import com.hepic.tucana.model.shiro.SysUser;
import java.util.List;

/**
 *用户服务类
 */
public interface SysUserService {

    List<SysUser> selectSysUserListByModel(SysUser model);

    SysUser selectSysUserById(Long id);

    int insertSysUser(SysUser model);

    int updateSysUser(SysUser model);
}
