package com.hepic.tucana.service.impl;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.dao.mysql.LoggingEventDao;
import com.hepic.tucana.dal.entity.mysql.LoggingEvent;
import com.hepic.tucana.model.common.PageListModel;
import com.hepic.tucana.model.log.LogListRequest;
import com.hepic.tucana.service.LogService;
import com.hepic.tucana.util.datetime.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/27.
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LoggingEventDao loggingEventDao;

    @Override
    public PageListModel<LoggingEvent> getLogList(LogListRequest request) {
        PageListModel<LoggingEvent> response = new PageListModel<>();
        try {
            request.setSkip(request.getPage() - 1);
            List<LoggingEvent> list = loggingEventDao.selectByPage(request);
            Long total = loggingEventDao.selectCount(request);
            if (!CollectionUtils.isEmpty(list)){
                list.forEach(p->p.setTimestmp(DateUtil.date2String(new Date(Long.valueOf(p.getTimestmp())))));
            }
            response.setList(list);
            response.setTotal(total);
        } catch (Exception e) {
            log.error("查询日志列表异常", e);
        }
        return response;
    }
}
