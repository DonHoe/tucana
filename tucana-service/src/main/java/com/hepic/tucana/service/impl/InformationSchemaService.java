package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.InformationSchemaDao;
import com.hepic.tucana.dal.entity.mysql.Columns;
import com.hepic.tucana.dal.entity.mysql.TableInfo;
import com.hepic.tucana.util.CommonUtil;
import com.hepic.tucana.util.constant.ConstantMap;
import com.hepic.tucana.util.constant.ConstantString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
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

        return StringUtils.EMPTY;
    }

    /**
     * 构建生成器
     *
     * @return
     */
    public TableInfo generateTableInfo(String table) {

        TableInfo tableInfo = informationSchemaDao.getTableByName(ConstantString.database, table);
        if (table == null) {
            return tableInfo;
        }
        List<Columns> columns = informationSchemaDao.getColumnsList(ConstantString.database, table);
        tableInfo.setTableName(CommonUtil.translateName(tableInfo.getTableName(), ""));
        String tableComment = tableInfo.getTableComment();
        if (StringUtils.isNotBlank(tableComment) && (tableComment.indexOf("表") == tableComment.length() - 1)) {
            tableInfo.setTableComment(tableComment.substring(0, tableComment.length() - 1));
        }
        columns.forEach(p -> {
            p.setColumnName(CommonUtil.translateName(p.getColumnName(), ""));
            p.setDataType(ConstantMap.typeMap.get(p.getDataType()));
        });
        tableInfo.setColumns(columns);
        return tableInfo;
    }

    /**
     * 获取数据库中的表集合
     *
     * @return
     */
    public List<TableInfo> getTableList() {
        return informationSchemaDao.getTableList(ConstantString.database);
    }

    /**
     * 获取列集合
     *
     * @param database 数据库
     * @param table    表
     * @return
     */
    public List<Columns> getColumnsList(String table) {
        return informationSchemaDao.getColumnsList(ConstantString.database, table);
    }

}
