package com.hqep.dataSharingPlatform.pmsn.action;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.AllTableService;
import com.hqep.dataSharingPlatform.pmsn.service.JcfwService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RequestMapping("/jcfw")
@RestController
@Api(description = "数据服务")
public class JcfwAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected JcfwService service;

    // 根据系统查询表信息-详情（ 数据目录=》二级系统 ）
    @ApiOperation(value = "查询共享数据表信息-详情", notes = "查询共享数据表信息-详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryJcfw", method = RequestMethod.POST)
    public PageData queryJcfw(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryJcfw(pd);
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            PageData paginationPd = new PageData();
            Page page = PageHelper.startPage(index, size);
            paginationPd.put("total", page.getTotal()); // 查询数据量小的时候注释
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
            resultPd.put("data", list);

        } catch (Exception e) {
            logger.error("查询菜单列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询菜单表列表出错");
        }
        return resultPd;

    }

    @ApiOperation(value = "查询共享数据表信息-详情", notes = "查询共享数据表信息-详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryJcfwmx", method = RequestMethod.POST)
    public PageData queryJcfwmx(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryJcfwmx(pd);
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            PageData paginationPd = new PageData();
            Page page = PageHelper.startPage(index, size);
            paginationPd.put("total", page.getTotal()); // 查询数据量小的时候注释
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
            resultPd.put("data", list);

        } catch (Exception e) {
            logger.error("查询菜单列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询菜单表列表出错");
        }
        return resultPd;

    }

}
