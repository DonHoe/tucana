package com.hepic.tucana.model;

import com.hepic.tucana.model.spider.ExtractField;

import java.util.List;
import java.util.Map;

public class SpiderConfig {

    private Long id;

    private String key;

    private String name;

    private String desc;

    private Integer status;

    private String startUrl;

    private String userAgent;

    private Integer sleepTime;

    private Integer retryTimes;

    private List<String> regexTargetUrls;

    private List<ExtractField> extractFields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public List<String> getRegexTargetUrls() {
        return regexTargetUrls;
    }

    public void setRegexTargetUrls(List<String> regexTargetUrls) {
        this.regexTargetUrls = regexTargetUrls;
    }

    public List<ExtractField> getExtractFields() {
        return extractFields;
    }

    public void setExtractFields(List<ExtractField> extractFields) {
        this.extractFields = extractFields;
    }
}
