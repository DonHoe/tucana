package com.hepic.tucana.service;

import com.hepic.tucana.model.dal.Answer;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/4/13.
 */
public interface TestService {

    List<Answer> getList(String name);

}
