package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.dao.mongo.MongoDao;
import com.hepic.tucana.dal.entity.sqlite.Answer;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    MongoDao mongoDao;

    @RequestMapping("/get")
    public String get() {
        CommonResponse<String> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        response.setResult(testService.findUserById(1).getUserName());
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getAnswer")
    public String getAnswer(String name) {
        CommonResponse<List<Answer>> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        response.setResult(testService.findAnswerByName(name));
        return JSON.toJSONString(response);
    }

    @RequestMapping("/addAnswer")
    public String addAnswer() {
        CommonResponse<Integer> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        //mongoDao.addData();
        response.setResult(1);
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getList")
    public String getList() {
        CommonResponse<List<Map<String,Object>>> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        response.setResult(mongoDao.getList());
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getError")
    public String getError(HttpServletRequest request) throws Exception {
        try {
            Integer x = 1 / 0;
        } catch (Exception e) {
            throw new Exception("test error");
        }
        return null;
    }
}
