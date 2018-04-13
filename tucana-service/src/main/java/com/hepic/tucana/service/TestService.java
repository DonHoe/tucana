package com.hepic.tucana.service;

import com.hepic.tucana.dal.entity.mysql.User;

/**
 * @author hd23973
 * @Title:
 * @Description:
 * @date 2018/4/13.
 */
public interface TestService {

    User findUserById(Integer id);

}
