package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.service.TestService;
import com.hepic.tucana.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/get")
    public String get() {
        LoggerUtil.warn("debug");
        LoggerUtil.info("info");
        return testService.findUserById(1).getUserName();
    }

    @RequestMapping("/getAnswer")
    public String getAnswer(String name) {
        String result = JSON.toJSONString(testService.findAnswerByName(name));
        return result;
    }
}
