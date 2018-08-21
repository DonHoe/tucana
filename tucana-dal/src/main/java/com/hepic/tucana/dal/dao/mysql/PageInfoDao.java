package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.PageInfo;

import java.util.List;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/21.
 */
public interface PageInfoDao {

    int insert(PageInfo entity);

    List<PageInfo> selectListByModel(PageInfo request);
}
