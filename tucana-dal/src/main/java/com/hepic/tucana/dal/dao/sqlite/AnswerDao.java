package com.hepic.tucana.dal.dao.sqlite;

import com.hepic.tucana.dal.entity.sqlite.Answer;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/6/5.
 */
public interface AnswerDao {

    @Select("SELECT * FROM Answer limit 100;\n")
    List<Answer> getList();

}
