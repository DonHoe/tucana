package com.hepic.tucana.web.base;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/6/14.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 默认异常处理
     *
     * @param request 请求实体
     * @param e       异常
     * @return 默认异常代号
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defaultExceptionHandle(HttpServletRequest request, Exception e) {
        log.error("请求参数:" + JSON.toJSONString(request), e);
        CommonResponse<Integer> response = new CommonResponse<>();
        response.setResponseEnum(ResponseEnum.Code_999);
        return JSON.toJSONString(response);
    }
}
