package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.MutualAid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutualAidDao extends JpaRepository<MutualAid, Long> {
}
