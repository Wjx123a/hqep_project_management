package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.pmsn.service.FmqdService;
import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hqep.dataSharingPlatform.common.utils.ExcelUtil.downloadExcel;

@RequestMapping("/fmqd")
@RestController
@Api(description = "负面清单查询")
public class FmqdAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private FmqdService service;


    @ApiOperation(value = "负面清单页面查询条件系统名称", notes = "负面清单页面查询条件系统名称")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryParmSystemName", method = RequestMethod.POST)
    public PageData queryParmSystemName(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryParmSystemName(pd);
            resultPd.put("data", list);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("负面清单页面查询条件系统名称", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "负面清单页面查询条件系统名称");
        }
        return resultPd;
    }



    @ApiOperation(value = "负面清单查询列表", notes = "负面清单查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryParmList", method = RequestMethod.POST)
    public PageData queryParmList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryParmList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询负面清单列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询负面清单列表出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "负面清单查询列表-字段级信息", notes = "负面清单查询列表-字段级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryParmList_column", method = RequestMethod.POST)
    public PageData queryParmList_column(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryParmList_column(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询负面清单列表-字段级信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询负面清单列表-字段级信息出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "导出" , notes = "导出")
    @RequestMapping("/exportExcel")
    @RequestLog("导出负面清单")
    public @ResponseBody void exportExcel (@RequestBody PageData pd ) {
        try {
            int num = 0;
//            PageData pd = new PageData("xtmc",xtmc,"xtyh",xtyh,"sjbywmc",sjbywmc,"sjbzwmc",sjbzwmc,"zdywmc",zdywmc,"zdzwmc",zdzwmc);
            List<PageData> list = service.queryParmListForExcel(pd);
            if (list.size() > 0) {
                // 表头
                Map<Integer, String> header = new HashMap<>();
                header.put(1, "序号");
                header.put(2, "系统名称");
                header.put(3, "系统用户");
                header.put(4, "数据表英文名称");
                header.put(5, "数据表中文名称");
                header.put(6, "数据表注释");
                header.put(7, "负面清单类型");
                header.put(8, "英文字段");
                header.put(9, "中文字段");
                header.put(10, "字段解释");
                header.put(11, "是否主键");
                header.put(12, "是否敏感字段");
                List<Map<Integer, Object>> dataList = new ArrayList<>();
                for (PageData resultPd : list) {
                    Map<Integer, Object> row = new HashMap<>();
                    row.put(1, ++num);
                    row.put(2, resultPd.get("xtmc") == null ? "" : resultPd.get("xtmc").toString());
                    row.put(3, resultPd.get("xtyh") == null ? "" : resultPd.get("xtyh").toString());
                    row.put(4, resultPd.get("ywbm") == null ? "" : resultPd.get("ywbm").toString());
                    row.put(5, resultPd.get("zwbm") == null ? "" : resultPd.get("zwbm").toString());
                    row.put(6, resultPd.get("bjs") == null ? "" : resultPd.get("bjs").toString());
                    row.put(7, resultPd.get("sffmqd") == null ? "" : resultPd.get("sffmqd").toString());
                    row.put(8, resultPd.get("ywzd") == null ? "" : resultPd.get("ywzd").toString());
                    row.put(9, resultPd.get("zwzd") == null ? "" : resultPd.get("zwzd").toString());
                    row.put(10, resultPd.get("zdjs") == null ? "" : resultPd.get("zdjs").toString());
                    row.put(11, resultPd.get("sfzj") == null ? "" : resultPd.get("sfzj").toString());
                    row.put(12, resultPd.get("sfbt") == null ? "" : resultPd.get("sfbt").toString());
                    dataList.add(row);
                }
                ByteArrayOutputStream out = ExcelUtil.OutPutExcel("负面清单", header, dataList);
                downloadExcel("负面清单.xls", out, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "修改负面清单", notes = "修改负面清单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/updateParm", method = RequestMethod.POST)
    public PageData updateParm(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.updateParm(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("修改负面清单出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改负面清单出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "模板下载", notes = "模板下载")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @GetMapping("/downloadTemplate")
    public void downloadFtl(HttpServletResponse response) {
        try {
            //获取要下载的模板名称
            String fileName = "负面清单数据导入模板.xlsx";
            //获取文件的路径
            String filePath = FmqdAction.class.getResource("/template/"+fileName).getFile();
            //设置要下载的文件的名称
            response.setHeader("Content-disposition", "attachment;fileName=" + new String(fileName.getBytes("gbk"), "iso8859-1"));
            //通知客服文件的MIME类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            FileInputStream input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            //修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
            response.setHeader("Content-Length", String.valueOf(input.getChannel().size()));
            input.close();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("FmqdAction-->downloadTemplate"+e.getMessage());
        }
    }

    @ApiOperation(value = "导入excel文件", notes = "导入excel文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @PostMapping("/excelImport")

    public PageData excelImport(@RequestBody MultipartFile excelFile) {
        PageData pageData = new PageData();
        int status;
        try {
            if (excelFile != null && !excelFile.isEmpty()) {
                InputStream is = excelFile.getInputStream();
                Workbook wb = WorkbookFactory.create(is);
                Sheet sheet = wb.getSheetAt(0);
                if (sheet == null) {
                    return null;
                }
                // 获取错误信息
                List<String> errorMsg = service.errorMsg(sheet);
                // 如果错误信息不为空,返回错误信息
                if (errorMsg.size() > 0) {
                    pageData.put("data", new ArrayList<>());
                    pageData.put("error", new PageData("msg", errorMsg));
                    return pageData;
                }
                List<PageData> list = service.buildList(sheet);
                // 判断是否有主键重复(用户+英文表名+英文字段名)，如果重复则从集合中移除数据不新增
                List<PageData> saveList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    String param = list.get(i).get("xtyh").toString() + list.get(i).get("sjbywmc").toString() + list.get(i).get("zdywmc").toString();
                    int count = service.queryCount(param);
                    if (count > 0) {
                        // 从集合中移除
                        list.remove(i);
                        i--;
                    }
                }
                if(list.size() > 0){
                    service.insertPlan(list);
                    pageData.put("data", "success");
                    pageData.put("error", null);
                }else {
                    pageData.put("data", "数据已存在,无须重复导入!");
                    pageData.put("error", null);
                }
            } else {
                pageData.put("data", new ArrayList<>());
                pageData.put("error", "文件为空,无法导入!");
            }
        } catch(ServiceException e) {
            logger.error("导入负面清单失败",e);
            pageData.put("data", new ArrayList<>());
            pageData.put("error", e.getMessage());
        } catch (Exception e) {
            logger.error("导入负面清单失败",e);
            pageData.put("data", new ArrayList<>());
            pageData.put("error", "导入负面清单失败");
        }
        return pageData;
    }
}
