package com.hepic.tucana.job;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.dal.entity.mysql.Article;
import com.hepic.tucana.util.datetime.DateUtil;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Service
public class MutualAidProcessor implements PageProcessor {

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36")
            .setSleepTime(2000)
            .setRetryTimes(2);

    public Map<String,Integer> rateMap = new HashMap<String,Integer>(){{
        put("力荐",5);
        put("推荐",4);
        put("还行",3);
        put("较差",2);
        put("很差",1);
    }};

    @Override
    public void process(Page page) {
        System.out.println("开始处理");
        page.addTargetRequests(page.getHtml().links().regex(".*limit.*").all());
        Selectable commentList = page.getHtml().css(".comment-item");
        String title = page.getHtml().$("title").xpath("title/text()").get();
        List<Article> list = new ArrayList<>();
        for (Selectable item : commentList.nodes()) {
            Article itemArticle = new Article();
            String dataCid = item.$(".comment-item","data-cid").get();
            String authorId = item.$(".comment-info a","href").get();
            String author = item.$(".comment-info a").xpath("a/text()").get();
            String content = item.$(".short").xpath("span/text()").get();
            String commentTime = item.$(".comment-time","title").get();
            String rating = item.$(".rating","title").get();

            itemArticle.setCategory(title);
            itemArticle.setKey(dataCid);
            itemArticle.setAuthorId(authorId);
            itemArticle.setAuthor(author);
            itemArticle.setContent(content);
            itemArticle.setPublishTime(DateUtil.string2Date(commentTime));
            itemArticle.setRate(rateMap.get(rating));
            list.add(itemArticle);
            //System.out.println(JSON.toJSONString(itemArticle));
        }
        page.putField("data", JSON.toJSONString(list));
    }

    @Override
    public Site getSite() {
        return site;
    }
}
