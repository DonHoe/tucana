package com.hepic.tucana.model.exception;

import com.hepic.tucana.model.enums.ResponseEnum;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/2.
 */
public class BaseException extends RuntimeException {

    private Integer code;

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
