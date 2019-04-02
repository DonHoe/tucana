package com.hepic.tucana.job;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.SpiderConfig;
import com.hepic.tucana.model.spider.ExtractField;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Map;

@Service
public class PageProcessorFactory implements IPageProcessorFactory {

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
                    page.putField(p.getField(), page.getHtml().xpath(p.getRule()).get());
                }
                page.putField("config-id", config.getId());
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
        Pipeline pipeline = new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                for (Map.Entry<String, Object> item : resultItems.getAll().entrySet()) {
                    System.out.println(item.getKey() + "->" + item.getValue());
                }
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
