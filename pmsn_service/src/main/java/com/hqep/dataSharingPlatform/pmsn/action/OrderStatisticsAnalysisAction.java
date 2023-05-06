package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.pmsn.service.OrderStatisticsAnalysisService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/OrderStatisticsAnalysis")
public class OrderStatisticsAnalysisAction {
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Autowired
    OrderStatisticsAnalysisService orderStatisticsAnalysisService;


    /**
     * 全量工单统计分析柱状图的数据查询
     */

    /**
     * @方法名: queryBarData
     * @功能描述: 全量工单统计分析 柱状图的数据查询
     * @作者信息： sssJL
     * @创建时间: 9:13 2022/3/2
     **/
    @ApiOperation(value = "全量工单统计分析柱状图的数据查询", notes = "全量工单统计分析柱状图的数据查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/queryBarData", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody ResultBodyBean queryBarData(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            List<PageData> list =  orderStatisticsAnalysisService.queryBarData(pd);
            resultBodyBean.setData(list);
            status = HttpStatus.OK.value();
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("全量工单统计分析柱状图的数据查询");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }


    /**
     * @方法名: queryTableListData
     * @功能描述: 全量工单统计分析表格的数据查询
     * @作者信息： sssJL
     * @创建时间: 9:13 2022/3/2
     **/
    @ApiOperation(value = "全量工单统计分析表格的数据查询", notes = "全量工单统计分析表格的数据查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/queryTableListData", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody ResultBodyBean queryTableListData(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
            List<PageData> list =  orderStatisticsAnalysisService.queryTableListData(pd);
            resultPd.put("list",list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
            resultBodyBean.setData(resultPd);
            status = HttpStatus.OK.value();
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("全量工单统计分析表格的数据查询");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }

    /**
     * @方法名: queryTableListDataForDetails
     * @功能描述: 全量工单统计分析表格的详情查询
     * @作者信息： sssJL
     * @创建时间: 9:13 2022/3/2
     **/
    @ApiOperation(value = "全量工单统计分析表格的详情查询", notes = "全量工单统计分析表格的详情查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/queryTableListDataForDetails", method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody ResultBodyBean queryTableListDataForDetails(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        PageData resultPd = new PageData();
        try {
            List<PageData> list =  orderStatisticsAnalysisService.queryTableListDataForDetails(pd);
            resultPd.put("list",list);
            resultPd.put("error", null);
            resultBodyBean.setData(resultPd);
            status = HttpStatus.OK.value();
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("全量工单统计分析表格的详情查询");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }
}
