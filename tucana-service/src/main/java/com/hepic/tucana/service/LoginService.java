package com.hepic.tucana.service;

import com.hepic.tucana.dal.entity.authority.SysUser;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/12.
 */
public interface LoginService {
    SysUser login(String name, String password);
}
