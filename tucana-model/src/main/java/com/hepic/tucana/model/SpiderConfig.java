package com.hepic.tucana.model;

import java.util.List;
import java.util.Map;

public class SpiderConfig {

    private Long id;

    private String key;

    private String startUrl;

    private String userAgent;

    private Integer sleepTime;

    private Integer retryTimes;

    private List<String> regexTargetUrl;

    private Map<String, String> extractField;

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

    public List<String> getRegexTargetUrl() {
        return regexTargetUrl;
    }

    public void setRegexTargetUrl(List<String> regexTargetUrl) {
        this.regexTargetUrl = regexTargetUrl;
    }

    public Map<String, String> getExtractField() {
        return extractField;
    }

    public void setExtractField(Map<String, String> extractField) {
        this.extractField = extractField;
    }
}
