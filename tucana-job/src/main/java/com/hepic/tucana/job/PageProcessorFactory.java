package com.hepic.tucana.job;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.job.JobConfig;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PageProcessorFactory {

    /**
     * 生成页面处理程序
     *
     * @param config
     * @return
     */
    PageProcessor createPageProcessor(JobConfig config) {
        PageProcessor pageProcessor = new PageProcessor() {
            @Override
            public void process(Page page) {
                for (String p : config.getRegexTargetUrl()) {
                    page.addTargetRequests(page.getHtml().links().regex(p).all());
                }
                for (Map.Entry<String, String> p : config.getExtractField().entrySet()) {
                    page.putField(p.getKey(), page.getHtml().xpath(p.getValue()).get());
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
    Pipeline createPipeline(JobConfig config) {
        Pipeline pipeline = (resultItems, task) -> System.out.println(JSON.toJSONString(resultItems));
        return pipeline;
    }

    /**
     * 构建主体
     *
     * @return
     */
    public Spider getSpider() {
        JobConfig config = new JobConfig();
        config.setId(1L);
        config.setKey(UUID.randomUUID().toString());
        config.setSleepTime(500);
        config.setRetryTimes(2);
        config.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
        config.setStartUrl("https://www.cnblogs.com/");
        config.setRegexTargetUrl(Arrays.asList("(https://www.cnblogs\\.com/\\w+/p/.+)"));
        config.setExtractField(new HashMap<String, String>() {{
            put("title", "//head/title/tidyText()");
        }});
        return Spider.create(createPageProcessor(config)).addPipeline(createPipeline(config)).addUrl(config.getStartUrl());
    }
}
