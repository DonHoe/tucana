package com.hepic.tucana.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author hd23973
 * @Title:
 * @Description:
 * @date 2018/4/13.
 */
public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);


    public static void debug(String msg) {
        logger.debug("<{}><{}><{}>{}", logHead().get("packageName"), logHead().get("className"), logHead().get("methodName"), msg);
    }

    public static void info(String msg) {
        logger.info("<{}><{}><{}>{}", logHead().get("packageName"), logHead().get("className"), logHead().get("methodName"), msg);
    }

    public static void warn(String msg) {
        logger.warn("<{}><{}><{}>{}", logHead().get("packageName"), logHead().get("className"), logHead().get("methodName"), msg);
    }


    private static HashMap logHead() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        // 获取所有上层调用方法的堆栈信息
        StackTraceElement[] stacks = (new Throwable()).getStackTrace();
        logger.debug("<{}><{}><{}>stacks length:{}", "com.hepic.tucana.util", "LoggerUtil", "logHead", stacks.length);
        try {
            String clazzName = stacks[2].getClassName();
            String logSubCategory = stacks[2].getMethodName();
            String logModule = clazzName.substring(0, clazzName.lastIndexOf("."));
            String logCategory = clazzName.substring(clazzName.lastIndexOf(".") + 1, clazzName.length());
            hashMap.put("packageName", logModule);
            hashMap.put("className", logCategory);
            hashMap.put("methodName", logSubCategory);
        } catch (Exception e) {
            logger.error("<{}><{}><{}>{}", "com.hepic.tucana.util", "LoggerUtil", "logHead", e.getMessage(), e);
        }
        return hashMap;
    }
}
