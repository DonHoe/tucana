package com.hepic.tucana.job;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.dao.mysql.ArticleDao;
import com.hepic.tucana.dal.entity.mysql.Article;
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

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            String data = resultItems.get("data");
            if (StringUtils.isBlank(data)) {
                return;
            }
            List<Article> list = JSON.parseArray(data, Article.class);
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            for (Article item : list) {
                List<Article> exist = articleDao.findByCategoryAndKey(item.getCategory(),item.getKey());
                if (CollectionUtils.isNotEmpty(exist)) {
                    continue;
                }
                System.out.println(JSON.toJSONString(item));
                articleDao.save(item);
            }
        } catch (Exception e) {
            log.error("数据处理失败", e);
        }
    }
}
