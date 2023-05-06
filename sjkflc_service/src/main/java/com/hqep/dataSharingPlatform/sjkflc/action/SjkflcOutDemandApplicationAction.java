package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOutDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOutDemandApplicationService;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcProcessApplyService;
import com.hqep.redis.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PageData2Web;
import utils.UUIDUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;
import static com.hqep.dataSharingPlatform.common.utils.ExcelUtil.downloadExcel;

/**
 * 外部需求受理申请单(SjkflcOutDemandApplication)表控制层
 *
 * @author sssJL
 * @since 2021-10-06 14:46:38
 */
@RestController
@RequestMapping("/sjkflcOutDemandApplication")
@Api(tags = {"外部需求受理申请单"})
public class SjkflcOutDemandApplicationAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;


    @Autowired
    private SjkflcProcessApplyService sjkflcProcessApplyService;
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcOutDemandApplicationService sjkflcOutDemandApplicationService;

    /**
     * 通过主键查询单条数据
     *
     * @param pd 主键信息
     * @return 单条数据
     */
    @PostMapping(value = "/queryById")
    @ApiOperation("通过主键查询单条数据")
    @ResponseBody
    public PageData queryById(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String demandCode = pd.getString("demandCode");
            SjkflcOutDemandApplication sjkflcOutDemandApplication = sjkflcOutDemandApplicationService.queryById(demandCode);
            resultPd = PageData2Web.WebForObj(sjkflcOutDemandApplication);
        } catch (Exception e) {
            logger.error("查询外部需求受理申请单列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询外部需求受理申请单列表出错");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    @ResponseBody
    public PageData list(@RequestBody SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        PageData resultPd = new PageData();
        try {
            if (sjkflcOutDemandApplication.getCreatePersonCode() == null || "".equals(sjkflcOutDemandApplication.getCreatePersonCode())) {
                sjkflcOutDemandApplication.setCreatePersonCode("无用户信息，不可以查询！");
            }
            List<SjkflcOutDemandApplication> list = sjkflcOutDemandApplicationService.queryList(sjkflcOutDemandApplication);
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询外部需求受理申请单列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     * 查询非草稿状态的数据（已上报的数据和撤回及撤回的数据）
     * @param sjkflcOutDemandApplication 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPage(@RequestBody SjkflcOutDemandApplication sjkflcOutDemandApplication, HttpServletRequest request) {
        PageData resultPd = new PageData();
        try {
          //  ResultBodyBean resultBodyBean = (ResultBodyBean)request.getSession().getAttribute("userinfo");
           // resultBodyBean.getData();
           // System.out.println(resultBodyBean.getData());
          //  String userinfo = RedisUtil.get("userinfo",0);
          //  JSONObject jsonObject = JSONObject.fromObject(userinfo);
          //  PageData test =(PageData)JSONObject.toBean(jsonObject, PageData.class);
           // System.out.println(test);
            int pageIndex = sjkflcOutDemandApplication.getPageIndex();
            int pageSize = sjkflcOutDemandApplication.getPageSize();
            if (sjkflcOutDemandApplication.getCreatePersonCode() == null || "".equals(sjkflcOutDemandApplication.getCreatePersonCode())) {
                sjkflcOutDemandApplication.setCreatePersonCode("无用户信息，不可以查询！");
            }
            sjkflcOutDemandApplication.setSbStateNo("非草稿状态");
            PageInfo<SjkflcOutDemandApplication> pageInfo = sjkflcOutDemandApplicationService.queryListByPage(sjkflcOutDemandApplication, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询外部需求受理申请单列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     * 查询草稿状态的数据（未已上报的数据）
     * @param sjkflcOutDemandApplication 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPageForCg")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPageForCg(@RequestBody SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        PageData resultPd = new PageData();
        try {
            int pageIndex = sjkflcOutDemandApplication.getPageIndex();
            int pageSize = sjkflcOutDemandApplication.getPageSize();
            if (sjkflcOutDemandApplication.getCreatePersonCode() == null || "".equals(sjkflcOutDemandApplication.getCreatePersonCode())) {
                sjkflcOutDemandApplication.setCreatePersonCode("无用户信息，不可以查询！");
            }
            sjkflcOutDemandApplication.setSbState("0");
            PageInfo<SjkflcOutDemandApplication> pageInfo = sjkflcOutDemandApplicationService.queryListByPage(sjkflcOutDemandApplication, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询外部需求受理申请单列表出错!");
        }
        return resultPd;
    }
    /**
     * 新增数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public PageData create(@RequestBody SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        PageData resultPd = new PageData();
        try {
//            SjkflcOutDemandApplication qModel = new SjkflcOutDemandApplication();
//            qModel.setDemandCode(sjkflcOutDemandApplication.getDemandCode());
//            List<SjkflcOutDemandApplication> list = sjkflcOutDemandApplicationService.queryList(qModel);
//            if (list != null && list.size()>0) {
//                resultPd = PageData2Web.WebForBooleanObj(false,"该需求编码已经存在！");
//            } else {
            String demandName = sjkflcOutDemandApplication.getDemandOrgName() + sjkflcOutDemandApplication.getDemandStartTime();
            sjkflcOutDemandApplication.setDemandName(demandName);
            sjkflcOutDemandApplication.setDemandCode(UUIDUtil.getUUID());
            boolean flag = sjkflcOutDemandApplicationService.insert(sjkflcOutDemandApplication);
            resultPd = PageData2Web.WebForBoolean(flag);
//            }
        } catch (Exception e) {
            logger.error("新增外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增外部需求受理申请单列表出错!");
        }
        return resultPd;
    }

    /**
     * 修改数据
     *
     * @param sjkflcOutDemandApplication 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    @ResponseBody
    public PageData modify(@RequestBody SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        PageData resultPd = new PageData();
        try {
            SjkflcOutDemandApplication qModel = new SjkflcOutDemandApplication();
            qModel.setDemandCode(sjkflcOutDemandApplication.getDemandCode());
            List<SjkflcOutDemandApplication> list = sjkflcOutDemandApplicationService.queryList(qModel);
            if (list != null && list.size()>0 && !list.get(0).getId().equals(sjkflcOutDemandApplication.getId())) {
                resultPd = PageData2Web.WebForBooleanObj(false,"该需求编码已经存在！");
            } else {
                boolean flag = sjkflcOutDemandApplicationService.update(sjkflcOutDemandApplication);
                resultPd = PageData2Web.WebForBoolean(flag);
            }
        } catch (Exception e) {
            logger.error("修改外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改外部需求受理申请单列表出错!");
        }
        return resultPd;
    }


    /**
     * 弃用
     * 撤回数据
     * 对已上报的，但是未进行审批的流程的数据进行撤回操作
     * （该功能转移到SjkflcProcessApplyAction类中 ）
     * @param sjkflcOutDemandApplication 实例对象
     * @return 撤回数据结果
     */
    @PostMapping(value = "/back")
    @ApiOperation("撤回数据")
    @ResponseBody
    public PageData back(@RequestBody SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcOutDemandApplicationService.backById(sjkflcOutDemandApplication.getDemandCode());
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("撤回数据外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "撤回数据外部需求受理申请单列表出错!");
        }
        return resultPd;
    }


    /**
     * 删除数据
     *
     * @param pd 主键
     * @return 删除结果
     */
    @PostMapping(value = "/remove")
    @ApiOperation("删除数据")
    @ResponseBody
    public PageData remove(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String demandCode = pd.getString("demandCode");
            boolean flag = sjkflcOutDemandApplicationService.deleteById(demandCode);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("删除外部需求受理申请单列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除外部需求受理申请单列表出错!");
        }
        return resultPd;
    }


    /**
     * 给前端一个ID值
     * 用处：附件生成与申请相关联的code值
     *
     * @return uuid
     */
    @PostMapping(value = "/queryFjCode")
    @ApiOperation("通过主键查询单条数据")
    @ResponseBody
    public PageData queryFjCode() {
        PageData resultPd = new PageData();
        try {
            resultPd = PageData2Web.WebForObj(UUIDUtil.getUUID());
        } catch (Exception e) {
            logger.error("查询外部需求受理申请单列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询外部需求受理申请单列表出错");
        }
        return resultPd;
    }



    @ApiOperation(value = "导出" , notes = "导出")
    @RequestMapping("/exportExcel")
    public @ResponseBody void exportExcel (@RequestBody SjkflcOutDemandApplication sjkflcOutDemandApplication) {
        try {
            if (sjkflcOutDemandApplication.getCreatePersonCode() == null || "".equals(sjkflcOutDemandApplication.getCreatePersonCode())) {
                sjkflcOutDemandApplication.setCreatePersonCode("无用户信息，不可以查询！");
            }
            List<SjkflcOutDemandApplication> list = sjkflcOutDemandApplicationService.queryList(sjkflcOutDemandApplication);
            int num = 0;
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
                for (SjkflcOutDemandApplication model : list) {
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
                ByteArrayOutputStream out = ExcelUtil.OutPutExcel("外部需求受理申请", header, dataList);
                downloadExcel("负面清单.xls", out, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
