package com.hepic.tucana.dal.entity.mysql;

import java.util.Date;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
public class PageInfo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标识
     */
    private String code;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片列表
     */
    private String imgList;

    /**
     * 创建时间
     */
    private Date cdate;

    /**
     * 修改时间
     */
    private Date udate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        this.imgList = imgList;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }
}
