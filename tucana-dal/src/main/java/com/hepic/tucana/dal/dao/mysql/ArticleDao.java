package com.hepic.tucana.dal.dao.mysql;

import java.util.List;

import com.hepic.tucana.dal.entity.mysql.Article;

/**
 * 数据访问接口
 */
public interface ArticleDao {

    List<Article> selectArticleListByModel(Article model);

    Article selectArticleById(Long id);

    int insertArticle(Article model);

    int updateArticle(Article model);

}