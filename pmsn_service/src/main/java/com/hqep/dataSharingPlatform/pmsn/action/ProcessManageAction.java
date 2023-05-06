package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.pmsn.service.ProcessManageService;
import com.hqep.dataSharingPlatform.common.utils.PageData;
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
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/processManage")
@RestController
@Api(description = "流程管理、节点管理")
public class ProcessManageAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private ProcessManageService service;


    @ApiOperation(value = "查询流程信息", notes = "查询流程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryProcessList", method = RequestMethod.POST)
    public PageData queryProcessList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryProcessList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询流程信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询流程信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "查询流程节点信息", notes = "查询流程节点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryProcessNodeList", method = RequestMethod.POST)
    public PageData queryProcessNodeList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryProcessNodeList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询流程节点信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询流程节点信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "修改流程信息", notes = "修改流程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/updateProcess", method = RequestMethod.POST)
    public PageData updateProcess(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.updateProcess(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("修改流程信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改流程信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "修改流程节点信息", notes = "修改流程节点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/updateProcessNode", method = RequestMethod.POST)
    public PageData updateProcessNode(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.updateProcessNode(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("修改流程节点信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改流程节点信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "修改流程节点排序", notes = "修改流程节点排序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/updateProcessNodeJdpx", method = RequestMethod.POST)
    public PageData updateProcessNodeJdpx(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.updateProcessNodeJdpx(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("修改流程节点排序出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改流程节点排序出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "新增流程信息", notes = "新增流程信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/insertProcess", method = RequestMethod.POST)
    public PageData insertProcess(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.insertProcess(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("新增流程信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增流程信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "新增流程节点信息", notes = "新增流程节点信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/insertProcessNode", method = RequestMethod.POST)
    public PageData insertProcessNode(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.insertProcessNode(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("新增流程节点信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增流程节点信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "判断流程编码是否存在", notes = "判断流程编码是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryCount", method = RequestMethod.POST)
    public int queryCount(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        int count = 0;
        try {
            count = service.queryCount(pd);
            resultPd.put("data", count);
        } catch (Exception e) {
            logger.error("判断流程编码是否存在出错", e);
            resultPd.put("data", -1);
            resultPd.put("error", "判断流程编码是否存在出错");
        }
        return count;
    }

    @ApiOperation(value = "判断相同流程下的流程节点是否存在", notes = "判断相同流程下的流程节点是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryCountLcjd", method = RequestMethod.POST)
    public int queryCountLcjd(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        int count = 0;
        try {
            count = service.queryCountLcjd(pd);
            resultPd.put("data", count);
        } catch (Exception e) {
            logger.error("判断相同流程下的流程节点是否存在出错", e);
            resultPd.put("data", -1);
            resultPd.put("error", "判断相同流程下的流程节点是否存在出错");
        }
        return count;
    }

    @ApiOperation(value = "判断是否只有一条主流程或者子流程在启用", notes = "判断是否只有一条主流程或者子流程在启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryProcessStatus", method = RequestMethod.POST)
    public int queryProcessStatus(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        int count = 0;
        try {
            if ("1".equals(pd.get("lclx"))) {
                resultPd.put("data", 0);
            } else {
                count = service.queryProcessStatus(pd);
                resultPd.put("data", count);
            }
        } catch (Exception e) {
            logger.error("判断是否只有一条主流程或者子流程在启用出错", e);
            resultPd.put("data", -1);
            resultPd.put("error", "判断是否只有一条主流程或者子流程在启用出错");
        }
        return count;
    }

    @ApiOperation(value = "查询当前启用子流程的节点编码", notes = "查询当前启用子流程的节点编码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryEnableChildProcessNodeList", method = RequestMethod.POST)
    public PageData queryEnableChildProcessNodeList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryEnableChildProcessNodeList(pd);
            resultPd.put("data", list);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询当前启用子流程的节点编码出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询当前启用子流程的节点编码出错");
        }
        return resultPd;
    }

}
