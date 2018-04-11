package com.hepic.dao.mysql;

import com.hepic.entity.mysql.User;

/**
 * @author hd23973
 * @Title:
 * @Description:
 * @date 2018/4/11.
 */
public interface UserDao {

    User findUserById(Integer id);

}
