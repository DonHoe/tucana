package com.hepic.tucana.service;

import com.hepic.tucana.dal.dao.mysql.SysUserDao;
import com.hepic.tucana.dal.entity.authority.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    @Override
    public List<SysUser> selectSysUserListByModel(SysUser model) {
        return sysUserDao.selectSysUserListByModel(model);
    }

    @Override
    public SysUser selectSysUserById(String id) {
        return sysUserDao.selectSysUserById(id);
    }

    @Override
    public int insertSysUser(SysUser model) {
        return sysUserDao.insertSysUser(model);
    }

    @Override
    public int updateSysUser(SysUser model) {
        return sysUserDao.updateSysUser(model);
    }
}
