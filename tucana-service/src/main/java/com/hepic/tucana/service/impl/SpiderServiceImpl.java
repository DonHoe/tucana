package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.JobConfigDao;
import com.hepic.tucana.dal.entity.mysql.JobConfig;
import com.hepic.tucana.dal.entity.mysql.JobExtractField;
import com.hepic.tucana.dal.entity.mysql.JobTargetUrl;
import com.hepic.tucana.job.IPageProcessorFactory;
import com.hepic.tucana.job.SpiderConstants;
import com.hepic.tucana.model.SpiderConfig;
import com.hepic.tucana.model.spider.ExtractField;
import com.hepic.tucana.util.exception.BaseException;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpiderServiceImpl {

    private JobConfigDao jobConfigDao;

    private MongoTemplate mongoTemplate;

    private IPageProcessorFactory pageProcessorFactory;

    private static List<Spider> spiderList;

    private static List<SpiderConfig> spiderConfigList;

    /**
     * 初始化
     */
    @Autowired
    public SpiderServiceImpl(JobConfigDao jobConfigDao, IPageProcessorFactory pageProcessorFactory, MongoTemplate mongoTemplate) {
        this.jobConfigDao = jobConfigDao;
        this.pageProcessorFactory = pageProcessorFactory;
        this.mongoTemplate = mongoTemplate;
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
        List<ExtractField> jobExtractFieldList = jobExtractFields.stream().map(p -> {
            ExtractField item = new ExtractField();
            item.setField(p.getField());
            item.setRule(p.getRule());
            return item;
        }).collect(Collectors.toList());
        spiderConfig.setExtractFields(jobExtractFieldList);
        List<JobTargetUrl> jobTargetUrls = jobConfigDao.getJobTargetUrlByConfigId(jobConfig.getId());
        spiderConfig.setRegexTargetUrls(jobTargetUrls.stream().map(p -> p.getExpression()).collect(Collectors.toList()));
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
     * 根据key获取
     *
     * @param key
     * @return
     */
    public SpiderConfig getSpiderConfigByKey(String key) {
        Optional<SpiderConfig> optionalSpider = spiderConfigList.stream().filter(p -> p.getKey().equals(key)).findFirst();
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
        jobConfig.setDelete(0);
        jobConfig.setStartUrl(spiderConfig.getStartUrl());
        jobConfig.setUserAgent(spiderConfig.getUserAgent());
        jobConfig.setSleepTime(spiderConfig.getSleepTime());
        jobConfig.setRetryTimes(spiderConfig.getRetryTimes());
        jobConfigDao.insertJobConfig(jobConfig);
        if (CollectionUtils.isNotEmpty(spiderConfig.getRegexTargetUrls())) {
            spiderConfig.getRegexTargetUrls().stream().forEach(p -> {
                JobTargetUrl jobTargetUrl = new JobTargetUrl();
                jobTargetUrl.setJobId(jobConfig.getId());
                jobTargetUrl.setExpression(p);
                jobConfigDao.insertJobTargetUrl(jobTargetUrl);
            });
        }
        if (CollectionUtils.isNotEmpty(spiderConfig.getExtractFields())) {
            spiderConfig.getExtractFields().stream().forEach(p -> {
                JobExtractField jobExtractField = new JobExtractField();
                jobExtractField.setJobId(jobConfig.getId());
                jobExtractField.setField(p.getField());
                jobExtractField.setRule(p.getRule());
                jobConfigDao.insertJobExtractField(jobExtractField);
            });
        }
        spiderConfigList.add(spiderConfig);
        spiderList.add(pageProcessorFactory.getSpider(spiderConfig));
        return 1;
    }

    /**
     * 更新配置
     *
     * @param spiderConfig
     * @return
     */
    public Integer modifySpiderConfig(SpiderConfig spiderConfig) {
        if (spiderConfig == null || spiderConfig.getId() < 1 || StringUtils.isEmpty(spiderConfig.getKey())) {
            throw new BaseException(2001, "配置为空");
        }
        Spider spider = getSpiderByKey(spiderConfig.getKey());
        SpiderConfig spiderConfigExist = getSpiderConfigByKey(spiderConfig.getKey());
        if (spider == null) {
            throw new BaseException(2002, "找不到");
        }
        if (spider.getStatus() == Spider.Status.Running) {
            throw new BaseException(2003, "仍在执行中");
        }
        //更新主记录
        JobConfig jobConfig = new JobConfig();
        jobConfig.setId(spiderConfig.getId());
        jobConfig.setKey(spiderConfig.getKey());
        jobConfig.setName(spiderConfig.getName());
        jobConfig.setDesc(spiderConfig.getDesc());
        jobConfig.setStartUrl(spiderConfig.getStartUrl());
        jobConfig.setUserAgent(spiderConfig.getUserAgent());
        jobConfig.setSleepTime(spiderConfig.getSleepTime());
        jobConfig.setRetryTimes(spiderConfig.getRetryTimes());
        jobConfigDao.updateModel(jobConfig);
        //删除旧记录
        jobConfigDao.deleteJobTargetUrl(jobConfig.getId());
        jobConfigDao.deleteJobExtractField(jobConfig.getId());
        spiderList.remove(spider);
        spiderConfigList.remove(spiderConfigExist);
        if (CollectionUtils.isNotEmpty(spiderConfig.getExtractFields())) {
            spiderConfig.getExtractFields().stream().forEach(p -> {
                JobExtractField jobExtractField = new JobExtractField();
                jobExtractField.setField(p.getField());
                jobExtractField.setJobId(jobConfig.getId());
                jobExtractField.setRule(p.getRule());
                jobConfigDao.insertJobExtractField(jobExtractField);
            });
        }
        if (CollectionUtils.isNotEmpty(spiderConfig.getRegexTargetUrls())) {
            spiderConfig.getRegexTargetUrls().stream().forEach(p -> {
                JobTargetUrl jobTargetUrl = new JobTargetUrl();
                jobTargetUrl.setExpression(p);
                jobTargetUrl.setJobId(jobConfig.getId());
                jobConfigDao.insertJobTargetUrl(jobTargetUrl);
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
    public Integer startSpider(String key) {
        Spider spider = getSpiderByKey(key);
        if (spider == null) {
            throw new BaseException(2001, "找不到");
        }
        if (spider.getStatus() == Spider.Status.Running) {
            throw new BaseException(2002, "正在执行");
        }
        spider.start();
        spiderConfigList.stream().filter(p -> p.getKey().equals(key)).forEach(p -> p.setStatus(Spider.Status.Running.ordinal()));
        return Spider.Status.Running.ordinal();
    }

    /**
     * 停止
     *
     * @param key
     */
    public Integer stopSpider(String key) {
        Spider spider = getSpiderByKey(key);
        if (spider == null) {
            throw new BaseException(2001, "找不到");
        }
        if (spider.getStatus() == Spider.Status.Stopped) {
            throw new BaseException(2002, "已经停止");
        }
        spider.stop();
        spiderConfigList.stream().filter(p -> p.getKey().equals(key)).forEach(p -> p.setStatus(Spider.Status.Stopped.ordinal()));
        return Spider.Status.Stopped.ordinal();
    }

    /**
     * 移除配置
     *
     * @param key
     */
    public void removeSpider(String key) {
        Spider spider = getSpiderByKey(key);
        if (spider == null) {
            throw new BaseException(2001, "找不到");
        }
        if (spider.getStatus() == Spider.Status.Running) {
            spider.stop();
        }
        spiderList.remove(spider);
        SpiderConfig spiderConfig = getSpiderConfig(key);
        if (spiderConfig == null) {
            return;
        }
        spiderConfigList.remove(spiderConfig);
        jobConfigDao.deleteJob(key);
        jobConfigDao.deleteJobExtractField(spiderConfig.getId());
        jobConfigDao.deleteJobTargetUrl(spiderConfig.getId());
    }

    /**
     * 查询执行结果
     *
     * @param key  标识
     * @param page 页数
     * @param size 行数
     * @return
     */
    public List<Map<String, Object>> getSpiderResultList(String key, Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        List<Map<String, Object>> result = new ArrayList<>();
        if (StringUtils.isEmpty(key)) {
            return result;
        }
        Class resultType = HashMap.class;
        Query query = new Query();
        query.addCriteria(Criteria.where("_config_key").is(key));
        query.with(PageRequest.of(page, size));
        return mongoTemplate.find(query, resultType, SpiderConstants.SPIDER_RESULT_COLLECTION);
    }

    /**
     * 清空已有的数据
     *
     * @param key
     * @return
     */
    public Integer clearSpiderResult(String key) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_config_key").is(key));
        DeleteResult deleteResult = mongoTemplate.remove(query, SpiderConstants.SPIDER_RESULT_COLLECTION);
        return (int) deleteResult.getDeletedCount();
    }

    /**
     * 获取所有表头
     *
     * @param key
     * @return
     */
    public List<String> getFieldList(String key) {
        List<JobExtractField> fieldList = jobConfigDao.getJobExtractFieldByKey(key);
        return fieldList.stream().map(p -> p.getField()).filter(p -> !p.startsWith("_")).collect(Collectors.toList());
    }
}
