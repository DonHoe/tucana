package com.hepic.tucana.job;

import com.alibaba.fastjson.JSON;
import com.hepic.tucana.model.dal.Article;
import com.hepic.tucana.model.dal.ArticleImg;
import com.hepic.tucana.util.CommonUtil;
import com.hepic.tucana.util.datetime.DateUtil;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.stream.Collectors;

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

        Selectable links = page.getHtml().links();

        page.addTargetRequests(links.all()
                .stream()
                .filter(p -> p.contains("forumdisplay.php?fid=19") || (p.contains("viewthread.php") && !p.contains("&page=")))
                .collect(Collectors.toList()));

        Html html = page.getHtml();

        if (page.getRequest().getUrl().contains("forumdisplay")) {
            List<Article> list = new ArrayList<>();
            Selectable articleHtml = html.$("tbody[id^='normalthread_']");

            for (Selectable article : articleHtml.nodes()) {
                Article itemArticle = new Article();
                itemArticle.setCategory("night");
                itemArticle.setType(1);
                String key = article.$(".subject").regex("thread_\\d+").get();
                if (StringUtils.isNotBlank(key)) {
                    key = key.replace("thread_", "");
                }
                String url = article.$(".subject span a", "href").get();
                String title = article.$(".subject span a", "text").get();
                String author = article.$(".author cite").$("a", "text").get();
                String authorId = article.$(".author cite").regex("(?<=uid=)\\d+(?=\")").get();
                String publishStr = article.$(".author em", "text").get();
                itemArticle.setPublishTime(DateUtil.string2Date(publishStr, "yyyy-M-d"));
                String comment = article.$(".nums strong", "text").get();
                String read = article.$(".nums em", "text").get();
                String vote = article.$("font").regex("(?<=被顶)\\d+(?=次)").get();
                List<String> attach = article.$("img.attach").all();
                if (attach.stream().anyMatch(p -> p.contains("加分"))) {
                    itemArticle.setHot("加分");
                }
                if (attach.stream().anyMatch(p -> p.contains("精华"))) {
                    itemArticle.setTop("精华");
                }
                itemArticle.setKey(key);
                itemArticle.setUrl(url);
                itemArticle.setTitle(title);
                itemArticle.setAuthor(author);
                itemArticle.setAuthorId(authorId);
                itemArticle.setComment(CommonUtil.tryParseInteger(comment, 0));
                itemArticle.setRead(CommonUtil.tryParseInteger(read, 0));
                itemArticle.setVote(CommonUtil.tryParseInteger(vote, 0));
                System.out.println(JSON.toJSONString(itemArticle));
                list.add(itemArticle);
            }
            page.putField("type", "article");
            page.putField("data", JSON.toJSONString(list));
            return;
        }
        if (page.getRequest().getUrl().contains("viewthread")) {
            List<ArticleImg> list = new ArrayList<ArticleImg>();
            String keyStr = html.regex("(?<=tid=)\\d+(?=\\&)").get();
            Selectable imgList = html.$(".t_msgfont img");
            imgList.nodes().forEach(p -> {
                ArticleImg articleImg = new ArticleImg();
                articleImg.setArticleId(CommonUtil.tryParseInteger(keyStr, 0).longValue());
                String imgPath = p.regex("(?<=file=\").+?(?=\")").get();
                if (StringUtils.isBlank(imgPath)) {
                    return;
                }
                articleImg.setImg(imgPath);
                articleImg.setKey(p.regex("(?<=attachments//).+?(?=\\.)").get());
                list.add(articleImg);
                System.out.println(JSON.toJSONString(articleImg));
            });
            List<String> contextList = new ArrayList<>();
            List<String> fontList = html.$(".t_msgfont p").all();
            fontList.forEach(p -> {
                String temp = p.replaceAll("<.*?>", "");
                if (StringUtils.isNotBlank(temp)) {
                    contextList.add(temp);
                }
            });
            page.putField("key", keyStr);
            page.putField("type", "attachment");
            page.putField("context", StringUtils.join(contextList, "\n"));
            page.putField("data", JSON.toJSONString(list));
            return;
        }
        page.setSkip(true);
        return;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
