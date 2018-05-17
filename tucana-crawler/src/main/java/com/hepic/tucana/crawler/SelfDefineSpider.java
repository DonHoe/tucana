package com.hepic.tucana.crawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author hd23973
 * @Title:
 * @Description:
 * @date 2018/5/17.
 */
public class SelfDefineSpider extends Spider {

    private Long id;

    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SelfDefineSpider(PageProcessor pageProcessor, Long id, String name) {
        super(pageProcessor);
        setId(id);
        setName(name);
    }

}
