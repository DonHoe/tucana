package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.SysMenuDao;
import com.hepic.tucana.dal.dao.SysRoleDao;
import com.hepic.tucana.dal.dao.SysUserDao;
import com.hepic.tucana.model.shiro.User;
import com.hepic.tucana.service.LoginService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/12.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysRoleDao sysRoleDao;

    @Autowired
    SysMenuDao sysMenuDao;

    /**
     * 登录
     *
     * @param name     用户名
     * @param password 密码
     * @return 用户实体
     */
    @Override
    public User login(String name, String password) {
        User user = new User();
        user.setUserName(name);
        user.setPassword(password);
        List<User> list = sysUserDao.selectSysUserListByModel(user);
        User result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
        }
        return result;
    }
}
