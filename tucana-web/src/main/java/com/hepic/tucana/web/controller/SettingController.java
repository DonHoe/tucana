package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.mysql.Columns;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.impl.InformationSchemaService;
import com.hepic.tucana.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setting")
public class SettingController extends BaseController {

    /**
     * 数据库架构服务类
     */
    @Autowired
    private InformationSchemaService informationSchemaService;

    /**
     * 数据库
     */
    private static String DATABASE_NAME = "data";

    /**
     * 获取表集合
     *
     * @return
     */
    @GetMapping("getTableList")
    public String getTableList() {
        CommonResponse<List<String>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<String> result = informationSchemaService.getTableList(DATABASE_NAME);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取表集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 获取列集合
     *
     * @param tableName
     * @return
     */
    @GetMapping("getColumnsList")
    public String getColumnsList(String tableName) {
        CommonResponse<List<Columns>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Columns> result = informationSchemaService.getColumnsList(DATABASE_NAME, tableName);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }


}
