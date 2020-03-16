package com.zk.device.service.impl;

import com.zk.device.dal.InformationSchemaDao;
import com.zk.device.model.dal.Columns;
import com.zk.device.model.dal.TableInfo;
import com.zk.device.util.CommonUtil;
import com.zk.device.util.VelocityUtil;
import com.zk.device.util.constant.ConstantMap;
import com.zk.device.util.constant.ConstantString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class InformationSchemaService {

    @Autowired
    private InformationSchemaDao informationSchemaDao;

    private Map<String, String> templateMap = new HashMap<String, String>() {{
        put("velocity/mapper.xml.vm", "resources/mapping/%sMapper.xml");
        put("velocity/dao.java.vm", "java/dao/%sDao.java");
        put("velocity/service.java.vm", "java/service/%sServiceImpl.java");
        put("velocity/interface.java.vm", "java/service/%sService.java");
        put("velocity/entity.java.vm", "java/entity/%s.java");
        put("velocity/controller.java.vm", "java/controller/%sController.java");
    }};

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
        tableInfo.setProject("com.zk.device");
        List<Columns> columns = informationSchemaDao.getColumnsList(ConstantString.database, table);
        tableInfo.setAttributeName(CommonUtil.translateName(tableInfo.getTableName(), ""));
        tableInfo.setBeanName(CommonUtil.toUpperInitial(tableInfo.getAttributeName()));
        String tableComment = tableInfo.getTableComment();
        if (StringUtils.isNotBlank(tableComment) && (tableComment.indexOf("表") == tableComment.length() - 1)) {
            tableInfo.setTableComment(tableComment.substring(0, tableComment.length() - 1));
        }
        columns.forEach(p -> {
            p.setPropertyName(CommonUtil.translateName(p.getColumnName(), ""));
            p.setPropertyNameUp(CommonUtil.toUpperInitial(p.getPropertyName()));
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

    /**
     * 构建代码
     *
     * @param table
     * @return
     * @throws IOException
     */
    public byte[] codeCreate(String table) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(byteArrayOutputStream);

        TableInfo tableInfo = generateTableInfo(table);
        Map<String, Object> map = new HashMap<>();
        map.put("table", tableInfo);

        templateMap.entrySet().forEach(p -> {
            String result = null;
            try {
                result = VelocityUtil.render(p.getKey(), map);
                // 添加到zip
                String fileName = String.format(p.getValue(), tableInfo.getBeanName());
                zip.putNextEntry(new ZipEntry(fileName));
                zip.write(result.getBytes(Charset.defaultCharset()));
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        zip.close();
        return byteArrayOutputStream.toByteArray();
    }

}
