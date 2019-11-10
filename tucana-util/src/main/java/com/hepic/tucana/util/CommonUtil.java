package com.hepic.tucana.util;

/**
 * 通用工具类
 */
public class CommonUtil {

    public static Integer tryParseInteger(String strInt, Integer defaultValue) {
        try {
            defaultValue = Integer.valueOf(strInt);

        } catch (Exception e) {

        }
        return defaultValue;
    }
}
