package com.hepic.tucana.service;

import com.hepic.tucana.dal.dao.SysMenuDao;
import com.hepic.tucana.dal.dao.SysRoleDao;
import com.hepic.tucana.dal.dao.SysUserDao;
import com.hepic.tucana.model.shiro.SysUser;
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
    public SysUser login(String name, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(name);
        sysUser.setPassword(password);
        List<SysUser> list = sysUserDao.selectSysUserListByModel(sysUser);
        SysUser result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
        }
        return result;
    }
}
