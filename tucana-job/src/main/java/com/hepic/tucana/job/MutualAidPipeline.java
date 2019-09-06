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
            Article dataObj = JSON.parseObject(data, Article.class);
            if (dataObj == null) {
                return;
            }
            if (StringUtils.isBlank(dataObj.getKey())) {
                return;
            }
            List<Article> exist = articleDao.findByCategoryAndKey(dataObj.getCategory(), dataObj.getKey());
            if (CollectionUtils.isNotEmpty(exist)) {
                return;
            }
            System.out.println(JSON.toJSONString(dataObj));
            articleDao.save(dataObj);
        } catch (Exception e) {
            log.error("数据处理失败", e);
        }
    }
}
