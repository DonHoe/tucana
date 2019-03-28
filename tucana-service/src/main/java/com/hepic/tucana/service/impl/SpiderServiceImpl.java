package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.JobConfigDao;
import com.hepic.tucana.dal.entity.mysql.JobConfig;
import com.hepic.tucana.dal.entity.mysql.JobExtractField;
import com.hepic.tucana.dal.entity.mysql.JobTargetUrl;
import com.hepic.tucana.job.IPageProcessorFactory;
import com.hepic.tucana.model.SpiderConfig;
import com.hepic.tucana.util.exception.BaseException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpiderServiceImpl {

    @Autowired
    private JobConfigDao jobConfigDao;

    @Autowired
    private IPageProcessorFactory pageProcessorFactory;

    private static List<Spider> spiderList;

    private static List<SpiderConfig> spiderConfigList;

    /**
     * 初始化
     */
    public SpiderServiceImpl() {
        spiderList = new ArrayList<>();
        spiderConfigList = new ArrayList<>();
        initSpider();
    }

    /**
     * 初始化
     */
    private void initSpider() {
        List<JobConfig> jobConfigs = jobConfigDao.getJobConfigList();
        for (JobConfig jobConfig : jobConfigs) {
            SpiderConfig spiderConfig = generateSpiderConfig(jobConfig);
            spiderConfigList.add(spiderConfig);
            spiderList.add(pageProcessorFactory.getSpider(spiderConfig));
        }
    }

    /**
     * 根据主配置构建明细配置
     *
     * @param jobConfig
     * @return
     */
    private SpiderConfig generateSpiderConfig(JobConfig jobConfig) {
        SpiderConfig spiderConfig = new SpiderConfig();
        spiderConfig.setId(jobConfig.getId());
        spiderConfig.setKey(jobConfig.getKey());
        spiderConfig.setName(jobConfig.getName());
        spiderConfig.setDesc(jobConfig.getDesc());
        spiderConfig.setStatus(0);
        spiderConfig.setRetryTimes(jobConfig.getRetryTimes());
        spiderConfig.setSleepTime(jobConfig.getSleepTime());
        spiderConfig.setStartUrl(jobConfig.getStartUrl());
        spiderConfig.setUserAgent(jobConfig.getUserAgent());
        List<JobExtractField> jobExtractFields = jobConfigDao.getJobExtractFieldByConfigId(jobConfig.getId());
        Map<String, String> jobExtractFieldMap = jobExtractFields.stream().collect(Collectors.toMap(JobExtractField::getKey, JobExtractField::getValue));
        spiderConfig.setExtractField(jobExtractFieldMap);
        List<JobTargetUrl> jobTargetUrls = jobConfigDao.getJobTargetUrlByConfigId(jobConfig.getId());
        spiderConfig.setRegexTargetUrl(jobTargetUrls.stream().map(p -> p.getExpression()).collect(Collectors.toList()));
        return spiderConfig;
    }

    /**
     * 获取所有配置
     *
     * @return
     */
    public List<SpiderConfig> getJobConfigList() {
        return spiderConfigList;
    }

    /**
     * 获取单个明细配置
     *
     * @param key
     * @return
     */
    public SpiderConfig getSpiderConfig(String key) {
        JobConfig jobConfig = jobConfigDao.getJobConfigByKey(key);
        if (jobConfig == null) {
            return null;
        }
        return generateSpiderConfig(jobConfig);
    }

    /**
     * 根据key获取
     *
     * @param key
     * @return
     */
    public Spider getSpiderByKey(String key) {
        Optional<Spider> optionalSpider = spiderList.stream().filter(p -> p.getUUID().equals(key)).findFirst();
        if (!optionalSpider.isPresent()) {
            return null;
        }
        return optionalSpider.get();
    }

    /**
     * 新增配置
     *
     * @param spiderConfig
     * @return
     */
    public Integer addSpiderConfig(SpiderConfig spiderConfig) {
        if (spiderConfig == null) {
            return 0;
        }
        JobConfig jobConfig = new JobConfig();
        jobConfig.setKey(UUID.randomUUID().toString());
        jobConfig.setName(spiderConfig.getName());
        jobConfig.setDesc(spiderConfig.getDesc());
        jobConfig.setStatus(spiderConfig.getStatus());
        jobConfig.setStartUrl(spiderConfig.getStartUrl());
        jobConfig.setUserAgent(spiderConfig.getUserAgent());
        jobConfig.setSleepTime(spiderConfig.getSleepTime());
        jobConfig.setRetryTimes(spiderConfig.getRetryTimes());
        jobConfigDao.insertJobConfig(jobConfig);
        if (CollectionUtils.isNotEmpty(spiderConfig.getRegexTargetUrl())) {
            spiderConfig.getRegexTargetUrl().stream().forEach(p -> {
                JobTargetUrl jobTargetUrl = new JobTargetUrl();
                jobTargetUrl.setJobId(jobConfig.getId());
                jobTargetUrl.setExpression(p);
                jobConfigDao.insertJobTargetUrl(jobTargetUrl);
            });
        }
        if (CollectionUtils.isNotEmpty(spiderConfig.getExtractField().entrySet())) {
            spiderConfig.getExtractField().entrySet().stream().forEach(p -> {
                JobExtractField jobExtractField = new JobExtractField();
                jobExtractField.setJobId(jobConfig.getId());
                jobExtractField.setKey(p.getKey());
                jobExtractField.setValue(p.getValue());
                jobConfigDao.insertJobExtractField(jobExtractField);
            });
        }
        spiderConfigList.add(spiderConfig);
        spiderList.add(pageProcessorFactory.getSpider(spiderConfig));
        return 1;
    }

    /**
     * 启动
     *
     * @param key
     */
    public void startSpider(String key) {
        Spider spider = getSpiderByKey(key);
        if (spider == null) {
            throw new BaseException(2001, "找不到");
        }
        if (spider.getStatus() == Spider.Status.Running) {
            throw new BaseException(2002, "正在执行");
        }
        spider.start();
        spiderConfigList.stream().filter(p -> p.getKey().equals(key)).forEach(p -> p.setStatus(Spider.Status.Running.ordinal()));
        jobConfigDao.updateJobStatus(key, Spider.Status.Running.ordinal());
    }

    /**
     * 停止
     *
     * @param key
     */
    public void stopSpider(String key) {
        Spider spider = getSpiderByKey(key);
        if (spider == null) {
            throw new BaseException(2001, "找不到");
        }
        if (spider.getStatus() == Spider.Status.Stopped) {
            throw new BaseException(2002, "已经停止");
        }
        spider.stop();
        spiderConfigList.stream().filter(p -> p.getKey().equals(key)).forEach(p -> p.setStatus(Spider.Status.Stopped.ordinal()));
        jobConfigDao.updateJobStatus(key, Spider.Status.Stopped.ordinal());
    }


}
