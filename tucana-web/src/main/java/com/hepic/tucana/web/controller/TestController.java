package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return testService.findUserById(1).getUserName();
    }

    @RequestMapping("/getAnswer")
    public String getAnswer(String name) {
        log.info("info", name);
        String result = JSON.toJSONString(testService.findAnswerByName(name));
        return result;
    }

    @RequestMapping("/getError")
    public String getError() throws Exception {
        try{
            Integer x= 1/0;
        }catch (Exception e){
            throw new Exception("asdasdad");
        }
        return null;
    }
}
