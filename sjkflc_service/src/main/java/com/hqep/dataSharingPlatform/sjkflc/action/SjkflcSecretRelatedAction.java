package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcSecretRelated;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcSecretRelatedService;
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
 * 涉密数据判定表(SjkflcSecretRelated)表控制层
 *
 * @author sssJL
 * @since 2021-10-27 15:06:52
 */
@RestController
@RequestMapping("/sjkflcSecretRelated")
@Api(tags = {"涉密数据判定表"})
public class SjkflcSecretRelatedAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcSecretRelatedService sjkflcSecretRelatedService;

    /**
     * 通过主键查询单条数据
     *
     * @param model 主键信息
     * @return 单条数据
     */
    @PostMapping(value = "/queryById")
    @ApiOperation("通过主键查询单条数据")
    @ResponseBody
    public PageData queryById(@RequestBody SjkflcSecretRelated model) {
        PageData resultPd = new PageData();
        try {
            String id = model.getId();
            SjkflcSecretRelated sjkflcSecretRelated = sjkflcSecretRelatedService.queryById(id);
            resultPd = PageData2Web.WebForObj(sjkflcSecretRelated);
        } catch (Exception e) {
            logger.error("查询涉密数据判定表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询涉密数据判定表列表出错");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    @ResponseBody
    public PageData list(@RequestBody SjkflcSecretRelated sjkflcSecretRelated) {
        PageData resultPd = new PageData();
        try {
            List<SjkflcSecretRelated> list = sjkflcSecretRelatedService.queryList(sjkflcSecretRelated);
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询涉密数据判定表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询涉密数据判定表列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPage(@RequestBody SjkflcSecretRelated sjkflcSecretRelated) {
        PageData resultPd = new PageData();
        try {
            int pageIndex = sjkflcSecretRelated.getPageIndex();
            int pageSize = sjkflcSecretRelated.getPageSize();
            PageInfo<SjkflcSecretRelated> pageInfo = sjkflcSecretRelatedService.queryListByPage(sjkflcSecretRelated, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询涉密数据判定表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询涉密数据判定表列表出错!");
        }
        return resultPd;
    }

    /**
     * 新增数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public PageData create(@RequestBody SjkflcSecretRelated sjkflcSecretRelated) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcSecretRelatedService.insert(sjkflcSecretRelated);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("新增涉密数据判定表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增涉密数据判定表列表出错!");
        }
        return resultPd;
    }

    /**
     * 修改数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    @ResponseBody
    public PageData modify(@RequestBody SjkflcSecretRelated sjkflcSecretRelated) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcSecretRelatedService.update(sjkflcSecretRelated);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("修改涉密数据判定表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改涉密数据判定表列表出错!");
        }
        return resultPd;
    }


    /**
     * 上报或者撤回数据
     *
     * @param sjkflcSecretRelated 实例对象
     * @return 上报或者撤回数据结果
     */
    @PostMapping(value = "/back")
    @ApiOperation("撤回数据")
    @ResponseBody
    public PageData back(@RequestBody SjkflcSecretRelated sjkflcSecretRelated) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcSecretRelatedService.update(sjkflcSecretRelated);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("撤回数据涉密数据判定表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "撤回数据涉密数据判定表列表出错!");
        }
        return resultPd;
    }


    /**
     * 删除数据
     *
     * @param model 主键信息
     * @return 删除结果
     */
    @PostMapping(value = "/remove")
    @ApiOperation("删除数据")
    @ResponseBody
    public PageData remove(@RequestBody SjkflcSecretRelated model) {
        PageData resultPd = new PageData();
        try {
            String id = model.getId();
            boolean flag = sjkflcSecretRelatedService.deleteById(id);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("删除涉密数据判定表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除涉密数据判定表列表出错!");
        }
        return resultPd;
    }

}
