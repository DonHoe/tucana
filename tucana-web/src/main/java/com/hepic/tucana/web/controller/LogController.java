package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.mysql.LoggingEvent;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.common.PageListModel;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.model.log.LogListRequest;
import com.hepic.tucana.service.LogService;
import com.hepic.tucana.util.datetime.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/27.
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("/getList")
    public String getLogList(LogListRequest request) {
        CommonResponse<PageListModel<LoggingEvent>> response = new CommonResponse<>();
        log.info("请求参数:" + JSON.toJSONString(request));
        try {
            if (StringUtils.isBlank(request.getStartTime())) {
                request.setStartTime(String.valueOf(DateUtil.addMinute(new Date(), -30).getTime()));
            }
            if (StringUtils.isBlank(request.getEndTime())) {
                request.setEndTime(String.valueOf(new Date().getTime()));
            }
            if (request.getRow() == null || request.getRow() < 1) {
                request.setRow(1);
            }
            if (request.getPage() == null || request.getPage() < 1) {
                request.setPage(1);
            }
            PageListModel<LoggingEvent> result = logService.getLogList(request);
            response.setResult(result);
            response.setResponseEnum(ResponseEnum.Code_1000);
        } catch (Exception e) {
            log.error("查询异常", e);
            response.setResponseEnum(ResponseEnum.Code_999);
        }
        String strResponse = JSON.toJSONString(response);
        //log.info("响应内容:" + strResponse);
        return strResponse;
    }

}
