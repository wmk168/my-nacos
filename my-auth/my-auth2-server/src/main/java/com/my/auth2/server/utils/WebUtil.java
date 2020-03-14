package com.my.auth2.server.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;

/**
 * web工具类
 * 
 * @author minkeWei
 *
 */
public class WebUtil {
	
	public static boolean isAjaxRequest(HttpServletRequest request) {
		//PC判断
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
        	//APP判断
        	String accept = request.getHeader("accept");
			if(accept!= null && accept.indexOf("application/json") != -1){
			    return true;
			}
            return false;
        }
    }
	
	/**
	 * 设置跨域
	 * @param response
	 */
	public static void setOriginHeader(HttpServletResponse response) {
		// 允许跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
		response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");

	}
	
	/**
	 * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    public static void writeJson(HttpServletResponse response, Object object) {
        writeJson(response, JSON.toJSONString(object), MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
    
	/**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    public static void writeJson(HttpServletResponse response, String string, String type) {
        try {
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
        }
    }
}
