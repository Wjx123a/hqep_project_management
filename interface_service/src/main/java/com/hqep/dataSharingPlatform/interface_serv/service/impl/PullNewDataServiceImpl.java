package com.hqep.dataSharingPlatform.interface_serv.service.impl;

import com.google.gson.Gson;
import com.hqep.dataSharingPlatform.interface_serv.dao.PullNewDataDao;
import com.hqep.dataSharingPlatform.interface_serv.service.PullNewDataService;
import com.hqep.dataSharingPlatform.common.utils.HttpResult;
import com.hqep.dataSharingPlatform.common.utils.JsonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/18$ 10:28$
 * @Version: 1.0
 */
@Service
public class PullNewDataServiceImpl implements PullNewDataService {
    @Autowired
    private PullNewDataDao productDao;

    @Override
    public Map InsertOrUpdateUsers(Map params) {
        try {
            String userList = HttpResult.postbyJson(HttpResult.URL_GET_ALL_USERS, new HashMap());
//            String userList = HttpResult.MSG_1;
            Gson gson = new Gson();
            Map userListMap = gson.fromJson(userList, Map.class);
            if (userListMap != null && userListMap.containsKey("message") && "success".equals(userListMap.get("message"))) {
                System.out.println("getColumnsDict Success");
                System.out.println(userListMap.get("data"));
                List<Map> result = (List<Map>) userListMap.get("data");
                int i = productDao.replaceMoreUsers(result);

                return i >= 0 ? JsonMsg.creatSuccessMapForOpertion("更新进入" + result.size() + "条数据") : JsonMsg.creatErrorMap("500", "更新了0条数据 或 更新失败！");
            } else {
                return JsonMsg.creatErrorMap("500", "请求http接口获取用户列表出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonMsg.creatErrorMap("500", "请求http接口获取用户列表出错！");
        }
    }

    @Override
    public Map InsertOrUpdateColumns(Map params) {

        Map post_params = new LinkedHashMap();
//        post_params.put("tableName", "table_001");
//        post_params.put("tableCnName", "表_001");
//        post_params.put("tableType", "贴源层");
//        post_params.put("columnName", "column_01");
//        post_params.put("columnCnName", "列_01");
//        post_params.put("columnType", "varchar");
//        post_params.put("columnLength", 1000);
//        post_params.put("columnNullable", true);
        post_params.put("pageIndex", params.get("pageIndex"));
        if(params.containsKey("pageSize")){
            post_params.put("pageSize", params.get("pageSize"));
        }else{
            post_params.put("pageSize", 6000);
        }
        Map result = this.replaceMoreColumns(post_params);
        if ("error".equals(result.get("state"))) {
            return result;
        } else if ("success".equals(result.get("state")) && (result.get("message") instanceof Integer)) {
            post_params.put("pageIndex", (int) params.get("pageIndex") - 0 + 1);
            // TODO 开始递归，递归条件  获取的dataList数据为0 则不再进行
            return this.InsertOrUpdateColumns(post_params);
        } else {
            return result;
        }
    }

    public Map replaceMoreColumns(Map post_params) {
        try {
            String columnsList = HttpResult.postbyJson(HttpResult.URL_GET_COLUMNS_DICT,post_params);
//            String columnsList = HttpResult.MSG_2;
            Gson gson = new Gson();
            Map columnsListMap = gson.fromJson(columnsList, Map.class);
            if (columnsListMap != null && columnsListMap.containsKey("message") && "success".equals(columnsListMap.get("message"))) {
                System.out.println("getColumnsDict Success");
                System.out.println(columnsListMap.get("data"));
                Map pageInfo = (Map) columnsListMap.get("data");
//                int pageSize = (int) pageInfo.get("pageSize");
//                int pageIndex = (int) pageInfo.get("pageIndex");
//                int total = (int) pageInfo.get("total");
//                int totalPages = (int) ;
                List<Map> dataList = (List<Map>) pageInfo.get("dataList");

                try{
                      productDao.replaceMoreColumns(dataList);
                }catch (Exception e){
                    System.err.println("更新数据出错！！！ 跳过了这个错误===》》params：：："+post_params);
                    System.err.println("更新数据出错！！！ 跳过了这个错误===》》params：：："+post_params);
                    System.err.println("更新数据出错！！！ 跳过了这个错误===》》params：：："+post_params);
                }

//                if (5 == (int) post_params.get("pageIndex")) {
//                    dataList = new ArrayList();
//                }
                return JsonMsg.creatSuccessMapForOpertion(dataList == null || dataList.size() == 0 ? "处理字段字典数据完毕,共" + pageInfo.get("totalPages") + "条数据" : dataList.size());
            } else {
                return JsonMsg.creatErrorMap("500", "获取字段字典出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonMsg.creatErrorMap("500", "获取字段字典出错！");
        }
    }
}
