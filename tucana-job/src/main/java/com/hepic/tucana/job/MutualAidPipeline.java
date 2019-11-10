package com.hepic.tucana.job;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.ArticleDao;
import com.hepic.tucana.dal.ArticleImgDao;
import com.hepic.tucana.model.dal.Article;
import com.hepic.tucana.model.dal.ArticleImg;
import com.hepic.tucana.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Slf4j
@Service
public class MutualAidPipeline implements Pipeline {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleImgDao articleImgDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            String type = resultItems.get("type");
            String data = resultItems.get("data");
            if (type == "article") {
                List<Article> dataObj = JSON.parseArray(data, Article.class);
                for (Article item : dataObj) {
                    Article query = new Article();
                    query.setCategory(item.getCategory());
                    query.setKey(item.getKey());
                    List<Article> exists = articleDao.selectArticleListByModel(query);
                    if (CollectionUtils.isNotEmpty(exists)) {
                        Article exist = exists.get(0);
                        exist.setHot(exist.getHot());
                        exist.setTop(exist.getTop());
                        exist.setComment(exist.getComment());
                        exist.setRead(exist.getRead());
                        exist.setVote(exist.getVote());
                        continue;
                    }
                    articleDao.insertArticle(item);
                }
                return;
            }
            if (type == "attachment") {
                String key = resultItems.get("key");
                Integer articleId = CommonUtil.tryParseInteger(key, 0);
                String context = resultItems.get("context");
                Article query = new Article();
                query.setCategory("night");
                query.setKey(key);
                List<Article> exist = articleDao.selectArticleListByModel(query);
                if (CollectionUtils.isNotEmpty(exist)) {
                    Article article = exist.get(0);
                    article.setContent(context);
                    articleDao.updateArticle(article);
                }
                List<ArticleImg> imgList = JSON.parseArray(data, ArticleImg.class);
                for (ArticleImg item : imgList) {
                    ArticleImg queryImg = new ArticleImg();
                    queryImg.setArticleId(articleId.longValue());
                    queryImg.setKey(item.getKey());
                    List<ArticleImg> articleImgs = articleImgDao.selectArticleImgListByModel(queryImg);
                    if (CollectionUtils.isEmpty(articleImgs)) {
                        articleImgDao.insertArticleImg(item);
                    }
                }
                return;
            }
        } catch (Exception e) {
            log.error("数据处理失败", e);
        }
    }
}
