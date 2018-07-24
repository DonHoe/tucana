package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.UserDao;
import com.hepic.tucana.dal.entity.mysql.User;
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
    private UserDao userDao;

    /**
     * 登录
     *
     * @param name     用户名
     * @param password 密码
     * @return 用户实体
     */
    @Override
    public User login(String name, String password) {
        User user = userDao.findUserByName(name);
        if (user == null || user.getUserId() < 1) {
            throw new BaseException(2001, "找不到用户");
        }
        if (!Md5.encode(password,Md5.encryptionKey).equals(user.getPassword())){
            throw new BaseException(2002, "密码错误");
        }
        return user;
    }

}
