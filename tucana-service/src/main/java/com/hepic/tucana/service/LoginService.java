package com.hepic.tucana.service;

import com.hepic.tucana.model.authority.SysUser;
import com.hepic.tucana.model.authority.User;

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
