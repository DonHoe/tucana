package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/6/5.
 */
@Repository
public interface AnswerDao extends JpaRepository<Answer, Long> {
}
