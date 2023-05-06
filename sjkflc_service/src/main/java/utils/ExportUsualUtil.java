package utils;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * create by sssJL
 * create date 2019-10-29
 * Description 导出文件
 */
public class ExportUsualUtil {


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
     * 导出文件
     * @param dataList 导出数据
     * @param titleNames 表头中文
     * @param fieldsNames 表头对应字段名
     * @param exportPath 导出路径
     * @param fileName 文件名称
     */
    public boolean exportExcel(List<Map<String,Object>> dataList, String[] titleNames, String[] fieldsNames,
                               String exportPath , String fileName) {
        OutputStream outputStream;
        try {
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            /*设置行高*/
            sheet.setDefaultRowHeight((short)300);
            workbook.setSheetName(0,"明细数据");
            CellStyle titleStyle = getCellStyle(workbook,true,12,null);
            CellStyle infoStyle = getCellStyle(workbook,false,12,"left");
            // 写入第一行标题
            Row row = sheet.createRow(0);
            row.setHeightInPoints(30);
            Cell cell = row.createCell(0);
            cell.setCellValue(fileName.replace(".xlsx","").replace(".xls",""));
            cell.setCellStyle(titleStyle);
            // 合并单元格
            CellRangeAddress rangeAddress = new CellRangeAddress(0,0,0,titleNames.length);
            sheet.addMergedRegion(rangeAddress);
            // 写入表头行
            row = sheet.createRow(1);
            row.setHeightInPoints(20);
            // 写入第一行第一列 =》序号
            cell = row.createCell(0);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("序号");
            for (int j = 0; j < titleNames.length; j++) {
                cell = row.createCell(j+1);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleNames[j]);
            }
            // 写入内容 遍历数据List
            for (int i = 0; i < dataList.size(); i++) {
                row = sheet.createRow(i + 2);
                //写入序号
                cell = row.createCell(0);
                cell.setCellStyle(infoStyle);
                cell.setCellValue(i+1);
                for (int k = 0; k < fieldsNames.length; k++) {
                    cell = row.createCell(k+1);
                    cell.setCellStyle(infoStyle);
                    String temp = String.valueOf(dataList.get(i).get(fieldsNames[k]));
                    if (temp == null || "".equals(temp) || "00000000".equals(temp)
                            || "null".equals(temp) || "NULL".equals(temp)) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(temp);
                    }
                }
            }
            // 必须在单元格设值以后进行
            // 设置为根据内容自动调整列宽
            for (int n = 0; n < titleNames.length; n++) {
                sheet.autoSizeColumn(n);
            }
            File fileFolder = new File(exportPath);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            File expfile = new File(fileFolder, fileName);
            outputStream = new FileOutputStream(expfile);
            workbook.write(outputStream);
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 导出文件
     * @param dataList 导出数据
     * @param titleNames 表头中文
     * @param fieldsNames 表头对应字段名
     * @param exportPath 导出路径
     * @param fileName 文件名称
     */
    public boolean exportExcel(List<Map<String,Object>> dataList, String[] titleNames, String[] fieldsNames,
                               String exportPath , String fileName, HttpServletResponse response) {
        OutputStream outputStream;
        try {
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            /*设置行高*/
            sheet.setDefaultRowHeight((short)300);
            workbook.setSheetName(0,"明细数据");
            CellStyle titleStyle = getCellStyle(workbook,true,12,null);
            CellStyle infoStyle = getCellStyle(workbook,false,12,"left");
            // 写入第一行标题
            Row row = sheet.createRow(0);
            row.setHeightInPoints(30);
            Cell cell = row.createCell(0);
            cell.setCellValue(fileName.replace(".xlsx","").replace(".xls",""));
            cell.setCellStyle(titleStyle);
            // 合并单元格
            CellRangeAddress rangeAddress = new CellRangeAddress(0,0,0,titleNames.length);
            sheet.addMergedRegion(rangeAddress);
            // 写入表头行
            row = sheet.createRow(1);
            row.setHeightInPoints(20);
            // 写入第一行第一列 =》序号
            cell = row.createCell(0);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("序号");
            for (int j = 0; j < titleNames.length; j++) {
                cell = row.createCell(j+1);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleNames[j]);
            }
            // 写入内容 遍历数据List
            for (int i = 0; i < dataList.size(); i++) {
                row = sheet.createRow(i + 2);
                //写入序号
                cell = row.createCell(0);
                cell.setCellStyle(infoStyle);
                cell.setCellValue(i+1);
                for (int k = 0; k < fieldsNames.length; k++) {
                    cell = row.createCell(k+1);
                    cell.setCellStyle(infoStyle);
                    String temp = String.valueOf(dataList.get(i).get(fieldsNames[k]));
                    if (temp == null || "".equals(temp) || "00000000".equals(temp)
                            || "null".equals(temp) || "NULL".equals(temp)) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue(temp);
                    }
                }
            }
            // 必须在单元格设值以后进行
            // 设置为根据内容自动调整列宽
            for (int n = 0; n < titleNames.length; n++) {
                sheet.autoSizeColumn(n);
            }
            File fileFolder = new File(exportPath);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            //设置contentType为vnd.ms-excel
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            // 对文件名进行处理。防止文件名乱码
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

            // Content-disposition属性设置成以附件方式进行下载
            response.setHeader("Content-disposition", "attachment;filename="+fileName);
            //调取response对象中的OutputStream对象
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static CellStyle getCellStyle(Workbook workbook, boolean bold, int fontSize,String align) {
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置这些样式
        // 文字位置 左 右 中
        if ("left".equals(align)) {
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        } else if ("right".equals(align)) {
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        } else {
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        }
        //垂直居中
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //边框
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        //自动换行
        cellStyle.setWrapText(false);
        // 生成一个字体
        Font font = workbook.createFont();
        // 字体大小
        font.setFontHeightInPoints((short) fontSize);
        if (bold) {
            // 加粗
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        }
        // 字体
        font.setFontName("宋体");
        // 把字体应用到当前的样式
        cellStyle.setFont(font);
        return cellStyle;

    }
}
