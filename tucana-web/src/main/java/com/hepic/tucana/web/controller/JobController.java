package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.job.PageInfoSpider;
import com.hepic.tucana.model.SpiderConfig;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.impl.SpiderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

import java.util.List;

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

    @Autowired
    SpiderServiceImpl spiderServiceImpl;

    @GetMapping("/start")
    public String start() {
        CommonResponse<String> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Spider spider = pageInfoSpider.getSpider();
            if (spider.getStatus() == Spider.Status.Running) {
                responseDto.setMessage("已经启动");
            } else {
                spider.runAsync();
                responseDto.setMessage("启动成功");
            }
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("JOB启动异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    /**
     * 获取配置列表
     *
     * @return
     */
    @GetMapping("getSpiderList")
    public String getSpiderList() {
        CommonResponse<List<SpiderConfig>> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            List<SpiderConfig> list = spiderServiceImpl.getJobConfigList();
            responseDto.setResult(list);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列表异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    /**
     * 获取配置列表
     *
     * @return
     */
    @GetMapping("saveSpiderConfig")
    public String saveSpiderConfig(SpiderConfig spiderConfig) {
        CommonResponse<Integer> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = spiderServiceImpl.saveSpiderConfig(spiderConfig);
            responseDto.setResult(result);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("配置异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

}
