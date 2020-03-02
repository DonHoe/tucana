package com.hepic.tucana.web.base;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.web.controller.SystemController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数合法性校验异常
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationBodyException(MethodArgumentNotValidException exception) {

        StringBuffer buffer = new StringBuffer();

        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();

            errors.forEach(p -> {

                FieldError fieldError = (FieldError) p;
                buffer.append(fieldError.getDefaultMessage()).append(",");
            });
        }
        CommonResponse<Integer> response = new CommonResponse();
        response.setResponseEnum(ResponseEnum.Code_2000);
        response.setMessage(buffer.toString().substring(0, buffer.toString().length() - 1));
        return JSON.toJSONString(response);
    }
}
