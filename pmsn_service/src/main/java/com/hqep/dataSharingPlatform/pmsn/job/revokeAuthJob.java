package com.hqep.dataSharingPlatform.pmsn.job;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.action.GetDataWorksIntoStockAction;
import com.hqep.dataSharingPlatform.pmsn.service.ProcessApplyService;
import com.hqep.dataSharingPlatform.pmsn.service.QueryGdService;
import com.hqep.dataSharingPlatform.pmsn.service.RevokeAuthJobService;
import com.hqep.dataSharingPlatform.sjkflc.service.ReportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hqep_project_management
 * @ClassName revokeAuthJob
 * @author: sssJL
 * @create: 2023-03-22 10:50
 * @Version V1.0
 * @description:
 **/
@Component
public class revokeAuthJob {

    @Autowired
    RevokeAuthJobService service;

    @Autowired
    private ProcessApplyService processApplyService;
    @Autowired
    private QueryGdService queryGdService;
    @Autowired
    private ReportCenterService reportCenterService;
    /**
     * 定时设置使用时间收回权限
     * 定时：每日执行
     */
    @Scheduled(cron = "0 30 20 * * ?") // 间隔30秒执行 之后改为 每天执行
    //@Scheduled(cron = "0/30 * * * * ? ") // 间隔30秒执行 之后改为 每天执行
    public void pullUsersByDay(){
        String printTime = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("定时设置使用时间收回权限 定时任务 开始时间:" + printTime);
        //Map params = new LinkedHashMap();
        //service.revokeAuthJob();
        //Map map = pullNewDataService.InsertOrUpdateUsers(params);
        GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();
        Map sqMap = null;
        List<PageData> revokeAuthList = service.queryRevokeAuthList(new PageData());
        // 遍历需要取消授权的数据表 i
        for (int i = 0; i < revokeAuthList.size(); i++) {
            PageData onePd = revokeAuthList.get(i);
            String[] sqyhArr = onePd.getString("sqperson").split(",");
            // 遍历需要取消授权的中台用户 j
            for (int j = 0; j < sqyhArr.length; j++) {
                // 取消授权的表名
                String tableName = onePd.getString("sqbsqbm");
                // 表名去除"odps."
                if (tableName.toLowerCase().startsWith("odps.")) {
                    tableName = tableName.toLowerCase().substring(tableName.toLowerCase().indexOf(".") + 1);
                }
                // 截取项目名称
                String projectName = tableName.toLowerCase().substring(0, tableName.toLowerCase().indexOf("."));
                System.out.println("取消授权用户-----" + j + "-----" + sqyhArr[j]);
                Map params_revokeMap = new LinkedHashMap<>();
                params_revokeMap.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                params_revokeMap.put("projectName", projectName); //项目名称
                params_revokeMap.put("tableName", tableName); //具体表名
                params_revokeMap.put("toUserId", sqyhArr[j]); //ram账号

                // 正式取消授权方法
                sqMap = sqAction.revokeSelectDesc(params_revokeMap);
                PageData param = new PageData();
                param.put("grant_type", "数据中台");
                param.put("tablename", tableName);
                param.put("username", "sys_name");
                param.put("ztzh", sqyhArr[j]);
                if (sqMap.get("error") == null) { // 判断中台是否授权成功
                    param.put("opstate", "revoke_success");
                    // 插入取消/授权的日志表
                    reportCenterService.inertGrantForLogs(param);
                    // 取消授权成功执行以下
                    param = new PageData();
                    param.put("sqrid", onePd.getString("sqrid"));
                    param.put("sqbsqbm", tableName);
                    // 1.购物车的申请状态改为 -2
                    processApplyService.updateshoppingcatForRevoke(param);

                    // 2.更新工单状态
                    param = new PageData();
                    param.put("lcspid", onePd.getString("lcspid"));
                    param.put("revokeNameCode", "sys_code");
                    param.put("revokeName", "sys_name");
                    //param.put("revokeTime",revokeTime); // DATE_FORMAT(now(), '%Y-%m-%d %H:%i:%s')
                    queryGdService.updatePullWorkOrderForRevoke(param);
                }
            }
        }
        printTime = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("定时设置使用时间收回权限 定时任务 结束时间:" + printTime);
    }
}
