package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.QueryGdService;
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

@RequestMapping("/queryGd")
@RestController
@Api(description = "工单查询")
public class QueryGdAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private QueryGdService service;

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

    @ApiOperation(value = "查询一条工单信息", notes = "查询一条工单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/querySpGd", method = RequestMethod.POST)
    public PageData querySpGd(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {

            List<PageData> list = service.querySpGd(pd);
            resultPd.put("data", list);
            //int total = list.size();
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询一条工单信息失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询一条工单信息失败");
        }
        return resultPd;
    }
    @ApiOperation(value = "全量工单查询", notes = "全量工单查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryGdCount", method = RequestMethod.POST)
    public PageData queryParmList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryGdCount(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            //int total = list.size();
            int total = service.queryGdCountCount(pd);
            paginationPd.put("total", total);
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询全量工单表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询全量工单表列表出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "查询一条工单信息", notes = "查询一条工单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryrowClickOne", method = RequestMethod.POST)
    public PageData queryrowClickOne(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryrowClickOne(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            //int total = list.size();
            int total = service.queryrowClickOneCount(pd);
            paginationPd.put("total", total);
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询一条工单信息失败", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询一条工单信息失败");
        }
        return resultPd;
    }

}
