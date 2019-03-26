package com.hepic.tucana.job;

import com.hepic.tucana.model.SpiderConfig;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public interface IPageProcessorFactory {

    PageProcessor createPageProcessor(SpiderConfig config);

    Pipeline createPipeline(SpiderConfig config);

    Spider getSpider(SpiderConfig config);
}
