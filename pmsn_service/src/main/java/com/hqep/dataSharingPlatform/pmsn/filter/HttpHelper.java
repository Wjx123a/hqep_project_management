package com.hqep.dataSharingPlatform.pmsn.filter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 用于将字节数组转换为字符串
 *
 *
 *
 */
public class HttpHelper {
    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        String bodyString = "";
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HashMap<String,String>  map = new HashMap<String,String>();
        map.put("%", "\\\\\\\\%");
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            bodyString = sb.toString();
             if(httpRequest.getRequestURI().startsWith("/")){
            	 for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					bodyString = bodyString.replaceAll(key, map.get(key));

				}
             }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bodyString;
    }
}