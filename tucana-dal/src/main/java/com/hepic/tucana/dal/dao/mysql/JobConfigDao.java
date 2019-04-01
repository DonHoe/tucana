package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.JobConfig;
import com.hepic.tucana.dal.entity.mysql.JobExtractField;
import com.hepic.tucana.dal.entity.mysql.JobTargetUrl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
            " ,`delete` " +
            " ,creator " +
            " ,create_time AS createTime " +
            " ,modifier " +
            " ,update_time AS updateTime " +
            " FROM job_config WHERE `delete` = 0 ")
    List<JobConfig> getJobConfigList();

    @Select("SELECT `id` " +
            " ,`key` " +
            " ,`name` " +
            " ,`desc` " +
            " ,`start_url` AS startUrl " +
            " ,`user_agent` AS userAgent " +
            " , sleep_time AS sleepTime " +
            " ,retry_times AS retryTimes " +
            " ,`delete` " +
            " ,creator " +
            " ,create_time AS createTime " +
            " ,modifier " +
            " ,update_time AS updateTime " +
            " FROM job_config WHERE `key` = #{key} AND `delete` = 0  LIMIT 1 ")
    JobConfig getJobConfigByKey(String key);

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
            " ,`delete` "+
            " ,creator  " +
            " ,create_time AS createTime  " +
            " ,modifier  " +
            " ,update_time AS updateTime  " +
            " FROM job_extract_field where `job_id` = #{jobId} AND `delete` = 0 ")
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
            " ,`delete` "+
            " ,creator " +
            " ,create_time AS createTime " +
            " ,modifier " +
            " ,update_time AS updateTime " +
            "FROM job_target_url WHERE `job_id` = #{jobId} AND `delete` = 0 ")
    List<JobTargetUrl> getJobTargetUrlByConfigId(Long jobId);

    /**
     * 插入主配置
     *
     * @param jobConfig 主配置
     * @return
     */
    @Insert("INSERT INTO `job_config` " +
            " (`name`, `desc`,`delete`, `start_url`, `user_agent`, `sleep_time`, `retry_times`, `creator`) " +
            " VALUES " +
            " (#{name}, #{desc},#{delete}, #{startUrl}, #{userAgent}, #{sleepTime}, #{retryTimes}, #{creator});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
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
    @Options(useGeneratedKeys = true, keyProperty = "id")
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
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertJobTargetUrl(JobTargetUrl jobTargetUrl);

    /**
     * 按key更新状态
     *
     * @param key
     * @return
     */
    @Update("UPDATE job_config SET `delete` = 1 WHERE `key` = #{key} ")
    Integer deleteJob(String key);

    /**
     * 按主配置id删除提取数据
     *
     * @param jobId
     * @return
     */
    @Update("UPDATE job_extract_field SET `delete` = 1 WHERE job_id = #{jobId} ")
    Integer deleteJobExtractField(Long jobId);

    /**
     * 按主配置id删除url拉取数据
     *
     * @param jobId
     * @return
     */
    @Update("UPDATE job_target_url SET `delete` = 1 WHERE job_id = #{jobId} ")
    Integer deleteJobTargetUrl(Long jobId);

    /**
     * 更新数据
     *
     * @param jobConfig
     * @return
     */
    Integer updateModel(JobConfig jobConfig);
}
