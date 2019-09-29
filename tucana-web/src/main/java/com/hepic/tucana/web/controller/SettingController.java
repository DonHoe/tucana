package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.mysql.Columns;
import com.hepic.tucana.dal.entity.mysql.TableInfo;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.impl.InformationSchemaService;
import com.hepic.tucana.util.exception.BaseException;
import com.hepic.tucana.web.base.BaseController;
import com.hepic.tucana.web.base.VelocityInitializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
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
     * 获取表集合
     *
     * @return
     */
    @GetMapping("getTableList")
    public String getTableList() {
        CommonResponse<List<TableInfo>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<TableInfo> result = informationSchemaService.getTableList();
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
    public String getColumnsList(String table) {
        CommonResponse<List<Columns>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<Columns> result = informationSchemaService.getColumnsList(table);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 获取列集合
     *
     * @param tableName
     * @return
     */
    @GetMapping("getTableInfo")
    public String getTableInfo(String table) {
        CommonResponse<TableInfo> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            TableInfo result = informationSchemaService.generateTableInfo(table);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 代码构建
     *
     * @return
     */
    @RequestMapping("/codeCreate")
    public String codeCreate(String table) {
        CommonResponse<String> responseDto = new CommonResponse<>();
        try {
            VelocityInitializer.initVelocity();
            Template t = Velocity.getTemplate("templates/mapper.vm");
            VelocityContext ctx = new VelocityContext();
            TableInfo tableInfo = informationSchemaService.generateTableInfo(table);
            ctx.put("table", tableInfo);
            StringWriter sw = new StringWriter();
            t.merge(ctx, sw);
            System.out.println(sw.toString());
            responseDto.setResult(sw.toString());
            sw.close();
        } catch (BaseException e) {
            responseDto.setCode(e.getCode());
            responseDto.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("", e);
        }
        return JSON.toJSONString(responseDto);
    }
}
