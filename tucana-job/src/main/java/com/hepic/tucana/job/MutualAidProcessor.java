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
            .addHeader("Cookie","bid=3gHKL4xdeiE; ll=\"118163\"; _vwo_uuid_v2=D2DB2ECEFD6642752841469BBE847D808|75d6f321f845a72affdd6aedec6da43a; gr_user_id=3f3841fb-dd94-4dca-9a48-31a6b8215fe4; viewed=\"26708119\"; __utmc=30149280; __utmc=223695111; __utmz=30149280.1566869824.14.13.utmcsr=baidu.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=30149280.1787966388.1557802871.1566874416.1566885850.16; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1566885850%2C%22https%3A%2F%2Fwww.baidu.com%2F%22%5D; _pk_ses.100001.4cf6=*; ap_v=0,6.0; ct=y; __utmb=30149280.4.10.1566885850; _ck_desktop_mode=; vmode=; _ga=GA1.2.1787966388.1557802871; _gid=GA1.2.1858781418.1566886209; UM_distinctid=16cd1b28deb46c-004a067f2c211-7220633d-52210-16cd1b28decad0; Hm_lvt_19fc7b106453f97b6a84d64302f21a04=1566886236; Hm_lpvt_19fc7b106453f97b6a84d64302f21a04=1566886293; __utma=223695111.2017875466.1559554370.1566885850.1566887119.13; __utmb=223695111.0.10.1566887119; __utmz=223695111.1566887119.13.10.utmcsr=baidu|utmccn=(organic)|utmcmd=organic|utmctr=%E8%B1%86%E7%93%A3; dbcl2=\"183000291:DKEOhxVZH6M\"; ck=8VL1; _pk_id.100001.4cf6=c87fb2cdac8fe58f.1559554370.12.1566887719.1566874416.; push_noty_num=0; push_doumail_num=0")
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
