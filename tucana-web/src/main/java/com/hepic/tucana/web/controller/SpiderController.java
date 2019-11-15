package com.hepic.tucana.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hepic.tucana.dal.ArticleDao;
import com.hepic.tucana.dal.ArticleImgDao;
import com.hepic.tucana.job.PageInfoSpider;
import com.hepic.tucana.model.common.CommonResponse;
import com.hepic.tucana.model.dal.Article;
import com.hepic.tucana.model.dal.ArticleImg;
import com.hepic.tucana.model.enums.ResponseEnum;
import com.hepic.tucana.util.datetime.DateUtil;
import com.hepic.tucana.util.exception.BaseException;
import com.hepic.tucana.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/spider")
public class SpiderController extends BaseController {

    @Value(value = "${download.basePath}")
    private String basePath;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleImgDao articleImgDao;

    @Autowired
    PageInfoSpider pageInfoSpider;

    @GetMapping
    public String index() {
        return "spider";
    }

    /**
     * 返回查询数据
     *
     * @param query
     * @return
     */
    @GetMapping(value = "getData")
    @ResponseBody
    public PageInfo getData(Article query) {
        startPage();
        List<Article> list = articleDao.findArticleListByModel(query);
        return new PageInfo(list);
    }

    /**
     * 抓取
     *
     * @param url
     * @return
     */
    @RequestMapping("/startSpider")
    @ResponseBody
    public String startSpider(String url) {
        CommonResponse<String> responseDto = new CommonResponse<>();
        try {
            Spider spider = pageInfoSpider.getSpider(url);
            if (spider == null) {
                throw new BaseException(2000, "build failed");
            }
            spider.start();
        } catch (BaseException e) {
            responseDto.setCode(e.getCode());
            responseDto.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDto.setResponseEnum(ResponseEnum.Code_999);
            log.error("JOB启动异常", e);
        }
        return JSON.toJSONString(responseDto);
    }

    /**
     * 统计各个状态的占比
     *
     * @return
     */
    @GetMapping(value = "countStatus", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String countStatus() {
        List<Map<String, Long>> map = articleImgDao.countStatus();
        return JSON.toJSONString(map);
    }

    /**
     * 统计各个状态的占比
     *
     * @return
     */
    @GetMapping(value = "getArticleCountByMin", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getArticleCountByMin() {
        Date now = new Date();
        String time = DateUtil.date2String(now, "HH:mm");
        String date = DateUtil.date2String(now, "yyyy-MM-dd HH:mm");
        Long count = articleDao.getArticleCountByMin(date);
        Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        map.put("count", count);
        return JSON.toJSONString(map);
    }

    /**
     * 统计各个状态的占比
     *
     * @return
     */
    @GetMapping(value = "getImgList", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getImgList(Long articleId) {
        ArticleImg articleImg = new ArticleImg();
        articleImg.setArticleId(articleId);
        articleImg.setStatus(1);
        List<ArticleImg> list = articleImgDao.selectArticleImgListByModel(articleImg);
        List<String> images = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            images = list.stream().map(p -> {
                if (StringUtils.isBlank(p.getPath())) {
                    return StringUtils.EMPTY;
                }
                return "/image/" + p.getPath().replace(basePath, StringUtils.EMPTY);
            }).collect(Collectors.toList());
        }
        return JSON.toJSONString(images);
    }

    /**
     * 统计各个状态的占比
     *
     * @return
     */
    @GetMapping(value = "deleteFailImg", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteFailImg(Long num) {
        ArticleImg articleImg = new ArticleImg();
        articleImg.setStatus(-1);
        List<ArticleImg> list = articleImgDao.selectArticleImgListByModel(articleImg);
        for (ArticleImg item : list) {
            if (StringUtils.isBlank(item.getPath())) {
                continue;
            }
            if (item.getStatus() != -1) {
                continue;
            }
            File file = new File(item.getPath());
            if (file.exists()) {
                file.delete();
            }
        }
        return JSON.toJSONString(list.size());
    }
}
