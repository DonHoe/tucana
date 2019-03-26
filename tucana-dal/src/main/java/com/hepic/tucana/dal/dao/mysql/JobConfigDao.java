package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.JobConfig;
import com.hepic.tucana.dal.entity.mysql.JobExtractField;
import com.hepic.tucana.dal.entity.mysql.JobTargetUrl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JobConfigDao {

    /**
     * 获取当前所有配置
     *
     * @return
     */
    @Select("SELECT `id` " +
            " ,`key` " +
            " ,`name` " +
            " ,`desc` " +
            " ,`start_url` AS startUrl " +
            " ,`user_agent` AS userAgent " +
            " , sleep_time AS sleepTime " +
            " ,retry_times AS retryTimes " +
            " ,creator " +
            " ,create_time AS createTime " +
            " ,modifier " +
            " ,update_time AS updateTime " +
            " FROM job_config")
    List<JobConfig> getJobConfigList();

    /**
     * 根据主配置id查询提取配置
     *
     * @param jobId 主配置id
     * @return
     */
    @Select("SELECT `id`  " +
            " ,`job_id` AS jobId  " +
            " ,`key`  " +
            " ,`value`  " +
            " ,creator  " +
            " ,create_time AS createTime  " +
            " ,modifier  " +
            " ,update_time AS updateTime  " +
            " FROM job_extract_field where `job_id` = #{jobId}")
    List<JobExtractField> getJobExtractFieldByConfigId(Long jobId);

    /**
     * 根据主配置id查询URL筛选规则
     *
     * @param jobId 主配置id
     * @return
     */
    @Select("SELECT `id` " +
            " ,`job_id` AS jobId " +
            " ,`expression` " +
            " ,`type` " +
            " ,creator " +
            " ,create_time AS createTime " +
            " ,modifier " +
            " ,update_time AS updateTime " +
            "FROM job_target_url WHERE `job_id` = #{jobId}")
    List<JobTargetUrl> getJobTargetUrlByConfigId(Long jobId);

    /**
     * 插入主配置
     *
     * @param jobConfig 主配置
     * @return
     */
    @Insert("INSERT INTO `job_config` " +
            " (`name`, `desc`, `start_url`, `user_agent`, `sleep_time`, `retry_times`, `creator`) " +
            " VALUES " +
            " (#{name}, #{desc}, #{startUrl}, #{userAgent}, #{sleepTime}, #{retryTimes}, #{creator});")
    Integer insertJobConfig(JobConfig jobConfig);

    /**
     * 插入提取配置
     *
     * @param jobExtractField 提取配置
     * @return
     */
    @Insert("INSERT INTO `job_extract_field` " +
            " (`job_id`, `key`, `value`, `creator`) " +
            " VALUES " +
            " (#{jobId}, #{key}, #{value}, #{creator});")
    Integer insertJobExtractField(JobExtractField jobExtractField);

    /**
     * 插入URL筛选规则
     *
     * @param jobTargetUrl URL筛选规则
     * @return
     */
    @Insert("INSERT INTO `job_target_url` " +
            " (`job_id`, `expression`, `creator`) " +
            " VALUES " +
            " (#{jobId}, #{expression}, #{creator});")
    Integer insertJobTargetUrl(JobTargetUrl jobTargetUrl);
}
