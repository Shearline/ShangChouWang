package com.jimmy.crowd.util;

import com.jimmy.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CrowdUtil {
    /**
     * 判断当前请求是否是Ajax请求
     *
     * @param request request请求对象
     * @return true：当前请求是Ajax请求
     * false：当前请求不是Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        //1.获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        //2.判断
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                || (xRequestHeader != null && "XMLHttpRequest".equals(xRequestHeader));
    }

    /**
     * 对明文字符串传入MD5加密
     *
     * @param source 传入的明文字符串
     * @return 加密结果
     */
    public static String md5(String source) {
        //1.判断source是否有效
        if (source == null || source.length() <= 0) {
            //2.如果不是有效的字符串，抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        //3.获取MessageDigest对象
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 4.获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

            // 5.执行加密
            byte[] output = messageDigest.digest(input);

            // 6.创建一个BigInteger对象，
            int sigNum = 1;
            BigInteger bigInteger = new BigInteger(sigNum, output);

            // 7.按照16进制将bigInteger值转换为字符串
            int radix = 16;

            return bigInteger.toString(radix).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
