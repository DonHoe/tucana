package com.hepic.tucana.dal;

import com.hepic.tucana.model.dal.ArticleImg;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface ArticleImgDao {

    List<ArticleImg> selectArticleImgListByModel(ArticleImg model);

    ArticleImg selectArticleImgOneByModel(ArticleImg model);

    List<ArticleImg> selectArticleImgNoExif(Integer limit);

    ArticleImg selectArticleImgById(Long id);

    int insertArticleImg(ArticleImg model);

    int updateArticleImg(ArticleImg model);

    List<Map<String, Long>> countStatus();
}