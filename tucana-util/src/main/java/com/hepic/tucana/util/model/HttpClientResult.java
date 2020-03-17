package com.hepic.tucana.util.model;

public class HttpClientResult {

    /**
     * Http状态码
     */
    int code;

    /**
     * 相应内容
     */
    String content;

    public HttpClientResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
