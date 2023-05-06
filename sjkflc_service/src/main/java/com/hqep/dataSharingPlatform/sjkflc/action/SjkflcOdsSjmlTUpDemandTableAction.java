package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandTable;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpDemandTableService;
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
 * 数据上传_需求数据表信息表(SjkflcOdsSjmlTUpDemandTable)表控制层
 *
 * @author sssJL
 * @since 2021-10-11 10:13:28
 */
@RestController
@RequestMapping("/sjkflcOdsSjmlTUpDemandTable")
@Api(tags = {"数据上传_需求数据表信息表"})
public class SjkflcOdsSjmlTUpDemandTableAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcOdsSjmlTUpDemandTableService sjkflcOdsSjmlTUpDemandTableService;

    /**
     * 通过主键查询单条数据
     *
     * @param pd 主键
     * @return 单条数据
     */
    @PostMapping(value = "/queryById")
    @ApiOperation("通过主键查询单条数据")
    @ResponseBody
    public PageData queryById(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            String id = pd.getString("id");
            SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable = sjkflcOdsSjmlTUpDemandTableService.queryById(id);
            resultPd = PageData2Web.WebForObj(sjkflcOdsSjmlTUpDemandTable);
        } catch (Exception e) {
            logger.error("查询数据上传_需求数据表信息表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_需求数据表信息表列表出错");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    @ResponseBody
    public PageData list(@RequestBody SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        PageData resultPd = new PageData();
        try {
            List<SjkflcOdsSjmlTUpDemandTable> list = sjkflcOdsSjmlTUpDemandTableService.queryList(sjkflcOdsSjmlTUpDemandTable);
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询数据上传_需求数据表信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_需求数据表信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPage(@RequestBody SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        PageData resultPd = new PageData();
        try {
            int pageIndex = sjkflcOdsSjmlTUpDemandTable.getPageIndex();
            int pageSize = sjkflcOdsSjmlTUpDemandTable.getPageSize();
            PageInfo<SjkflcOdsSjmlTUpDemandTable> pageInfo = sjkflcOdsSjmlTUpDemandTableService.queryListByPage(sjkflcOdsSjmlTUpDemandTable, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询数据上传_需求数据表信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据上传_需求数据表信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 新增数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public PageData create(@RequestBody SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        PageData resultPd = new PageData();
        try {
            SjkflcOdsSjmlTUpDemandTable qModel = new SjkflcOdsSjmlTUpDemandTable();
            qModel.setDemandCode(sjkflcOdsSjmlTUpDemandTable.getDemandCode());
            List<SjkflcOdsSjmlTUpDemandTable> list = sjkflcOdsSjmlTUpDemandTableService.queryList(qModel);
            if(list !=null && list.size()>0) {
                resultPd = PageData2Web.WebForBooleanObj(false,"该需求编码已经存在！");
            } else {
                boolean flag = sjkflcOdsSjmlTUpDemandTableService.insert(sjkflcOdsSjmlTUpDemandTable);
                resultPd = PageData2Web.WebForBoolean(flag);
            }
        } catch (Exception e) {
            logger.error("新增数据上传_需求数据表信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增数据上传_需求数据表信息表列表出错!");
        }
        return resultPd;
    }

    /**
     * 修改数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    @ResponseBody
    public PageData modify(@RequestBody SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        PageData resultPd = new PageData();
        try {
            SjkflcOdsSjmlTUpDemandTable qModel = new SjkflcOdsSjmlTUpDemandTable();
            qModel.setDemandCode(sjkflcOdsSjmlTUpDemandTable.getDemandCode());
            List<SjkflcOdsSjmlTUpDemandTable> list = sjkflcOdsSjmlTUpDemandTableService.queryList(qModel);
            if(list !=null && list.size()>0) {
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getId().equals(sjkflcOdsSjmlTUpDemandTable.getId())) {
                        resultPd = PageData2Web.WebForBooleanObj(false,"该需求编码已经存在！");
                        return resultPd;
                    }
                }
            }
            sjkflcOdsSjmlTUpDemandTable.setModifyType("2");
            boolean flag = sjkflcOdsSjmlTUpDemandTableService.update(sjkflcOdsSjmlTUpDemandTable);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("修改数据上传_需求数据表信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改数据上传_需求数据表信息表列表出错!");
        }
        return resultPd;
    }


    /**
     * 上传数据
     *
     * @param sjkflcOdsSjmlTUpDemandTable 实例对象
     * @return 上传数据结果
     */
    @PostMapping(value = "/report")
    @ApiOperation("上传数据")
    @ResponseBody
    public PageData report(@RequestBody SjkflcOdsSjmlTUpDemandTable sjkflcOdsSjmlTUpDemandTable) {
        PageData resultPd = new PageData();
        boolean flag = false;
        try {
            String id = sjkflcOdsSjmlTUpDemandTable.getId();
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
                flag = reportOne(sjkflcOdsSjmlTUpDemandTable.getId());
            }
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("上传数据_数据上传_需求数据表信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "上传数据_数据上传_需求数据表信息表列表出错!");
        }
        return resultPd;
    }

    public boolean reportOne(String id){
        SjkflcOdsSjmlTUpDemandTable sbModel = sjkflcOdsSjmlTUpDemandTableService.queryById(id);
        sbModel.setBatchTime(DateUtils.getNowDate());
        // 查询第二数据源是否有数据
        SjkflcOdsSjmlTUpDemandTable qModel = new SjkflcOdsSjmlTUpDemandTable();
        qModel.setId(id);
        List<SjkflcOdsSjmlTUpDemandTable> ckList = sjkflcOdsSjmlTUpDemandTableService.queryListForApi(qModel);
        if (ckList!=null && ckList.size()>0) {
            // 更新第二数据源
            sjkflcOdsSjmlTUpDemandTableService.updateForApi(sbModel);
        } else {
            // 插入第二数据源
            sjkflcOdsSjmlTUpDemandTableService.insertForApi(sbModel);
        }
        // 更新本地上报标识（批次日期）
        qModel.setBatchTime(sbModel.getBatchTime());
        boolean flag = sjkflcOdsSjmlTUpDemandTableService.update(qModel);
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
            String id = pd.getString("id");
            boolean flag = sjkflcOdsSjmlTUpDemandTableService.deleteById(id);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("删除数据上传_需求数据表信息表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除数据上传_需求数据表信息表列表出错!");
        }
        return resultPd;
    }

}
