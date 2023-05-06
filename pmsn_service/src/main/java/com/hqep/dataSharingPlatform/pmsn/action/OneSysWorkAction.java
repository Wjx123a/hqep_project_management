package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.action.GetDataWorksIntoStockAction;
import com.hqep.dataSharingPlatform.pmsn.service.OneSysWorkService;
import com.hqep.dataSharingPlatform.pmsn.service.ProcessApplyService;
import com.hqep.dataSharingPlatform.pmsn.unit.AddDshzUnit;
import com.hqep.dataSharingPlatform.pmsn.unit.ListUnit;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.DateUtils;
import utils.UUIDUtil;

import java.util.*;

/***
 * create by sssjl
 * create time 2022-07-11
 * description  一级系统数据共享流程贯通
 */
@Controller
@RequestMapping("/oneSysWork")
public class OneSysWorkAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcessApplyService processApplyService;
    @Autowired
    private OneSysWorkService oneSysWorkService;

    /**
     * 退回
     * @param pd
     * @return
     */
    @ApiOperation(value = "退回", notes = "退回")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/backShoppingcatOnesys", method = RequestMethod.POST)
    public PageData backShoppingcatOnesys(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String id = pd.getString("id");
            String[] ids = id.split(",");
            for (int i = 0; i < ids.length; i++) {
                PageData upZt = new PageData();
                upZt.put("id", ids[i]);
                upZt.put("zt", "3");
                oneSysWorkService.updateDataToShoppingcatOnesys(upZt);
                upZt.put("tableId", pd.get("id"));
                oneSysWorkService.deleteByOnesysWorkTablesId(upZt);
            }
            oneSysWorkService.deleteByOnesysWorkOrderShoppingcatId(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("将待申请信息保存至订单栏出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "将待申请信息保存至订单栏出错");
        }
        return resultPd;
    }
    /**
     * 将待申请信息保存至订单栏
     * @param pd
     * @return
     */
    @ApiOperation(value = "将待申请信息保存至订单栏", notes = "将待申请信息保存至订单栏")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/insertDataToShoppingcatOnesys", method = RequestMethod.POST)
    public PageData insertDataToShoppingcatOnesys(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            // 如果不是 国网黑龙江省电力本部 的用户 查询地市数据是否拆分
            if (!"国网黑龙江省电力本部".equals(pd.get("sqrssdw"))) {
                AddDshzUnit.addDshz(pd);
                List<PageData> list = processApplyService.querySqTableName(pd);
                if (list != null && list.size()>0) {
                    PageData data = list.get(0);
                    // 如果未拆分数据，前台提示 此表地市数据未拆分，请与业务人员联系！
                    if (data.getString("table_ename") == null || "".equals(data.getString("table_ename"))){
                        resultPd.put("data", new ArrayList<>());
                        resultPd.put("error", "此表地市数据未拆分，请与业务人员联系！");
                        return resultPd;
                    }
                } else {
                    resultPd.put("data", new ArrayList<>());
                    resultPd.put("error", "此表地市数据未拆分，请与业务人员联系！");
                    return resultPd;
                }
            }
            if (pd.get("bsskj") == null || "".equals(pd.get("bsskj"))) {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "表所属空间未知，请与业务人员联系！");
                return resultPd;
            }

            // 如果是国网黑龙江省电力本部数据
            // 或者 是地市数据 并且地市数据已经拆分向下执行加入订单栏
            oneSysWorkService.insertDataToShoppingcatOnesys(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("将待申请信息保存至订单栏出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "将待申请信息保存至订单栏出错");
        }
        return resultPd;
    }

    /**
     * 通过主键删除数据-订单栏
     * @param pd
     * @return
     */
    @ApiOperation(value = "通过主键删除数据-订单栏", notes = "通过主键删除数据-订单栏")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/delShoppingcatOnesys", method = RequestMethod.POST)
    public PageData delShoppingcatOnesys(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            oneSysWorkService.delShoppingcatOnesys(pd);
            resultPd.put("data", "删除成功");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("删除订单栏数据出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除订单栏数据出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "根据登录人ID查询订单栏信息", notes = "根据登录人ID查询订单栏信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryShoppingcatOnesys", method = RequestMethod.POST)
//    @NoRepeatSubmit(name = "test")
    public PageData queryShoppingcatOnesys(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = oneSysWorkService.queryShoppingcatOnesys(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("根据登录人ID查询订单栏信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "根据登录人ID查询订单栏信息出错");
        }
        return resultPd;
    }


    /**
     * 提交订单栏的申请信息
     * @param pd
     * @return
     */
    @ApiOperation(value = "提交订单栏的申请信息", notes = "提交订单栏的申请信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/submitDataToShoppingcatOnesys", method = RequestMethod.POST)
    public PageData submitDataToShoppingcatOnesys(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String shoppingcartId = "";
            String id = UUIDUtil.getUUID();
            List<Map> list = (List<Map>) pd.get("tableList");
            if (list != null && list.size()>0) {
                shoppingcartId = ListUnit.ListMapToStrWithComma(list,"id");
                for (int i = 0; i < list.size(); i++) {
                    String tableId = UUIDUtil.getUUID();
                    Map model = list.get(i);
                    model.put("tableId",model.get("id"));
                    model.put("objectCname",model.get("sqbzwbm"));
                    model.put("objectName",model.get("sqbywbm"));
                    model.put("schemaName",model.get("sqbxtyh"));
                    model.put("systemName",model.get("sqbssxt"));
                    model.put("deployType",model.get("bsfs"));
                    model.put("applicationDept",pd.get("applicationDept"));
                    model.put("applicationName",pd.get("applicationName"));
                    model.put("demandFreq",pd.get("demandFreq"));
                    model.put("firstTopicName",pd.get("firstTopicName"));
                    model.put("rangeDesc",pd.get("rangeDesc"));
                    model.put("secondTopicName",pd.get("secondTopicName"));
                    model.put("topicRemark",pd.get("topicRemark"));
                    model.put("parentId",id);
                    oneSysWorkService.insertDataToOnesysWorkTables(model);
                    PageData upZt = new PageData();
                    upZt.put("id",model.get("id"));
                    upZt.put("zt",1);
                    oneSysWorkService.updateDataToShoppingcatOnesys(upZt);
                }
            }
            // 数据入库
            pd.put("dept","hl");
            pd.put("shoppingcartId",shoppingcartId);
            pd.put("id",id);
            oneSysWorkService.insertDataToOnesysWorkOrder(pd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("将待申请信息保存至订单栏出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "将待申请信息保存至订单栏出错");
        }
        return resultPd;
    }


    @ApiOperation(value = "查询提交申请的流转数据信息", notes = "查询提交申请的流转数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryDataToOnesysWorkOrderList", method = RequestMethod.POST)
    public PageData queryDataToOnesysWorkOrderList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String grantType = pd.getString("grantType");
            String orgName = pd.getString("orgName");
            String roleName = pd.getString("roleName");
            if (grantType != null && ("数据申请".equals(grantType) || "数据授权".equals(grantType))) {
                pd.put("userid","");
            } else if (grantType != null && "数据查询".equals(grantType) && orgName != null && "国网黑龙江省电力本部".equals(orgName)) {
                pd.put("userid","");
            } else if (grantType != null && "数据查询".equals(grantType) && roleName != null && "管理员".equals(roleName)) {
                pd.put("userid","");
            }
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = oneSysWorkService.queryDataToOnesysWorkOrderList(pd);
            for (int i = 0; i < list.size(); i++) {
                PageData modelOne = list.get(i);
                String parentId = modelOne.getString("id");
                PageData params = new PageData();
                params.put("parentId",parentId);
                // 查询申请表
                if (parentId != null && !"".equals(parentId)) {
                    List<PageData> tableslist = oneSysWorkService.queryDataToOnesysWorkTablesList(params);
                    list.get(i).put("details",tableslist);
                } else {
                    // 如果没有查到 传空 （正常不应该有空，存在空的情况是有错误的）
                    list.get(i).put("details",new ArrayList<>());
                }
                // 查询授权用户
                params = new PageData();
                String grantUserNo = pd.getString("grantUserNo");
                if (grantUserNo != null) {
                    String[] grantUserNos = grantUserNo.split(",");
                    params.put("grantUserNos",grantUserNos);
                    List<PageData> grantlist = processApplyService.querySjztUserList(params);
                    list.get(i).put("grantlist",grantlist);
                }
            }
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("根据登录人ID查询订单栏信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "根据登录人ID查询订单栏信息出错");
        }
        return resultPd;
    }


    @ApiOperation(value = "查询提交申请的流转数据信息详情", notes = "查询提交申请的流转数据信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryDataToOnesysWorkOrderListForDetails", method = RequestMethod.POST)
    public PageData queryDataToOnesysWorkOrderListForDetails(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String grantType = pd.getString("grantType");
            if (grantType != null && "授权".equals(grantType)) {
                pd.put("grantType","查询已经完成流转的数据");
            }
            String id = pd.getString("id");
            if (id != null && !"".equals(id)) {
                List<PageData> list = oneSysWorkService.queryDataToOnesysWorkOrderList(pd);
                for (int i = 0; i < list.size(); i++) {
                    PageData modelOne = list.get(i);
                    String parentId = modelOne.getString("id");
                    PageData params = new PageData();
                    params.put("parentId",parentId);
                    // 查询申请表
                    if (parentId != null && !"".equals(parentId)) {
                        List<PageData> tableslist = oneSysWorkService.queryDataToOnesysWorkTablesList(params);
                        list.get(i).put("details",tableslist);
                    } else {
                        // 如果没有查到 传空 （正常不应该有空，存在空的情况是有错误的）
                        list.get(i).put("details",new ArrayList<>());
                    }
                    // 查询授权用户
                    params = new PageData();
                    String grantUserNo = modelOne.getString("grantUserNo");
                    if (grantUserNo!= null) {
                        String[] grantUserNos = grantUserNo.split(",");
                        params.put("grantUserNos",grantUserNos);
                        List<PageData> grantlist = processApplyService.querySjztUserList(params);
                        list.get(i).put("grantlist",grantlist);
                    }
                }
                resultPd.put("data", list);
                resultPd.put("error", null);
            } else {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "主键id参数不能为空");
            }
        } catch (Exception e) {
            logger.error("根据登录人ID查询订单栏信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "根据登录人ID查询订单栏信息出错");
        }
        return resultPd;
    }




    /**
     * 查询订单的审批流程信息
     * @param pd
     * @return
     */
    @ApiOperation(value = "查询订单的审批流程信息", notes = "查询订单的审批流程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryDemandWorkflow", method = RequestMethod.POST)
    public PageData queryDemandWorkflow(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String demandId = pd.getString("demandId");
            pd.put("demandId",demandId);
            List<PageData> list = oneSysWorkService.queryOnesysDownActList(pd);
            // 将最后一个节点置为正在执行 // 测试使用
//            if (list!=null && list.size()>0) {
//                list.get(list.size()-1).put("state","0");
//            }
//            if (list!=null && list.size()>0) {
//                list.get(list.size()-2).put("state","2");
//            }
            resultPd.put("data", list);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("将待申请信息保存至订单栏出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "将待申请信息保存至订单栏出错");
        }
        return resultPd;
    }


    /**
     * 查询一级数据下发日志信息
     * @param pd
     * @return
     */
    @ApiOperation(value = "查询一级数据下发日志信息", notes = "查询一级数据下发日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryDataToOnesysDownLogsList", method = RequestMethod.POST)
    public PageData queryDataToOnesysDownLogsList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = oneSysWorkService.queryDataToOnesysDownLogsList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("将待申请信息保存至订单栏出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "将待申请信息保存至订单栏出错");
        }
        return resultPd;
    }


    /**
     * 查询订单的审批流程信息
     * @param pd
     * @return
     */
    @ApiOperation(value = "授权申请成功的国网的数据", notes = "授权申请成功的国网的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/grantSjztForUserName", method = RequestMethod.POST)
    public PageData grantSjztForUserName(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            GetDataWorksIntoStockAction sqAction = new GetDataWorksIntoStockAction();
            Map sqMap =null;
            PageData qPd = new PageData();
            String id = pd.getString("id");
            if (id != null && !"".equals(id)) {
                String[] ids = id.split(",");
                Date curDate = new Date();
                String time = DateUtils.formatDate(curDate,"yyyy-MM-dd");
                String subTime = DateUtils.formatDate(curDate, "yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < ids.length; i++) {
                    qPd = new PageData();
                    qPd.put("id",ids[i]);
                    List<PageData> parentlist = oneSysWorkService.queryDataToOnesysWorkOrderList(qPd);
                    qPd.put("parentId",ids[i]);
                    List<PageData> list = oneSysWorkService.queryDataToOnesysWorkTablesList(qPd);
                    if (list!= null && list.size()>0) {
                        for (int j = 0; j < list.size(); j++) {
                            PageData parentModel = parentlist.get(i);
                            PageData modelOne = list.get(i);
                            String grantUserNo = parentModel.getString("grantUserNo");
                            if (grantUserNo!= null) {
                                String[] sqyhArr = grantUserNo.split(",");
                                for (int k = 0; k <sqyhArr.length; k++) {
                                    System.out.println("授权用户-----" + i + "-----" + sqyhArr[i]);
                                    Map params_grantList = new LinkedHashMap<>();
                                    params_grantList.put("fromUserId", "root"); //本地账，使用改账号对toUserId进行授权操作
                                    params_grantList.put("projectName", "sjzt_hlj_dwd_pro"); //项目名称 sjzt_hlj_ods_pro被邵文强改成了sjzt_hlj_dwd_pro（张一飞让改的）
                                    params_grantList.put("tableName", modelOne.get("sqbsqbm")); //具体表名
                                    params_grantList.put("toUserId", sqyhArr[i]); //ram账号
                                    params_grantList.put("expDate", System.currentTimeMillis() + 10 * 365 * 24 * 60 * 60 * 1000);
                                    // 正式授权方法
                                    sqMap = sqAction.grantSelectDesc(params_grantList);
//                                    /*测试的时候使用*/
//                                    sqMap = sqAction.grantSelectDescTest(params_grantList);
                                    sqMap.put("state","success");
                                    sqMap.put("error",null);
                                    if (sqMap.get("error") == null) { // 判断中台是否授权成功
                                        PageData params = new PageData();
                                        params.put("id",ids[i]);
                                        params.put("grantTime",subTime);
                                        params.put("state",3);
                                        // 持久化返回数据
                                        oneSysWorkService.updateDataToOnesysWorkOrder(params);
                                    } else {
                                        resultPd = new PageData(sqMap);
                                        resultPd.put("error",null);
                                        return resultPd;


                                    }
                                }
                            }
                        }
                    }

                }
                resultPd.put("error", null);
            } else {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "主键id参数不能为空");
            }
        } catch (Exception e) {
            logger.error("授权申请成功的国网的数据出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "授权申请成功的国网的数据出错");
        }
        return resultPd;
    }




}
