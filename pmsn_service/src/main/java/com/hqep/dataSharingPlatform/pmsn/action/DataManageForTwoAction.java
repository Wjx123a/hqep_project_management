package com.hqep.dataSharingPlatform.pmsn.action;

import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.DataManageForTwoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.hqep.dataSharingPlatform.common.utils.ExcelUtil.downloadExcel;

/**
 * @program: hqep_project_management
 * @ClassName DataManageForTwoAction
 * @author: sssJL
 * @create: 2022-10-27 10:10
 * @Version V1.0
 * @description:
 **/
@RequestMapping("/dataManage")
@RestController
@Api(description = "数据目录管理导入导出")
public class DataManageForTwoAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private DataManageForTwoService dataManageForTwoService;


    @ApiOperation(value = "模板下载", notes = "模板下载")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/downloadTemplate", method = {RequestMethod.POST, RequestMethod.GET})
    public void downloadFtl(HttpServletResponse response) {
        try {
            //获取要下载的模板名称
            String fileName = "BPM_TEMP.xlsx";
            //获取文件的路径
            String filePath = AllTableAction.class.getResource("/template/"+fileName).getFile();
            System.out.println(filePath);
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
        long start = System.currentTimeMillis(); //程序执行前的时间戳
        System.out.println("开始执行"+new Date()); //程序执行前的时间戳
        PageData pageData = new PageData();
        PageData resultPd = new PageData();
        List<PageData> sheet1Lsit = null;
        List<PageData> sheet2Lsit = null;
        List<PageData> sheet3Lsit = null;
        List<PageData> sheet4Lsit = null;
        List<PageData> sheet5Lsit = null;
        String sheetName = "1.编码表";
        try {
            if (excelFile != null && !excelFile.isEmpty()) {
                InputStream is1 = excelFile.getInputStream();
                String[] sheet1Fields = {"XH", "TYPE", "TYPE_CODE", "SYSTEM_NAME", "SIMPLE_NAME", "SYSTEM_TYPE", "SYSTEM_CODE"};
                List FieldsVerify = new ArrayList();
                FieldsVerify.add("TYPE");
                FieldsVerify.add("TYPE_CODE");
                FieldsVerify.add("SYSTEM_NAME");
                FieldsVerify.add("SYSTEM_CODE");
                Workbook wb1 = WorkbookFactory.create(is1);
                Sheet sheet1 = wb1.getSheet(sheetName);
                is1.close();
                if (sheet1 == null) {
                    System.out.println("请勿改变《"+sheetName+"》sheet页名称！");
                    resultPd.put("data", new ArrayList<>());
                    resultPd.put("error","请勿改变《"+sheetName+"》sheet页名称！");
                    return resultPd;
                } else {
                    sheetName = "2.1业务系统信息";
                    Map read = getSheetList(sheet1, sheet1Fields, 1,FieldsVerify);
                    if ("500".equals(read.get("code"))) {
                        resultPd.put("data", new ArrayList<>());
                        resultPd.put("error",sheetName + read.get("message"));
                        return resultPd;
                    } else {
                        sheet1Lsit = (List<PageData>) read.get("data");
                    }
                    List<PageData> sheet1SystemLsit = sheet1Lsit.stream().filter(map -> "03".equals(map.get("TYPE_CODE")) ).collect(Collectors.toList());
                    List<PageData> sheet1DeptLsit = sheet1Lsit.stream().filter(map -> "02".equals(map.get("TYPE_CODE")) ).collect(Collectors.toList());
//                    List<PageData> sheet1CityLsit = sheet1Lsit.stream().filter(map -> "01".equals(map.get("TYPE_CODE")) ).collect(Collectors.toList());
                    InputStream is2 = excelFile.getInputStream();
                    String[] sheet2Fields = {"XH",
                            "SIMPLE_NAME", "SYSTEM_NAME", "SYSTEM_CODE", "SYSTEM_TYPE", "ORG_CODE",
                            "ORG_NAME", "DEPT_CODE", "DEPT_NAME", "DEPT_NO", "DATA_SCALE",
                            "MODIFY_TYPE"};
                    FieldsVerify = new ArrayList();
                    FieldsVerify.add("SIMPLE_NAME");
                    FieldsVerify.add("SYSTEM_NAME");
                    FieldsVerify.add("SYSTEM_CODE");
                    FieldsVerify.add("SYSTEM_TYPE");
                    FieldsVerify.add("ORG_CODE");
                    FieldsVerify.add("ORG_NAME");
                    FieldsVerify.add("DEPT_CODE");
                    FieldsVerify.add("DEPT_NAME");
                    FieldsVerify.add("DEPT_NO");
                    FieldsVerify.add("DATA_SCALE");
                    FieldsVerify.add("MODIFY_TYPE");
                    Workbook wb2 = WorkbookFactory.create(is2);
                    Sheet sheet2 = wb2.getSheet(sheetName);
                    is2.close();
                    if (sheet2 == null) {
                        System.out.println("请勿改变《"+sheetName+"》sheet页名称！");
                        resultPd.put("data", new ArrayList<>());
                        resultPd.put("error","请勿改变《"+sheetName+"》sheet页名称！");
                        return resultPd;
                    } else {
                        read = getSheetList(sheet2, sheet2Fields, 2, FieldsVerify);
                        if ("500".equals(read.get("code"))) {
                            resultPd.put("data", new ArrayList<>());
                            resultPd.put("error",sheetName + read.get("message"));
                            return resultPd;
                        } else {
                            sheet2Lsit = (List<PageData>) read.get("data");
                        }
                        for (int i = 0; i < sheet2Lsit.size(); i++) {
                            boolean flag1 = true;   //验证系统
                            boolean flag2 = true;  // 验证部门 不需要验证
                            boolean flag3 = true;   // 验证省份
                            for (int j = 0; j < sheet1SystemLsit.size(); j++) {
                                if (sheet2Lsit.get(i).get("SIMPLE_NAME").equals(sheet1SystemLsit.get(j).get("SIMPLE_NAME"))) {
                                    if (sheet2Lsit.get(i).get("SYSTEM_NAME").equals(sheet1SystemLsit.get(j).get("SYSTEM_NAME"))) {
                                        if (sheet2Lsit.get(i).get("SYSTEM_CODE").equals(sheet1SystemLsit.get(j).get("SYSTEM_CODE"))) {
                                            if (sheet2Lsit.get(i).get("SYSTEM_TYPE").equals(sheet1SystemLsit.get(j).get("SYSTEM_TYPE"))) {
                                                flag1 = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            for (int j = 0; j < sheet1DeptLsit.size(); j++) {
                                if (sheet2Lsit.get(i).get("DEPT_CODE").equals(sheet1DeptLsit.get(j).get("SYSTEM_CODE"))) {
                                    if (sheet2Lsit.get(i).get("DEPT_NAME").equals(sheet1DeptLsit.get(j).get("SYSTEM_NAME"))) {
                                        flag2 = false;
                                        break;
                                    }
                                }
                            }
                            if ("01020".equals(sheet2Lsit.get(i).get("ORG_CODE"))
                                    && "国网黑龙江省电力有限公司".equals(sheet2Lsit.get(i).get("ORG_NAME"))) {
                                flag3 = false;
                            }
                            if (flag1 || flag2 || flag3) {
                                pageData.put("data", new ArrayList<>());
                                pageData.put("error", "《"+sheetName + "》sheet页，第" + (i+1) +"条数据，未在《1.编码表》码表中匹配！");
                                return pageData;
                            }
//                            PageData pd = new PageData();
//                            pd.put("SYSTEM_NAME",sheet2Lsit.get(i).get("SYSTEM_NAME"));
//                            pd.put("SIMPLE_NAME",sheet2Lsit.get(i).get("SIMPLE_NAME"));
//                            pd.put("SYSTEM_CODE",sheet2Lsit.get(i).get("SYSTEM_CODE"));
//                            int querytotal = dataManageForTwoService.query_t_up_system(pd);
//                            if (querytotal > 0) {
//                                sheet2Lsit.get(i).put("MODIFY_TYPE","2");
//                            } else {
//                                sheet2Lsit.get(i).put("MODIFY_TYPE","1");
//                            }
//                                System.out.println("《"+sheetName + "》sheet页，第" + (i+1) +"条数据，匹配成功！");
                        }
                        sheetName = "2.2业务系统表信息";
                        InputStream is3 = excelFile.getInputStream();
                        String[] sheet3Fields = {"XH",
                                "ORG_NAME", "ORG_CODE", "SIMPLE_NAME", "SYSTEM_CODE", "DB_USER",
                                "TABLE_ENAME", "TABLE_CNAME", "TABLE_DESC", "DATA_NUM", "COLUMN_NUM",
                                "TABLE_TYPE", "TABLE_TYPE_CODE", "NEGATIVE_TYPE", "NEGATIVE_TYPE_CODE", "IS_ZT",
                                "IS_UPLOAD", "CREATE_TIME", "UPDATE_TIME", "DB_TYPE","DATA_LABEL",
                                "BUSINESS_LABEL","MODIFY_TYPE","RELEASE_TIME"
                                };
                        FieldsVerify = new ArrayList();
                        FieldsVerify.add("SIMPLE_NAME");
                        FieldsVerify.add("SYSTEM_CODE");
                        FieldsVerify.add("ORG_CODE");
                        FieldsVerify.add("ORG_NAME");
                        FieldsVerify.add("DB_USER");
                        FieldsVerify.add("TABLE_ENAME");
                        FieldsVerify.add("DATA_NUM");
                        FieldsVerify.add("COLUMN_NUM");
                        FieldsVerify.add("TABLE_TYPE");
                        FieldsVerify.add("TABLE_TYPE_CODE");
                        FieldsVerify.add("NEGATIVE_TYPE");
                        FieldsVerify.add("NEGATIVE_TYPE_CODE");
                        FieldsVerify.add("IS_ZT");
                        FieldsVerify.add("IS_UPLOAD");
                        FieldsVerify.add("DB_TYPE");
                        FieldsVerify.add("MODIFY_TYPE");
                        FieldsVerify.add("RELEASE_TIME");
                        Workbook wb3 = WorkbookFactory.create(is3);
                        Sheet sheet3 = wb3.getSheet(sheetName);
                        is3.close();
                        Map tableNameMap = new HashMap();
                        if (sheet3 == null) {
                            System.out.println("请勿改变《"+sheetName+"》sheet页名称！");
                            resultPd.put("data", new ArrayList<>());
                            resultPd.put("error","请勿改变《"+sheetName+"》sheet页名称！");
                            return resultPd;
                        } else {
                            read =  getSheetList(sheet3, sheet3Fields, 2,FieldsVerify);
                            if ("500".equals(read.get("code"))) {
                                resultPd.put("data", new ArrayList<>());
                                resultPd.put("error",sheetName + read.get("message"));
                                return resultPd;
                            } else {
                                sheet3Lsit = (List<PageData>) read.get("data");
                            }
                            for (int i = 0; i < sheet3Lsit.size(); i++) {
                                tableNameMap.put(sheet3Lsit.get(i).get("TABLE_ENAME"),"TABLE_ENAME");
                                boolean flag1 = true;   //验证系统
                                boolean flag2 = false;  // 验证部门 不需要验证
                                boolean flag3 = true;   // 验证省份
                                for (int j = 0; j < sheet1SystemLsit.size(); j++) {
                                    if (sheet3Lsit.get(i).get("SIMPLE_NAME").equals(sheet1SystemLsit.get(j).get("SIMPLE_NAME"))) {
                                        if (sheet3Lsit.get(i).get("SYSTEM_CODE").equals(sheet1SystemLsit.get(j).get("SYSTEM_CODE"))) {
                                            flag1 = false;
                                            break;
                                        }
                                    }
                                }
                                if ("01020".equals(sheet3Lsit.get(i).get("ORG_CODE"))
                                        && "国网黑龙江省电力有限公司".equals(sheet3Lsit.get(i).get("ORG_NAME"))) {
                                    flag3 = false;
                                }
                                if (flag1 || flag2 || flag3) {
                                    pageData.put("data", new ArrayList<>());
                                    pageData.put("error", "《"+sheetName + "》sheet页，第" + (i+1) +"条数据，未在《1.编码表》码表中匹配！");
                                    return pageData;
                                }
//                                PageData pd = new PageData();
//                                pd.put("SIMPLE_NAME",sheet3Lsit.get(i).get("SIMPLE_NAME"));
//                                pd.put("SYSTEM_CODE",sheet3Lsit.get(i).get("SYSTEM_CODE"));
//                                pd.put("DB_USER",sheet3Lsit.get(i).get("DB_USER"));
//                                pd.put("TABLE_ENAME",sheet3Lsit.get(i).get("TABLE_ENAME"));
//                                int querytotal = dataManageForTwoService.query_t_up_table(pd);
//                                if (querytotal > 0) {
//                                    sheet3Lsit.get(i).put("MODIFY_TYPE","2");
//                                } else {
//                                    sheet3Lsit.get(i).put("MODIFY_TYPE","1");
//                                }
//                                System.out.println("《"+sheetName + "》sheet页，第" + (i+1) +"条数据，匹配成功！");
                            }
//                    System.out.println(sheet1Lsit);
                        }
                        sheetName = "2.3业务系统字段信息";
                        InputStream is4 = excelFile.getInputStream();
                        String[] sheet4Fields = {"XH",
                                "ORG_NAME", "ORG_CODE", "SIMPLE_NAME", "SYSTEM_CODE", "DB_USER",
                                "TABLE_ENAME", "COLUMN_ENAME", "COLUMN_CNAME", "COLUMN_DESC", "COLUMN_TYPE",
                                "IS_PK", "IS_REQUIRED", "IS_SENSITIVE","MODIFY_TYPE","RELEASE_TIME"};
                        FieldsVerify = new ArrayList();
                        FieldsVerify.add("SIMPLE_NAME");
                        FieldsVerify.add("SYSTEM_CODE");
                        FieldsVerify.add("ORG_CODE");
                        FieldsVerify.add("ORG_NAME");
                        FieldsVerify.add("DB_USER");
                        FieldsVerify.add("TABLE_ENAME");
                        FieldsVerify.add("COLUMN_ENAME");
                        FieldsVerify.add("COLUMN_TYPE");
                        FieldsVerify.add("IS_PK");
                        FieldsVerify.add("IS_REQUIRED");
                        FieldsVerify.add("IS_SENSITIVE");
                        FieldsVerify.add("MODIFY_TYPE");
                        Workbook wb4 = WorkbookFactory.create(is4);
                        Sheet sheet4 = wb4.getSheet(sheetName);
                        is4.close();
                        if (sheet4 == null) {
                            System.out.println("请勿改变《"+sheetName+"》sheet页名称！");
                            resultPd.put("data", new ArrayList<>());
                            resultPd.put("error","请勿改变《"+sheetName+"》sheet页名称！");
                            return resultPd;
                        } else {
                            read =  getSheetList(sheet4, sheet4Fields, 2,FieldsVerify);
                            if ("500".equals(read.get("code"))) {
                                resultPd.put("data", new ArrayList<>());
                                resultPd.put("error",sheetName + read.get("message"));
                                return resultPd;
                            } else {
                                sheet4Lsit = (List<PageData>) read.get("data");
                            }
                            for (int i = 0; i < sheet4Lsit.size(); i++) {
                                if(!tableNameMap.containsKey(sheet4Lsit.get(i).get("TABLE_ENAME"))) {
                                    resultPd.put("data", new ArrayList<>());
                                    resultPd.put("error","《"+sheetName + "》sheet页，第" + (i+1) +"条数据,表英文名，未在《2.2业务系统表信息》表中匹配！");
                                    return resultPd;
                                }
                                boolean flag1 = true;   //验证系统
                                boolean flag2 = false;  // 验证部门 不需要验证
                                boolean flag3 = true;   // 验证省份
                                for (int j = 0; j < sheet1SystemLsit.size(); j++) {
                                    if (sheet4Lsit.get(i).get("SIMPLE_NAME").equals(sheet1SystemLsit.get(j).get("SIMPLE_NAME"))) {
                                        if (sheet4Lsit.get(i).get("SYSTEM_CODE").equals(sheet1SystemLsit.get(j).get("SYSTEM_CODE"))) {
                                            flag1 = false;
                                            break;
                                        }
                                    }
                                }
                                if ("01020".equals(sheet4Lsit.get(i).get("ORG_CODE"))
                                        && "国网黑龙江省电力有限公司".equals(sheet4Lsit.get(i).get("ORG_NAME"))) {
                                    flag3 = false;
                                }
                                if (flag1 || flag2 || flag3) {
                                    pageData.put("data", new ArrayList<>());
                                    pageData.put("error", "《"+sheetName + "》sheet页，第" + (i+1) +"条数据，未在《1.编码表》码表中匹配！");
                                    return pageData;
                                }
//                                PageData pd = new PageData();
//                                pd.put("SIMPLE_NAME",sheet4Lsit.get(i).get("SIMPLE_NAME"));
//                                pd.put("SYSTEM_CODE",sheet4Lsit.get(i).get("SYSTEM_CODE"));
//                                pd.put("DB_USER",sheet4Lsit.get(i).get("DB_USER"));
//                                pd.put("TABLE_ENAME",sheet4Lsit.get(i).get("TABLE_ENAME"));
//                                pd.put("COLUMN_ENAME",sheet4Lsit.get(i).get("COLUMN_ENAME"));
//                                int querytotal = dataManageForTwoService.query_t_up_column(pd);
//                                if (querytotal > 0) {
//                                    sheet4Lsit.get(i).put("MODIFY_TYPE","2");
//                                } else {
//                                    sheet4Lsit.get(i).put("MODIFY_TYPE","1");
//                                    boolean tFlag = true;
//                                    for (int j = 0; j < sheet3Lsit.size(); j++) {
//                                        if (sheet4Lsit.get(i).get("TABLE_ENAME").equals(sheet3Lsit.get(j).get("TABLE_ENAME"))) {
//                                            tFlag = false;
//                                            break;
//                                        }
//                                    }
//                                    if (tFlag) {
//                                        pageData.put("data", new ArrayList<>());
//                                        pageData.put("error", "《"+sheetName + "》sheet页，第" + (i+1) +"条数据，未找到对应的table表信息！");
//                                        return pageData;
//                                    }

//                                }
//                                System.out.println("《"+sheetName + "》sheet页，第" + (i+1) +"条数据，匹配成功！");
                            }
                        }
                        sheetName = "2.4业务系统目录信息";
                        InputStream is5 = excelFile.getInputStream();
                        String[] sheet5Fields = {"XH",
                                "ORG_CODE", "ORG_NAME", "SIMPLE_NAME", "SYSTEM_CODE", "ONE_CATALOG_NAME",
                                "TWO_CATALOG_NAME", "THREE_CATALOG_NAME", "DB_USER", "TABLE_ENAME","MODIFY_TYPE",
                                "RELEASE_TIME"};
                        FieldsVerify = new ArrayList();
                        FieldsVerify.add("SIMPLE_NAME");
                        FieldsVerify.add("SYSTEM_CODE");
                        FieldsVerify.add("ORG_CODE");
                        FieldsVerify.add("ORG_NAME");
                        FieldsVerify.add("ONE_CATALOG_NAME");
                        FieldsVerify.add("DB_USER");
                        FieldsVerify.add("TABLE_ENAME");
                        FieldsVerify.add("MODIFY_TYPE");
                        Workbook wb5 = WorkbookFactory.create(is5);
                        Sheet sheet5 = wb5.getSheet(sheetName);
                        is5.close();
                        if (sheet5 == null) {
                            System.out.println("请勿改变《"+sheetName+"》sheet页名称！");
                            resultPd.put("data", new ArrayList<>());
                            resultPd.put("error","请勿改变《"+sheetName+"》sheet页名称！");
                            return resultPd;
                        } else {
                            read = getSheetList(sheet5, sheet5Fields, 2,FieldsVerify);
                            if ("500".equals(read.get("code"))) {
                                resultPd.put("data", new ArrayList<>());
                                resultPd.put("error",sheetName + read.get("message"));
                                return resultPd;
                            } else {
                                sheet5Lsit = (List<PageData>) read.get("data");
                            }
                            for (int i = 0; i < sheet5Lsit.size(); i++) {
                                boolean flag1 = true;   //验证系统
                                boolean flag2 = false;  // 验证部门 不需要验证
                                boolean flag3 = true;   // 验证省份
                                for (int j = 0; j < sheet1SystemLsit.size(); j++) {
                                    if (sheet5Lsit.get(i).get("SIMPLE_NAME").equals(sheet1SystemLsit.get(j).get("SIMPLE_NAME"))) {
                                        if (sheet5Lsit.get(i).get("SYSTEM_CODE").equals(sheet1SystemLsit.get(j).get("SYSTEM_CODE"))) {
                                            flag1 = false;
                                            break;
                                        }
                                    }
                                }
                                if ("01020".equals(sheet5Lsit.get(i).get("ORG_CODE"))
                                        && "国网黑龙江省电力有限公司".equals(sheet5Lsit.get(i).get("ORG_NAME"))) {
                                    flag3 = false;
                                }
                                if (flag1 || flag2 || flag3) {
                                    pageData.put("data", new ArrayList<>());
                                    pageData.put("error", "《"+sheetName + "》sheet页，第" + (i+1) +"条数据，未在《1.编码表》码表中匹配！");
                                    return pageData;
                                }
//                                PageData pd = new PageData();
//                                pd.put("SIMPLE_NAME",sheet5Lsit.get(i).get("SIMPLE_NAME"));
//                                pd.put("SYSTEM_CODE",sheet5Lsit.get(i).get("SYSTEM_CODE"));
//                                pd.put("DB_USER",sheet5Lsit.get(i).get("DB_USER"));
//                                pd.put("TABLE_ENAME",sheet5Lsit.get(i).get("TABLE_ENAME"));
//                                pd.put("COLUMN_ENAME",sheet5Lsit.get(i).get("COLUMN_ENAME"));
//                                int querytotal = dataManageForTwoService.query_t_up_column(pd);
//                                if (querytotal > 0) {
//                                    sheet5Lsit.get(i).put("MODIFY_TYPE","2");
//                                } else {
//                                    sheet5Lsit.get(i).put("MODIFY_TYPE","1");
//                                }
//                                System.out.println("《"+sheetName + "》sheet页，第" + (i+1) +"条数据，匹配成功！");
//                                dataManageForTwoService.insert_t_up_catalog_table_rel(sheet5Lsit.get(i));

                            }
                        }
                    }
                }

                for (int i = 0; i < sheet2Lsit.size(); i++) {
                    dataManageForTwoService.insert_t_up_system(sheet2Lsit.get(i));
                }
                for (int i = 0; i < sheet3Lsit.size(); i++) {
                    dataManageForTwoService.insert_t_up_table(sheet3Lsit.get(i));
                }
                for (int i = 0; i < sheet4Lsit.size(); i++) {
                    dataManageForTwoService.insert_t_up_column(sheet4Lsit.get(i));
                }
                for (int i = 0; i < sheet5Lsit.size(); i++) {
                    dataManageForTwoService.insert_t_up_catalog_table_rel(sheet5Lsit.get(i));
                }
                pageData.put("data", "success");
                pageData.put("error", null);
                return pageData;
            } else {
                pageData.put("data", new ArrayList<>());
                pageData.put("error", "文件为空,无法导入!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入二级目录管理表失败",e);
            pageData.put("data", new ArrayList<>());
            pageData.put("error", "导入二级目录管理表失败");
        } finally {
            long end = System.currentTimeMillis(); //程序执行后的时间戳
            System.out.println("导入二级目录管理表用时 ：" + (end - start)/1000);
        }
        return pageData;
    }

    public  Map getSheetList(Sheet sheet,String[] fields,int readNum ,List verify) {
        Map<String,Object> result = new HashMap();
        List<PageData> sheetList = new ArrayList<>();
        try {
            // 行数
            int rowNum=0, column=0;
            int rows = sheet.getPhysicalNumberOfRows();
            if (rows < readNum) {
                result.put("code","500");
                result.put("message","数据不存在! 请添加后导入");
                return result;
            }
            PageData map = new PageData();
            // 错误信息
            // 检查excel文件
            for (Row row : sheet) {
                // 记录行号
                rowNum = row.getRowNum();
                // 跳过表头
                if(rowNum < readNum) {
                    column = row.getPhysicalNumberOfCells();
                    continue;
                }
                map = new PageData();
                for (int i = 0; i < fields.length; i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        if("CREATE_TIME".equals(fields[i]) ){
                            try {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                DateFormat formater = new SimpleDateFormat("yyyy-M-dd");
//                                DateFormat formater1 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
                                System.out.println(cell.getStringCellValue());
                                map.put(fields[i],formater.parse(String.valueOf(cell.getStringCellValue())));
                            } catch (Exception e) {
                                e.printStackTrace();
                                result.put("message","创建时间（可选）日期格式错误！");
                                result.put("code","500");
                                System.out.println("创建时间（可选）日期格式错误");
                                return result;
                            }
                        } else if ("UPDATE_TIME".equals(fields[i]) ){
                            try {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                DateFormat formater = new SimpleDateFormat("yyyy-M-dd");
                                DateFormat formater1 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
                                String  str = cell.getStringCellValue();
                                if ("".equals(str)) {
                                    map.put(fields[i],"");
                                } else {
                                    map.put(fields[i],formater.parse(String.valueOf(cell.getStringCellValue())));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                result.put("message","最后更新时间（可选）日期格式错误！");
                                result.put("code","500");
                                System.out.println("最后更新时间（可选）日期格式错误");
                                return result;
                            }
                        } else if("RELEASE_TIME".equals(fields[i])) {
                            try {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                DateFormat formater = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
                                map.put(fields[i],formater.parse(cell.getStringCellValue()));
                            } catch (Exception e) {
                                e.printStackTrace();
                                result.put("message","发布时间日期格式错误！");
                                result.put("code","500");
                                System.out.println("发布时间日期格式错误");
                                return result;
                            }
                        } else {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            String verifyStr = cell.getStringCellValue();
                            if (verify.contains(fields[i]) && ("".equals(verifyStr) || verifyStr == null) ) {
                                result.put("message",(i +1)+"列值不允许为空！");
                                result.put("code","500");
                                System.out.println((i +1)+"列值不允许为空！");
                                return result;
                            } else {
                                map.put(fields[i],verifyStr);
                            }
                        }
                    } else {
                        if (verify.contains(fields[i])) {
                            result.put("message",(i +1)+"列值不允许为空！");
                            result.put("code","500");
                            System.out.println((i +1)+"列值不允许为空！");
                            return result;
                        } else {
                            map.put(fields[i],"");
                        }
                    }
                }
                sheetList.add(map);
            }
            result.put("data",sheetList);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code","500");
            result.put("message","读取异常！");
        }

        return result;
    }


}
