package com.hepic.tucana.service;

import com.hepic.tucana.dal.entity.authority.SysUser;
import com.hepic.tucana.dal.entity.authority.User;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/12.
 */
public interface LoginService {

    User findUserByName(String name);

    SysUser login(String name, String password);
}
