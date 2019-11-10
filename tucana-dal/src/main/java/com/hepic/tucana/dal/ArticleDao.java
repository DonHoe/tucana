package com.hepic.tucana.dal;

import com.hepic.tucana.model.dal.Article;

import java.util.List;

/**
 * 数据访问接口
 */
public interface ArticleDao {

    List<Article> selectArticleListByModel(Article model);

    List<Article> findArticleListByModel(Article model);

    Article selectArticleById(Long id);

    int insertArticle(Article model);

    int updateArticle(Article model);

    long getArticleCountByMin(String min);

}