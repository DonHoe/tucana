package com.hepic.tucana.dal.dao.mysql;

import com.hepic.tucana.dal.entity.mysql.Columns;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InformationSchemaDao {

    /**
     * 查询表
     *
     * @param database database name
     * @return table list
     */
    @Select("select TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_type = 'BASE TABLE' AND TABLE_SCHEMA = #{database} ")
    List<String> getTableList(String database);

    /**
     * 表查询字段
     *
     * @param database database name
     * @param table table name
     * @return column list
     */
    @Select("SELECT COLUMN_NAME AS columnName, " +
            "ORDINAL_POSITION AS ordinalPosition, " +
            "COLUMN_DEFAULT AS columnDefault, " +
            "IS_NULLABLE AS isNullable, " +
            "COLUMN_TYPE AS columnType, " +
            "COLUMN_KEY AS columnKey, " +
            "EXTRA AS extra " +
            "FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = #{database} AND TABLE_NAME = #{table} ")
    List<Columns> getColumnsList(String database, String table);
}
