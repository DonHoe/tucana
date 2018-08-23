package com.hepic.tucana.dal.entity.mysql;

import java.io.Serializable;
import java.util.Date;

/**
 * cursor_url
 *
 * @author
 */
public class CursorUrl implements Serializable {
    private Long id;

    private String url;

    private Byte crawl;

    private Integer status;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getCrawl() {
        return crawl;
    }

    public void setCrawl(Byte crawl) {
        this.crawl = crawl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}