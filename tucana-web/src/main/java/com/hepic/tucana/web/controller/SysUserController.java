package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.authority.SysUser;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.service.SysUserService;
import com.hepic.tucana.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@Slf4j
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping("/selectSysUserListByModel")
    public String selectSysUserListByModel() {
        CommonResponse<List<SysUser>> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            SysUser model = new SysUser();
            List<SysUser> list = sysUserService.selectSysUserListByModel(model);
            responseDto.setResult(list);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("操作异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(SysUser sysUser) {
        CommonResponse<Integer> responseDto = new CommonResponse<>();
        try {
            responseDto.setResponseEnum(ResponseEnum.Code_1000);
            Integer result = 0;
            if (sysUser.getId() == null || sysUser.getId() == 0) {
                result = sysUserService.insertSysUser(sysUser);
            } else {
                result = sysUserService.updateSysUser(sysUser);
            }
            responseDto.setResult(result);
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("操作异常", e);
        }
        return JSON.toJSONString(responseDto);
    }
}
