package com.hepic.tucana.web.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hd23973
 * @Title:
 * @Description:
 * @date 2018/6/14.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defaultExceptionHandle(HttpServletRequest request, Exception e) {
        log.error("请求参数不可读", e);
        return "{'code':400}";
    }
}
