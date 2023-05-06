package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcProcessAudit;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcProcessAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PageData2Web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 审核流程意见表(SjkflcProcessAudit)表控制层
 *
 * @author sssJL
 * @since 2021-10-08 15:05:29
 */
@RestController
@RequestMapping("/sjkflcProcessAudit")
@Api(tags = {"审核流程意见表"})
public class SjkflcProcessAuditAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcProcessAuditService sjkflcProcessAuditService;

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
            SjkflcProcessAudit sjkflcProcessAudit = sjkflcProcessAuditService.queryById(demandCode);
            resultPd = PageData2Web.WebForObj(sjkflcProcessAudit);
        } catch (Exception e) {
            logger.error("查询审核流程意见表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询审核流程意见表列表出错");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    @ResponseBody
    public PageData list(@RequestBody SjkflcProcessAudit sjkflcProcessAudit) {
        PageData resultPd = new PageData();
        try {
            if (sjkflcProcessAudit.getDemandCode() == null ||"".equals(sjkflcProcessAudit.getDemandCode())) {
                sjkflcProcessAudit.setDemandCode("无查询主键，不能查询数据！");
            }
            List<SjkflcProcessAudit> list = sjkflcProcessAuditService.queryList(sjkflcProcessAudit);
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询审核流程意见表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询审核流程意见表列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPage(@RequestBody SjkflcProcessAudit sjkflcProcessAudit) {
        PageData resultPd = new PageData();
        try {
            int pageIndex = sjkflcProcessAudit.getPageIndex();
            int pageSize = sjkflcProcessAudit.getPageSize();
            PageInfo<SjkflcProcessAudit> pageInfo = sjkflcProcessAuditService.queryListByPage(sjkflcProcessAudit, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询审核流程意见表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询审核流程意见表列表出错!");
        }
        return resultPd;
    }

    /**
     * 新增数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public PageData create(@RequestBody SjkflcProcessAudit sjkflcProcessAudit) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcProcessAuditService.insert(sjkflcProcessAudit);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("新增审核流程意见表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增审核流程意见表列表出错!");
        }
        return resultPd;
    }

    /**
     * 修改数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    @ResponseBody
    public PageData modify(@RequestBody SjkflcProcessAudit sjkflcProcessAudit) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcProcessAuditService.update(sjkflcProcessAudit);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("修改审核流程意见表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改审核流程意见表列表出错!");
        }
        return resultPd;
    }


    /**
     * 上报或者撤回数据
     *
     * @param sjkflcProcessAudit 实例对象
     * @return 上报或者撤回数据结果
     */
    @PostMapping(value = "/back")
    @ApiOperation("撤回数据")
    @ResponseBody
    public PageData back(@RequestBody SjkflcProcessAudit sjkflcProcessAudit) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcProcessAuditService.update(sjkflcProcessAudit);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("撤回数据审核流程意见表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "撤回数据审核流程意见表列表出错!");
        }
        return resultPd;
    }


    /**
     * 删除数据
     *
     * @param demandCode 主键
     * @return 删除结果
     */
    @PostMapping(value = "/remove")
    @ApiOperation("删除数据")
    @ResponseBody
    public PageData remove(@RequestBody String demandCode) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcProcessAuditService.deleteById(demandCode);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("删除审核流程意见表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除审核流程意见表列表出错!");
        }
        return resultPd;
    }

}
