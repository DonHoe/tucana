package com.hepic.tucana.dal.dao;

import com.hepic.tucana.model.dal.Columns;
import com.hepic.tucana.model.dal.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InformationSchemaDao {

    /**
     * 查询表
     *
     * @param database database name
     * @return table list
     */
    @Select("select TABLE_NAME as tableName  " +
            " ,TABLE_COMMENT as tableComment " +
            " ,CREATE_TIME as createTime " +
            " ,UPDATE_TIME as updateTime " +
            " FROM INFORMATION_SCHEMA.TABLES " +
            " WHERE table_type = 'BASE TABLE' AND TABLE_SCHEMA = #{database} ")
    List<TableInfo> getTableList(String database);

    /**
     * 查询表
     *
     * @param database database name
     * @param table    table name
     * @return table list
     */
    @Select("select TABLE_NAME as tableName  " +
            " ,TABLE_COMMENT as tableComment " +
            " FROM INFORMATION_SCHEMA.TABLES " +
            " WHERE table_type = 'BASE TABLE' " +
            " AND TABLE_SCHEMA = #{database} AND TABLE_NAME = #{table} ")
    TableInfo getTableByName(String database, String table);

    /**
     * 表查询字段
     *
     * @param database database name
     * @param table    table name
     * @return column list
     */
    @Select("SELECT COLUMN_NAME AS columnName, " +
            "ORDINAL_POSITION AS ordinalPosition, " +
            "COLUMN_DEFAULT AS columnDefault, " +
            "IS_NULLABLE AS isNullable, " +
            "COLUMN_TYPE AS columnType, " +
            "DATA_TYPE AS dataType, " +
            "COLUMN_KEY AS columnKey, " +
            "EXTRA AS extra ," +
            "COLUMN_COMMENT AS columnComment " +
            "FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = #{database} AND TABLE_NAME = #{table} ")
    List<Columns> getColumnsList(String database, String table);
}
