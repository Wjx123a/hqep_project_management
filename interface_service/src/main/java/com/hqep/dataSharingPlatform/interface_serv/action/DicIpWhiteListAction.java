package com.hqep.dataSharingPlatform.interface_serv.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.model.DicIpWhiteList;
import com.hqep.dataSharingPlatform.common.vo.JsonResult;
import com.hqep.dataSharingPlatform.interface_serv.service.DicIpWhiteListService;
import com.hqep.dataSharingPlatform.common.exception.CustomException;
import com.hqep.dataSharingPlatform.common.utils.JsonMsg;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import io.swagger.annotations.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
/**
 * (DicIpWhiteList)表控制层
 *
 * @author 邵文强
 * @since 2021-03-30 08:39:58
 */
@RestController
@RequestMapping("/dicIpWhiteList")
@Api(description = "")
public class DicIpWhiteListAction {
Logger logger = LogManager.getLogger(DicIpWhiteListAction.class);
    /**
     * 服务对象
     */
    @Resource
    private DicIpWhiteListService dicIpWhiteListService;

    /**
     * 通过实体查询符合条件数据()
     *
     * @param model  实体类
     * @return 符合条件的实体列表
     */
    @ApiOperation(value = "通过实体查询符合条件数据", notes = "通过实体查询符合条件数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "model", dataType = "DicIpWhiteList", required = false, value = "客户端传入实体", defaultValue = "")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @Transactional
    @ResponseBody
    @PostMapping("/selectByModel")
//    @RequestLog("通过实体查询符合条件数据")
    public JsonResult selectByModel(@RequestBody DicIpWhiteList model) {
         logger.info("通过实体查询符合条件数据 Action层(selectByModel)");
         try {
             Page page = PageHelper.startPage(model.getPageNum(), model.getPageSize());
             List<DicIpWhiteList> list = this.dicIpWhiteListService.selectByModel(model);
             return JsonResult.success("查询成功",list);
         } catch (CustomException e){
             e.printStackTrace();
             logger.error("通过实体查询符合条件数据 Action层(selectByModel)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         } catch (Exception e) {
             e.printStackTrace();
             logger.error("通过实体查询符合条件数据 Action层(selectByModel)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         }
    }

    /**
     * 通过实体信息插入一条数据()
     *
     * @param model  实体类
     * @return {state:"success or error"}
     */
    @ApiOperation(value = "通过实体信息插入一条数据数据", notes = "通过实体信息插入一条数据数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "model", dataType = "DicIpWhiteList", required = false, value = "客户端传入实体", defaultValue = "")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @Transactional
    @ResponseBody
    @PostMapping("/insertOneModel")
//    @RequestLog("通过实体信息插入一条数据数据")
    public JsonResult insertOneModel(@RequestBody DicIpWhiteList model) {
         logger.info("通过实体信息插入一条数据 Action层(insertOneModel)");
         try {
             return this.dicIpWhiteListService.insertOneModel(model);
         } catch (CustomException e){
             e.printStackTrace();
             logger.error("通过实体信息插入一条数据 Action层(insertOneModel)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         } catch (Exception e) {
             e.printStackTrace();
             logger.error("通过实体信息插入一条数据 Action层(insertOneModel)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         }
    }


     /**
     * 通过实体信息更新一条数据()
     *
     * @param model  实体类
     * @return {state:"success or error"}
     */
    @ApiOperation(value = "通过实体信息更新一条数据数据", notes = "通过实体信息更新一条数据数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "model", dataType = "DicIpWhiteList", required = false, value = "客户端传入实体", defaultValue = "")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @Transactional
    @ResponseBody
    @PostMapping("/updateOneModel")
//    @RequestLog("通过实体信息更新一条数据数据")
    public JsonResult updateOneModel(@RequestBody DicIpWhiteList model) {
         logger.info("通过实体信息更新一条数据 Action层(updateOneModel)");
         try {
             return this.dicIpWhiteListService.updateOneModel(model);
         } catch (CustomException e){
             e.printStackTrace();
             logger.error("通过实体信息更新一条数据 Action层(updateOneModel)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         } catch (Exception e) {
             e.printStackTrace();
             logger.error("通过实体信息更新一条数据 Action层(updateOneModel)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         }
    }

    /**
     * 通过实体信息更新一条数据()
     *
     * @param model  实体类
     * @return {state:"success or error"}
     */
    @ApiOperation(value = "通过实体信息删除一条数据数据", notes = "通过实体信息删除一条数据数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "body", name = "model", dataType = "DicIpWhiteList", required = false, value = "客户端传入实体", defaultValue = "")
    })
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @Transactional
    @ResponseBody
    @PostMapping("/deleteById")
//    @RequestLog("通过实体信息删除一条数据数据")
    public JsonResult deleteById(@RequestBody DicIpWhiteList model) {
         logger.info("通过实体信息删除一条数据 Action层(deleteById)");
         try {
             return this.dicIpWhiteListService.deleteById(model.getId());
         } catch (CustomException e){
             e.printStackTrace();
             logger.error("通过实体信息删除一条数据 Action层(deleteById)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         } catch (Exception e) {
             e.printStackTrace();
             logger.error("通过实体信息删除一条数据 Action层(deleteById)出错");
             Writer writer = new StringWriter();
             e.printStackTrace(new PrintWriter(writer));
             logger.error("error:" + writer.toString());
             throw new CustomException(writer.toString());
         }
    }
}