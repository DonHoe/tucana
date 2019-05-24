package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.dao.sqlite.AnswerDao;
import com.hepic.tucana.dal.entity.sqlite.Answer;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    AnswerDao answerDao;

    @RequestMapping("/get")
    public String get() {
        CommonResponse<String> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        response.setResult("");
        return JSON.toJSONString(response);
    }

    @RequestMapping("/getAnswer")
    public String getAnswer(String name) {
        CommonResponse<List<Answer>> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_1000);
        response.setResult(testService.getList(name));
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
