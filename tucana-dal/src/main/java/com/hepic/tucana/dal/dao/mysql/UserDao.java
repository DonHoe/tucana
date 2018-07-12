package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.User;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/4/11.
 */
public interface UserDao {

    User findUserById(Integer id);

    User findUserByName(String name);

}
