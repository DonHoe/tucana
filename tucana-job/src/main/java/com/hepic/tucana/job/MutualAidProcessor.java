package com.hepic.tucana.job;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.mysql.Article;
import com.hepic.tucana.util.datetime.DateUtil;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Service
public class MutualAidProcessor implements PageProcessor {

    private Pattern pattern = Pattern.compile("https?://.*?/");

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36")
            .setSleepTime(2000)
            .setRetryTimes(2);

    @Override
    public void process(Page page) {
        System.out.println("开始处理");
        //限定本域
        Matcher matcher = pattern.matcher(page.getRequest().getUrl());
        if (matcher.find()) {
            String domain = matcher.group();
            Selectable links = page.getHtml().links();
            List<String> listList = links.regex(domain + ".*").all();
            page.addTargetRequests(listList);
        }
        Html html = page.getHtml();

        List<Article> list = new ArrayList<>();

        Article itemArticle = new Article();
        String title = page.getHtml().$("title").xpath("title/text()").get();
        String key = title;
        if (StringUtils.isBlank(key)) {
            page.setSkip(true);
            return;
        }
        String content = html.$("#link-report span", "text").get();
        String rating = html.$(".rating_num", "text").get();
        String comment = html.$(".rating_people span", "text").get();

        itemArticle.setTitle(title);
        itemArticle.setCategory("douban");
        itemArticle.setKey(key);
        itemArticle.setContent(content);
        itemArticle.setRate(rating);
        itemArticle.setComment(Integer.valueOf(StringUtils.isBlank(comment) ? "0" : comment));
        list.add(itemArticle);
        page.putField("data", JSON.toJSONString(itemArticle));
    }

    @Override
    public Site getSite() {
        return site;
    }
}
