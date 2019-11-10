package com.hepic.tucana.model.spider;

import java.math.BigDecimal;

public class MovieRate {

    private Integer rate;

    private Long num;

    private BigDecimal percent;

    public MovieRate(Integer rate, Long num) {
        this.rate = rate;
        this.num = num;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
