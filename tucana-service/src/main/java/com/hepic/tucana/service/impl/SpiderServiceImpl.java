package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.JobConfigDao;
import com.hepic.tucana.dal.entity.mysql.JobConfig;
import com.hepic.tucana.dal.entity.mysql.JobExtractField;
import com.hepic.tucana.dal.entity.mysql.JobTargetUrl;
import com.hepic.tucana.job.IPageProcessorFactory;
import com.hepic.tucana.model.SpiderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpiderServiceImpl {

    @Autowired
    private JobConfigDao jobConfigDao;

    @Autowired
    private IPageProcessorFactory pageProcessorFactory;

    private static List<Spider> spiderList;

    /**
     * 初始化
     */
    public SpiderServiceImpl() {
        spiderList = new ArrayList<>();
        initSpider();
    }

    /**
     * 初始化
     */
    private void initSpider() {
        List<SpiderConfig> spiderConfigs = new ArrayList();
        List<JobConfig> jobConfigs = jobConfigDao.getJobConfigList();
        for (JobConfig jobConfig : jobConfigs) {
            SpiderConfig spiderConfig = new SpiderConfig();
            spiderConfig.setId(jobConfig.getId());
            spiderConfig.setKey(jobConfig.getKey());
            spiderConfig.setRetryTimes(jobConfig.getRetryTimes());
            spiderConfig.setSleepTime(jobConfig.getSleepTime());
            spiderConfig.setStartUrl(jobConfig.getStartUrl());
            spiderConfig.setUserAgent(jobConfig.getUserAgent());
            List<JobExtractField> jobExtractFields = jobConfigDao.getJobExtractFieldByConfigId(jobConfig.getId());
            Map<String, String> jobExtractFieldMap = jobExtractFields.stream().collect(Collectors.toMap(JobExtractField::getKey, JobExtractField::getValue));
            spiderConfig.setExtractField(jobExtractFieldMap);
            List<JobTargetUrl> jobTargetUrls = jobConfigDao.getJobTargetUrlByConfigId(jobConfig.getId());
            spiderConfig.setRegexTargetUrl(jobTargetUrls.stream().map(p -> p.getExpression()).collect(Collectors.toList()));
            spiderConfigs.add(spiderConfig);
            spiderList.add(pageProcessorFactory.getSpider(spiderConfig));
        }
    }
}
