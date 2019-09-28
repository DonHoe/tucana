package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.InformationSchemaDao;
import com.hepic.tucana.dal.entity.mysql.Columns;
import com.hepic.tucana.dal.entity.mysql.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationSchemaService {

    @Autowired
    private InformationSchemaDao informationSchemaDao;

    /**
     * 构建生成器
     *
     * @return
     */
    public String generateCode(String table) {

        List<Columns> columns = informationSchemaDao.getColumnsList("", "");

        return StringUtils.EMPTY;
    }

    /**
     * 获取数据库中的表集合
     *
     * @param database 数据库
     * @return
     */
    public List<TableInfo> getTableList(String database) {
        return informationSchemaDao.getTableList(database);
    }

    /**
     * 获取列集合
     *
     * @param database 数据库
     * @param table    表
     * @return
     */
    public List<Columns> getColumnsList(String database, String table) {
        return informationSchemaDao.getColumnsList(database, table);
    }

}
