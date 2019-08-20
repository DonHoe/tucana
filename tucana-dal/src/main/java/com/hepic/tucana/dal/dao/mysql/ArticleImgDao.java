package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.ArticleImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleImgDao extends JpaRepository<ArticleImg, Long> {
}
