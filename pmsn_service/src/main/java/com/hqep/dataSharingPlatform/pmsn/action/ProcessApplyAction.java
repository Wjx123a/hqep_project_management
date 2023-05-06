package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.NormalEnum;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.pmsn.dao.ProcessManageDao;
import com.hqep.dataSharingPlatform.pmsn.service.OneSysWorkService;
import com.hqep.dataSharingPlatform.pmsn.service.ProcessApplyService;
import com.hqep.dataSharingPlatform.pmsn.service.impl.QueryGdServiceImpl;
import com.hqep.dataSharingPlatform.pmsn.unit.AddDshzUnit;
import com.hqep.dataSharingPlatform.pmsn.unit.ListUnit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hqep.dataSharingPlatform.common.utils.ExcelUtil.downloadExcel;

@RequestMapping("/processApply")
@RestController
@Api(description = "流程申请")
public class ProcessApplyAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private ProcessApplyService service;
    @Autowired
    private QueryGdServiceImpl queryGdService;
    @Autowired
    private ProcessManageDao processManageDao;
    @Autowired
    private OneSysWorkService oneSysWorkService;

    /**
     * 查询相关内容
     */
    @ApiOperation(value = "查询标签", notes = "查询标签")
    @RequestMapping(value = "/queryBq", method = RequestMethod.POST)
    @ResponseBody

    public PageData queryBq(@RequestBody PageData pd)  {
        PageData resultPd = new PageData();
        try{
            List<PageData> list  = service.queryBq(pd);

            resultPd.put("data", list);
        }catch (Exception e){
            logger.error("查询标签出错", e);
            resultPd.put("error", "查询标签出错");
        }

        return resultPd;
    }

    /**
     * 查询相关内容
     */

    @ApiOperation(value = "查询有效表信息", notes = "查询有效表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryTableList", method = RequestMethod.POST)
    public PageData queryTableList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
//            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryTableList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
//            paginationPd.put("total", page.getTotal());
            int total = service.queryTableListCount(pd);
            paginationPd.put("total", total);
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询有效表信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询有效表信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "根据表名查询所有字段信息", notes = "根据表名查询所有字段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryCloumnList", method = RequestMethod.POST)
    public PageData queryCloumnList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryCloumnList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("根据表名查询所有字段信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "根据表名查询所有字段信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "查询申请去审批", notes = "查询申请去审批")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryProgressToAudit", method = RequestMethod.POST)
    public PageData queryProgressToAudit(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            PageData spjb1 = service.querySpjb(pd);
            pd.put("spjd1",spjb1.getString("role_name"));
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryProgressToAudit(pd); //只接受jsrid：接收人id、jsjs：接收角色
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询申请去审批出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询申请去审批出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "用户查询自己提交的申请", notes = "用户查询自己提交的申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestLog("本人申请记录查询")
    @RequestMapping(value = "/queryProgressListForMe", method = RequestMethod.POST)
    public PageData queryProgressListForMe(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        long start = System.currentTimeMillis(); //程序执行前的时间戳
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryProgressListForMe(pd); // 参数为sqrid:申请人ID
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("用户查询自己提交的申请出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "用户查询自己提交的申请出错");
        }
        long end = System.currentTimeMillis(); //程序执行后的时间戳
        System.out.println("用户查询自己提交的申请 ：" + (end - start)/1000);
        return resultPd;
    }

    @ApiOperation(value = "导出用户查询自己提交的申请", notes = "导出用户查询自己提交的申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestLog("本人申请记录导出")
    @RequestMapping(value = "/queryProgressListForMeForExcel", method = RequestMethod.POST)
    public void queryProgressListForMeForExcel(@RequestBody PageData pd) {
        int num = 0;
        long start = System.currentTimeMillis(); //程序执行前的时间戳
        try {
            List<PageData> list = service.queryProgressListForMe(pd); // 参数为sqrid:申请人ID
            if (list.size() > 0) {
                // 表头
                Map<Integer, String> header = new HashMap<>();
                header.put(1, "序号");
                header.put(2, "系统名称");
                header.put(3, "所属部门");
                header.put(4, "申请人名称");
                header.put(5, "英文表名");
                header.put(6, "中文表名");
                header.put(7, "申请时间");
                header.put(8, "是否负面清单");
                header.put(9, "审批节点");
                header.put(10, "中台云账号");
                List<Map<Integer, Object>> dataList = new ArrayList<>();
                for (PageData resultPd : list) {
                    Map<Integer, Object> row = new HashMap<>();
                    row.put(1, ++num);
                    row.put(2, resultPd.get("sqbssxt") == null ? "" : resultPd.get("sqbssxt").toString());
                    row.put(3, resultPd.get("sqbssbm") == null ? "" : resultPd.get("sqbssbm").toString());
                    row.put(4, resultPd.get("sqrmc") == null ? "" : resultPd.get("sqrmc").toString());
                    row.put(5, resultPd.get("sqbywbm") == null ? "" : resultPd.get("sqbywbm").toString());
                    row.put(6, resultPd.get("sqbzwbm") == null ? "" : resultPd.get("sqbzwbm").toString());
                    row.put(7, resultPd.get("sqsj") == null ? "" : resultPd.get("sqsj").toString());
                    row.put(8, resultPd.get("sffmqd") == null ? "" : resultPd.get("sffmqd").toString());
                    row.put(9, resultPd.get("dqjdmc") == null ? "" : resultPd.get("dqjdmc").toString());
                    row.put(10, resultPd.get("sqperson") == null ? "" : resultPd.get("sqperson").toString());
                    dataList.add(row);
                }
                ByteArrayOutputStream out = ExcelUtil.OutPutExcel("当前用户提交申请审批列表", header, dataList);
                downloadExcel("当前用户提交申请审批列表.xls", out, response);
            }

        } catch (Exception e) {
            logger.error("导出用户查询自己提交的申请出错", e);
        }
        long end = System.currentTimeMillis(); //程序执行后的时间戳
        System.out.println("导出用户查询自己提交的申请 ：" + (end - start)/1000);
    }

    @ApiOperation(value = "用户查询自己提交的申请的审批日志记录", notes = "用户查询自己提交的申请的审批日志记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryProgressListLogForMe", method = RequestMethod.POST)
    public PageData queryProgressListLogForMe(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryProgressListLogForMe(pd); // 参数为lcpch:流程批次号,sjlc:上级节点(两个参数值应该相等)
            resultPd.put("data", list);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("用户查询自己提交的申请的审批日志记录出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "用户查询自己提交的申请的审批日志记录出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "审批角色查询自己审批过的申请", notes = "审批角色查询自己审批过的申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryProgressListForAudit", method = RequestMethod.POST)
    public PageData queryProgressListForAudit(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryProgressListForAudit(pd); // 参数为shrid:审核人ID
            resultPd.put("data", list);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("审批角色查询自己审批过的申请出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "审批角色查询自己审批过的申请出错");
        }

        return resultPd;
    }

    @ApiOperation(value = "用户提交申请", notes = "用户提交申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestLog("用户提交申请")
    @RequestMapping(value = "/commitTask", method = RequestMethod.POST)
    public PageData commitTask(@RequestBody List<PageData> list) {
        UUID uuid = UUID.randomUUID();
        PageData resultPd = new PageData();
        resultPd.put("error", null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        try {
            String tycbm = "";
            List<PageData> dqList;
            PageData queryPro = new PageData();
            //有且仅有一条主流程在启用
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("主流程不存在");
            }
            PageData mainPro=proList.get(0);
            // 循环用户申请
            for (int i = 0; i < list.size(); i++) {
                PageData param = list.get(i);
                param.put("lcbm", mainPro.getString("lcbm"));//主流程编码
                dqList = service.queryZxStatus(param);//该用户申请的表最新一条主流程信息
                PageData item = null;
                if (dqList.size() > 0) {
                    item = dqList.get(0);
                }
                String spzt = null == item ? "" : item.getString("spzt");
                if (null == item || NormalEnum.AUDIT_NO.equals(spzt)) {
                    //不存在、审核不通过
                    param.put("lcmc", mainPro.getString("lcmc"));//主流程名称
                    param.put("sqsj",simpleDateFormat.format(new Date()));
                    param.put("uuid",String.valueOf(uuid));
                    //添加所属流程状态 和 工单表信息
                    service.addProgress(param);
                    //queryGdService.addGd(param);
                    service.updateshoppingcat(param);
                } else {
                    continue;//重复提交直接忽略
                }
            }
            //非负面清单 零审批
            resultPd = FfmqdZeroSpFunc(list);
           // resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户提交申请失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "用户提交申请失败");
        }finally {
            resultPd.put("data", new ArrayList<>());
            resultPd.put("note", "操作结束");
        }
        System.out.println(resultPd);
        return resultPd;
    }

    @ApiOperation(value = "审核通过", notes = "审核通过")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestLog("审批通过")
    @RequestMapping(value = "/auditTaskYes", method = RequestMethod.POST)
    public PageData auditTaskYes(@RequestBody List<PageData> list) {
        PageData resultPd = new PageData();
        try {
            String tycbm = "";
            List dqList;
            PageData queryPro = new PageData();
            //有且仅有一条主流程在启用
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("主流程不存在");
            }
            PageData param = new PageData();
            // 循环用户申请
            PageData pdUpdate = new PageData();
            for (int i = 0; i < list.size()-1; i++) {
                param = list.get(i);
                System.out.println(list.get(list.size()-1).getString("shrid"));
                param.put("shrid",list.get(list.size()-1).getString("shrid"));
                System.out.println(param.get("shrid"));
                param.put("shrmc",list.get(list.size()-1).getString("shrmc"));
                param.put("shrssbm",list.get(list.size()-1).getString("shrssbm"));
                param.put("shrssdw",list.get(list.size()-1).getString("shrssdw"));
                param.put("shjg","同意");
//                //param.put("shjg",list.get(list.size()-1).getString("shjg"));
                System.out.println("参数："+ param);
                queryGdService.updateSpZt(param);
                Map auditMap = service.auditProgressYes(param);
                System.out.println(param);
                List<PageData> zeroStateList = service.queryZeroState(new PageData());
                if(zeroStateList != null && zeroStateList.size() > 0 && (("非负面清单".equals(list.get(0).get("fmqdlx")))
                        || (list.get(0).get("fmqdlx") == null  ||  "".equals(list.get(0).get("fmqdlx")) ))
                   ) {
                    Map jdbmsMap = ListUnit.ListMapToMap(zeroStateList,"jdbm");
                    if ("LC-04-JD-01".equals(auditMap.get("curNodeId"))
                            && jdbmsMap.containsKey("LC-90-JD-02")) {
                        System.out.println("======================================");
                        System.out.println("进入非负面清单零审批开始=》===================审批通过");
                        System.out.println("进入非负面清单零审批开始=》进入互联网部自动审批===================");
                        param.put("shrid","21aeea853f2d4689b214cd2f2efcbe6e");
                        param.put("shrmc","互联网部");
                        param.put("shrssbm","互联网部");
                        param.put("shrssdw","国网黑龙江省电力本部");
                        param.put("shjg","同意");
                        queryGdService.updateSpZt(param);
                        service.auditProgressYes(param);
                    }
                    if ("LC-04-JD-01".equals(auditMap.get("curNodeId"))
                            && jdbmsMap.containsKey("LC-90-JD-02")
                            && jdbmsMap.containsKey("LC-90-JD-03")) {
                        System.out.println("进入非负面清单零审批开始=》完成互联网部自动审批===================");
                        System.out.println("进入非负面清单零审批开始=》进入大数据中心自动审批===================");
                        param.put("shrid","c28cc0c2eae84148b9307584394e0e1c");
                        param.put("shrmc","大数据中心");
                        param.put("shrssbm","大数据中心");
                        param.put("shrssdw","国网黑龙江省电力本部");
                        param.put("shjg","同意");
                        queryGdService.updateSpZt(param);
                        Map sqMap = service.auditProgressYes(param);
                        System.out.println(sqMap);
                        if ("success".equals(sqMap.get("state"))) {
                            resultPd.put("text", "授权成功！");
                        } else {
                            resultPd.put("data", new ArrayList<>());
                            resultPd.put("error", "调用中台授权接口失败！");
                            resultPd.put("text", "调用中台授权接口失败！");
                        }
                    }
                    if ("LC-04-JD-02".equals(auditMap.get("curNodeId"))
                            && jdbmsMap.containsKey("LC-90-JD-03")) {
                        System.out.println("进入非负面清单零审批开始=》完成互联网部自动审批===================");
                        System.out.println("进入非负面清单零审批开始=》进入大数据中心自动审批===================");
                        param.put("shrid","c28cc0c2eae84148b9307584394e0e1c");
                        param.put("shrmc","大数据中心");
                        param.put("shrssbm","大数据中心");
                        param.put("shrssdw","国网黑龙江省电力本部");
                        param.put("shjg","同意");
                        queryGdService.updateSpZt(param);
                        Map sqMap = service.auditProgressYes(param);
                        System.out.println(sqMap);
                        if ("success".equals(sqMap.get("state"))) {
                            resultPd.put("text", "授权成功！");
                        } else {
                            resultPd.put("data", new ArrayList<>());
                            resultPd.put("error", "调用中台授权接口失败！");
                            resultPd.put("text", "调用中台授权接口失败！");
                        }
                    }
                    System.out.println("进入非负面清单零审批开始=》完成大数据中心自动审批===================");
                }
            }
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("审批通过用户提交申请失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "审批通过用户提交申请失败");
        }finally {
            resultPd.put("data", new ArrayList<>());
            resultPd.put("note", "操作结束");
        }
        System.out.println(resultPd);
        return resultPd;
    }

    @ApiOperation(value = "审核不通过", notes = "审核不通过")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestLog("审批驳回")
    @RequestMapping(value = "/auditTaskNo", method = RequestMethod.POST)
    public PageData auditTaskNo(@RequestBody List<PageData> list) {
        PageData resultPd = new PageData();
        try {
            String tycbm = "";
            List<PageData> dqList;
            PageData queryPro = new PageData();
            //有且仅有一条主流程在启用
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("主流程不存在");
            }
            // 循环用户申请
            for (int i = 0; i < list.size(); i++) {
                PageData param = list.get(i);
                service.auditProgressNo(param);
            }
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("审核不通过失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "审核不通过失败");
        }finally {
            resultPd.put("data", new ArrayList<>());
            resultPd.put("note", "操作结束");
        }
        return resultPd;
    }

    @ApiOperation(value = "查询流程最新进度", notes = "更新流程最新进度")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryLatestProgress", method = RequestMethod.POST)
    public PageData queryLatestProgress(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryLatestProgress(pd);
            resultPd.put("data", list);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询流程最新进度出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询流程最新进度出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "更新流程最新进度", notes = "更新流程最新进度")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/editLatestProgress", method = RequestMethod.POST)
    public PageData editLatestProgress(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.editLatestProgress(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("更新流程最新进度出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "更新流程最新进度出错");
        }
        return resultPd;
    }

    /**
     * 20210302第二次修改
     */

    // 将待申请信息保存至购物车
    @ApiOperation(value = "将待申请信息保存至购物车", notes = "将待申请信息保存至购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestLog("将待申请信息保存至购物车")
    @RequestMapping(value = "/insertDataToShoppingcat", method = RequestMethod.POST)
    public PageData insertDataToShoppingcat(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            // 如果不是 国网黑龙江省电力本部 的用户 查询地市数据是否拆分
            if (!"国网黑龙江省电力本部".equals(pd.get("sqrssdw"))) {
                AddDshzUnit.addDshz(pd);
                List<PageData> list = service.querySqTableName(pd);
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
            // 如果是 国网黑龙江省电力本部 的用户 禁止对贴源层数据进行授权
            if ("国网黑龙江省电力本部".equals(pd.get("sqrssdw"))) {
                if (pd.get("bsskj") != null && "贴源层".equals(pd.get("bsskj"))) {
                    resultPd.put("data", new ArrayList<>());
                    resultPd.put("error", "未开启贴源层数据表申请权限的功能！");
                    return resultPd;
                }
                if (pd.get("bsskj") != null && "二级目录".equals(pd.get("bsskj"))) {
                    resultPd.put("data", new ArrayList<>());
                    resultPd.put("error", "未开启贴源层数据表申请权限的功能！");
                    return resultPd;
                }
            }
            if (pd.get("bsskj") == null || "".equals(pd.get("bsskj"))) {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "表所属空间未知，请与业务人员联系！");
                return resultPd;
            }
            if (pd.get("addType") != null && "订单栏".equals(pd.get("addType")) ) {
                // 如果是需要加入订单栏
                // 如果是国网黑龙江省电力本部数据
                // 或者 是地市数据 并且地市数据已经拆分向下执行加入订单栏
                oneSysWorkService.insertDataToShoppingcatOnesys(pd);
                resultPd.put("data", "success");
                resultPd.put("error", null);
            } else {
                // 如果是需要加入购物车
                // 如果是国网黑龙江省电力本部数据
                // 或者 是地市数据 并且地市数据已经拆分向下执行加人购物车
                service.insertDataToShoppingcat(pd);
                resultPd.put("data", "success");
                resultPd.put("error", null);
            }
        } catch (Exception e) {
            logger.error("将待申请信息保存至购物车出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "将待申请信息保存至购物车出错");
        }
        return resultPd;
    }

    // 根据登录人ID查询购物车信息
    @ApiOperation(value = "根据登录人ID查询购物车信息", notes = "根据登录人ID查询购物车信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryShoppingcat", method = RequestMethod.POST)
    public PageData queryShoppingcat(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryShoppingcat(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("根据登录人ID查询购物车信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "根据登录人ID查询购物车信息出错");
        }
        return resultPd;
    }

    // 查询所有工单信息
    @ApiOperation(value = "查询所有工单信息", notes = "查询所有工单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryAllApplyDataList", method = RequestMethod.POST)
    public PageData queryAllApplyDataList(@RequestBody PageData pd,HttpServletRequest request) {
        PageData resultPd = new PageData();
        try {
            if(pd.containsKey("spzt")&&null!=pd.get("spzt")) {
                if (pd.get("spzt").equals("待审批")) {
                    pd.put("spzt", "0");
                } else if (pd.get("spzt").equals("审批通过")) {
                    pd.put("spzt", "1");
                } else if (pd.get("spzt").equals("审批驳回")) {
                    pd.put("spzt", "2");
                } else if(pd.get("spzt").equals("审批结束")){
                    pd.put("spzt", "3");
                } else {
                    pd.put("spzt", "");
                }
            }else{
                pd.put("spzt", null);
            }
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            String userId = pd.getString("userName");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryAllApplyDataList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询所有工单信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询所有工单信息出错");
        }
        return resultPd;
    }

    // 查询工单信息下的申请子表
    @ApiOperation(value = "查询工单信息下的申请子表", notes = "查询工单信息下的申请子表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryAllApplyDataListForNext", method = RequestMethod.POST)
    public PageData queryAllApplyDataListForNext(@RequestBody PageData pd,HttpServletRequest request) {
        PageData resultPd = new PageData();
        try {
            if(pd.containsKey("numid")&&null!=pd.get("numid")) {
                List<PageData> list = service.queryAllApplyDataListForNext(pd);
                resultPd.put("data", list);
                resultPd.put("error", null);
            } else {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "未查询到申请表详情！");
            }
        } catch (Exception e) {
            logger.error("查询到申请表详情出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询到申请表详情出错");
        }
        return resultPd;
    }

    // 查询工单流程审批详情信息
    @ApiOperation(value = "查询工单流程审批详情信息", notes = "查询工单流程审批详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryAllApplyDataListForDetails", method = RequestMethod.POST)
    public PageData queryAllApplyDataListForDetails(@RequestBody PageData pd,HttpServletRequest request) {
        PageData resultPd = new PageData();
        try {
            List<PageData> splist = new ArrayList<>();
            if(pd.containsKey("numid")&&null!=pd.get("numid")) {
                List<PageData> list = service.queryAllApplyDataListForNext(pd);
                if (list != null && list.size() > 0) {
                    pd.put("lcpch",list.get(0).get("lcpch"));
                    splist = service.queryAllApplyDataListForDetails(pd);
                    boolean flag = false;
                    for (int i = 0; i < list.size(); i++) {
                        if (!flag && (list.get(i).get("lcxq") == null || "".equals(list.get(i).get("lcxq")))) {
                            list.get(i).put("lcxq",list.get(i).get("username") + "，正在处理中...");
                            flag = true;
                        }else if (flag && (list.get(i).get("lcxq") == null || "".equals(list.get(i).get("lcxq")))) {
                            list.get(i).put("lcxq","待处理...");
                        }
                    }
                }
                resultPd.put("data", splist);
                resultPd.put("error", null);
            } else {
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "未查询到流程详情！");
            }
        } catch (Exception e) {
            logger.error("查询所有工单信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询工单流程审批详情信息");
        }
        return resultPd;
    }

    // 查询中台授权用户信息
    @ApiOperation(value = "查询中台授权用户信息", notes = "查询中台授权用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/querySjztUserList", method = RequestMethod.POST)
    public PageData querySjztUserList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            System.out.println("-----------------------------");
            System.out.println(pd);
            List<PageData> list = service.querySjztUserList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询中台授权用户信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询中台授权用户信息出错");
        }
        return resultPd;
    }
    /**
     * 移除购物车记录内容
     * @param pd
     * @return
     */
    // 移除购物车记录内容
    @ApiOperation(value = "移除购物车记录内容", notes = "移除购物车记录内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/delForShoppingcat", method = RequestMethod.POST)
    public PageData delForShoppingcat(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.delForShoppingcat(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("移除购物车记录内容出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "移除购物车记录内容出错");
        }
        return resultPd;
    }


    //非负面清单 零审批
    public PageData FfmqdZeroSpFunc(List<PageData> list) {
        PageData result = new PageData();
        try {
            if (list != null && list.size()>0 ){ // "非负面清单".equals(list.get(0).get("sffmqd"))) {
                PageData params = list.get(0);
                PageData pd = new PageData();
                pd.put("jsjs","11");
                pd.put("loginId","c1286e891c6e4d11810f0aa0abcf1b59");
                pd.put("pageIndex",1);
                pd.put("pageSize",10);
                PageData spjb1 = service.querySpjb(pd);
                pd.put("spjd1",spjb1.getString("role_name"));
                pd.put("sqridForZero",params.get("sqrid"));
                pd.put("xtbmForZero",params.get("xtbm"));
                pd.put("sqbsqbmForZero",params.get("sqbsqbm"));
                pd.put("sqbywbmForZero",params.get("sqbywbm"));
                pd.put("sqbxtyhForZero",params.get("sqbxtyh"));
                List<PageData> list1 = service.queryProgressToAudit(pd); //只接受jsrid：接收人id、jsjs：接收角色
                pd = new PageData();
                pd.put("jsjs","11");
                pd.put("spjd1",spjb1.getString("role_name"));
                pd.put("loginId","c1286e891c6e4d11810f0aa0abcf1b59");
                pd.put("numidForZero",list1.get(0).get("numid"));
                List<PageData> relist = service.queryProgressToAudit(pd); //只接受jsrid：接收人id、jsjs：接收角色
                PageData perPd = new PageData();
                perPd.put("shjg","同意");
                perPd.put("shrid","c1286e891c6e4d11810f0aa0abcf1b59");
                perPd.put("shrmc","数据中心");
                perPd.put("shrssbm","信息通讯");
                perPd.put("shrssdw","国网黑龙江省电力本部");
                relist.add(perPd);
                result = auditTaskYesForZero(relist);
                System.out.println(relist);
            } else {
                result.put("error", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", new ArrayList<>());
            result.put("error", "授权失败！");
            result.put("text", "授权失败！");
        }
        return result;
    }


    // 开始负面清单零审批判断及操作
    public PageData auditTaskYesForZero(List<PageData> list) {
        PageData resultPd = new PageData();
        resultPd.put("error", null);
        try {
            String tycbm = "";
            List dqList;
            PageData queryPro = new PageData();
            //有且仅有一条主流程在启用
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("主流程不存在");
            }
            PageData param = new PageData();
            // 循环用户申请
            for (int i = 0; i < list.size()-1; i++) {
                param = list.get(i);
                List<PageData> zeroStateList = service.queryZeroState(new PageData());
                if(zeroStateList != null && zeroStateList.size() > 0 && ( "非负面清单".equals(param.get("fmqdlx")) ||
                (param.get("fmqdlx") == null  ||  "".equals(param.get("fmqdlx"))))) {
                    Map jdbmsMap = ListUnit.ListMapToMap(zeroStateList,"jdbm");
                    if (jdbmsMap.containsKey("LC-90-JD-01")) {
                        System.out.println("======================================");
                        System.out.println("进入非负面清单零审批开始=》===================auditTaskYesForZero");
                        System.out.println("进入非负面清单零审批开始=》进入信通公司自动审批===================");
                        param.put("shrid",list.get(list.size()-1).getString("shrid"));
                        param.put("shrmc",list.get(list.size()-1).getString("shrmc"));
                        param.put("shrssbm",list.get(list.size()-1).getString("shrssbm"));
                        param.put("shrssdw",list.get(list.size()-1).getString("shrssdw"));
                        param.put("shjg","同意");
//                //param.put("shjg",list.get(list.size()-1).getString("shjg"));
                        queryGdService.updateSpZt(param);
                        service.auditProgressYes(param);
                        System.out.println("进入非负面清单零审批开始=》完成信通公司自动审批===================");
                    }
                    if(("非负面清单".equals(param.get("fmqdlx"))
                            && "数据中心".equals(list.get(list.size()-1).getString("shrmc")))
                            || (param.get("fmqdlx") == null  ||  "".equals(param.get("fmqdlx")) )
                    ) {
                        if (jdbmsMap.containsKey("LC-90-JD-01") && jdbmsMap.containsKey("LC-90-JD-02")) {
                            System.out.println("进入非负面清单零审批开始=》进入互联网部自动审批===================");
                            param.put("shrid","21aeea853f2d4689b214cd2f2efcbe6e");
                            param.put("shrmc","互联网部");
                            param.put("shrssbm","互联网部");
                            param.put("shrssdw","国网黑龙江省电力本部");
                            param.put("shjg","同意");
                            queryGdService.updateSpZt(param);
                            service.auditProgressYes(param);
                            System.out.println("进入非负面清单零审批开始=》完成互联网部自动审批===================");
                        }
                        if (jdbmsMap.containsKey("LC-90-JD-01")
                                && jdbmsMap.containsKey("LC-90-JD-02")
                                && jdbmsMap.containsKey("LC-90-JD-03")) {
                            System.out.println("进入非负面清单零审批开始=》进入大数据中心自动审批===================");
                            param.put("shrid","c28cc0c2eae84148b9307584394e0e1c");
                            param.put("shrmc","大数据中心");
                            param.put("shrssbm","大数据中心");
                            param.put("shrssdw","国网黑龙江省电力本部");
                            param.put("shjg","同意");
                            queryGdService.updateSpZt(param);
                            Map sqMap = service.auditProgressYes(param);
                            System.out.println(sqMap);
                            if ("success".equals(sqMap.get("state"))) {
                                resultPd.put("error", null);
                                resultPd.put("text", "授权成功！");
                            } else {
                                resultPd.put("data", new ArrayList<>());
                                resultPd.put("error", "调用中台授权接口失败！");
                                resultPd.put("text", "调用中台授权接口失败！");
                            }
                        }
                        System.out.println("进入非负面清单零审批开始=》完成大数据中心自动审批===================");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("审批通过用户提交申请失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "审批通过用户提交申请失败");
        }finally {
            resultPd.put("data", new ArrayList<>());
            resultPd.put("note", "操作结束");
        }
        System.out.println(resultPd);
        return resultPd;
    }

}
