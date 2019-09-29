package com.hepic.tucana.dal.entity.mysql;

import java.util.List;

public class TableInfo {

    /**
     * 基础项目包
     */
    private String project;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 类名
     */
    private String beanName;

    /**
     * 类名
     */
    private String attributeName;

    /**
     * 备注
     */
    private String tableComment;

    /**
     * 列信息
     */
    private List<Columns> columns;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
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
