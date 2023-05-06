package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpDemandApplicationService;
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
 * 数据上传_需求提报环节信息表(SjkflcOdsSjmlTUpDemandApplication)表控制层
 *
 * @author sssJL
 * @since 2021-10-09 13:22:54
 */
@RestController
@RequestMapping("/sjkflcOdsSjmlTUpDemandApplication")
@Api(tags = {"数据上传_需求提报环节信息表"})
public class SjkflcOdsSjmlTUpDemandApplicationAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcOdsSjmlTUpDemandApplicationService sjkflcOdsSjmlTUpDemandApplicationService;

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
            SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication = sjkflcOdsSjmlTUpDemandApplicationService.queryById(demandCode);
            resultPd = PageData2Web.WebForObj(sjkflcOdsSjmlTUpDemandApplication);
        } catch (Exception e) {
            logger.error("查询数据上传_需求提报环节信息表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_需求提报环节信息表列表出错");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    @ResponseBody
    public PageData list(@RequestBody SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        PageData resultPd = new PageData();
        try {
            List<SjkflcOdsSjmlTUpDemandApplication> list = sjkflcOdsSjmlTUpDemandApplicationService.queryList(sjkflcOdsSjmlTUpDemandApplication);
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询数据上传_需求提报环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_需求提报环节信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPage(@RequestBody SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        PageData resultPd = new PageData();
        try {
            int pageIndex = sjkflcOdsSjmlTUpDemandApplication.getPageIndex();
            int pageSize = sjkflcOdsSjmlTUpDemandApplication.getPageSize();
            PageInfo<SjkflcOdsSjmlTUpDemandApplication> pageInfo = sjkflcOdsSjmlTUpDemandApplicationService.queryListByPage(sjkflcOdsSjmlTUpDemandApplication, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询数据上传_需求提报环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_需求提报环节信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public PageData create(@RequestBody SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcOdsSjmlTUpDemandApplicationService.insert(sjkflcOdsSjmlTUpDemandApplication);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("新增数据上传_需求提报环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增数据上传_需求提报环节信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    @ResponseBody
    public PageData modify(@RequestBody SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcOdsSjmlTUpDemandApplicationService.update(sjkflcOdsSjmlTUpDemandApplication);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("修改数据上传_需求提报环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改数据上传_需求提报环节信息表列表出错!");
        }
        return resultPd;
    }


    /**
     * 上报数据
     *
     * @param sjkflcOdsSjmlTUpDemandApplication 实例对象
     * @return 上报数据结果
     */
    @PostMapping(value = "/report")
    @ApiOperation("上传数据")
    @ResponseBody
    public PageData report(@RequestBody SjkflcOdsSjmlTUpDemandApplication sjkflcOdsSjmlTUpDemandApplication) {
        PageData resultPd = new PageData();
        boolean flag = true;
        try {
            List<SjkflcOdsSjmlTUpDemandApplication> list = sjkflcOdsSjmlTUpDemandApplicationService.queryList(sjkflcOdsSjmlTUpDemandApplication);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    SjkflcOdsSjmlTUpDemandApplication qModel = new SjkflcOdsSjmlTUpDemandApplication();
                    qModel.setOrgCode(sjkflcOdsSjmlTUpDemandApplication.getOrgCode());
                    qModel.setDemandCode(sjkflcOdsSjmlTUpDemandApplication.getDemandCode());
                    List<SjkflcOdsSjmlTUpDemandApplication> cklist = sjkflcOdsSjmlTUpDemandApplicationService.queryListForApi(qModel);
                    //UEP调用插入上传数据的接口
                    SjkflcOdsSjmlTUpDemandApplication model = list.get(i);
                    model.setBatchTime(DateUtils.getNowDate());
                    if (cklist != null && cklist.size()>0) {
                        sjkflcOdsSjmlTUpDemandApplicationService.updateForApi(model);
                    } else {
                        sjkflcOdsSjmlTUpDemandApplicationService.insertForApi(model);

                    }
                    sjkflcOdsSjmlTUpDemandApplicationService.update(model);
                }
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            logger.error("上传数据_数据上传_需求提报环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "上传数据_数据上传_需求提报环节信息表列表出错!");
        }
        resultPd = PageData2Web.WebForBoolean(flag);
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
            boolean flag = sjkflcOdsSjmlTUpDemandApplicationService.deleteById(demandCode);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("删除数据上传_需求提报环节信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除数据上传_需求提报环节信息表列表出错!");
        }
        return resultPd;
    }

}
