package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.sqlite.Answer;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/get")
    public String get() {
        log.warn("debug", "filter1", "filter2");
        log.info("info", "filter1");
        CommonResponse<String> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        response.setResult(testService.findUserById(1).getUserName());
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getAnswer")
    public String getAnswer(String name) {
        log.info("info", name);
        CommonResponse<List<Answer>> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        response.setResult(testService.findAnswerByName(name));
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getError")
    public String getError() throws Exception {
        try {
            Integer x = 1 / 0;
        } catch (Exception e) {
            throw new Exception("asdasdad");
        }
        return null;
    }
}
