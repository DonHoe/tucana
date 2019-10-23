package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.dao.AnswerDao;
import com.hepic.tucana.model.dal.Answer;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/qa")
public class ZhiHuController extends BaseController {

    @Autowired
    AnswerDao answerDao;

    @GetMapping("/getList")
    public String getList() {
        CommonResponse<List<Answer>> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            List<Answer> list = answerDao.getList();
            responseDto.setResult(list);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("JOB启动异常", e);
        }
        return JSON.toJSONString(responseDto);
    }
}
