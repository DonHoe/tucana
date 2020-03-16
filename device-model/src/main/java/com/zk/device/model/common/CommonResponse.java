package com.zk.device.model.common;

import com.zk.device.model.enums.ResponseEnum;

/**
 * @author device
 * @Title:
 * @Description:
 * @date 2018/7/10.
 */
public class CommonResponse<T> {

    /**
     * 响应code
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 业务数据
     */
    private T result;

    /**
     * 根据枚举值设置相应
     * @param responseEnum
     */
    public void setResponseEnum(ResponseEnum responseEnum) {
        if (responseEnum != null) {
            this.code = responseEnum.getCode();
            this.message = responseEnum.getMessage();
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
