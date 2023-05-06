package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.pmsn.service.AllTableService;
import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.TUpCatalogTableRelService;
import com.hqep.dataSharingPlatform.pmsn.unit.StaticVariable;
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

import static com.alibaba.druid.sql.parser.Token.THEN;
import static com.github.pagehelper.PageHelper.startPage;
import static com.hqep.dataSharingPlatform.common.utils.ExcelUtil.downloadExcel;

@RequestMapping("/allTable")
@RestController
@Api(description = "全量表查询")
public class AllTableAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private AllTableService service;
    @Autowired
    private TUpCatalogTableRelService tUpCatalogTableRelService;


    @ApiOperation(value = "全量表查询列表", notes = "全量表查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryParmList", method = RequestMethod.POST)
    public PageData queryParmList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
//            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryParmList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
//            paginationPd.put("total", page.getTotal());
            int total = service.queryListCount(pd);
            paginationPd.put("total", total);
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询全量表列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询全量表列表出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "导出" , notes = "导出")
    @GetMapping("/exportExcel")
    public @ResponseBody void exportExcel (@RequestParam(value = "xtmc", required = false) String xtmc,
                                           @RequestParam(value = "ssbm", required = false) String ssbm,
                                           @RequestParam(value = "ywbm", required = false) String ywbm) {
        try {
            int num = 0;
            PageData pd = new PageData("xtmc", xtmc,"ssbm", ssbm,"ywbm", ywbm);
            List<PageData> list = service.queryParmList(pd);
            if (list.size() > 0) {
                // 表头
                Map<Integer, String> header = new HashMap<>();
                header.put(1, "序号");
                header.put(2, "系统名称");
                header.put(3, "部门");
                header.put(4, "用户");
                header.put(5, "英文表名");
                header.put(6, "中文表名");
                header.put(7, "表解释");
                header.put(8, "字段名称");
                header.put(9, "中文名称");
                header.put(10, "字段解释");
                header.put(11, "类型");
                header.put(12, "位数");
                header.put(13, "是否为空");
                List<Map<Integer, Object>> dataList = new ArrayList<>();
                for (PageData resultPd : list) {
                    Map<Integer, Object> row = new HashMap<>();
                    row.put(1, ++num);
                    row.put(2, resultPd.get("xtmc") == null ? "" :
                            resultPd.get("xtmc").toString());
                    row.put(3, resultPd.get("ssbm") == null ? "" :
                            resultPd.get("ssbm").toString());
                    row.put(4, resultPd.get("yh") == null ? "" :
                            resultPd.get("yh").toString());
                    row.put(5, resultPd.get("ywbm") == null ? "" :
                            resultPd.get("ywbm").toString());
                    row.put(6, resultPd.get("zwbm") == null ? "" :
                            resultPd.get("zwbm").toString());
                    row.put(7, resultPd.get("bjs") == null ? "" :
                            resultPd.get("bjs").toString());
                    row.put(8, resultPd.get("ywzdmc") == null ? "" :
                            resultPd.get("ywzdmc").toString());
                    row.put(9, resultPd.get("zwzdmc") == null ? "" :
                            resultPd.get("zwzdmc").toString());
                    row.put(10, resultPd.get("zdjs") == null ? "" :
                            resultPd.get("zdjs").toString());
                    row.put(11, resultPd.get("zdlx") == null ? "" :
                            resultPd.get("zdlx").toString());
                    row.put(12, resultPd.get("zdcd") == null ? "" :
                            resultPd.get("zdcd").toString());
                    row.put(13, resultPd.get("sfwk") == null ? "" :
                            resultPd.get("sfwk").toString());
                    dataList.add(row);
                }
                ByteArrayOutputStream out = ExcelUtil.OutPutExcel("全量表", header, dataList);
                downloadExcel("全量表.xls", out, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "修改全量表", notes = "修改全量表")
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
            logger.error("修改全量表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改全量表出错");
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
            String fileName = "全量表数据导入模板.xlsx";
            //获取文件的路径
            String filePath = AllTableAction.class.getResource("/template/"+fileName).getFile();
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
            logger.error("AllTableAction-->downloadTemplate"+e.getMessage());
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
                    String param = list.get(i).get("yh").toString() + list.get(i).get("ywbm").toString() + list.get(i).get("ywzdmc").toString();
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
            logger.error("导入全量表失败",e);
            pageData.put("data", new ArrayList<>());
            pageData.put("error", e.getMessage());
        } catch (Exception e) {
            logger.error("导入全量表失败",e);
            pageData.put("data", new ArrayList<>());
            pageData.put("error", "导入全量表失败");
        }
        return pageData;
    }

    /**
     * 20210302第二次修改
     */
    // 根据系统查询表信息 （ 数据目录=》二级系统 ，贴源层 ，共享层 ，分析层 ）
    @ApiOperation(value = "根据系统查询表信息", notes = "根据系统查询表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryTableListBySytem", method = RequestMethod.POST)
    public PageData queryTableListBySytem(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        long start = System.currentTimeMillis(); //程序执行前的时间戳
        try {
            String ywbmrc = pd.getString("ywbm");
            pd.put("ywbmrc",ywbmrc);
            String ywbm = pd.getString("ywbm");
            try { // 对 _ 进行转义
                ywbm = ywbm.replaceAll("_","\\\\_");
                pd.put("ywbm",ywbm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            if ("一级目录".equals(pd.get("bsskj"))) {
                String levelName = pd.getString("levelName");
                int flag = 0;
                if (levelName != null && !"".equals(levelName)) {
                    String[] levelNames = levelName.split(",");
                    for (int i = 0; i < levelNames.length; i++) {
                        flag = i;
                        if (i==0) {
                            pd.put("THREE_CATALOG_NAME",levelNames[i]);
                        } else if (i==1) {
                            pd.put("FOUR_CATALOG_NAME",levelNames[i]);
                        } else if (i==2) {
                            pd.put("FIVE_CATALOG_NAME",levelNames[i]);
                        } else if (i==3) {
                            pd.put("SIX_CATALOG_NAME",levelNames[i]);
                        }
                    }
                    String xtmc = pd.getString("xtmc");
                    ++flag;
                    if (flag==0) {
                        pd.put("THREE_CATALOG_NAME",xtmc);
                    } else if (flag==1) {
                        pd.put("FOUR_CATALOG_NAME",xtmc);
                    } else if (flag==2) {
                        pd.put("FIVE_CATALOG_NAME",xtmc);
                    } else if (flag==3) {
                        pd.put("SIX_CATALOG_NAME",xtmc);
                    }
                } else {
                    String xtmc = pd.getString("xtmc");
                    pd.put("THREE_CATALOG_NAME",xtmc);
                }
                Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
                List<PageData> list = tUpCatalogTableRelService.queryOneLevelListByOneSystem(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal()); // 查询数据量小的时候注释
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
                System.out.println("中间：" + (System.currentTimeMillis() - start)/1000);

            } else if ("二级目录".equals(pd.get("bsskj"))) {
                String levelName = pd.getString("levelName");
                int flag = 0;
                if (levelName != null && !"".equals(levelName)) {
                    String[] levelNames = levelName.split(",");
                    for (int i = 0; i < levelNames.length; i++) {
                        flag = i;
                        if (i==0) {
                            pd.put("THREE_CATALOG_NAME",levelNames[i]);
                        } else if (i==1) {
                            pd.put("FOUR_CATALOG_NAME",levelNames[i]);
                        } else if (i==2) {
                            pd.put("FIVE_CATALOG_NAME",levelNames[i]);
                        } else if (i==3) {
                            pd.put("SIX_CATALOG_NAME",levelNames[i]);
                        }
                    }
                    String xtmc = pd.getString("xtmc");
                    ++flag;
                    if (flag==0) {
                        pd.put("THREE_CATALOG_NAME",xtmc);
                    } else if (flag==1) {
                        pd.put("FOUR_CATALOG_NAME",xtmc);
                    } else if (flag==2) {
                        pd.put("FIVE_CATALOG_NAME",xtmc);
                    } else if (flag==3) {
                        pd.put("SIX_CATALOG_NAME",xtmc);
                    }
                } else {
                    String xtmc = pd.getString("xtmc");
                    pd.put("THREE_CATALOG_NAME",xtmc);
                }
                Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
                List<PageData> list = tUpCatalogTableRelService.queryOneLevelListByTwoSystem(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal()); // 查询数据量小的时候注释
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
            } else if ("贴源层".equals(pd.get("bsskj"))) {
                pd.put("is_zt","是");
                Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
                List<PageData> list = service.queryTableListByTYCSystem(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal()); // 查询数据量小的时候注释
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
            } else if ("共享层".equals(pd.get("bsskj"))) {
                Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
                String xtmc = (String) pd.get("xtmc");
                String xtmcbh = "";
                if (xtmc!=null) {
                    switch (xtmc) {
                        case "综合域":
                        xtmcbh ="dwd_itg";
                        break;
                        case "资产域":
                        xtmcbh ="dwd_ast";
                        break;
                        case "项目域":
                        xtmcbh ="dwd_prj";
                        break;
                        case "物资域":
                        xtmcbh ="dwd_mat";
                        break;
                        case "市场域":
                        xtmcbh ="dwd_mrt";
                        break;
                        case "客户域":
                        xtmcbh ="dwd_cst";
                        break;
                        case "电网域":
                        xtmcbh ="dwd_grid";
                        break;
                        case "财务域":
                        xtmcbh ="dwd_fin";
                        break;
                        case "安全域":
                        xtmcbh ="dwd_saf";
                        break;
                        // 因为不知道表所属域 希望在点击中台共享层菜单时就可以查找表
                        case "中台共享层":
                        pd.put("xtmc","");
                        break;
                        default:
                            xtmcbh = "";
                    }
                }
                if (!"".equals(xtmcbh)) {
                    pd.put("xtmcbh",xtmcbh);
                }
                List list = service.queryTableListByGXCXTY(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
//                int total =  service.queryTableListByGXCXTYCount(pd);
                paginationPd.put("total", page.getTotal()); // 查询数据量小的时候注释
//                paginationPd.put("total", total);
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
            }  else if ("分析层".equals(pd.get("bsskj"))) {
                String xtmc = (String) pd.get("xtmc");
                String xtmcbh = "";
                if (xtmc!=null) {
                    switch (xtmc) {
                        case "维度表":
                            xtmcbh ="dim_";
                            break;
                        case "汇总宽表":
                            xtmcbh ="dws_";
                            break;
                        case "应用层表":
                            xtmcbh ="ads_";
                            break;
                        case "事实明细层表":
                            xtmcbh ="fct_";
                            break;
                        default:
                            xtmcbh = "";
                    }
                }
                if (!"".equals(xtmcbh)) {
                    pd.put("xtmcbh",xtmcbh);
                }

                Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
                List<PageData> list = service.queryTableListByFXCXTY(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal()); // 查询数据量小的时候注释
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
            } else if ("二级系统".equals(pd.get("bsskj"))
                    || "源端".equals(pd.get("bsskj"))){  // 如果系统名称 数据目录的内容 进入该代码块
                // Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
                List<PageData> list = service.queryTableListBySytem(pd);
                if (ywbmrc != null && ! "".equals(ywbmrc) ) {
                    service.insertrc(pd);
                }
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
//            paginationPd.put("total", page.getTotal()); // 查询数据量大的时候注释
                int total = service.queryTableListBySytemCount(pd); // 查询数据量小的时候注释
                paginationPd.put("total", total); // 查询数据量小的时候注释
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
                }
            //----------------------------获取表标签
            List<PageData> dataList = (List<PageData>) resultPd.get("data");
            for(int j=0;j< dataList.size();j++){
                if(dataList.get(j).get("table_cn_name") != null && dataList.get(j).get("table_cn_name") != ""){
                    List bq = service.querbq(dataList.get(j).get("table_cn_name").toString());
                    ((List<PageData>) resultPd.get("data")).get(j).put("bqmc",bq);

                }
            }
        } catch (Exception e) {
            logger.error("根据系统查询表信息查询出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "根据系统查询表信息查询出错");
        }
        long end = System.currentTimeMillis(); //程序执行后的时间戳
        System.out.println("根据系统查询表信息 （ 数据目录=》二级系统 ，贴源层 ，共享层 ，分析层 ：" + (end - start)/1000);
        return resultPd;
    }

    // 根据系统查询表信息-详情（ 数据目录=》二级系统 ）
    @ApiOperation(value = "根据系统查询表信息-详情", notes = "根据系统查询表信息-详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryTableListBySytemDetail", method = RequestMethod.POST)
    public PageData queryTableListBySytemDetail(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        long start = System.currentTimeMillis(); //程序执行前的时间戳
        try { //
            if ("一级目录".equals(pd.get("bsskj"))) {
                int size = pd.getInt("pageSize");
                int index = pd.getInt("pageIndex");
                Page page = PageHelper.startPage(index, size);
                List<PageData> list = service.queryTableListByOneSytemDetail(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal());
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
            } else if ("共享层".equals(pd.get("bsskj"))) {
                int size = pd.getInt("pageSize");
                int index = pd.getInt("pageIndex");
                Page page = PageHelper.startPage(index, size);
                String ywbm = pd.getString("ywbm");
                if (ywbm!=null && ywbm.startsWith("sjzt_")) {
                    ywbm = "odps." +ywbm ;
                    pd.put("ywbm",ywbm);
                }
                List<PageData> list = service.queryTableListByGXCXTYForDetails(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal());
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);

            } else if ("分析层".equals(pd.get("bsskj"))) {
                int size = pd.getInt("pageSize");
                int index = pd.getInt("pageIndex");
                Page page = PageHelper.startPage(index, size);
                String ywbm = pd.getString("ywbm");
                if (ywbm!=null && ywbm.startsWith("sjzt_")) {
                    ywbm = "odps." +ywbm ;
                    pd.put("ywbm",ywbm);
                }
                List<PageData> list = service.queryTableListByFXCXTYForDetails(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal());
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
            }
            else if ("贴源层".equals(pd.get("bsskj"))) {
                int size = pd.getInt("pageSize");
                int index = pd.getInt("pageIndex");
                Page page = PageHelper.startPage(index, size);
                String ywbm = pd.getString("table_ename");
                if (ywbm!=null && ywbm.startsWith("sjzt_")) {
                    ywbm = "odps." +ywbm ;
                    pd.put("ywbm",ywbm);
                }
                List<PageData> list = service.queryTableListByTYCXTYForDetails(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal());
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
            }
//            else if ("二级目录".equals(pd.get("bsskj"))) {
//                int size = pd.getInt("pageSize");
//                int index = pd.getInt("pageIndex");
//                Page page = PageHelper.startPage(index, size);
//                List<PageData> list = service.queryTableListByTwoSytemDetail(pd);
//                resultPd.put("data", list);
//                PageData paginationPd = new PageData();
//                paginationPd.put("total", page.getTotal());
//                paginationPd.put("pageSize", size);
//                paginationPd.put("current", index);
//                // 页码
//                resultPd.put("pagination", paginationPd);
//            }
            else {
                int size = pd.getInt("pageSize");
                int index = pd.getInt("pageIndex");
                Page page = PageHelper.startPage(index, size);
                List<PageData> list = service.queryTableListBySytemDetail(pd);
                resultPd.put("data", list);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal());
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
            }
            long end = System.currentTimeMillis(); //程序执行后的时间戳
            System.out.println("根据系统查询表信息 查询字段详情用时：" + (end - start)/1000);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("根据系统查询表信息-详情查询出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "根据系统查询表信息-详情查询出错");
        }
        return resultPd;
    }


    // 根据系统查询表信息-详情（ 数据目录=》二级系统 ）的导出
    @ApiOperation(value = "根据系统查询表信息-详情里的导出", notes = "根据系统查询表信息-详情里的导出")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryTableListBySytemDetailForExcel", method = RequestMethod.POST)
    public void queryTableListBySytemDetailForExcel(@RequestBody PageData pd) {
        try {
            List<PageData> list = new ArrayList<>();
            if ("一级目录".equals(pd.get("bsskj"))) {
                list = service.queryTableListByOneSytemDetail(pd);
            } else if ("共享层".equals(pd.get("bsskj"))) {
                String ywbm = pd.getString("ywbm");
                if (ywbm!=null && ywbm.startsWith("sjzt_")) {
                    ywbm = "odps." +ywbm ;
                    pd.put("ywbm",ywbm);
                }
                list = service.queryTableListByGXCXTYForDetails(pd);
            } else if ("分析层".equals(pd.get("bsskj"))) {
                String ywbm = pd.getString("ywbm");
                if (ywbm!=null && ywbm.startsWith("sjzt_")) {
                    ywbm = "odps." +ywbm ;
                    pd.put("ywbm",ywbm);
                }
                list = service.queryTableListByFXCXTYForDetails(pd);
            }  else if ("贴源层".equals(pd.get("bsskj"))) {
                String ywbm = pd.getString("table_ename");
                if (ywbm!=null && ywbm.startsWith("sjzt_")) {
                    ywbm = "odps." +ywbm ;
                    pd.put("ywbm",ywbm);
                }
                list = service.queryTableListByTYCXTYForDetails(pd);
            } else {
                list = service.queryTableListBySytemDetail(pd);
            }
            int num = 0;
            if (list.size() > 0) {
                // 表头
                Map<Integer, String> header = new HashMap<>();
                header.put(1, "序号");
                header.put(2, "系统名称");
                header.put(3, "部门");
                header.put(4, "用户");
                header.put(5, "英文表名");
                header.put(6, "中文表名");
                header.put(7, "字段英文名称");
                header.put(8, "字段中文名称");
                header.put(9, "字段解释");
                header.put(10, "字段类型");
                header.put(11, "字段位数");
                header.put(12, "字段是否为空");
                List<Map<Integer, Object>> dataList = new ArrayList<>();
                for (PageData expPd : list) {
                    Map<Integer, Object> row = new HashMap<>();
                    row.put(1, ++num);
                    row.put(2, expPd.get("xtmc") == null ? "" : expPd.get("xtmc").toString());
                    row.put(3, expPd.get("ssbm") == null ? "" : expPd.get("ssbm").toString());
                    //
                    row.put(4, expPd.get("xtyh") == null ? "" : expPd.get("xtyh").toString());
                    row.put(5, expPd.get("ywbm") == null ? "" : expPd.get("ywbm").toString());
                    row.put(6, expPd.get("zwbm") == null ? "" : expPd.get("zwbm").toString());
                    row.put(7, expPd.get("ywzdmc") == null ? "" : expPd.get("ywzdmc").toString());
                    row.put(8, expPd.get("zwzdmc") == null ? "" : expPd.get("zwzdmc").toString());
                    //
                    row.put(9, expPd.get("zdjs") == null ? "" : expPd.get("zdjs").toString());
                    row.put(10, expPd.get("zdlx") == null ? "" : expPd.get("zdlx").toString());
                    row.put(11, expPd.get("zdcd") == null ? "" : expPd.get("zdcd").toString());
                    row.put(12, expPd.get("sfwk") == null ? "" : expPd.get("sfwk").toString());
                    dataList.add(row);
                }
                ByteArrayOutputStream out = ExcelUtil.OutPutExcel("全量表", header, dataList);
                downloadExcel("全量表.xls", out, response);
            }
        } catch (Exception e) {
            logger.error("根据系统查询表信息-详情查询出错", e);
        }
    }


    // 查询数据表的总计条数(申请热度) 暂时没用
    @ApiOperation(value = "查询数据表的总计条数(申请热度)", notes = "查询数据表的总计条数(申请热度)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryTableAllApplyCount", method = RequestMethod.POST)
    public int queryTableAllApplyCount(@RequestBody PageData pd) {
        Map resultMap = new HashMap();
        int sqrd = 0;
        try {
            sqrd = service.queryTableAllApplyCount(pd);
            resultMap.put("sqrd", sqrd);
        } catch (Exception e) {
            logger.error("查询数据表的总计条数(申请热度)出错", e);
            resultMap.put("error", "查询数据表的总计条数(申请热度)出错");
        }
        return sqrd;
    }

    // 查询当前表是否已经收藏
    @ApiOperation(value = "查询当前表是否已经收藏", notes = "查询当前表是否已经收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryIsCollection", method = RequestMethod.POST)
    public int queryIsCollection(@RequestBody PageData pd) {
        Map resultMap = new HashMap();
        int collectionCount = 0;
        try {
            if ("二级系统".equals(pd.get("bsskj"))
                    || "源端".equals(pd.get("bsskj"))){  // 如果系统名称 数据目录的内容 进入该代码块
                collectionCount = service.queryIsCollection(pd);
            } else if ("共享层".equals(pd.get("bsskj"))) {
                collectionCount = service.queryIsCollectionByGXCXTY(pd);
            } else if ("分析层".equals(pd.get("bsskj"))) {
                collectionCount = service.queryIsCollectionByFXCXTY(pd);
            } else if ("一级目录".equals(pd.get("bsskj"))) {
                if ("".equals(pd.get("sqbsqbm"))) {
                    collectionCount = service.queryIsCollectionForOneSys(pd);
                    if (collectionCount==0) {
                        collectionCount = 3;
                    }
                } else {
                    collectionCount = service.queryIsCollectionForOneSys(pd);
                }
            } else if ("二级目录".equals(pd.get("bsskj"))) {
                collectionCount = service.queryIsCollectionByFXCXTY(pd);
            }else if("贴源层".equals(pd.get("bsskj"))) {
                collectionCount = service.queryIsCollectionTyc(pd);
            }
            resultMap.put("collectionCount", collectionCount);
        } catch (Exception e) {
            logger.error("查询当前表是否已经收藏出错", e);
            resultMap.put("error", "查询当前表是否已经收藏出错");
        }
        return collectionCount;
    }

    // 贴源层是否接入表
    @ApiOperation(value = "贴源层是否接入表", notes = "贴源层是否接入表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryZjztTableCount", method = RequestMethod.POST)
    public int queryZjztTableCount(@RequestBody PageData pd) {
        Map resultMap = new HashMap();
        int jrCount = 0;
        try {
            // 判断是不是共享层查询，如果是 直接置为2（共享层的表都是录入中台的，所以不用判断是否接入中台）
//            if(pd.getString("xtmc") != null
//                    && !"".equals(pd.getString("xtmc"))
//                    && StaticVariable.SYS_FIELD_NAME_GX.indexOf(pd.getString("xtmc")+",") > -1) {
            if ("一级目录".equals(pd.get("bsskj"))) {
                if ("购物车".equals(pd.get("addType"))) {
                    jrCount = service.queryOneSysZjztTableCount(pd);
                } else {
                    // 如果是订单栏那么直接过去 不在查询是否存在
                    jrCount = 99;
                }
            } else if ("二级目录".equals(pd.get("bsskj"))) {
                jrCount = service.queryZjztTableCount(pd);
            } else if ("二级系统".equals(pd.get("bsskj"))
                    || "源端".equals(pd.get("bsskj"))) {
                // 判断不是共享层查询，查询是否接入中台中
                jrCount = service.queryZjztTableCount(pd);
                resultMap.put("jrCount", jrCount);
            } else if ("贴源层".equals(pd.get("bsskj"))) {
                jrCount = 2;
            } else if ("共享层".equals(pd.get("bsskj"))) {
                jrCount = 2;
            } else if ("分析层".equals(pd.get("bsskj"))) {
                jrCount = 2;
            } else {
                jrCount = 0;
            }
        } catch (Exception e) {
            logger.error("贴源层是否接入表出错", e);
            resultMap.put("error", "贴源层是否接入表出错");
        }
        return jrCount;
    }

    // 查询共享层表信息
    @ApiOperation(value = "查询共享层表信息", notes = "查询共享层表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryGxcTableList", method = RequestMethod.POST)
    public PageData queryGxcTableList(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size); // 查询数据量大的时候注释
            List<PageData> list = service.queryGxcTableList(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal()); // 查询数据量大的时候注释
//            int total = service.queryGxcTableListCount(pd); // 查询数据量小的时候注释
//            paginationPd.put("total", total); // 查询数据量小的时候注释
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询共享层表信息出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询共享层表信息出错");
        }
        return resultPd;
    }

    // 查询共享层表信息-字段
    @ApiOperation(value = "查询共享层表信息-字段", notes = "查询共享层表信息-字段")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryGxcTableListColumn", method = RequestMethod.POST)
    public PageData queryGxcTableListColumn(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = service.queryGxcTableListColumn(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询共享层表信息-字段出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询共享层表信息-字段出错");
        }
        return resultPd;
    }


}
