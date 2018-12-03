package com.hepic.tucana.job;

import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Service
public class PageInfoProcessor implements PageProcessor {

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31").setSleepTime(500).setRetryTimes(2);

    @Override
    public void process(Page page) {
        System.out.println("开始处理");
        page.addTargetRequests(page.getHtml().links().regex("(https://www.cnblogs\\.com/\\w+/p/\\w+)").all());
        System.out.println(page.getHtml());
        String title = page.getHtml().xpath("//head/title/tidyText()").get();
        page.putField("title", title);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
