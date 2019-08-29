package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.ArticleDao;
import com.hepic.tucana.model.spider.MovieRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoubanMovieService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 获取评分分布
     *
     * @return
     */
    public List<MovieRate> getRatePercent() {
        return articleDao.getRatePercent();
    }
}
