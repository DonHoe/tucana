package com.hepic.tucana.service;

import com.hepic.tucana.dal.dao.mysql.LoginDao;
import com.hepic.tucana.dal.entity.authority.Role;
import com.hepic.tucana.dal.entity.authority.SysUser;
import com.hepic.tucana.dal.entity.authority.User;
import com.hepic.tucana.service.LoginService;
import com.hepic.tucana.util.encryption.Md5;
import com.hepic.tucana.util.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/12.
 */
@Service
public class LoginServiceImpl implements LoginService {

    /**
     * 用户表数据访问
     */
    @Autowired
    private LoginDao loginDao;

    /**
     * 查询用户信息
     *
     * @param name
     * @return
     */
    @Override
    public User findUserByName(String name) {
        User user = loginDao.findUserByName(name);
        if (user == null) {
            return null;
        }
        user.setRoles(loginDao.findRoleByUserId(user.getId()));
        for (Role role : user.getRoles()) {
            role.setPermissions(loginDao.findPermissionByRoleId(role.getId()));
        }
        return user;
    }


    /**
     * 登录
     *
     * @param name     用户名
     * @param password 密码
     * @return 用户实体
     */
    @Override
    public SysUser login(String name, String password) {
        SysUser sysUser = loginDao.findUserByName(name);
        if (sysUser == null || sysUser.getId() < 1) {
            throw new BaseException(2001, "找不到用户");
        }
        if (!Md5.encode(password, Md5.encryptionKey).equals(sysUser.getPassword())) {
            throw new BaseException(2002, "密码错误");
        }
        return sysUser;
    }

}
