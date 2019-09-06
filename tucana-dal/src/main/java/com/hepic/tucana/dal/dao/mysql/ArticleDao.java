package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.Article;
import com.hepic.tucana.model.spider.MovieRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, Long> {

    List<Article> findByCategoryAndKey(String category, String key);

    @Query(value = "SELECT new com.hepic.tucana.model.spider.MovieRate(type ,count(id)) FROM Article WHERE type !=0 GROUP BY type")
    List<MovieRate> getRatePercent();

}
