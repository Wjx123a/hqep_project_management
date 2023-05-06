package com.hqep.dataSharingPlatform.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    /**
     * 导出excel模板
     * @param fileName
     * @param response
     * @param request
     */
    public static void downloadTemplate(String fileName, HttpServletResponse response, HttpServletRequest request) {
        OutputStream out = null;
        FileInputStream in = null;
        try {
            String userAgent = request.getHeader("User-Agent");
//            String path = request.getSession().getServletContext().getRealPath(File.separator) +
//                    "excelTemplate" + File.separator;
            ServletContext servletContext = null;
            servletContext = request.getSession().getServletContext();
            if (servletContext == null) {
                servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
            }
            String path = servletContext.getRealPath("excelTemplate/");
            String fileDownload = path + fileName;
            //针对IE或者以IE为内核的浏览器：
            if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
                //非IE浏览器的处理：
            } else {
                // 处理中文乱码
                fileName = new String(fileName.getBytes("GBK"),"ISO-8859-1");
            }
            response.reset();
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getOutputStream();
            in = new FileInputStream(fileDownload);
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0) {
                out.write(b, 0, i);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
                out = null;
            }
        }
    }

    /**
     * @Author Hanyf
     * @Description 格式转换
     * @Date
     * @Param [cell]
     * @return java.lang.String
     **/
    public static String parseExcel(Cell cell) {
        if (cell == null) {
            return null;
        }
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                //like12 add,20190919,支持日期格式
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date d = cell.getDateCellValue();
                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellValue = df2.format(d);
                }
                //数字
                else {
                    cellValue = df.format(cell.getNumericCellValue()).toString();
                }
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    /**
     * 数据转换成ByteArrayOutputStream数据流
     * 若转换失败返回null
     * @param sheetName
     * @param header
     * @param dataList
     * @return ByteArrayOutputStream
     */
    public static ByteArrayOutputStream OutPutExcel(String sheetName, Map<Integer, String> header, List<Map<Integer, Object>> dataList){

        Workbook book = new HSSFWorkbook();
        if (sheetName == null || sheetName.isEmpty()) {
            sheetName = "sheet1";
        }
        Sheet sheet = book.createSheet(sheetName);
        // 记录每列最大的长度，用于调整列宽
        Map<Integer, Integer> widthMap = new HashMap<>();
        // 填充header
        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        int column = 0;
        for (int i = 0; i< header.size(); i++) {
            Cell cell = headerRow.createCell(column++);
            cell.setCellValue(header.get(i+1));
            widthMap.put(i, header.get(i+1).getBytes().length);
        }
        // 填充数据
        for (Map<Integer, Object> map : dataList) {

            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (int i = 0; i < map.size(); i++) {

                Object value = map.get(i+1);
                Cell cell = row.createCell(colNum++);
                if (value instanceof String) {
                    cell.setCellValue((String)value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer)value);
                } else if (value instanceof Double) {
                    cell.setCellValue((double)value);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((boolean)value);
                } else if (value instanceof Date) {
                    cell.setCellValue((Date)value);
                } else if (value instanceof Byte) {
                    cell.setCellValue((byte)value);
                }else if (value instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) value).doubleValue());
                }
            }
        }
        // 设置列宽
        for (int i = 0; i< widthMap.size(); i++) {
            sheet.setColumnWidth(i, 256 * widthMap.get(i));
        }
        // 转换成byte数据流
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream(1024 * 32);
            book.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 下载流文件
     * @param fileName
     * @param out
     * @param response
     */
    public static void downloadExcel(String fileName, ByteArrayOutputStream out, HttpServletResponse response){
        try {
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));
            response.addHeader("Content-Length", "" + out.toByteArray().length);
            response.setContentType("application/x-excel;charset=UTF-8");
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            toClient.write(out.toByteArray());
            toClient.flush();
            toClient.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
