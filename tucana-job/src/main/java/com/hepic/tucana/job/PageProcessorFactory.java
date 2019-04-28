package com.hepic.tucana.job;

import com.hepic.tucana.dal.dao.mongo.MongoDao;
import com.hepic.tucana.model.SpiderConfig;
import com.hepic.tucana.model.spider.ExtractField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;

@Service
public class PageProcessorFactory implements IPageProcessorFactory {

    @Autowired
    MongoDao mongoDao;

    /**
     * 生成页面处理程序
     *
     * @param config
     * @return
     */
    public PageProcessor createPageProcessor(SpiderConfig config) {
        PageProcessor pageProcessor = new PageProcessor() {
            @Override
            public void process(Page page) {
                for (String p : config.getRegexTargetUrls()) {
                    page.addTargetRequests(page.getHtml().links().regex(p).all());
                }
                for (ExtractField p : config.getExtractFields()) {
                    String fieldValue = page.getHtml().xpath(p.getRule()).get();
                    if (StringUtils.isEmpty(fieldValue)) {
                        continue;
                    }
                    page.putField(p.getField(), fieldValue);
                }
                page.putField("_request_url", page.getRequest().getUrl());
                page.putField("_config_key", config.getKey());
                page.putField("_config_name", config.getName());
                page.putField("_createTime", new Date());
            }

            @Override
            public Site getSite() {
                return Site.me().setUserAgent(config.getUserAgent())
                        .setSleepTime(config.getSleepTime())
                        .setRetryTimes(config.getRetryTimes());
            }
        };
        return pageProcessor;
    }

    /**
     * 创建持久化处理
     *
     * @return
     */
    public Pipeline createPipeline(SpiderConfig config) {
        Pipeline pipeline = (resultItems, task) -> {
            if (resultItems.getAll() != null && resultItems.getAll().size() > 4) {
                mongoDao.addData(resultItems.getAll(), SpiderConstants.SPIDER_RESULT_COLLECTION);
            }
        };
        return pipeline;
    }

    /**
     * 构建主体
     *
     * @return
     */
    public Spider getSpider(SpiderConfig config) {
        return Spider.create(createPageProcessor(config)).addPipeline(createPipeline(config)).addUrl(config.getStartUrl()).setUUID(config.getKey());
    }
}
