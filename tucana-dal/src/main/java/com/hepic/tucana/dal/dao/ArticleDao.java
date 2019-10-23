package com.hepic.tucana.dal.dao;

import java.util.List;

import com.hepic.tucana.model.dal.Article;

/**
 * 数据访问接口
 */
public interface ArticleDao {

    List<Article> selectArticleListByModel(Article model);

    Article selectArticleById(Long id);

    int insertArticle(Article model);

    int updateArticle(Article model);

}