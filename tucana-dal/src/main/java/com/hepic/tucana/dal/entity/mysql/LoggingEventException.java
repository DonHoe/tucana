package com.hepic.tucana.dal.entity.mysql;


/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/9/4.
 */
public class LoggingEventException {

    private Integer eventId;

    private Integer i;

    private String traceLine;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public String getTraceLine() {
        return traceLine;
    }

    public void setTraceLine(String traceLine) {
        this.traceLine = traceLine;
    }
}
