package com.hqep.dataSharingPlatform.pmsn.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.CheckGwService;
import com.hqep.dataSharingPlatform.pmsn.unit.ListUnit;
import com.hqep.dataSharingPlatform.pmsn.unit.RCheckMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 两级数据目录一致性校验方案
 * 比对程序
 */
@RestController
@RequestMapping("/checkGwCompare")
public class CheckGwCompareAction {

    @Autowired
    CheckGwService service;


    /**
     * 抽取国网全量资源目录树信息
     * @param request
     * @return
     */
    @RequestMapping("/extractCatalogTreeByAll")
    public Map<String,Object> extractCatalogTreeByAll(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String orgCode = req.getParameter("orgCode");
            String systemCode = req.getParameter("systemCode");
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryCatalogTree";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("orgCode", orgCode);
            map.add("systemCode", systemCode);
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                List<Map<String,Object>> list = (List<Map<String,Object>>)result.get("data");
                if (list != null && list.size()>0) {
                    List<List<Map<String, Object>>> lists = ListUnit.splistList(list, 100);
                    for (int i = 0; i < lists.size(); i++) {
                        service.insertCatalogTree(lists.get(i));
                    }
                }
            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }


    /**
     * 抽取国网全量表列表信息
     * @param request
     * @return
     */
    @RequestMapping("/extractTableListBySystem")
    public List<Map<String,Object>> extractTableListBySystem(ServletRequest request){
        List<Map<String,Object>> resultList = new ArrayList<>();
        try {
            Map<String,Object> result = this.createToken(null,null);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                String token  = (String) ((Map) result.get("data")).get("data");
//                String token  = (String) result.get("data");
                HttpServletRequest req = (HttpServletRequest) request;
                String req_systemCode = req.getParameter("systemCode");
                PageData pd = new PageData();
                pd.put("SYSTEM_CODE",req_systemCode);
                List<PageData> systemList = service.queryGwSystemList(pd);
                for (int i = 0; i < systemList.size(); i++) {
                    String systemCode = systemList.get(i).getString("SYSTEM_CODE");
                    if (systemCode != null &&  !"".equals(systemCode)) {
                        resultList.add(queryTableListBySystem(systemCode,token));
                    } else {
                        resultList.add(RCheckMsg.errorMsg(500,"查询到系统编码为空："+i,null));
                    }
                }
            } else {
                resultList.add(RCheckMsg.errorMsg(500,"账号或者密码错误",null));
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }


    /**
     * 抽取国网全量表属性信息
     * @param request
     * @return
     */
    @RequestMapping("/extractTableProListBySystemTable")
    public List<Map<String,Object>> extractTableProListBySystemTable(ServletRequest request){
        System.out.println("抽取国网全量表属性信息");
        List<Map<String,Object>> resultList = new ArrayList<>();
        try {
            Map<String,Object> result = this.createToken(null,null);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                String token  = (String) ((Map) result.get("data")).get("data");
                HttpServletRequest req = (HttpServletRequest) request;
                String req_systemCode = req.getParameter("systemCode");
                PageData pd = new PageData();
                pd.put("SYSTEM_CODE",req_systemCode);
                List<PageData> systemList = service.queryGwSystemList(pd);
                System.out.println(systemList);
                System.out.println(systemList);
                for (int i = 0; i < systemList.size(); i++) {
                    String systemCode = systemList.get(i).getString("SYSTEM_CODE");
                    if (systemCode != null &&  !"".equals(systemCode)) {
                        resultList.add(queryTableProListBySystemTable(systemCode,token));
                    } else {
                        resultList.add(RCheckMsg.errorMsg(500,"查询到系统编码为空："+i,null));
                    }
                }
            } else {
                resultList.add(RCheckMsg.errorMsg(500,"账号或者密码错误",null));
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultList.add(RCheckMsg.errorMsg(500,"抽取出现错误",null));
        }
        return resultList;
    }




    /**
     * 按照系统抽取 每一个系统的所有列
     * @param systemCode
     * @param token
     * @return
     */
    public Map<String,Object> queryTableProListBySystemTable(String systemCode, String token){
        List<Map<String,Object>> resultList = new ArrayList<>();
        try {
            System.out.println("按照系统抽取 每一个系统的所有列");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", "10");
            map.add("pageNum", "1");
            map.add("systemCode", systemCode);
            Map result = this.postForObject(url,token,map);
            // 当前系统共有多少条 total
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                int total = (Integer)dataMap.get("total");
                for (int i = 1; i <= total/100+1; i++) {
                    // 查询表：每100条表 查一次 表
                    map = new LinkedMultiValueMap<>();
                    map.add("pageSize", String.valueOf(100));
                    map.add("pageNum", String.valueOf(i));
                    map.add("systemCode", systemCode);
                    result = this.postForObject(url,token,map);
                    dataMap = (Map<String,Object>)result.get("data");
                    List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                    // 将查询table结果的Id拼成ids(id,id)
                    String  tableIds = ListUnit.ListToStrWithComma(list,"tableId");
                    insertTableProListByTableIds(tableIds,token);
                    resultList.add(RCheckMsg.successMsg( tableIds,token));
                }
                resultList.add(RCheckMsg.successMsg( "系统编码："+systemCode,token));
            }
            return RCheckMsg.successMsg(resultList,null);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }

    public Map<String,Object>  insertTableProListByTableIds(String tableIds, String token){
        try {
            int totalCol = 0;
            System.out.println("按照系统抽取:通过tableidssinsertTableProListByTableIds");
            String urlCol = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableProList";
            MultiValueMap<String, Object>  mapCol = new LinkedMultiValueMap<>();
            // 获取列 按照tableids 查询列 每次查询tableids的 total
            mapCol = new LinkedMultiValueMap<>();
            mapCol.add("pageSize", "10");
            mapCol.add("pageNum", "1");
            mapCol.add("tableIds", tableIds);
            Map resultColTotal = this.postForObject(urlCol,token,mapCol);
            // 判断列是否返回
            if (resultColTotal.get("success") != null && ((boolean)resultColTotal.get("success") || "true".equals(resultColTotal.get("success")))) {
                // 得到列total
                Map<String,Object> dataMap = (Map<String,Object>)resultColTotal.get("data");
                 totalCol = (Integer)dataMap.get("total");

                for (int i = 1; i <= totalCol/1000+1; i++) {
                    mapCol = new LinkedMultiValueMap<>();
                    mapCol.add("pageSize", "1000");
                    mapCol.add("pageNum", String.valueOf(i));
                    mapCol.add("tableIds", tableIds);
                    Map resultCol = this.postForObject(urlCol, token, mapCol);
                    if (resultCol.get("success") != null && ((boolean) resultCol.get("success") || "true".equals(resultCol.get("success")))) {
                        Map<String,Object> dataMapCol = (Map<String,Object>)resultCol.get("data");
                        List<Map<String,Object>> listCol = (List<Map<String, Object>>) dataMapCol.get("list");
                        if (listCol != null && listCol.size()>0) {
                            // 拆分每100条执行一次
                            if (listCol.size() > 100) {
                                List<List<Map<String, Object>>> lists = ListUnit.splistList(listCol, 100);
                                for (int j = 0; j < lists.size(); j++) {
                                    service.insertTableProList(lists.get(j));
                                }
                            } else {
                                service.insertTableProList(listCol);
                            }
                        }
                    }
                }
            }
            return RCheckMsg.successMsg( "插入成功："+totalCol,token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
    }



    /**
     * 按照系统抽取 每一个系统的所有表
     * @param systemCode
     * @param token
     * @return
     */
    public Map<String,Object>  queryTableListBySystem(String systemCode, String token){
        int total = 0 ;
        try {
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", "10");
            map.add("pageNum", "1");
            map.add("systemCode", systemCode);
//            查询10条 为了得到总条数 total
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                // 得到总条数 total
                total = (Integer)dataMap.get("total");
                // 遍历total
                int sizeNum = (total/1000+1);
                for (int i = 1; i <= sizeNum; i++) {
                    map = new LinkedMultiValueMap<>();
                    map.add("pageSize", String.valueOf(1000));
                    map.add("pageNum", String.valueOf(i));
                    map.add("systemCode", systemCode);
                    result = this.postForObject(url,token,map);
                    dataMap = (Map<String,Object>)result.get("data");
                    List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                    if (list != null && list.size()>0) {
                        if (list.size() > 100) {
                            List<List<Map<String, Object>>> lists = ListUnit.splistList(list, 100);
                            for (int j = 0; j < lists.size(); j++) {
                                service.insertTableList(lists.get(j));
                            }
                        } else {
                            service.insertTableList(list);
                        }
                    }
                }
            }
            return RCheckMsg.successMsg( "系统编码："+ systemCode+ ",共导入：" + total+ "条", token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }


    /**
     * 调用接口
     * @param url
     * @param token
     * @param map
     * @return
     */
    public Map<String,Object> postForObject(String url, String token, MultiValueMap<String, Object> map) {
        // 请求地址
//        String url = "http://localhost:8080/auth/getToken";

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("token",token);
        //提交参数设置
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("appId", "01000");
//        map.add("appKey", "cde18afb42556eeed09783b437eece17");

        // 组装请求体
        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<MultiValueMap<String, Object>>(map, headers);
        RestTemplate template =new RestTemplate();
        // 发送post请求，并输出结果
        Map<String,Object> result = template.postForObject(url, request, Map.class);
        System.out.println(result);
        return result;
    }


    /**
     * 获取token
     * @param userName
     * @param password
     * @return
     */
    public  Map<String,Object> createToken(String userName, String password) {
        try {
            if (userName == null) {
                userName = "PEN_HA_CHECK_USER";
            }
            if (password == null) {
                password = "a55da74131b9417f73dc2b232e2481ac";
            }
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/auth/login";
            String token = null;
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("userName", userName);
            map.add("password", password);
            Map result = this.postForObject(url,token,map);
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            System.out.println(e);
            return RCheckMsg.errorMsg(500,"接口返回失敗",null);
        }
    }

}
