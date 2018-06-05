package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.UserDao;
import com.hepic.tucana.dal.dao.sqlite.AnswerDao;
import com.hepic.tucana.dal.entity.mysql.User;
import com.hepic.tucana.dal.entity.sqlite.Answer;
import com.hepic.tucana.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hd23973
 * @Title:
 * @Description:
 * @date 2018/4/13.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AnswerDao answerDao;

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public List<Answer> findAnswerByName(String name) {
        return answerDao.getListByName(name);
    }
}
