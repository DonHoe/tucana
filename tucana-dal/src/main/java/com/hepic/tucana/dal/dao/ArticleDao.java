package com.hepic.tucana.dal.dao;

import java.util.List;

import com.hepic.tucana.model.dal.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据访问接口
 */
@Mapper
public interface ArticleDao {

    List<Article> selectArticleListByModel(Article model);

    Article selectArticleById(Long id);

    int insertArticle(Article model);

    int updateArticle(Article model);

}