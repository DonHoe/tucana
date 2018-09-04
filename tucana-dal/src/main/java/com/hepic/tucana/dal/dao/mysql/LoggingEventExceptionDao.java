package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.LoggingEventException;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/9/4.
 */
public interface LoggingEventExceptionDao {

    @Select("select i,trace_line as traceLine from logging_event_exception where event_id=#{eventId}")
    List<LoggingEventException> getEventException(int eventId);
}
