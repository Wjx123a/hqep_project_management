package com.hqep.dataSharingPlatform.pmsn.action;


import com.alibaba.fastjson.JSON;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.pmsn.service.LoginService;
import com.hqep.dataSharingPlatform.pmsn.service.NlkfRestfulApiService;
import com.hqep.dataSharingPlatform.pmsn.service.PersonService;
import com.hqep.dataSharingPlatform.pmsn.service.SysMenuService;
import com.hqep.dataSharingPlatform.pmsn.unit.ExpUnits;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOutDemandApplication;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hqep.dataSharingPlatform.common.utils.ExcelUtil.downloadExcel;

/**
 * @类名: SjglyyybAction
 * @功能描述 数据管理运营月报数据
 * @作者信息 sssJL
 * @创建时间 2021/12/14
 */
@RequestMapping("/sjglyyyb")
@RestController
@Api(description = "数据管理运营月报数据")
public class SjglyyybAction {


    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @Autowired
    protected NlkfRestfulApiService nlkfRestfulApiService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @方法名:getSjglyyybList
     * @功能描述:获取数据管理运营月报数据
     * @作者信息：sssJL
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "获取数据管理运营月报数据", notes = "获取数据管理运营月报数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/getSjglyyybList", method = {RequestMethod.POST,RequestMethod.GET})
//    @RequestLog("获取数据管理运营月报数据")
    public @ResponseBody
    ResultBodyBean getSjglyyybList(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                List<PageData> list = nlkfRestfulApiService.getSjglyyybList(pd);
                resultBodyBean.setData(list);
                status = HttpStatus.OK.value();
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("获取数据管理运营月报数据");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }




    @ApiOperation(value = "导出" , notes = "导出")
    @RequestMapping("/exportExcel")
    public @ResponseBody void exportExcel (@RequestBody PageData pd) {
        try {
            List<PageData> list = nlkfRestfulApiService.getSjglyyybList(pd);
            String sourcePath = SjglyyybAction.class.getResource("/").getFile().toString();
            String templatePath = sourcePath + "template/数据管理运营月报模板.xlsx";
            //String str = "D:/template%208.5/数据管理运营月报模板.xlsx";
            templatePath = URLDecoder.decode(templatePath,"utf-8");

            String exportPath = sourcePath + "excelDownload/";
            String fileName = "数据管理运营月报模板.xlsx";
//            // 同一个sheet页中第一个表格
            String[] fieldsNames = {
                    "tjny",
                    "byfzymlsyl",
                    "byfyhs",
                    "zymlyhs",
                    "tbzzl",
                    "rmjsxt1",
                    "srl1",
                    "rmjsxt2",
                    "srl2",
                    "rmjsxt3",
                    "srl3",
                    "rmjsxt4",
                    "srl4",
                    "rmjsxt5",
                    "srl5",
                    "rmjsxt6",
                    "srl6",
                    "rmjsxt7",
                    "srl7",
                    "rmjsxt8",
                    "srl8",
                    "rmjsxt9",
                    "srl9",
                    "rmjsxt10",
                    "srl10",
                    "rpjsch1",
                    "rcjxcs1",
                    "rpjsch2",
                    "rcjxcs2",
                    "rpjsch3",
                    "rcjxcs3",
                    "rpjsch4",
                    "rcjxcs4",
                    "rpjsch5",
                    "rcjxcs5",
                    "rpjsch6",
                    "rcjxcs6",
                    "rpjsch7",
                    "rcjxcs7",
                    "rpjsch8",
                    "rcjxcs8",
                    "rpjsch9",
                    "rcjxcs9",
                    "rpjsch10",
                    "rcjxcs10",
                    "gdslsl",
                    "sqsjbsl",
                    "sqjzgdsl",
                    "gdbjsl",
                    "gdbjl",
                    "sjfmqdslgdsl",
                    "sjfmqdbjgdsl",
                    "sjfmqdbjl",
                    "gdqlcpjsc",
                    "gdqlcpjschb",
                    "fmqdgdqlcpjsc",
                    "ffmqdgdqlcpjsc",
                    "gdtbslzdbm",
                    "gdtbslzdbmgdsl",
                    "xqslzscpjsc",
                    "ywshhjzscpjsc",
                    "sqpzhjzscpjsc",
                    "tableSystem1",
                    "sqbzwbm1",
                    "tableName1",
                    "xqcs1",
                    "tableSystem2",
                    "sqbzwbm2",
                    "tableName2",
                    "xqcs2",
                    "tableSystem3",
                    "sqbzwbm3",
                    "tableName3",
                    "xqcs3",
                    "bz",
                    "tjr",
                    "lxdh"
            };
            if (list.size() > 0) {
                // 同一个sheet第一个表
                Map<String,List<PageData>> dataEMap = new HashMap();
                Map<String, String[]> fieldsMap = new HashMap();
                Map<String, List<Integer>> numMap = new HashMap();
                Map<String, Integer> wrNumMap = new HashMap();
                Map<String, Integer> wcNumMap = new HashMap();
                Map<String, Boolean> existIndexMap = new HashMap();
                Map<String, Boolean> shiftRowsMap = new HashMap<>();
                dataEMap.put("数据管理运营月报数据填报",list);
                fieldsMap.put("数据管理运营月报数据填报",fieldsNames);
                wrNumMap.put("数据管理运营月报数据填报",2);
                wcNumMap.put("数据管理运营月报数据填报",35);
                existIndexMap.put("数据管理运营月报数据填报",false);
                shiftRowsMap.put("数据管理运营月报数据填报",true);
                ExpUnits.expExcelForMapList(response,templatePath,fileName,dataEMap,fieldsMap,wrNumMap,wcNumMap,existIndexMap,numMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
