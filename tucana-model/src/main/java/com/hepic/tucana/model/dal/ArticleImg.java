package com.hepic.tucana.model.dal;

import java.util.Date;

/**
 *
 */
public class ArticleImg {

    /**
    * 
    */
    private Long id;

    /**
    * 
    */
    private Long articleId;

    /**
    * 
    */
    private String img;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
