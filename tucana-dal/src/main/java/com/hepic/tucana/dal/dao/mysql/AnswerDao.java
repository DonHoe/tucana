package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.Answer;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/6/5.
 */
public interface AnswerDao {

    @Select(" SELECT * " +
            " FROM answer WHERE 1 = 1 ORDER BY vote DESC limit 100; ")
    @Results(id = "answerResults", value = {
            @Result(property = "authorId", column = "author_id"),
            @Result(property = "questionId", column = "question_id"),
            @Result(property = "questionName", column = "question_name"),
            @Result(property = "dataFlag", column = "data_flag"),
            @Result(property = "collectionId", column = "collection_id")
    })
    List<Answer> getList();

}
