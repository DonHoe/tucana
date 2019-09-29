package com.hepic.tucana.service.impl;

import com.hepic.tucana.dal.dao.mysql.InformationSchemaDao;
import com.hepic.tucana.dal.entity.mysql.Columns;
import com.hepic.tucana.dal.entity.mysql.TableInfo;
import com.hepic.tucana.util.CommonUtil;
import com.hepic.tucana.util.VelocityInitializer;
import com.hepic.tucana.util.VelocityUtil;
import com.hepic.tucana.util.constant.ConstantMap;
import com.hepic.tucana.util.constant.ConstantString;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.sql.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        tableInfo.setProject("com.hepic.tucana.dal");
        List<Columns> columns = informationSchemaDao.getColumnsList(ConstantString.database, table);
        tableInfo.setBeanName(CommonUtil.translateName(tableInfo.getTableName(), ""));
        String tableComment = tableInfo.getTableComment();
        if (StringUtils.isNotBlank(tableComment) && (tableComment.indexOf("表") == tableComment.length() - 1)) {
            tableInfo.setTableComment(tableComment.substring(0, tableComment.length() - 1));
        }
        columns.forEach(p -> {
            p.setPropertyName(CommonUtil.translateName(p.getColumnName(), ""));
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
    public List<Columns> getColumnsList(String table)  {
        return informationSchemaDao.getColumnsList(ConstantString.database, table);
    }

    public byte[] codeCreate(String table) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(byteArrayOutputStream);

        TableInfo tableInfo = generateTableInfo(table);
        Map<String, Object> map = new HashMap<>();
        map.put("table", tableInfo);
        String result = VelocityUtil.render("templates/mapper.xml.vm", map);
        // 添加到zip
        zip.putNextEntry(new ZipEntry("resources/mapping/mapper.xml"));
        zip.write(result.getBytes(Charset.defaultCharset()));
        zip.closeEntry();
        zip.close();
        return byteArrayOutputStream.toByteArray();
    }

}
