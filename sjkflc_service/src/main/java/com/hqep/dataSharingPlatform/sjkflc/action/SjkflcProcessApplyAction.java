package com.hqep.dataSharingPlatform.sjkflc.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.NormalEnum;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.sjkflc.dao.SjkflcProcessManageDao;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOutDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcProcessAudit;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpAuditProcessService;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOutDemandApplicationService;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcProcessApplyService;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcProcessAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.DateUtils;
import utils.MapObjUtil;
import utils.PageData2Web;
import utils.UUIDUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hqep.dataSharingPlatform.common.utils.ExcelUtil.downloadExcel;

@RequestMapping("/SjkflcProcessApply")
@RestController
@Api(description = "流程申请")
public class SjkflcProcessApplyAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private SjkflcProcessApplyService service;
    @Autowired
    private SjkflcProcessManageDao processManageDao;

    /**
     * 服务对象 外部需求受理申请单
     */
    @Autowired
    private SjkflcOutDemandApplicationService sjkflcOutDemandApplicationService;
    /**
     * 服务对象 审核流程意见表
     */
    @Autowired
    private SjkflcProcessAuditService sjkflcProcessAuditService;
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcOdsSjmlTUpAuditProcessService sjkflcOdsSjmlTUpAuditProcessService;
    /**
     * 相关方法
     */

    @ApiOperation(value = "用户提交申请", notes = "用户提交申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestLog("用户提交申请")
    @RequestMapping(value = "/commitTask", method = RequestMethod.POST)
    public PageData commitTask(@RequestBody List<PageData> list) {
        UUID uuid = UUID.randomUUID();
        PageData resultPd = new PageData();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        try {
            List<PageData> dqList;
            String demand_type_name = list.get(0).get("demandTypeName").toString();
            //判断当前执行的流程有且只有一个在启用状态的主流程
            PageData queryPro = new PageData();
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            //根据需求类型判断要走哪个流程
            if("公益服务类需求".equals(demand_type_name)){
                queryPro.put("lcbm", "LC-01");
            } else if("商务增值类需求".equals(demand_type_name)) {
                queryPro.put("lcbm", "LC-20");
            } else if("公共开放类需求".equals(demand_type_name)) {
                queryPro.put("lcbm", "LC-10");
            } else if("政府监管类需求".equals(demand_type_name)) {
                queryPro.put("lcbm", "LC-30");
            }
            // 查看主流程节点信息
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("流程不存在");
            }
            // 更新申请表的提交申请状态
            SjkflcOutDemandApplication sjkflcOutDemandApplication = new SjkflcOutDemandApplication();
            try {
                sjkflcOutDemandApplication = MapObjUtil.pageData2Object(list.get(0),SjkflcOutDemandApplication.class);
                sjkflcOutDemandApplication.setSbState("1");
                sjkflcOutDemandApplication.setSbStateName("初审");
                SjkflcOutDemandApplication queryModel = sjkflcOutDemandApplicationService.queryById(sjkflcOutDemandApplication.getDemandCode());
                if (queryModel != null) {
                    // 如果需求编码存在 且id相同 执行更新操作 （提交）
                    if (queryModel.getId().equals(sjkflcOutDemandApplication.getId())) {
                        sjkflcOutDemandApplicationService.update(sjkflcOutDemandApplication);
                    } else {
                        // 如果需求编码存在 但是id不相同 提示 该需求编码已经存在
                        resultPd = PageData2Web.WebForBooleanObj(false,"该需求编码已经存在！");
                        return resultPd;
                    }
                } else {
                    // 如果需求编码不存在  执行插入操作 （保存并提交）
                    sjkflcOutDemandApplicationService.insert(sjkflcOutDemandApplication);
                }
            } catch (Exception e) {
                logger.error("新增外部需求受理申请单列表出错!", e);
                resultPd.put("data", new ArrayList<>());
                resultPd.put("error", "新增外部需求受理申请单列表出错!");
                return resultPd;
            }
            PageData mainPro = proList.get(0);
            PageData param =  list.get(0);
            param.put("demandCode",sjkflcOutDemandApplication.getDemandCode());
            param.put("demandName",sjkflcOutDemandApplication.getDemandName());
            param.put("id",sjkflcOutDemandApplication.getId());
            // 判断数据是否涉密
//            String smFlag = param.getString("smFlag");
//            if (smFlag == null || "".equals(smFlag)) {
//                param.put("smFlag","0");
//            }
            if("公益服务类需求".equals(demand_type_name)){
            } else if("商务增值类需求".equals(demand_type_name)) {
            } else if("公共开放类需求".equals(demand_type_name)) {
                // 判断数据是否技术支撑
                String tzFlag = param.getString("sfzc");
                if (tzFlag == null || "".equals(tzFlag)) {
                    param.put("tzFlag","0");
                } else {
                    param.put("tzFlag",tzFlag);
                }
            } else if("政府监管类需求".equals(demand_type_name)) {
            }

            param.put("sqqxid","0");
            param.put("sqxqid", sjkflcOutDemandApplication.getId());
            param.put("sqxqbm", param.get("demandCode"));
            param.put("sqxqmc", param.get("demandName"));
            param.put("bz", "自定义备注说明");
            param.put("lcbm", mainPro.getString("lcbm")); //主流程编码
            dqList = service.queryZxStatus(param); //该用户申请的表最新一条主流程信息
            PageData item = null;
            if (dqList.size() > 0) {
                item = dqList.get(0);
            }
            String spzt = null == item ? "" : item.getString("spzt");
            if (null == item || NormalEnum.AUDIT_NO.equals(spzt)) {
                //不存在、审核不通过
                param.put("lcmc", mainPro.getString("lcmc"));//主流程名称
                param.put("sqsj", sjkflcOutDemandApplication.getDemandStartTime());
//                param.put("sqsj",simpleDateFormat.format(new Date()));
                param.put("uuid",String.valueOf(uuid));
                service.addProgress(param);
                param.put("dqjdmc","提交申请");
                List<PageData> tjsqList = service.queryProgressBySqxqID(param);
                if (tjsqList.size() > 0) {
                    PageData param1 = tjsqList.get(0);
                    this.auditTaskYesOne(param1);
                }

                param.put("auditStartTime", DateUtils.getNowTime());
                param.put("auditEndTime", DateUtils.getNowTime());
                param.put("shjg","同意");
                param.put("auditReason","提交申请");
                param.put("auditLink","提交申请");
                if("公益服务类需求".equals(demand_type_name)){
                   // this.throughAudit(param);
                } else if("商务增值类需求".equals(demand_type_name)) {
                } else if("公共开放类需求".equals(demand_type_name)) {
                } else if("政府监管类需求".equals(demand_type_name)) {
                  //  this.throughAudit(param);
                }
            }
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户提交申请失败", e);
            resultPd.put("data", new ArrayList<PageData>());
            resultPd.put("error", "用户提交申请失败");
        } finally {
            if ( resultPd.get("data") == null) {
                resultPd.put("data", new ArrayList<PageData>());
            }
            resultPd.put("note", "操作结束");
        }
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
            //判断当前执行的流程有且只有一个在启用状态的主流程
            PageData queryPro = new PageData();
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("主流程不存在");
            }
            PageData param = new PageData();
            param = list.get(0);
            SjkflcOutDemandApplication sjkflcOutDemandApplication = MapObjUtil.pageData2Object(param,SjkflcOutDemandApplication.class);
            sjkflcOutDemandApplication.setSmFlag( param.getString("smFlag"));
            sjkflcOutDemandApplication.setSfzc( param.getString("sfzc"));
            sjkflcOutDemandApplication.setDemandCode(param.getString("demandCode"));
            sjkflcOutDemandApplication.setSbState(param.getString("dqjdid"));
            sjkflcOutDemandApplication.setSbStateName(param.getString("dqjdmc"));
            sjkflcOutDemandApplicationService.update(sjkflcOutDemandApplication);

            param.put("shrid",list.get(list.size()-1).getString("shrid"));
            param.put("shrmc",list.get(list.size()-1).getString("shrmc"));
            param.put("shrssbm",list.get(list.size()-1).getString("shrssbm"));
            param.put("shrssdw",list.get(list.size()-1).getString("shrssdw"));
            param.put("shjg","同意");
            if(param.getString("sfzc") == null || "".equals(param.getString("sfzc"))) {
                if(param.getString("smFlag") == null || "".equals(param.getString("smFlag"))) {
                    param.put("tzFlag","0");
                } else {
                    param.put("tzFlag",param.getString("smFlag"));
                }
            } else {
                param.put("tzFlag",param.getString("sfzc"));
            }
            param.put("auditEndTime",DateUtils.getNowTime());
            this.throughAudit(param);
            service.auditProgressYes(param);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("审批通过失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "审批通过失败");
        } finally {
            resultPd.put("data", new ArrayList<>());
            resultPd.put("note", "操作结束");
        }
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
            //判断当前执行的流程有且只有一个在启用状态的主流程
            PageData queryPro = new PageData();
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("主流程不存在");
            }
            PageData param = list.get(0);
            param.put("shjg","驳回");
            param.put("auditEndTime",DateUtils.getNowTime());
            this.throughAudit(param);
            service.auditProgressNo(param);
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

    @ApiOperation(value = "查询申请去审批", notes = "查询申请去审批")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryProgressToAudit", method = RequestMethod.POST)
    public PageData queryProgressToAudit(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryProgressToAudit(pd); //只接受jsrid：接收人id、jsjs：接收角色
//            list = service.addNextNode(list);
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

    @ApiOperation(value = "工单管理查询", notes = "工单管理查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryOrders", method = RequestMethod.POST)
    public PageData queryOrders(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryOrders(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("工单管理查询出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "工单管理查询出错");
        }
        return resultPd;
    }



    /**
     * 撤回数据
     *
     * @param pd 实例对象
     * @return 撤回数据结果
     */
    @PostMapping(value = "/back")
    @ApiOperation("撤回数据")
    @ResponseBody
    public PageData back(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            // 申请编码（sqxqbm 原方法里面的 是模糊查询）
            pd.put("sqxqbm",pd.get("demandCode"));
            // 申请编码（sqxqmcbc 新增的查询字段 是绝对查询）
           // pd.put("sqxqmcbc",pd.get("demandCode"));
            // 审批状态（spztno 新增的查询字段 是绝对查询）
            pd.put("spztno","23");
            List<PageData> list = service.queryLatestProgress(pd);
           // System.out.println(list);
            pd.put("auditStartTime",DateUtils.getNowTime());
            pd.put("auditEndTime",DateUtils.getNowTime());
            pd.put("dqjdmc","撤回");
            pd.put("auditDept",pd.get("sqrssdw")==null? "": pd.get("sqrssdw"));
            // 判断数据是否可以撤回
            if (list != null && list.size() > 0) {
                // 如果最新节点有多条是 不能撤回
                if (list.size()>1) {
                    resultPd = PageData2Web.WebForBooleanObj(false,"已经进入审核流程不能撤回！");
                }
//                else if (!("初审".equals(list.get(0).getString("dqjdmc"))
//                        || "需求分析初审".equals(list.get(0).getString("dqjdmc")))) {
//                    // 如果最新节点 不是初审时 不能撤回
//                    resultPd = PageData2Web.WebForBooleanObj(false,"已经进入审核流程不能撤回！");
//                }
                else {
                    // 如果最新节点是初审 允许撤回
                    auditTaskBack(pd);
                    boolean flag = sjkflcOutDemandApplicationService.backById(pd.getString("demandCode"));
                    resultPd = PageData2Web.WebForBoolean(flag);
                }
            } else {
                // 如果没有最新节点数据可以撤回
                auditTaskBack(pd);
                boolean flag = sjkflcOutDemandApplicationService.backById(pd.getString("demandCode"));
                resultPd = PageData2Web.WebForBoolean(flag);
            }
        } catch (Exception e) {
            logger.error("撤回数据外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "撤回数据外部需求受理申请单列表出错!");
        }
        return resultPd;
    }


    @ApiOperation(value = "导出" , notes = "导出")
    @RequestMapping("/exportExcel")
    public @ResponseBody void exportExcel (@RequestBody PageData pd) {
        try {
            int num = 0;
            List<PageData> list = service.queryProgressToAudit(pd); //只接受jsrid：接收人id、jsjs：接收角色
            if (list.size() > 0) {
                // 表头
                Map<Integer, String> header = new HashMap<>();
                header.put(1, "序号");
                header.put(2,"需求名称");
                header.put(3,"需求编码");
                header.put(4,"需求单位名称");
                header.put(5,"需求单位类型");
                header.put(6,"需求开始时间");
                header.put(7,"需求完成时间");
                header.put(8,"数据申请原因");
                header.put(9,"数据用途");
                header.put(10,"数据内容简述");
                header.put(11,"需求类别名称");
                header.put(12,"需求类别编码");
                header.put(13,"数据提供方式");
                header.put(14,"数据提供载体");
                header.put(15,"数据提供频度");
                header.put(16,"需求单位联系人");
                header.put(17,"需求单位联系电话");
                header.put(18,"需求承接方单位名称");
                header.put(19,"需求承接方部门名称");
                header.put(20,"需求承接方联系人");
                header.put(21,"需求承接方邮箱");
                header.put(22,"需求承接方联系电话");
                header.put(23,"数据产生部门名称");
                header.put(24,"数据使用截止时间");
                List<Map<Integer, Object>> dataList = new ArrayList<>();
                for (PageData resultPd : list) {
                    SjkflcOutDemandApplication model = MapObjUtil.pageData2Object(resultPd,SjkflcOutDemandApplication.class);
                    Map<Integer, Object> row = new HashMap<>();
                    row.put(1, ++num);
                    row.put(2, model.getDemandName());
                    row.put(3, model.getDemandCode());
                    row.put(4, model.getDemandOrgName());
                    row.put(5, model.getDemandOrgType());
                    row.put(6, model.getDemandStartTime());
                    row.put(7, model.getDemandEndTime());
                    row.put(8, model.getDemandDescReason());
                    row.put(9, model.getDemandDescPurpose());
                    row.put(10, model.getDemandContent());
                    row.put(11, model.getDemandTypeName());
                    row.put(12, model.getDemandTypeCode());
                    row.put(13, model.getProvideForm());
                    row.put(14, model.getProvideCarrier());
                    row.put(15, model.getDataFreq());
                    row.put(16, model.getDemandNamePeo());
                    row.put(17, model.getDemandNameTel());
                    row.put(18, model.getUndertakeDeptBig());
                    row.put(19, model.getUndertakeDeptSmall());
                    row.put(20, model.getUndertakeDeptPeo());
                    row.put(21, model.getUndertakeDeptMail());
                    row.put(22, model.getUndertakeDeptTel());
                    row.put(23, model.getSourceDept());
                    row.put(24, model.getDataEndTime());
                    dataList.add(row);
                }
                ByteArrayOutputStream out = ExcelUtil.OutPutExcel("需求审批表", header, dataList);
                downloadExcel("需求审批表.xls", out, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUpdate(SjkflcOutDemandApplication model) {
        if ("草稿".equals(model.getSbState()) || "撤回".equals(model.getSbState()) || ("".equals(model.getSbState()))) {
            return true;
        }
        return false;
    }

    /**
     * 进入流程后 第一步不需要审核时，调用该方法
     * @param model
     * @return
     */
    public PageData auditTaskYesOne(PageData model) {
        PageData resultPd = new PageData();
        try {
            //判断当前执行的流程有且只有一个在启用状态的主流程
            PageData queryPro = new PageData();
            queryPro.put("qyzt", NormalEnum.IS_USE);
            queryPro.put("lclx", NormalEnum.IS_MAIN);
            List<PageData> proList = processManageDao.queryProcessList(queryPro);
            if (proList.size() == 0) {
                throw new Exception("主流程不存在");
            }
            PageData param = model;
            param.put("shrid", model.getString("shrid"));
            param.put("shrmc", model.getString("shrmc"));
            param.put("shrssbm", model.getString("shrssbm"));
            param.put("shrssdw", model.getString("shrssdw"));
            param.put("shjg","同意");
            service.auditProgressYes(param);
        } catch (Exception e) {
            logger.error("审批通过失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "审批通过失败");
        } finally {
            resultPd.put("data", new ArrayList<>());
            resultPd.put("note", "操作结束");
        }
        return resultPd;
    }

    public boolean throughAudit(PageData param) {
        try {
            SjkflcProcessAudit sjkflcProcessAudit = new SjkflcProcessAudit();
            sjkflcProcessAudit.setId(UUIDUtil.getUUID());
            sjkflcProcessAudit.setDemandCode(param.getString("demandCode"));
            sjkflcProcessAudit.setDemandName(param.getString("demandName"));
            sjkflcProcessAudit.setAuditOrg(param.getString("shrssdw"));
            sjkflcProcessAudit.setAuditDept(param.getString("auditDept"));
            sjkflcProcessAudit.setAuditPeo(param.getString("auditPeo"));
            sjkflcProcessAudit.setAuditOpinion(param.getString("auditOpinion"));
            sjkflcProcessAudit.setAuditReason(param.getString("auditReason"));
            sjkflcProcessAudit.setAuditState(param.getString("auditState"));
            sjkflcProcessAudit.setAuditStartTime(param.getString("auditStartTime"));
            sjkflcProcessAudit.setAuditEndTime(param.getString("auditEndTime"));
            sjkflcProcessAudit.setAuditLink(param.getString("auditLink"));
            sjkflcProcessAudit.setCreatePersonName(param.getString("shrmc"));
            sjkflcProcessAudit.setCreatePersonCode(param.getString("shrbm"));
            sjkflcProcessAudit.setHtCode(param.getString("htCode"));
            boolean flag = sjkflcProcessAuditService.insert(sjkflcProcessAudit);
            SjkflcOdsSjmlTUpAuditProcess ckModel = MapObjUtil.Object2Object(sjkflcProcessAudit,SjkflcOdsSjmlTUpAuditProcess.class);
            ckModel.setOrgName("国网黑龙江省电力有限公司");
            ckModel.setOrgCode("01020");
            ckModel.setModifyType("1");
            ckModel.setAuditOrg(param.getString("shrssdw"));
          //  sjkflcOdsSjmlTUpAuditProcessService.insert(ckModel);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // 审核撤回
    public PageData auditTaskBack(PageData pd) {
        PageData resultPd = new PageData();
        try {
            PageData param = pd;
            service.auditProgressBack(param);
            param.put("shjg","撤回");
            param.put("auditLink","撤回");
            // 因为提交没有记录所以撤回也取消记录
            // this.throughAudit(param);
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


}
