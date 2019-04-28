package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.job.PageInfoSpider;
import com.hepic.tucana.model.SpiderConfig;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.common.DynamicTableList;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.impl.SpiderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.Map;

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
    @PostMapping("saveSpiderConfig")
    public String saveSpiderConfig(@RequestBody SpiderConfig spiderConfig) {
        CommonResponse<Integer> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Integer result;
            if (spiderConfig.getId() < 1) {
                result = spiderServiceImpl.addSpiderConfig(spiderConfig);
            } else {
                result = spiderServiceImpl.modifySpiderConfig(spiderConfig);
            }

            responseDto.setResult(result);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("配置异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    /**
     * 删除主配置
     *
     * @return
     */
    @GetMapping("removeSpiderConfig")
    public String removeSpiderConfig(String key) {
        CommonResponse<Integer> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            spiderServiceImpl.removeSpider(key);
            responseDto.setResult(1);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("配置异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    /**
     * 启动
     *
     * @param key
     * @return
     */
    @GetMapping("startSpider")
    public String startSpider(String key) {
        CommonResponse<Integer> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = spiderServiceImpl.startSpider(key);
            responseDto.setResult(result);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("配置异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    /**
     * 暂停
     *
     * @param key
     * @return
     */
    @GetMapping("stopSpider")
    public String stopSpider(String key) {
        CommonResponse<Integer> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = spiderServiceImpl.stopSpider(key);
            responseDto.setResult(result);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("配置异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    /**
     * 获取执行结果
     *
     * @return
     */
    @GetMapping("getSpiderResultList")
    public String getSpiderResultList(String key, Integer page, Integer size) {
        CommonResponse<DynamicTableList> responseDto = new CommonResponse<>();
        try {
            DynamicTableList result = new DynamicTableList();
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            List<String> column = spiderServiceImpl.getFieldList(key);
            List<Map<String, Object>> list = spiderServiceImpl.getSpiderResultList(key, page, size);
            result.setColumn(column);
            result.setList(list);
            responseDto.setResult(result);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列表异常", e);
        }
        return JSON.toJSONStringWithDateFormat(responseDto, "yyyy-MM-dd HH:mm:ss");
    }

}
