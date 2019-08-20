package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.dao.mysql.AnswerDao;
import com.hepic.tucana.dal.entity.mysql.Answer;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/qa")
public class ZhiHuController {

    @Autowired
    AnswerDao answerDao;

    @GetMapping("/getList")
    public String getList() {
        CommonResponse<Page<Answer>> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Page<Answer> list = answerDao.findAll(PageRequest.of(1, 20));
            responseDto.setResult(list);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("JOB启动异常", e);
        }
        return JSON.toJSONString(responseDto);
    }
}
