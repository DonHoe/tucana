package com.zk.device.service;

import com.zk.device.model.shiro.User;

/**
 * @author device
 * @Title:
 * @Description:
 * @date 2018/7/12.
 */
public interface LoginService {

    User login(String name, String password);

}
