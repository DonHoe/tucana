package com.hepic.tucana.job;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Service
public class PageInfoProcessor implements PageProcessor {

    private Site site = Site.me().setSleepTime(500).setRetryTimes(2);

    @Override
    public void process(Page page) {
        String code = page.getHtml().regex("").get();
        if (StringUtils.isBlank(code)) {
            page.setSkip(true);
            return;
        }
        page.putField("code", code);
        List<String> urls = page.getUrl().regex("www").all();
        page.addTargetRequests(urls);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
