package com.hepic.tucana.dal.dao.sqlite;

import com.hepic.tucana.dal.entity.sqlite.Answer;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/6/5.
 */
public interface AnswerDao {

    List<Answer> getListByName(String name);

}
