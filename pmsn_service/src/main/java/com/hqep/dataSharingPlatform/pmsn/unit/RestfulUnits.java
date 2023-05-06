package com.hqep.dataSharingPlatform.pmsn.unit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import utils.UUIDUtil;

import java.util.HashMap;
import java.util.Map;

public class RestfulUnits {

    public static Map<String,Object> postForObject(String url, String token, MultiValueMap<String, Object> map) {
        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("token",token);
        // 组装请求体
        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<MultiValueMap<String, Object>>(map, headers);
        RestTemplate template =new RestTemplate();
        // 发送post请求，并输出结果
        System.out.println("输出请求参数："+map);
        Map<String,Object> result = template.postForObject(url, request, Map.class);
        System.out.println(result);
        return result;
    }

    public static Map<String,Object> postForObjectForJson(String url, String token, MultiValueMap<String, Object> map) {
        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token",token);
        // 组装请求体
        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<MultiValueMap<String, Object>>(map, headers);
        RestTemplate template =new RestTemplate();
        // 发送post请求，并输出结果
        System.out.println("输出请求参数："+map);
        Map<String,Object> result = template.postForObject(url, request, Map.class);
        System.out.println(result);
        return result;
    }


    public static Map<String,String> postForObjectForJSONObject(String url, String token,JSONObject param) {
        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("token",token);
//        // 组装请求体
        RestTemplate template =new RestTemplate();
        String jsonStr = param.toJSONString();
        System.out.println("请求参数："+jsonStr);
        HttpEntity<String> request = new HttpEntity<String>(param.toJSONString(),headers);
        Map result = template.postForEntity(url, request, Map.class).getBody();
        System.out.println(result);
        return result;
    }


}
