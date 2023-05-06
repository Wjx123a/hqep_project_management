package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcDictionary;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcDictionaryService;
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
 * 数据字典表(SjkflcDictionary)表控制层
 *
 * @author sssJL
 * @since 2021-10-13 15:34:06
 */
@RestController
@RequestMapping("/sjkflcDictionary")
@Api(tags = {"数据字典表"})
public class SjkflcDictionaryAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcDictionaryService sjkflcDictionaryService;

    /**
     * 通过主键查询单条数据
     *
     * @param dictId 主键
     * @return 单条数据
     */
    @PostMapping(value = "/queryById")
    @ApiOperation("通过主键查询单条数据")
    @ResponseBody
    public PageData queryById(@RequestBody String dictId) {
        PageData resultPd = new PageData();
        try {
            SjkflcDictionary sjkflcDictionary = sjkflcDictionaryService.queryById(dictId);
            resultPd = PageData2Web.WebForObj(sjkflcDictionary);
        } catch (Exception e) {
            logger.error("查询数据字典表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据字典表列表出错");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcDictionary 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    @ResponseBody
    public PageData list(@RequestBody SjkflcDictionary sjkflcDictionary) {
        PageData resultPd = new PageData();
        try {
            List<SjkflcDictionary> list = sjkflcDictionaryService.queryList(sjkflcDictionary);
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询数据字典表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据字典表列表出错!");
        }
        return resultPd;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcDictionary 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    @ResponseBody
    public PageData listByPage(@RequestBody SjkflcDictionary sjkflcDictionary) {
        PageData resultPd = new PageData();
        try {
            int pageIndex = sjkflcDictionary.getPageIndex();
            int pageSize = sjkflcDictionary.getPageSize();
            PageInfo<SjkflcDictionary> pageInfo = sjkflcDictionaryService.queryListByPage(sjkflcDictionary, pageIndex, pageSize);
            resultPd = PageData2Web.WebForPageInfo(pageInfo);
        } catch (Exception e) {
            logger.error("查询数据字典表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据字典表列表出错!");
        }
        return resultPd;
    }

    /**
     * 新增数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public PageData create(@RequestBody SjkflcDictionary sjkflcDictionary) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcDictionaryService.insert(sjkflcDictionary);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("新增数据字典表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增数据字典表列表出错!");
        }
        return resultPd;
    }

    /**
     * 修改数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    @ResponseBody
    public PageData modify(@RequestBody SjkflcDictionary sjkflcDictionary) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcDictionaryService.update(sjkflcDictionary);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("修改数据字典表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改数据字典表列表出错!");
        }
        return resultPd;
    }


    /**
     * 上报或者撤回数据
     *
     * @param sjkflcDictionary 实例对象
     * @return 上报或者撤回数据结果
     */
    @PostMapping(value = "/back")
    @ApiOperation("撤回数据")
    @ResponseBody
    public PageData back(@RequestBody SjkflcDictionary sjkflcDictionary) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcDictionaryService.update(sjkflcDictionary);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("撤回数据数据字典表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "撤回数据数据字典表列表出错!");
        }
        return resultPd;
    }


    /**
     * 删除数据
     *
     * @param dictId 主键
     * @return 删除结果
     */
    @PostMapping(value = "/remove")
    @ApiOperation("删除数据")
    @ResponseBody
    public PageData remove(@RequestBody String dictId) {
        PageData resultPd = new PageData();
        try {
            boolean flag = sjkflcDictionaryService.deleteById(dictId);
            resultPd = PageData2Web.WebForBoolean(flag);
        } catch (Exception e) {
            logger.error("删除数据字典表列表出错!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "删除数据字典表列表出错!");
        }
        return resultPd;
    }


    /**
     * 查询需求承接方部门名称/数据产生部门名称
     * @return
     */
    @PostMapping(value = "/queryDeptList")
    @ApiOperation("查询需求承接方部门名称/数据产生部门名称")
    @ResponseBody
    public PageData queryDeptList() {
        PageData resultPd = new PageData();
        try {
            List<Map> list = sjkflcDictionaryService.queryDeptList();
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询需求承接方部门名称/数据产生部门名称!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询需求承接方部门名称/数据产生部门名称出错!");
        }
        return resultPd;
    }
    /**
     * 查询需求系统名称/系统编码
     * @return
     */
    @PostMapping(value = "/querySysList")
    @ApiOperation("查询需求系统名称/系统编码")
    @ResponseBody
    public PageData querySysList() {
        PageData resultPd = new PageData();
        try {
            List<Map> list = sjkflcDictionaryService.querySysList();
            resultPd = PageData2Web.WebForList(list);
        } catch (Exception e) {
            logger.error("查询需求系统名称/系统编码!", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询需求系统名称/系统编码出错!");
        }
        return resultPd;
    }


}
