package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.JobConfig;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobConfigDao extends JpaRepository<JobConfig, Long> {
}
