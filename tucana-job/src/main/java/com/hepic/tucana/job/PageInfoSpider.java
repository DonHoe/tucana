package com.hepic.tucana.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Service
public class PageInfoSpider {

    @Autowired
    PageInfoProcessor pageInfoProcessor;

    @Autowired
    PageInfoPipeline pageInfoPipeline;

    @Autowired
    MySqlScheduler mySqlScheduler;

    private Spider spider;

    public Spider getSpider() {
        if (spider == null) {
            spider = Spider.create(pageInfoProcessor).setScheduler(mySqlScheduler).addPipeline(pageInfoPipeline);
        }
        return spider;
    }

}
