package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.LoggingEvent;
import com.hepic.tucana.model.log.LogListRequest;

import java.util.List;

public interface LoggingEventDao {

    List<LoggingEvent> selectByPage(LogListRequest request);

    Long selectCount(LogListRequest request);

}