package com.hepic.tucana.util.encryption;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/7/12.
 */
@Slf4j
public class Md5 {

    /**
     * 密码加密扩展字段
     */
    public static String encryptionKey = "encodeKey";

    /**
     * Md5加密
     *
     * @param str
     * @return
     */
    public static String encode(String str, String extendStr) {
        String result = "";
        try {
            str += extendStr;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Array = md5.digest(str.getBytes("utf-8"));
            BASE64Encoder base64en = new BASE64Encoder();
            result = base64en.encode(md5Array);
        } catch (NoSuchAlgorithmException e) {
            log.error("加密解密异常", e, str);
        } catch (UnsupportedEncodingException e) {
            log.error("加密解密异常", e, str);
        }
        return result;
    }
}
