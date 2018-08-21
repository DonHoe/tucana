package com.hepic.tucana.web.controller;

import com.hepic.tucana.job.PageInfoSpider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    PageInfoSpider pageInfoSpider;

    @GetMapping("/start")
    public String start() {
        Spider spider = pageInfoSpider.getSpider();
        if (spider.getStatus() == Spider.Status.Running) {

        } else {
            spider.start();
        }
        return "as";
    }

}
