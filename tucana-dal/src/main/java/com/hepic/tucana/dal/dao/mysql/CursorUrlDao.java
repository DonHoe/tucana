package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.CursorUrl;

public interface CursorUrlDao {

    int deleteByPrimaryKey(Long id);

    int insertSelective(CursorUrl record);

    CursorUrl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CursorUrl record);

    CursorUrl selectByUrl(String url);

    int selectCount(int status);

    CursorUrl selectTop();
}