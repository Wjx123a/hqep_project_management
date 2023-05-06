package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpAuditProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DateUtils;
import utils.PageData2Web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据上传_审核流程环节信息表(SjkflcOdsSjmlTUpAuditProcess)表控制层
 *
 * @author sssJL
 * @since 2021-10-09 13:27:37
 */
@RestController
@RequestMapping("/sjkflcOdsSjmlTUpAuditProcess")
@Api(tags = {"数据上传_审核流程环节信息表"})
public class SjkflcOdsSjmlTUpAuditProcessAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcOdsSjmlTUpAuditProcessService sjkflcOdsSjmlTUpAuditProcessService;

    /**
     * 通过主键查询单条数据
     *
     * @param demandCode 主键
     * @return 单条数据
     */
    @PostMapping(value = "/queryById")
    @ApiOperation("通过主键查询单条数据")
    @ResponseBody
    public PageData queryById(@RequestBody String demandCode) {
        PageData resultPd = new PageData();
        try {
            SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess = sjkflcOdsSjmlTUpAuditProcessService.queryById(demandCode);
            resultPd = PageData2Web.WebForObj(sjkflcOdsSjmlTUpAuditProcess);
        } catch (Exception e) {
            logger.error("查询数据上传_审核流程环节信息表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_审核流程环节信息表列表出错");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    @ResponseBody
    public PageData list(@RequestBody SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        PageData resultPd = new PageData();
        try {
            List<SjkflcOdsSjmlTUpAuditProcess> list = sjkflcOdsSjmlTUpAuditProcessService.queryList(sjkflcOdsSjmlTUpAuditProcess);
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询数据上传_审核流程环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_审核流程环节信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPage(@RequestBody SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        PageData resultPd = new PageData();
        try {
            int pageIndex = sjkflcOdsSjmlTUpAuditProcess.getPageIndex();
            int pageSize = sjkflcOdsSjmlTUpAuditProcess.getPageSize();
            PageInfo<SjkflcOdsSjmlTUpAuditProcess> pageInfo = sjkflcOdsSjmlTUpAuditProcessService.queryListByPage(sjkflcOdsSjmlTUpAuditProcess, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询数据上传_审核流程环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_审核流程环节信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public PageData create(@RequestBody SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcOdsSjmlTUpAuditProcessService.insert(sjkflcOdsSjmlTUpAuditProcess);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("新增数据上传_审核流程环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增数据上传_审核流程环节信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    @ResponseBody
    public PageData modify(@RequestBody SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcOdsSjmlTUpAuditProcessService.update(sjkflcOdsSjmlTUpAuditProcess);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("修改数据上传_审核流程环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改数据上传_审核流程环节信息表列表出错!");
        }
        return resultPd;
    }


    /**
     * 上报数据
     *
     * @param sjkflcOdsSjmlTUpAuditProcess 实例对象
     * @return 上报数据结果
     */
    @PostMapping(value = "/report")
    @ApiOperation("上传数据")
    @ResponseBody
    public PageData report(@RequestBody SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess) {
        PageData resultPd = new PageData();
        boolean flag = true;
        try {
            String id = sjkflcOdsSjmlTUpAuditProcess.getId();
            if (id == null || "".equals(id)) {
                resultPd = PageData2Web.WebForBooleanObj(false,"主键不能为空！");
                return resultPd;
            } else if(id.indexOf(",")>-1) {
                String[] ids = id.split(",");
                for (int i = 0; i < ids.length; i++) {
                    flag = reportOne(ids[i]);
                    if (!flag) {
                        break;
                    }
                }
            } else {
                flag = reportOne(sjkflcOdsSjmlTUpAuditProcess.getId());
            }
        } catch (Exception e) {
            flag = false;
            logger.error("上传数据_数据上传_审核流程环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "上传数据_数据上传_审核流程环节信息表列表出错!");
        }
        resultPd = PageData2Web.WebForBoolean(flag);
        return resultPd;
    }

    public boolean reportOne(String id){
        boolean flag = true;
        SjkflcOdsSjmlTUpAuditProcess sjkflcOdsSjmlTUpAuditProcess = new SjkflcOdsSjmlTUpAuditProcess();
        sjkflcOdsSjmlTUpAuditProcess.setId(id);
        List<SjkflcOdsSjmlTUpAuditProcess> list = sjkflcOdsSjmlTUpAuditProcessService.queryList(sjkflcOdsSjmlTUpAuditProcess);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SjkflcOdsSjmlTUpAuditProcess model = list.get(i);
                SjkflcOdsSjmlTUpAuditProcess qModel = new SjkflcOdsSjmlTUpAuditProcess();
                qModel.setOrgCode(model.getOrgCode());
                qModel.setDemandCode(model.getDemandCode());
                qModel.setAuditLink(model.getAuditLink());
                List<SjkflcOdsSjmlTUpAuditProcess> cklist = sjkflcOdsSjmlTUpAuditProcessService.queryListForApi(qModel);
                //UEP调用插入上传数据的接口
                model.setBatchTime(DateUtils.getNowDate());
                if (cklist != null && cklist.size()>0) {
                    sjkflcOdsSjmlTUpAuditProcessService.updateForApi(model);
                } else {
                    sjkflcOdsSjmlTUpAuditProcessService.insertForApi(model);
                }
                qModel.setBatchTime(model.getBatchTime());
                sjkflcOdsSjmlTUpAuditProcessService.update(qModel);
            }
        } else {
            flag = false;
        }
        return flag;
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
            boolean flag = sjkflcOdsSjmlTUpAuditProcessService.deleteById(demandCode);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("删除数据上传_审核流程环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除数据上传_审核流程环节信息表列表出错!");
        }
        return resultPd;
    }

}
