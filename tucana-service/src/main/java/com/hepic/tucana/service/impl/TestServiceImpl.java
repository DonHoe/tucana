package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.AnswerDao;
import com.hepic.tucana.model.dal.Answer;
import com.hepic.tucana.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/4/13.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private AnswerDao answerDao;

    @Override
    public List<Answer> getList(String name) {
        return answerDao.getList();
    }
}
