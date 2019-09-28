package com.hepic.tucana.util.constant;

import java.util.HashMap;
import java.util.Map;

public class ConstantMap {

    public static Map<String, String> typeMap = new HashMap<String, String>() {
        {
            put("tinyint", "Integer");
            put("smallint", "Integer");
            put("mediumint", "Integer");
            put("int", "Integer");
            put("integer", "integer");
            put("bigint", "Long");
            put("float", "Float");
            put("double", "Double");
            put("decimal", "BigDecimal");
            put("bit", "Boolean");
            put("char", "String");
            put("varchar", "String");
            put("tinytext", "String");
            put("text", "String");
            put("mediumtext", "String");
            put("longtext", "String");
            put("time", "Date");
            put("date", "Date");
            put("datetime", "Date");
            put("timestamp", "Date");
        }
    };

}
