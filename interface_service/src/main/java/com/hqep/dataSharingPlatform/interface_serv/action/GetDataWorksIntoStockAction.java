package com.hqep.dataSharingPlatform.interface_serv.action;

import com.google.gson.Gson;
import com.hqep.dataSharingPlatform.common.utils.HttpResult;
import com.hqep.dataSharingPlatform.common.utils.JsonMsg;
import com.hqep.dataSharingPlatform.interface_serv.service.PullNewDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 通过http方式获取datawork上数据目录、字段字典、调用授权方法
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/18$ 9:42$
 * @Version: 1.0
 */
@RequestMapping("/UpdateDataWorkData")
@RestController
public class GetDataWorksIntoStockAction {

    @Autowired
    private PullNewDataService pullNewDataService;

    /**
     * 是否测试环境授权
     * true 测试环境
     * false 生产环境
     */
    private static final boolean TEST_FLAG = false;

    /**
     * 获取用户列表拉取最新的用户到mysql中
     *
     * @param params 参数暂时未使用
     * @return
     */
    @RequestMapping("/getAllUsers")
    @ResponseBody
    public Map getAllUsers(Map params) {
        return pullNewDataService.InsertOrUpdateUsers(params);
    }


    /**
     * 获取字段字典
     * 拉取最新的地段字典到mysql中
     *
     * @param params 参数暂时未使用
     * @return
     */
    @RequestMapping("getColumnsDict")
    @ResponseBody
    public Map getColumnsDict(Map params) {
        params = new LinkedHashMap();
        params.put("pageIndex", 1);
        return pullNewDataService.InsertOrUpdateColumns(params);
    }

    /**
     * 授权方法
     *
     * @return
     */
    @RequestMapping("grantSelectDescs")
    @ResponseBody
//    @RequestLog("授权方法")
    public Map grantSelectDesc(Map params_grantList) {
        try {
            if (TEST_FLAG) {
                return JsonMsg.booleanToMap(true);
            }
            if (params_grantList != null) {
                String projectName = (String) params_grantList.get("projectName");
                if (projectName != null && !projectName.startsWith("sjzt_hlj")) {
                    // 如果不是sjzt_hlj开头，那么是分析层表，项目名称设置为固定的sjzt_hlj_ads_pro
                    projectName ="sjzt_hlj_ads_pro";
                    params_grantList.put("projectName",projectName);
                }
            }
//            params_grantList = new LinkedHashMap<>();
//            params_grantList.put("fromUserId", "root");//本地账，使用改账号对toUserId进行授权操作
//            params_grantList.put("projectName", "guowang_train1");//项目名称
//            params_grantList.put("tableName", "lesson12");//具体表名
//            params_grantList.put("toUserId", "gts_demo");//ram账号
            System.out.println("进入调用阿里授权接口方法============：");
            System.out.println("进入调用阿里授权接口方法：参数信息============："+ params_grantList);
            String grantResult = HttpResult.postbyJson(HttpResult.URL_GRANT_SELECT_DESCS, params_grantList);
//            String grantResult = HttpResult.MSG_3;
//            String grantResult = HttpResult.MSG_ERROR;
            Gson gson = new Gson();
            Map userListMap = gson.fromJson(grantResult, Map.class);
            System.out.println("进入调用阿里授权接口方法:返回信息============：" + userListMap);
            if (userListMap != null && userListMap.containsKey("message") && "success".equals(userListMap.get("message"))) {
                System.out.println("getColumnsDict Success");
                System.out.println(userListMap.get("data"));
//                pullNewDataService.grantResultLogs(params_grantList);
                return JsonMsg.booleanToMap(true);

            } else {
                return JsonMsg.creatErrorMap("500", "授权失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("退出调用阿里授权接口方法============：");
        return JsonMsg.creatErrorMap("500", "授权失败");
    }

    /**
     * 取消授权方法
     *
     * @return
     */
    @RequestMapping("revokeSelectDesc")
    @ResponseBody
//    @RequestLog("取消授权方法")
    public Map revokeSelectDesc(Map params_grantList) {
        try {
            if (TEST_FLAG) {
                return JsonMsg.booleanToMap(true);
            }
            if (params_grantList != null) {
                String projectName = (String) params_grantList.get("projectName");
                if (projectName != null && !projectName.startsWith("sjzt_hlj")) {
                    // 如果不是sjzt_hlj开头，那么是分析层表，项目名称设置为固定的sjzt_hlj_ads_pro
                    projectName ="sjzt_hlj_ads_pro";
                    params_grantList.put("projectName",projectName);
                }
            }
//            params_grantList = new LinkedHashMap<>();
//            params_grantList.put("fromUserId", "root");//本地账，使用改账号对toUserId进行授权操作
//            params_grantList.put("projectName", "guowang_train1");//项目名称
//            params_grantList.put("tableName", "lesson12");//具体表名
//            params_grantList.put("toUserId", "gts_demo");//ram账号
            System.out.println("进入调用阿里取消授权接口方法============：");
            System.out.println("进入调用阿里取消授权接口方法：参数信息============："+ params_grantList);
            String grantResult = HttpResult.postbyJson(HttpResult.URL_REVOKE_SELECT_DESCS, params_grantList);
//            String grantResult = HttpResult.MSG_3;
//            String grantResult = HttpResult.MSG_ERROR;
            Gson gson = new Gson();
            Map userListMap = gson.fromJson(grantResult, Map.class);
            System.out.println("进入调用阿里取消授权接口方法:返回信息============：" + userListMap);
            if (userListMap != null && userListMap.containsKey("message") && "success".equals(userListMap.get("message"))) {
                System.out.println("getColumnsDict Success");
                System.out.println(userListMap.get("data"));
//                pullNewDataService.grantResultLogs(params_grantList);
                return JsonMsg.booleanToMap(true);

            } else {
                return JsonMsg.creatErrorMap("500", "取消授权失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("退出调用阿里取消授权接口方法============：");
        return JsonMsg.creatErrorMap("500", "取消授权失败");
    }

    /**
     * 授权方法_测试
     *
     * @return
     */
    @RequestMapping("grantSelectDescsTest")
    @ResponseBody
//    @RequestLog("授权方法_测试")
    public Map grantSelectDescTest(Map params_grantList) {
        try {
            System.out.println("进入测试授权!");
            System.out.println("fromUserId=====" + params_grantList.get("fromUserId"));
            System.out.println("projectName=====" + params_grantList.get("projectName"));
            System.out.println("tableName=====" + params_grantList.get("tableName"));
            System.out.println("toUserId=====" + params_grantList.get("toUserId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonMsg.creatErrorMap("500", "授权失败");
    }


}
