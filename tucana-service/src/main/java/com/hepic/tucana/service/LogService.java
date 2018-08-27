package com.hepic.tucana.service;

import com.hepic.tucana.dal.entity.mysql.LoggingEvent;
import com.hepic.tucana.model.common.PageListModel;
import com.hepic.tucana.model.log.LogListRequest;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/27.
 */
public interface LogService {

    PageListModel<LoggingEvent> getLogList(LogListRequest request);
}
