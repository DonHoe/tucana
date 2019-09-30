package com.hepic.tucana.service;

import com.hepic.tucana.dal.entity.authority.SysUser;
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
     * 登录
     *
     * @param name     用户名
     * @param password 密码
     * @return 用户实体
     */
    @Override
    public SysUser login(String name, String password) {
        SysUser sysUser = new SysUser();
        return sysUser;
    }

}
