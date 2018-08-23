package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.job.PageInfoSpider;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    PageInfoSpider pageInfoSpider;

    @GetMapping("/start")
    public String start() {
        CommonResponse<String> responseDto = new CommonResponse<>();
        try{
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Spider spider = pageInfoSpider.getSpider();
            if (spider.getStatus() == Spider.Status.Running) {
                responseDto.setMessage("已经启动");
            } else {
                spider.runAsync();
                responseDto.setMessage("启动成功");
            }
        }catch (Exception e){
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            System.out.println(JSON.toJSONString(e));
            log.error("JOB启动异常",e);
        }
        return JSON.toJSONString(responseDto);
    }

}
