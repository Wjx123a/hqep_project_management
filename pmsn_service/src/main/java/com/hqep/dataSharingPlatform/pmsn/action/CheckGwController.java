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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 *
 */
@RestController
@RequestMapping("/checkGw")
// 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
public class CheckGwController {

    @Autowired
    CheckGwService service;

    @GetMapping("/test")
    public  Map<String,Object> test() {
        // 请求地址
        String url = "http://localhost:8080/auth/getToken";

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //提交参数设置
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appId", "01000");
        map.add("appKey", "cde18afb42556eeed09783b437eece17");

        // 组装请求体
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<MultiValueMap<String, String>>(map, headers);
        RestTemplate template =new RestTemplate();
        // 发送post请求，并输出结果
        String result = template.postForObject(url, request, String.class);
        System.out.println(result);
        return RCheckMsg.successMsg( result,null);
    }

    /**
     * 生成token
     * @param userName
     * @param password
     * @param timestamp
     * @return
     * @throws Exception
     */

    @RequestMapping("/getToken")
    public  Map<String,Object> createToken(String userName, String password, String timestamp) throws Exception {
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

    @RequestMapping("/login")
    public  Map<String,Object> login(String userName, String password, String timestamp) throws Exception {
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


    /**
     * 查询资源目录树信息
     | 参数名称   | 说明                     | 是否必须 | 类型   | schema |
     | ---------- | ------------------------ | -------- | ------ | ------ |
     | orgCode    | 单位编码（按照统一编码） | false    | string |        |
     | systemCode | 系统编码（按照统一编码） | false    | string |        |
     * @return
     */
    @RequestMapping("/queryCatalogTree")
    public Map<String,Object> queryCatalogTree(ServletRequest request){
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
    @RequestMapping("/queryCatalogTree_q")
    public Map<String,Object> queryCatalogTreeTest(ServletRequest request){
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
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }
    /**
     * 查询表列表信息
     * @param
    | 参数名称     | 说明                     | 类型   | 是否必须 |
    | ------------ | ------------------------ | ------ | -------- |
    | pageNum      | 当前页                   | int    | true     |
    | pageSize     | 每页的数量               | int    | true     |
    | orgCode      | 单位编码（按照统一编码） | string | false    |
    | systemCode   | 系统编码                 | string | false    |
    | dbName       | 数据源名称               | string | false    |
    | tableName    | 表英文名                 | string | false    |
    | oneCatalog   | 一级目录名称             | string | false    |
    | twoCatalog   | 二级目录名称             | string | false    |
    | threeCatalog | 三级目录名称             | string | false    |
     * @return
     */
    @RequestMapping("/queryTableList")
    public Map<String,Object>  queryTableList(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String pageSize = (req.getParameter("pageSize"));
            String pageNum =  (req.getParameter("pageNum"));
            String orgCode = req.getParameter("orgCode");
            String systemCode = req.getParameter("systemCode");
            String dbName = req.getParameter("dbName");
            String tableName = req.getParameter("tableName");
            String oneCatalog = req.getParameter("oneCatalog");
            String twoCatalog = req.getParameter("twoCatalog");
            String threeCatalog = req.getParameter("threeCatalog");
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", pageSize);
            map.add("pageNum", pageNum);
            map.add("dbName", dbName);
            map.add("tableName", tableName);
            map.add("oneCatalog", oneCatalog);
            map.add("twoCatalog", twoCatalog);
            map.add("threeCatalog", threeCatalog);
            map.add("orgCode", orgCode);
            map.add("systemCode", systemCode);
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
//                List<Map<String,Object>> list = (List<Map<String,Object>>)result.get("data");
                if (list != null && list.size()>0) {
                    List<List<Map<String, Object>>> lists = ListUnit.splistList(list, 100);
                    for (int i = 0; i < lists.size(); i++) {
                        service.insertTableList(lists.get(i));
                    }
                }
            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }



    @RequestMapping("/queryTableList_q")
    public Map<String,Object>  queryTableList_q(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String pageSize = (req.getParameter("pageSize"));
            String pageNum =  (req.getParameter("pageNum"));
            String orgCode = req.getParameter("orgCode");
            String systemCode = req.getParameter("systemCode");
            String dbName = req.getParameter("dbName");
            String tableName = req.getParameter("tableName");
            String oneCatalog = req.getParameter("oneCatalog");
            String twoCatalog = req.getParameter("twoCatalog");
            String threeCatalog = req.getParameter("threeCatalog");
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", pageSize);
            map.add("pageNum", pageNum);
            map.add("dbName", dbName);
            map.add("tableName", tableName);
            map.add("oneCatalog", oneCatalog);
            map.add("twoCatalog", twoCatalog);
            map.add("threeCatalog", threeCatalog);
            map.add("orgCode", orgCode);
            map.add("systemCode", systemCode);
            Map result = this.postForObject(url,token,map);
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }

    @RequestMapping("/queryTableListByOne")
    public Map<String,Object>  queryTableListByOne(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String pageSize = (req.getParameter("pageSize"));
            String pageNum =  (req.getParameter("pageNum"));
            String orgCode = req.getParameter("orgCode");
            String systemCode = req.getParameter("systemCode");
            String dbName = req.getParameter("dbName");
            String tableName = req.getParameter("tableName");
            String oneCatalog = req.getParameter("oneCatalog");
            String twoCatalog = req.getParameter("twoCatalog");
            String threeCatalog = req.getParameter("threeCatalog");
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", pageSize);
            map.add("pageNum", pageNum);
            map.add("dbName", dbName);
            map.add("tableName", tableName);
            map.add("oneCatalog", oneCatalog);
            map.add("twoCatalog", twoCatalog);
            map.add("threeCatalog", threeCatalog);
            map.add("orgCode", orgCode);
            map.add("systemCode", systemCode);
            Map result = this.postForObject(url,token,map);
//            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
//                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
//                List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
////                List<Map<String,Object>> list = (List<Map<String,Object>>)result.get("data");
//                if (list != null && list.size()>0) {
//                    List<List<Map<String, Object>>> lists = ListUnit.splistList(list, 100);
//                    for (int i = 0; i < lists.size(); i++) {
//                        service.insertTableList(lists.get(i));
//                    }
//                }
//            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }


    @RequestMapping("/queryTableListTotal")
    public Map<String,Object>  queryTableListTotal(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String orgCode = req.getParameter("orgCode");
            String systemCode = req.getParameter("systemCode");
            String dbName = req.getParameter("dbName");
            String tableName = req.getParameter("tableName");
            String oneCatalog = req.getParameter("oneCatalog");
            String twoCatalog = req.getParameter("twoCatalog");
            String threeCatalog = req.getParameter("threeCatalog");
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", "10");
            map.add("pageNum", "1");
            map.add("systemCode", systemCode);
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                int total = (Integer)dataMap.get("total");
                for (int i = 1; i <= total/1000+1; i++) {
                    map = new LinkedMultiValueMap<>();
                    map.add("pageSize", String.valueOf(1000));
                    map.add("pageNum", String.valueOf(i));
                    map.add("orgCode", orgCode);
                    map.add("systemCode", systemCode);
                    map.add("dbName", dbName);
                    map.add("tableName", tableName);
                    map.add("oneCatalog", oneCatalog);
                    map.add("twoCatalog", twoCatalog);
                    map.add("threeCatalog", threeCatalog);
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
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }


    @RequestMapping("/queryTableListTotalByNum")
    public Map<String,Object>  queryTableListTotalByNum(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            int total =  Integer.parseInt(req.getParameter("total"));
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            for (int i = 1; i <= total/1000+1; i++) {
                map = new LinkedMultiValueMap<>();
                map.add("pageSize", String.valueOf(1000));
                map.add("pageNum", String.valueOf(i));
                Map result = this.postForObject(url,token,map);
                if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                    Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                    List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                    if (list != null && list.size()>0) {
                        service.insertTableList(list);
                    }
                }
            }
            return RCheckMsg.successMsg( "共导入："+total,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }



    @RequestMapping("/queryTableListTotalByDy")
    public Map<String,Object>  queryTableListTotalByDy(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("token");
            int total =  Integer.parseInt(req.getParameter("total"));
            for (int i = 1; i <= total/1000+1; i++) {
                String pageSize = String.valueOf(1000);
                String pageNum = String.valueOf(i);
                Map result = this.insertTableList(pageSize,pageNum,request);
                if (!(boolean)result.get("success")) {
                    return result;
                }
            }
            return RCheckMsg.successMsg( "共导入："+total,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }


    @RequestMapping("/queryTableListTotalByPageNumForEndNum")
    public Map<String,Object>  queryTableListTotalByPageNumForEndNum(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("token");
            int pageSize = Integer.parseInt(req.getParameter("pageSize"));
            int pageNum =  Integer.parseInt(req.getParameter("pageNum"));
            String temp =  req.getParameter("endNum");
            if (temp == null || "".equals(temp)) {
                temp = "10";
            }
            int endNum =  Integer.parseInt(temp);
            for (int i = pageNum; i <= endNum; i++) {
                Map result = this.insertTableList(String.valueOf(pageSize),String.valueOf(i),request);
                if (!(boolean)result.get("success")) {
                    return result;
                }
            }
            return RCheckMsg.successMsg( "共导到："+pageNum*1000 + "，页码："+pageNum,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }

    @RequestMapping("/queryTableListTotalByPageNum")
    public Map<String,Object>  queryTableListTotalByPageNum(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("token");
            int pageSize = Integer.parseInt(req.getParameter("pageSize"));
            int pageNum =  Integer.parseInt(req.getParameter("pageNum"));
            Map result = this.insertTableList(String.valueOf(pageSize),String.valueOf(pageNum),request);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableList(String.valueOf(pageSize),String.valueOf(pageNum),request);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableList(String.valueOf(pageSize),String.valueOf(pageNum),request);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableList(String.valueOf(pageSize),String.valueOf(pageNum),request);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableList(String.valueOf(pageSize),String.valueOf(pageNum),request);
            if (!(boolean)result.get("success")) {
                return result;
            }
            return RCheckMsg.successMsg( "共导到："+pageNum*1000 + "，页码："+pageNum,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }


    public Map<String,Object>  insertTableList(String pageSize,String pageNum,ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", pageSize);
            map.add("pageNum",  pageNum);
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                if (list != null && list.size()>0) {
//                    service.insertTableList(list);
                    List<List<Map<String, Object>>> lists = ListUnit.splistList(list, 100);
                    for (int i = 0; i < lists.size(); i++) {
                        service.insertTableList(lists.get(i));
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
     * 查询表属性信息
     *
     | 参数名称 | 说明       | 类型   | 是否必须 |
     | -------- | ---------- | ------ | -------- |
     | pageNum  | 当前页     | int    | true     |
     | pageSize | 每页的数量 | int    | true     |
     | tableIds  |表ID集合英文逗号分隔，最大100个ID       | String | true     |

     * @return
     */
    @RequestMapping("/queryTableProList")
    public Map<String,Object> queryTableProList(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String pageSize = (req.getParameter("pageSize"));
            String pageNum =  (req.getParameter("pageNum"));
            String tableIds = req.getParameter("tableIds");
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryCatalogTree";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", pageSize);
            map.add("pageNum", pageNum);
            map.add("tableIds", tableIds);
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
//                service.insertTableProList((List<Map<String,Object>>)result.get("data"));
//                List<Map<String,Object>> list = (List<Map<String,Object>>)result.get("data");
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("data");
                if (list != null && list.size()>0) {
                    List<List<Map<String, Object>>> lists = ListUnit.splistList(list, 100);
                    for (int i = 0; i < lists.size(); i++) {
                        service.insertTableProList(lists.get(i));
                    }
                }
            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }

    @RequestMapping("/queryTableProList_q")
    public Map<String,Object> queryTableProList_q(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String pageSize = (req.getParameter("pageSize"));
            String pageNum =  (req.getParameter("pageNum"));
            String tableIds = req.getParameter("tableIds");
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryCatalogTree";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", pageSize);
            map.add("pageNum", pageNum);
            map.add("tableIds", tableIds);
            Map result = this.postForObject(url,token,map);
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }


    @RequestMapping("/queryTableProListByTable")
    public Map<String,Object> queryTableProListByTable(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            String token = req.getHeader("token");
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", "10");
            map.add("pageNum", "1");
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                int total = (Integer)dataMap.get("total");
                String urlCol = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableProList";
                MultiValueMap<String, Object> mapCol = new LinkedMultiValueMap<>();
                for (int i = 1; i <= total/100+1; i++) {
                    // 每100条表 查一次
                    map = new LinkedMultiValueMap<>();
                    map.add("pageSize", String.valueOf(100));
                    map.add("pageNum", String.valueOf(i));
                    result = this.postForObject(url,token,map);
                    dataMap = (Map<String,Object>)result.get("data");
                    List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                    // 将查询结果Id拼成 id,id
                    String  tableIds = ListUnit.ListToStrWithComma(list,"tableId");
                    // 获取列 按照tableids 查询列
                    mapCol = new LinkedMultiValueMap<>();
                    mapCol.add("pageSize", "1000");
                    mapCol.add("pageNum", "1");
                    mapCol.add("tableIds", tableIds);
                    Map resultCol = this.postForObject(urlCol,token,mapCol);
                    if (resultCol.get("success") != null && ((boolean)resultCol.get("success") || "true".equals(resultCol.get("success")))) {
                        Map<String,Object> dataMapCol = (Map<String,Object>)resultCol.get("data");
                        List<Map<String,Object>> listCol = (List<Map<String, Object>>) dataMapCol.get("data");
                        if (listCol != null && listCol.size()>0) {
                            List<List<Map<String, Object>>> lists = ListUnit.splistList(listCol, 100);
                            for (int j = 0; j < lists.size(); j++) {
                                service.insertTableProList(lists.get(i));
                            }
                        }
                    }
                }
            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }


    @RequestMapping("/queryTableProListByTableOne")
    public Map<String,Object> queryTableProListByTableOne(ServletRequest request){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            String token = req.getHeader("token");
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", "10");
            map.add("pageNum", "1");
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                int total = (Integer)dataMap.get("total");
                String urlCol = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableProList";
                MultiValueMap<String, Object> mapCol = new LinkedMultiValueMap<>();
                for (int i = 1; i <= 1; i++) {
                    // 每100条表 查一次
                    map = new LinkedMultiValueMap<>();
                    map.add("pageSize", String.valueOf(100));
                    map.add("pageNum", String.valueOf(i));
                    result = this.postForObject(url,token,map);
                    dataMap = (Map<String,Object>)result.get("data");
                    List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                    // 将查询结果Id拼成 id,id
                    String  tableIds = ListUnit.ListToStrWithComma(list,"tableId");
                    // 获取列 按照tableids 查询列
                    mapCol = new LinkedMultiValueMap<>();
                    mapCol.add("pageSize", "1000");
                    mapCol.add("pageNum", "1");
                    mapCol.add("tableIds", tableIds);
                    Map resultCol = this.postForObject(urlCol,token,mapCol);
                    if (resultCol.get("success") != null && ((boolean)resultCol.get("success") || "true".equals(resultCol.get("success")))) {
                        Map<String,Object> dataMapCol = (Map<String,Object>)resultCol.get("data");
                        List<Map<String,Object>> listCol = (List<Map<String, Object>>) dataMapCol.get("data");
                        if (listCol != null && listCol.size()>0) {
                            List<List<Map<String, Object>>> lists = ListUnit.splistList(listCol, 100);
                            for (int j = 0; j < lists.size(); j++) {
                                service.insertTableProList(lists.get(i));
                            }
                        }
                    }
                }
            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }



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



    @RequestMapping("/queryTableListTotalByPageNumTest")
    public Map<String,Object>  queryTableListTotalByPageNumTest(ServletRequest request){
        try {
            RestTemplate templateTest =new RestTemplate();
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("token");
            int pageSize = Integer.parseInt(req.getParameter("pageSize"));
            int pageNum =  Integer.parseInt(req.getParameter("pageNum"));
            Map result = this.insertTableListTest(String.valueOf(pageSize),String.valueOf(pageNum),request,templateTest);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableListTest(String.valueOf(pageSize),String.valueOf(pageNum),request,templateTest);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableListTest(String.valueOf(pageSize),String.valueOf(pageNum),request,templateTest);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableListTest(String.valueOf(pageSize),String.valueOf(pageNum),request,templateTest);
            if (!(boolean)result.get("success")) {
                return result;
            }
            pageNum = pageNum+1;
            result = this.insertTableListTest(String.valueOf(pageSize),String.valueOf(pageNum),request,templateTest);
            if (!(boolean)result.get("success")) {
                return result;
            }
            return RCheckMsg.successMsg( "共导到："+pageNum*1000 + "，页码："+pageNum,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }



    public Map<String,Object>  insertTableListTest(String pageSize,String pageNum,ServletRequest request, RestTemplate templateTest){
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("token");
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", pageSize);
            map.add("pageNum",  pageNum);
            Map result = this.postForObjectTest(url,token,map,templateTest);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
                if (list != null && list.size()>0) {
                    service.insertTableList(list);
                }
            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }




    public Map<String,Object> postForObjectTest(String url, String token, MultiValueMap<String, Object> map, RestTemplate templateTest) {
        // 请求地址
//        String url = "http://localhost:8080/auth/getToken";
        // templateTest =new RestTemplate();
        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("token",token);
        // 组装请求体
        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<MultiValueMap<String, Object>>(map, headers);
        // 发送post请求，并输出结果
        Map<String,Object> result = templateTest.postForObject(url, request, Map.class);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/queryTableList_1")
    public Map<String,Object>  queryTableList_1(@RequestBody PageData pd,HttpServletRequest req){
        try {
            String token = (req.getParameter("token"));
            String pageNum =  (req.getParameter("pageNum"));
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/queryTableList";
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("pageSize", "1000");
            map.add("pageNum", pageNum);
            Map result = this.postForObject(url,token,map);
            if (result.get("success") != null && ((boolean)result.get("success") || "true".equals(result.get("success")))) {
                Map<String,Object> dataMap = (Map<String,Object>)result.get("data");
                List<Map<String,Object>> list = (List<Map<String, Object>>) dataMap.get("list");
//                List<Map<String,Object>> list = (List<Map<String,Object>>)result.get("data");
                if (list != null && list.size()>0) {
                    List<List<Map<String, Object>>> lists = ListUnit.splistList(list, 100);
                    for (int i = 0; i < lists.size(); i++) {
                        service.insertTableList(lists.get(i));
                    }
                }
            }
            return RCheckMsg.successMsg( result,token);
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }

}
