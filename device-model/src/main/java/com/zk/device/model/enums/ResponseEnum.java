package com.zk.device.model.enums;

/**
 * @author device
 * @Title:
 * @Description:
 * @date 2018/7/10.
 */
public enum ResponseEnum {

    //正常返回值
    Code_1000(1000, "Success"),

    Code_2000(2000, "缺少参数"),

    //Login
    Code_900(900, "验证码错误"),
    Code_901(901, "用户不存在"),
    Code_902(902, "密码不正确"),

    //保底异常
    Code_999(999, "Error");

    /**
     * 响应值
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

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

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessage(int code) {
        for (ResponseEnum item : ResponseEnum.values()) {
            if (item.getCode() == code) {
                return item.message;
            }
        }
        return null;
    }

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}