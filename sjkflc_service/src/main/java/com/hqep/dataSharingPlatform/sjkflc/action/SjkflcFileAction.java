package com.hqep.dataSharingPlatform.sjkflc.action;

import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcFile;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (SjkflcFile)表控制层
 *
 * @author liuzg
 * @since 2021-09-29 11:21:40
 */
@RestController
@RequestMapping("/sjkflcFile")
@Api(tags = {"数据开放流程-文件管理"})
public class SjkflcFileAction {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 服务对象
     */
    @Autowired
    private SjkflcFileService sjkflcFileService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "/queryById")
    @ApiOperation("通过主键查询单条数据")
    public PageData queryById(String id) {
        PageData result = new PageData();
        try {
            SjkflcFile sjkflcFile = sjkflcFileService.queryById(id);
            result.put("data", sjkflcFile);
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param sjkflcFile 实例对象
     * @return 对象列表
     */
    @PostMapping(value = "/list")
    @ApiOperation("通过实体作为筛选条件查询")
    public PageData list(@RequestBody SjkflcFile sjkflcFile) {
        PageData result = new PageData();
        try {
            if (sjkflcFile.getCode() == null || "".equals(sjkflcFile.getCode() )) {
                sjkflcFile.setCode("code值为空，不允许查询！");
            }
            List<SjkflcFile> list = sjkflcFileService.queryList(sjkflcFile);
            result.put("data", list);
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 通过实体作为筛选条件查询 - 分页
     *
     * @param sjkflcFile 实例对象
     * @param pageNum    第几页
     * @param pageSize   每页多少条
     * @return 对象列表
     */
    @PostMapping(value = "/listByPage")
    @ApiOperation("通过实体作为筛选条件查询 - 分页")
    public PageInfo<SjkflcFile> listByPage(SjkflcFile sjkflcFile,
                                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        return sjkflcFileService.queryListByPage(sjkflcFile, pageNum, pageSize);
    }

    /**
     * 新增数据
     *
     * @param sjkflcFile 实例对象
     * @return 新增结果
     */
    @PostMapping(value = "/create")
    @ApiOperation("新增数据")
    public PageData create(SjkflcFile sjkflcFile) {
        PageData result = new PageData();
        try {
            boolean flag = sjkflcFileService.insert(sjkflcFile);
            result.put("data", flag);
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 修改数据
     *
     * @param sjkflcFile 实例对象
     * @return 修改结果
     */
    @PostMapping(value = "/modify")
    @ApiOperation("修改数据")
    public PageData modify(SjkflcFile sjkflcFile) {
        PageData result = new PageData();
        try {
            boolean flag = sjkflcFileService.update(sjkflcFile);
            result.put("data", flag);
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @PostMapping(value = "/remove")
    @ApiOperation("删除数据")
    public PageData remove(String id) {
        PageData result = new PageData();
        try {
            boolean flag = sjkflcFileService.deleteById(id);
            result.put("data", flag);
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 上传附件
     *
     * @param files
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/fileUpload")
    public PageData fileUpload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        PageData result = new PageData();
        try {
            Map<String, String> data = new HashMap<>();
            data.put("code", request.getParameter("code"));
            data.put("remark", request.getParameter("remark"));
            data.put("createUser", request.getParameter("createUser"));
            data.put("createTime", request.getParameter("createTime"));
            List<SjkflcFile> list = sjkflcFileService.fileUpload(files, data);
            result.put("data", list);
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping("/fileDelete")
    public PageData fileDelete(@RequestBody PageData pd) {
        PageData result = new PageData();
        try {
            String id = pd.getString("id");
            String filePath = pd.getString("filePath");
            boolean b = sjkflcFileService.fileDelete(id);
            if (b) {
                result.put("data", new ArrayList<>());
                result.put("error", null);
            } else {
                result.put("data", new ArrayList<>());
                result.put("error", "删除失败");
            }
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 下载附件
     *
     * @param pd
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/fileDownload")
    public PageData fileDownload(@RequestBody PageData pd, HttpServletResponse response) {
        PageData result = new PageData();
        try {
            String id = pd.getString("id");
            sjkflcFileService.fileDownload(id, response);
            result.put("data", new ArrayList<>());
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }

    /**
     * 下载压缩包
     *
     * @param pd
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/zipDownload")
    public PageData zipDownload(@RequestBody PageData pd, HttpServletResponse response) {
        PageData result = new PageData();
        try {
            String id = pd.getString("id");
            String[] ids = id.split(",");
            sjkflcFileService.zipDownload(ids, response);
            result.put("data", new ArrayList<>());
            result.put("error", null);
        } catch (Exception e) {
            logger.error("接口异常", e);
            result.put("data", new ArrayList<>());
            result.put("error", "接口异常");
        }
        return result;
    }
}
