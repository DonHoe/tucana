package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.authority.SysMenu;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "system")
public class SystemController {

    @Autowired
    SystemService systemService;

    /**
     * 获取菜单列表
     *
     * @param sysMenu
     * @return
     */
    @GetMapping(value = "getMenuList")
    public String getMenuList(SysMenu sysMenu) {
        CommonResponse<List<SysMenu>> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            List<SysMenu> result = systemService.getMenuList(sysMenu);
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

    /**
     * 获取菜单列表
     *
     * @param sysMenu
     * @return
     */
    @PostMapping(value = "saveMenu")
    public String saveMenu(@RequestBody SysMenu sysMenu) {
        CommonResponse<Integer> response = new CommonResponse();
        try {
            response.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (sysMenu.getId() == null || sysMenu.getId().intValue() == 0) {
                result = systemService.addMenu(sysMenu);
            } else {
                result = systemService.editMenu(sysMenu);
            }
            response.setResult(result);
        } catch (Exception e) {
            response.setResponseEnum(ResponseEnum.Code_999);
            log.error("获取列集合异常", e);
        }
        return JSON.toJSONString(response);
    }

}
