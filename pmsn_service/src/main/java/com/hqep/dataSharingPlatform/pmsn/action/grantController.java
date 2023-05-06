package com.hqep.dataSharingPlatform.pmsn.action;

import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.action.GetDataWorksIntoStockAction;
import com.hqep.dataSharingPlatform.pmsn.service.ProcessApplyService;
import com.hqep.dataSharingPlatform.pmsn.service.QueryGdService;
import com.hqep.dataSharingPlatform.sjkflc.service.ReportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: hqep_project_management
 * @ClassName grant
 * @author: sssJL
 * @create: 2023-02-25 11:07
 * @Version V1.0
 * @description:
 **/
@RestController
@RequestMapping("/grant")
public class grantController {

    @Autowired
    private ReportCenterService reportCenterService;

    @Autowired
    private ProcessApplyService processApplyService;
    @Autowired
    private QueryGdService queryGdService;

    @RequestMapping(value = "/revokeSelectDesc", method = RequestMethod.POST)
    @ResponseBody
    public ResultBodyBean revokeSelectDesc(@RequestBody PageData pd) {
        Map sqMap = null;
        try {
            GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();

            //String fromUserId = pd.getString("fromUserId"); // root
            //String projectName = pd.getString("projectName"); // tableName 截取
            String tableName = pd.getString("sqbsqbm");
            String sqperson = pd.getString("sqperson");
            String username = pd.getString("sqrmc");
            String loginNum = pd.getString("loginNum");
            String sqrid = pd.getString("sqrid");
            String lcspid = pd.getString("lcspid");


            if (tableName.toLowerCase().startsWith("odps.")) {
                tableName = tableName.toLowerCase().substring(tableName.toLowerCase().indexOf(".")+1);
            }
            String projectName = tableName.toLowerCase().substring(0,tableName.toLowerCase().indexOf("."));


            String[] sqyhArr = sqperson.split(",");
            // 遍历需要取消授权的中台用户 j
            for (int j = 0; j < sqyhArr.length; j++) {
                System.out.println("取消授权用户------" + j + "-----" + sqyhArr[j]);
                Map params_grantList = new LinkedHashMap<>();
                params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                params_grantList.put("projectName", projectName); //项目名称
                params_grantList.put("tableName", tableName); //具体表名
                params_grantList.put("toUserId", sqyhArr[j]); //ram账号
                //params_grantList.put("expDate",  System.currentTimeMillis()+10*365*24*60*60*1000);
                // 正式取消授权方法
                sqMap = sqAction.revokeSelectDesc(params_grantList);
                PageData param = new PageData();
                param.put("grant_type","数据中台");
                param.put("tablename",tableName);
                param.put("username",loginNum);
                param.put("ztzh",sqyhArr[j]);
                if(sqMap.get("error") == null) { // 判断中台是否授权成功
                    param.put("opstate","revoke_success");
                    // 插入取消/授权的日志表
                    reportCenterService.inertGrantForLogs(param);
                    // 取消授权成功执行以下
                    param = new PageData();
                    param.put("sqrid",sqrid);
                    param.put("sqbsqbm",tableName);
                    // 1.购物车的申请状态改为 -2
                    processApplyService.updateshoppingcatForRevoke(param);

                    // 2.更新工单状态
                    param = new PageData();
                    param.put("lcspid",lcspid);
                    param.put("revokeNameCode",loginNum);
                    param.put("revokeName",username);
                    //param.put("revokeTime",revokeTime); // DATE_FORMAT(now(), '%Y-%m-%d %H:%i:%s')
                    queryGdService.updatePullWorkOrderForRevoke(param);
                } else {
                    param.put("opstate","revoke_error");
                    // 插入取消/授权的日志表
                    reportCenterService.inertGrantForLogs(param);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResultBodyBean(sqMap,null);
    }

}
