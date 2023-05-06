package com.hqep.dataSharingPlatform.pmsn.action;

import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.action.GetDataWorksIntoStockAction;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.interface_serv.service.SysLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping("/ultra")
@RestController
@Api(description = "中台授权")
public class UltraAction {

    @Resource
    protected HttpServletResponse response;
    @Autowired
    private SysLogsService sysLogsService;


    @ApiOperation(value = "中台授权", notes = "中台授权")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @RequestMapping(value = "/authorization_one", method = RequestMethod.POST)
    @ResponseBody
    @RequestLog("中台授权")
    public ResultBodyBean getStatistics_one(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
//                中台总部接的表名前缀：odps.sjzt_hlj_ods_pro.ods
//                中台总部之前接的表名：odps.sjzt_hlj_ods_pro.ods_cms_epm_hlj_a_pay_flow
//                中台总部以后接的表名：odps.sjzt_hlj_ods_pro.ods_cms_epmljyx_epm_hlj_a_pay_flow  // （epmljyx：实例-cms）
//                中台地市之前接的表名：odps.sjzt_hlj_ds_pro.ads_dssjyyzc
//                中台地市之前接的表名：odps.sjzt_hlj_ds_pro.ads_dssjyyzc_cms_epm_hlj_a_pay_flow_jms
//                中台地市以后接的表名：odps.sjzt_hlj_ds_pro.ads_dssjyyzc_cms_epmljyx_epm_hlj_a_pay_flow_jms //（epmljyx：实例-cms）
            if (!pd.isEmpty()) {
                if ("sgcc1234".equals(pd.getString("pwd").toLowerCase())) {
                    System.out.println("对确定授权的表，直接为用户授权!!!");

                    GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();
                    String sqbsqbm = (String) pd.get("ztbm");
                    String zhxx = (String) pd.get("zhxx");
                    if (pd.get("zhxx") == null || "".equals(pd.get("zhxx"))) {
                        errorReasonBean.setCode("428");
                        errorReasonBean.setText("授权人不能为空！");
                        resultBodyBean.setError(errorReasonBean);
                        status = HttpStatus.PRECONDITION_REQUIRED.value();
                        response.setStatus(status);
                        return resultBodyBean;
                    }
                    Map params_grantList = new LinkedHashMap<>();
                    // 如果是总部表授权
                    if (sqbsqbm.toLowerCase().startsWith("odps.")) {
                        sqbsqbm = sqbsqbm.toLowerCase().substring(sqbsqbm.toLowerCase().indexOf(".")+1);
                    }
                    String projectName = sqbsqbm.toLowerCase().substring(0,sqbsqbm.toLowerCase().indexOf("."));

                    long expDate =  1000L * 60 * 60 * 24 * 180;
                    String useTime = pd.getString("useTime");
                    if (useTime !=null && !"".equals(useTime)) {
                        expDate = stepMonth(new Date(),Integer.valueOf(useTime));
                    }

                    if (sqbsqbm !=null && sqbsqbm.toLowerCase().indexOf("sjzt_hlj_ods_pro.ods") >=0) {
                        System.out.println("授权表名------ 1 -----" + sqbsqbm);
                        System.out.println("授权用户------ 1 -----" + zhxx);
                        params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                        params_grantList.put("projectName", projectName); //项目名称 guowang_train1被邵文强改成了sjzt_hlj_ods_pro（王德志让改的）
                        params_grantList.put("tableName", sqbsqbm); //具体表名
                        params_grantList.put("toUserId", zhxx); //ram账号
                        params_grantList.put("expDate",  expDate);
                        Map sqMap = sqAction.grantSelectDesc(params_grantList);
                        System.out.println(sqMap);
                        if ("success".equals(sqMap.get("state"))) {
                            PageData pageData = new PageData();
                            pageData.put("info", "总部=》授权表名:"+ sqbsqbm+",授权用户"+ zhxx + " 成功！");
                            resultBodyBean.setData(pageData);
                        } else {
                            errorReasonBean.setCode("428");
                            errorReasonBean.setText("调用中台授权接口失败！");
                            resultBodyBean.setError(errorReasonBean);
                            status = HttpStatus.PRECONDITION_REQUIRED.value();
                            response.setStatus(status);
                            return resultBodyBean;
                        }
                    } else if(sqbsqbm !=null && sqbsqbm.toLowerCase().indexOf("sjzt_hlj_ds_pro.ads_dssjyyzc")>=0) {
                        // 如果是地市单位授权
                        System.out.println("授权表名----- 1 -----" + sqbsqbm);
                        System.out.println("授权用户----- 1 -----" + zhxx);
                        params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                        params_grantList.put("projectName",projectName); //项目名称 guowang_train1被邵文强改成了sjzt_hlj_ods_pro（王德志让改的）
                        params_grantList.put("tableName", sqbsqbm); //具体表名
                        params_grantList.put("toUserId", zhxx); //ram账号
                        params_grantList.put("expDate", expDate);
                        Map sqMap = sqAction.grantSelectDesc(params_grantList);
                        System.out.println("授权接口传入参数：" + params_grantList);
                        System.out.println("授权接口返回参数：" + sqMap);
                        if ("success".equals(sqMap.get("state"))) {
                            PageData pageData = new PageData();
                            pageData.put("info", "地市=》授权表名:"+ sqbsqbm+",授权用户"+ zhxx + " 成功！");
                            resultBodyBean.setData(pageData);
                        } else {
                            errorReasonBean.setCode("428");
                            errorReasonBean.setText("调用中台授权接口失败！");
                            resultBodyBean.setError(errorReasonBean);
                            status = HttpStatus.PRECONDITION_REQUIRED.value();
                            response.setStatus(status);
                            return resultBodyBean;
                        }
                    }  else if(sqbsqbm !=null && sqbsqbm.toLowerCase().indexOf("sjzt_hlj_dwd_pro.")>=0) {
                        // 如果是共享层授权
                        System.out.println("授权表名----- 1 -----" + sqbsqbm);
                        System.out.println("授权用户----- 1 -----" + zhxx);
                        params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                        params_grantList.put("projectName", projectName); //项目名称 sjzt_hlj_ods_pro被邵文强改成了sjzt_hlj_dwd_pro（张一飞让改的）
                        params_grantList.put("tableName", sqbsqbm); //具体表名
                        params_grantList.put("toUserId", zhxx); //ram账号
                        params_grantList.put("expDate", expDate);
                        Map sqMap = sqAction.grantSelectDesc(params_grantList);
//                        Map sqMap = sqAction.grantSelectDescTest(params_grantList);
                        System.out.println("授权接口传入参数：" + params_grantList);
                        System.out.println("授权接口返回参数：" + sqMap);
                        if ("success".equals(sqMap.get("state"))) {
                            PageData pageData = new PageData();
                            pageData.put("info", "地市=》授权表名:"+ sqbsqbm+",授权用户"+ zhxx + " 成功！");
                            resultBodyBean.setData(pageData);
                        } else {
                            errorReasonBean.setCode("428");
                            errorReasonBean.setText("调用中台授权接口失败！");
                            resultBodyBean.setError(errorReasonBean);
                            status = HttpStatus.PRECONDITION_REQUIRED.value();
                            response.setStatus(status);
                            return resultBodyBean;
                        }
                    } else {
                        errorReasonBean.setCode("428");
                        errorReasonBean.setText("中台授权表名不合规！！！");
                        resultBodyBean.setError(errorReasonBean);
                        status = HttpStatus.PRECONDITION_REQUIRED.value();
                    }
                } else {
                    errorReasonBean.setCode("428");
                    errorReasonBean.setText("密码错误，无法完成授权！");
                    resultBodyBean.setError(errorReasonBean);
                    status = HttpStatus.PRECONDITION_REQUIRED.value();
                }
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            status = HttpStatus.OK.value();
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("中台授权");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            pd.put("result",resultBodyBean);
            response.setStatus(status);
            return resultBodyBean;
        }
    }




    @ApiOperation(value = "中台授权", notes = "中台授权")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    @ResponseBody
    @RequestLog("中台授权")
    public ResultBodyBean getStatistics(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                if ("sgcc1234".equals(pd.getString("pwd").toLowerCase())) {
                    System.out.println("对确定授权的表，直接为用户授权!!!");

                    GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();
                    String sqbsqbm = (String) pd.get("ztbm");
                    String zhxx = (String) pd.get("zhxx");
                    if (pd.get("zhxx") == null || "".equals(pd.get("zhxx"))) {
                        errorReasonBean.setCode("428");
                        errorReasonBean.setText("授权人不能为空！");
                        resultBodyBean.setError(errorReasonBean);
                        status = HttpStatus.PRECONDITION_REQUIRED.value();
                        response.setStatus(status);
                        return resultBodyBean;
                    }
                    Map params_grantList = new LinkedHashMap<>();
                    // 判断表是什么开头的 如果是odps. 就将odps.去掉
                    if (sqbsqbm.toLowerCase().startsWith("odps.")) {
                        sqbsqbm = sqbsqbm.toLowerCase().substring(sqbsqbm.toLowerCase().indexOf(".")+1);
                    }
                    // 得到 项目名称 projectName （取开始到第一个.下标的字符串）
                    String projectName = sqbsqbm.toLowerCase().substring(0,sqbsqbm.toLowerCase().indexOf("."));
                    if (!projectName.startsWith("sjzt_hlj")) {
//                        如果不是sjzt_hlj开头，那么是分析层表，项目名称设置为固定的sjzt_hlj_ads_pro
                        projectName ="sjzt_hlj_ads_pro";
                    }
                    long expDate =  1000L * 60 * 60 * 24 * 180;
                    String useTime = pd.getString("useTime");
                    if (useTime !=null && !"".equals(useTime)) {
                        expDate = stepMonth(new Date(),Integer.valueOf(useTime));
                    }
                    System.out.println("授权具体表名------ 1 -----" + sqbsqbm);
                    System.out.println("授权用户账号------ 1 -----" + zhxx);
                    System.out.println("授权项目名称------ 1 -----" + projectName);
                    params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                    params_grantList.put("projectName", projectName); //项目名称 guowang_train1被邵文强改成了sjzt_hlj_ods_pro（王德志让改的）
                    params_grantList.put("tableName", sqbsqbm); //具体表名
                    params_grantList.put("toUserId", zhxx); //ram账号
                    params_grantList.put("expDate",  expDate);
                    //params_grantList.put("expDate",  System.currentTimeMillis()+10*365*24*60*60*1000);
                    Map sqMap = sqAction.grantSelectDesc(params_grantList);
                    System.out.println("授权接口传入参数：" + params_grantList);
                    System.out.println("授权接口返回参数：" + sqMap);
                    if ("success".equals(sqMap.get("state"))) {
                        System.out.println("中台授权=》授权表名:"+ sqbsqbm+",授权用户"+ zhxx + " 成功！");
                        PageData pageData = new PageData();
                        pageData.put("info", "中台授权=》授权表名:"+ sqbsqbm+",授权用户"+ zhxx + " 成功！");
                        resultBodyBean.setData(pageData);
                    } else {
                        errorReasonBean.setCode("428");
                        errorReasonBean.setText("调用中台授权接口失败！");
                        resultBodyBean.setError(errorReasonBean);
                        status = HttpStatus.PRECONDITION_REQUIRED.value();
                        response.setStatus(status);
                        return resultBodyBean;
                    }
                } else {
                    errorReasonBean.setCode("428");
                    errorReasonBean.setText("密码错误，无法完成授权！");
                    resultBodyBean.setError(errorReasonBean);
                    status = HttpStatus.PRECONDITION_REQUIRED.value();
                }
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            status = HttpStatus.OK.value();
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("中台授权");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            pd.put("result",resultBodyBean);
            response.setStatus(status);
            return resultBodyBean;
        }
    }


//    @ApiOperation(value = "中台授权", notes = "中台授权")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
//    @RequestMapping(value = "/authorization1", method = RequestMethod.POST)
//    @ResponseBody
//    public Map getStatistics1(@RequestBody PageData pd) {
//        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
//        ResultBodyBean resultBodyBean = new ResultBodyBean();
//        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
//        Map sqMap = null;
//        try {
//            Map params_grantList = new LinkedHashMap<>();
////                中台总部接的表名前缀：odps.sjzt_hlj_ods_pro.ods
////                中台总部之前接的表名：odps.sjzt_hlj_ods_pro.ods_cms_epm_hlj_a_pay_flow
////                中台总部以后接的表名：odps.sjzt_hlj_ods_pro.ods_cms_epmljyx_epm_hlj_a_pay_flow  // （epmljyx：实例-cms）
////                中台地市之前接的表名：odps.sjzt_hlj_ds_pro.ads_dssjyyzc
////                中台地市之前接的表名：odps.sjzt_hlj_ds_pro.ads_dssjyyzc_cms_epm_hlj_a_pay_flow_jms
////                中台地市以后接的表名：odps.sjzt_hlj_ds_pro.ads_dssjyyzc_cms_epmljyx_epm_hlj_a_pay_flow_jms //（epmljyx：实例-cms）
//            GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();
//            params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
//            params_grantList.put("projectName", pd.get("a")); //项目名称 guowang_train1被邵文强改成了sjzt_hlj_ods_pro（王德志让改的）
//            params_grantList.put("tableName",  pd.get("b")); //具体表名
//            params_grantList.put("toUserId",  pd.get("c")); //ram账号
//            params_grantList.put("expDate",  System.currentTimeMillis()+10*365*24*60*60*1000);
//           sqMap = sqAction.grantSelectDesc(params_grantList);
//            return sqMap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            errorReasonBean.setCode("428");
//            errorReasonBean.setText("中台授权");
//            resultBodyBean.setError(errorReasonBean);
//            status = HttpStatus.PRECONDITION_REQUIRED.value();
//            sqMap = new HashMap();
//        } finally {
//            response.setStatus(status);
//            return sqMap;
//        }
//    }


    public static Long stepMonth(Date sourceDate, int month) {
         Calendar c = Calendar.getInstance();
         c.setTime(sourceDate);
         c.add(Calendar.MONTH, month);
         return c.getTime().getTime();
    }
}
