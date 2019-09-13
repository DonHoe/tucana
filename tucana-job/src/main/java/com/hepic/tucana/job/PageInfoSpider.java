package com.hepic.tucana.job;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Service
public class PageInfoSpider {

    @Autowired
    MutualAidProcessor mutualAidProcessor;

    @Autowired
    MutualAidPipeline pageInfoPipeline;

    //@Autowired
    //MySqlScheduler mySqlScheduler;

    private Spider spider;

    public Spider getSpider(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (spider != null) {
            spider.stop();
            spider.close();
        }
        spider = Spider.create(mutualAidProcessor).addPipeline(pageInfoPipeline).addUrl(url);
        return spider;
    }

    public static void main(String[] args){
        Map<String,String> map = new HashMap<>();
        System.out.println(map.put("1","2"));
        System.out.println(map.put("1","3"));
        System.out.println(map.put("1",null));
        System.out.println(map.put("1","4"));
    }

}
