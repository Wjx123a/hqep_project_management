package com.hqep.dataSharingPlatform.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class test {

    public static void main(String[] args) throws JsonProcessingException {

        String url1="http://192.168.137.65:8080/negative/user/ram/getAllUsers";
        String userList = grantSelectDesc(url1,new HashMap());
        Gson gson=new Gson();
        System.out.println("存储一下");
        System.out.println(userList);
        System.out.println("存储一下");
        Map userListMap = gson.fromJson(userList,Map.class);
        if(userListMap!=null&&userListMap.containsKey("message")&&"success".equals(userListMap.get("message"))){
            System.out.println("getColumnsDict Success");
            System.out.println(userListMap.get("data"));
        }else{
            System.out.println("getUserList Error");
            System.out.println(userListMap);
        }

        String url2="http://192.168.137.65:8080/negative/table/dict/getColumnsDict";
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("tableName", "table_001");
        params.put("tableCnName", "表_001");
        params.put("tableType", "贴源层");
        params.put("columnName","column_01");
        params.put("columnCnName","列_01");
        params.put("columnType","varchar");
        params.put("columnLength",1000);
        params.put("columnNullable",true);
        String ColumnsDict = grantSelectDesc(url2,params);
        System.out.println("存储一下");
        System.out.println(ColumnsDict);
        System.out.println("存储一下");
        Map ColumnsDictMap = gson.fromJson(ColumnsDict,Map.class);
        if(ColumnsDictMap!=null&&ColumnsDictMap.containsKey("message")&&"success".equals(ColumnsDictMap.get("message"))){
            System.out.println("getColumnsDict Success");
            System.out.println(ColumnsDictMap.get("data"));
        }else{
            System.out.println("getColumnsDict Error");
            System.out.println(ColumnsDictMap);
        }
        Map<String, Object> params_grantList = new LinkedHashMap<>();
        params_grantList.put("fromUserId","root");//本地账，使用改账号对toUserId进行授权操作
        params_grantList.put("projectName","guowang_train1");//项目名称
        params_grantList.put("tableName","lesson12");//具体表名
        params_grantList.put("toUserId","gts_demo");//ram账号
        String url3 ="http://192.168.137.65:8080/negative/grant/table/grantSelectDesc";
        String grantResult = grantSelectDesc(url3,params_grantList);
        System.out.println("存储一下");
        System.out.println(grantResult);
        System.out.println("存储一下");
        Map grantResultMap = gson.fromJson(grantResult,Map.class);
        if(grantResultMap!=null&&grantResultMap.containsKey("message")&&"success".equals(grantResultMap.get("message"))){
            System.out.println("grantResultMap Success");
            System.out.println(ColumnsDictMap.get("data"));
        }else{
            System.out.println("grantResultMap Error");
            System.out.println(ColumnsDictMap);
        }
    }

    public static String grantSelectDesc(String url,Map params) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        RestTemplate client = new RestTemplate();
//  一定要设置header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//  将提交的数据转换为String
//  最好通过bean注入的方式获取ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(params);
        HttpEntity<String> requestEntity = new HttpEntity<String>(value, headers);
//  执行HTTP请求
        ResponseEntity<String> response = client.postForEntity(url, requestEntity, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }
}
