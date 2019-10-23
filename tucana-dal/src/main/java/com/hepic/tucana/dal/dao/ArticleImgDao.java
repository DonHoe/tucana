package com.hepic.tucana.dal.dao;

import com.hepic.tucana.model.dal.ArticleImg;

import java.util.List;

/**
 * 数据访问接口
 */
public interface ArticleImgDao {

    List<ArticleImg> selectArticleImgListByModel(ArticleImg model);

    ArticleImg selectArticleImgById(Long id);

    int insertArticleImg(ArticleImg model);

    int updateArticleImg(ArticleImg model);

}