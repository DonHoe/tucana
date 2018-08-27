package com.hepic.tucana.model.log;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/27.
 */
public class LogListRequest {

    private String startTime;

    private String endTime;

    private String levelString;

    private String callerClass;

    private String callerMethod;

    private String arg0;

    private String arg1;

    private String arg2;

    private String arg3;

    private Integer page;

    private Integer row;

    private Integer skip;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLevelString() {
        return levelString;
    }

    public void setLevelString(String levelString) {
        this.levelString = levelString;
    }

    public String getCallerClass() {
        return callerClass;
    }

    public void setCallerClass(String callerClass) {
        this.callerClass = callerClass;
    }

    public String getCallerMethod() {
        return callerMethod;
    }

    public void setCallerMethod(String callerMethod) {
        this.callerMethod = callerMethod;
    }

    public String getArg0() {
        return arg0;
    }

    public void setArg0(String arg0) {
        this.arg0 = arg0;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }
}
