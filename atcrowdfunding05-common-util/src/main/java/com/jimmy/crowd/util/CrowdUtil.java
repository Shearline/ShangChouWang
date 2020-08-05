package com.jimmy.crowd.util;

import javax.servlet.http.HttpServletRequest;


public class CrowdUtil {
    /**
     * 判断当前请求是否是Ajax请求
     * @param request request请求对象
     * @return
     *         true：当前请求是Ajax请求
     *         false：当前请求不是Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        //1.获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");

        //2.判断
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                || (xRequestHeader != null && "XMLHttpRequest".equals(xRequestHeader));
    }
}
