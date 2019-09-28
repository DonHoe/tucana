package com.hepic.tucana.dal.entity.mysql;

import java.util.List;

public class TableInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 备注
     */
    private String tableComment;

    private List<Columns> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<Columns> getColumns() {
        return columns;
    }

    public void setColumns(List<Columns> columns) {
        this.columns = columns;
    }
}
