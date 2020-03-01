package com.hepic.tucana.dal.dao;

import com.hepic.tucana.model.dal.ArticleImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 数据访问接口
 */
@Mapper
public interface ArticleImgDao {

    List<ArticleImg> selectArticleImgListByModel(ArticleImg model);

    ArticleImg selectArticleImgById(Long id);

    int insertArticleImg(ArticleImg model);

    int updateArticleImg(ArticleImg model);

}