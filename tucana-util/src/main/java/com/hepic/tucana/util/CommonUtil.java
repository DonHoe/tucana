package com.hepic.tucana.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 通用工具类
 */
public class CommonUtil {

    /**
     * 转换Integer
     *
     * @param strInt
     * @param defaultValue
     * @return
     */
    public static Integer tryParseInteger(String strInt, Integer defaultValue) {
        try {
            defaultValue = Integer.valueOf(strInt);

        } catch (Exception e) {

        }
        return defaultValue;
    }

    /**
     * 转换命名
     *
     * @param name
     * @return
     */
    public static String translateName(String name, String prefix) {
        if (StringUtils.isBlank(name)) {
            return name;
        }
        if (StringUtils.isNotBlank(prefix) && name.startsWith(prefix)) {
            name = name.substring(prefix.length());
        }
        String[] names = name.split("_");
        if (names.length > 1) {
            name = names[0];
            for (int i = 1; i < names.length; i++) {
                String temp = names[i];
                if (StringUtils.isBlank(temp)) {
                    continue;
                }
                name += temp.substring(0, 1).toUpperCase();
                if (temp.length() > 1) {
                    name += temp.substring(1);
                }
            }
        }
        return name;
    }

    /**
     * 首字母大写
     *
     * @param word
     * @return
     */
    public static String toUpperInitial(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    /**
     * 生成UUID
     *
     * @return
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
