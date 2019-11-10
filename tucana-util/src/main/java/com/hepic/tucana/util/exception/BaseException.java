package com.hepic.tucana.util.exception;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
