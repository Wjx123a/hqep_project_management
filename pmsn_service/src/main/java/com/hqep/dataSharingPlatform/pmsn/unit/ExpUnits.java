package com.hqep.dataSharingPlatform.pmsn.unit;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpUnits {


    /**
     *  导出Excel文件 （完善）
     * @param response
     * @param templatePath 模板文件path
     * @param fileName 文件名称
     * @param dataEMap 导出数据
     * @param fieldsMap  导出的与对应表头的 数据库参数
     * @param wrNumMap 开始写入的行数
     * @param wcNumMap 开始写入的列数
     * @param existIndexMap 是否带有序号列
     * @param numCherkMap 数字列校验
     * @return
     */
    public static void expExcelForMapList(
            HttpServletResponse response, String templatePath, String fileName,
            Map<String,List<PageData>> dataEMap, Map<String, String[]> fieldsMap,
            Map<String, Integer> wrNumMap, Map<String, Integer> wcNumMap ,
            Map<String, Boolean> existIndexMap, Map<String, List<Integer>> numCherkMap) {
        try {
            InputStream is = null;
            try {
                is = new FileInputStream(templatePath);
            } catch (FileNotFoundException e) {
                templatePath = "C:/template/数据管理运营月报模板.xlsx";
                is = new FileInputStream(templatePath);
                e.printStackTrace();
            }
            Workbook workbook = WorkbookFactory.create(is);
            CellStyle cellStyle = getCellStyle(workbook,false, 10,null);
            //cellStyle.setWrapText(true);
            Row row;
            Cell cell;
            String cellValue;
            if (dataEMap != null && !dataEMap.isEmpty()) {
                for (Map.Entry<String, List<PageData>> entry : dataEMap.entrySet()) {
                    System.out.println(entry.getKey());
                    Sheet sheet = workbook.getSheet(String.valueOf(entry.getKey()));
                    if (sheet == null) {
                        continue;
                    }
                    List<PageData> dataList = entry.getValue();
                    if (dataList == null || dataList.isEmpty() || dataList.size() < 1) {
                        continue;
                    }
                    int wrNum = 1;
                    if (wrNumMap != null && !wrNumMap.isEmpty() && wrNumMap.get(entry.getKey())!=null) {
                        wrNum = wrNumMap.get(entry.getKey());
                    }
                    int wcNum = 0;
                    if (wcNumMap != null && !wcNumMap.isEmpty() && wcNumMap.get(entry.getKey())!= null) {
                        wcNum = wcNumMap.get(entry.getKey());
                    }
                    boolean existIndex = false;
                    if (existIndexMap != null && !existIndexMap.isEmpty() && existIndexMap.get(entry.getKey())!= null) {
                        existIndex = existIndexMap.get(entry.getKey());
                    }
                    String[] fieldsNames = fieldsMap.get(entry.getKey());
                    for (int i = 0; i < dataList.size(); i++) {
                        Map<String, Object> dataMap = dataList.get(i);
                        row = sheet.createRow((wrNum + i));
                        //设置行高
                        //  row.setHeight((short) 600);
                        for (int j = 0; j < fieldsNames.length; j++) {
                            cell = row.createCell(wcNum+j);
                            if (existIndex && j == 0) {
                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(i + 1);
                            } else {
                                cellValue = String.valueOf(dataMap.get(fieldsNames[j]));
                                if ("null".equals(cellValue)) {
                                    cellValue = "";
                                }
                                cell.setCellStyle(cellStyle);
                                List<Integer> numCheck = null;
                                if(numCherkMap !=null) {
                                    numCheck = numCherkMap.get(entry.getKey());
                                }
                                if (!"".equals(cellValue) && numCheck!= null && !numCheck.isEmpty() && numCheck.contains(j) ) {
                                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                    try {
                                        cell.setCellValue(Double.valueOf(cellValue));
                                    } catch (NumberFormatException e) {
                                        cell.setCellValue(cellValue);
                                    }
                                    continue;
                                }
                                cell.setCellValue(cellValue);
                            }
                        }
                    }
                }
            }
            downLoadExcelForWeb(workbook,fileName,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 前后台分离 使用  HttpServletResponse 下载
     * @param workbook 下载excel文件
     * @param fileName 文件名称
     * @param response HttpServletResponse
     */
    public static void downLoadExcelForWeb( Workbook workbook, String fileName, HttpServletResponse response) {
        try {
            //设置contentType为vnd.ms-excel
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            // 对文件名进行处理。防止文件名乱码
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            // Content-disposition属性设置成以附件方式进行下载
            response.setHeader("Content-disposition", "attachment;filename="+fileName);
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到 Excel cell 样式 工具类
     * @param workbook 所属 workbook
     * @param bold 是否加粗
     * @param fontSize 字体大小
     * @param align 文字局左中右
     * @return
     */
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

    /**
     * 得到 cell 值
     * @param cell cell小格子
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
                    cellValue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 创建下拉列表选项(单元格下拉框数据小于255字节时使用)
     *
     * @param sheet    所在Sheet页面
     * @param values   下拉框的选项值
     * @param firstRow 起始行（从0开始）
     * @param lastRow  终止行（从0开始）
     * @param firstCol 起始列（从0开始）
     * @param lastCol  终止列（从0开始）
     */
    public void createDropDownList(Sheet sheet, String[] values, int firstRow, int lastRow, int firstCol, int lastCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DataValidationConstraint constraint = helper.createExplicitListConstraint(values);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        if (dataValidation instanceof HSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(false);
        } else {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        sheet.addValidationData(dataValidation);
    }

    /**
     * 隐藏Sheet方式创建下拉框(单元格下拉框数据大于255字节时使用)
     *
     * @param sheet  需要添加下拉框的Sheet
     * @param firstRow 起始行
     * @param firstCol 其实列
     * @param endRow   终止行
     * @param endCol   终止列
     * @param dataArray  下拉框数组
     * @param wbCreat    所在excel的WorkBook，用于创建隐藏Sheet
     * @param hidddenSheetName    隐藏Sheet的名称
     * @return
     */
    public void createDropDownListWithHiddenSheet(Sheet sheet, int firstRow, int firstCol, int endRow, int endCol, String[] dataArray, Workbook wbCreat, String hidddenSheetName) {

        Sheet hidden = wbCreat.createSheet(hidddenSheetName);
        Cell cell = null;
        for (int i = 0, length = dataArray.length; i < length; i++) {
            String name = dataArray[i];
            Row row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(name);
        }
        Name namedCell = wbCreat.createName();
        namedCell.setNameName(hidddenSheetName);
        namedCell.setRefersToFormula(hidddenSheetName + "!$A$1:$A$" + dataArray.length);
        //sheet设置为隐藏
        wbCreat.setSheetHidden(wbCreat.getSheetIndex(hidden), true);
        //加载数据,将名称为hidden的
        DataValidationConstraint constraint = null;
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol,
                endCol);
        // 创建 DataValidation
        DataValidation validation = null;
        if (sheet instanceof XSSFSheet || sheet instanceof SXSSFSheet) {
            DataValidationHelper dvHelper = sheet.getDataValidationHelper();
            constraint = dvHelper.createFormulaListConstraint(hidddenSheetName);
            validation = dvHelper.createValidation(constraint, addressList);
        } else {
            constraint = DVConstraint.createFormulaListConstraint(hidddenSheetName);
            validation = new HSSFDataValidation(addressList, constraint);
        }
        if (validation instanceof HSSFDataValidation ) {
            validation .setSuppressDropDownArrow(false);
        } else {
            validation .setSuppressDropDownArrow(true);
            validation .setShowErrorBox(true);
        }
        sheet.addValidationData(validation);
    }


    /**
     * 合并单元格 方法
     * 适用于数据已经写入完成 合并第一列 合并第三行开始 默认没有序号列
     * @param sheet 合并的sheet页
     */
    public static void MergedRegionSheet(Sheet sheet) {
        MergedRegionSheet(sheet, 0, 2, false);
    }

    /**
     * 合并单元格 方法
     * 适用于数据已经写入完成
     * @param sheet 合并的sheet页
     * @param regionIndex 开始合并的最大列
     * @param writeRow 开始合并的行
     * @param indexFlag 是否有序号列
     */
    public static void MergedRegionSheet(Sheet sheet, int regionIndex, int writeRow, boolean indexFlag) {
        if (sheet == null) {
            return;
        }
        // 获取合并操作的最后 最后一行数据数据 (+1 是为了解决 wps 合并单元格 最后一组数据出现重合数据的问题)
        int rowTotal = sheet.getPhysicalNumberOfRows()+1;
        // 合并从 写入行开始  到最后一行结束
        for (int i=writeRow;i<rowTotal;i++) {
            for (int j = regionIndex ; j >=0; j--) {
                if (indexFlag && j == 0) {
                    continue;
                } else {
                    MergedRegionSheet( sheet,  j,  writeRow,  rowTotal,  indexFlag);
                }
            }
        }
    }

    /**
     * 合并单元格 方法
     * 适用于数据已经写入完成 对单列数据进行合并
     * @param sheet
     * @param regionIndex
     * @param regionStartRow
     * @param regionEndRow
     * @param indexFlag
     */
    public static void MergedRegionSheet(Sheet sheet, int regionIndex, int regionStartRow, int regionEndRow, boolean indexFlag) {
        if (sheet == null) {
            return;
        }
        Cell cell;
        Row row;
        int startRow, endRow, startCol, endCol;
        // 如果有序号列（indexFlag==true）
        // 定义写入序号开始
        int indexNum = 1;
        int checkIndexNum = 0;
        if (indexFlag) {
            checkIndexNum = 1;
        }
        // 合并从 写入行开始  到最后一行结束
        for (int i=regionStartRow;i<regionEndRow;i++) {
            // 取第一个cell
            startRow = i;
            endRow = i;
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            cell = row.getCell(regionIndex);
            if (cell == null) {
                continue;
            }
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String temp = cell.getStringCellValue();
            String tempCheck = temp;
            if (checkIndexNum<=regionIndex && regionIndex > 0) {
                cell = sheet.getRow(i).getCell(regionIndex-1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell == null) {
                    continue;
                }
                tempCheck = cell.getStringCellValue();
            }
            for (int j=i+1;j<regionEndRow;j++) {
                // 循环判断下一个cell是否和第一个相同
                row = sheet.getRow(j);
                if (row == null) {
                    row = sheet.createRow(j);
                }
                cell = row.getCell(regionIndex);
                if (cell == null) {
                    endRow = j-1;
                    i = j-1;
                    break;
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String temp2 = cell.getStringCellValue();
                String tempCheck2 = temp2;
                if (checkIndexNum<=regionIndex && regionIndex > 0) {
                    cell = sheet.getRow(j).getCell(regionIndex-1);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (cell == null) {
                        continue;
                    }
                    tempCheck2 = cell.getStringCellValue();
                }
                if (tempCheck != null && tempCheck.equals(tempCheck2) && !"".equals(temp)
                        && temp!= null && temp.equals(temp2) &&!"".equals(temp)) {
                    endRow = j;
                    i = j-1;
                    continue;
                } else {
                    endRow = j-1;
                    i = j-1;
                    break;
                }
            }
            // 如果开始行和结束行号一样
            // 不合并行列，但是判断是否写入序号
            if ( indexFlag && startRow == endRow) {
                cell =  sheet.getRow(startRow).getCell(0);
                if (cell == null) {
                    cell = sheet.getRow(startRow).createCell(0);
                }
                cell.setCellValue(indexNum);
                indexNum++;
                continue;
            } else {
                startCol = regionIndex;
                endCol = regionIndex;
                CellRangeAddress region = new CellRangeAddress(startRow, endRow, startCol, endCol);
                sheet.addMergedRegion(region);
                if (indexFlag && regionIndex == 1) {
                    cell =  sheet.getRow(startRow).getCell(0);
                    if (cell == null) {
                        cell = sheet.getRow(startRow).createCell(0);
                    }
                    cell.setCellValue(indexNum);
                    indexNum++;
                }
            }
        }

    }

    /**
     * 合并单元格 方法
     * 普通合并单元格方法
     * @param sheet 合并sheet页
     * @param startRow 开始行
     * @param endRow 结束行
     * @param startCol 开始列
     * @param endCol 结束列
     */
    public static void MergedRegionSheet(Sheet sheet,  int startRow,int endRow,int startCol,int endCol) {
        CellRangeAddress region = new CellRangeAddress(startRow, endRow, startCol, endCol);
        sheet.addMergedRegion(region);
    }


    /**
     * 根据列 获取 abcd列名
     * @param columnNum
     * @return
     */
    private static String getColumnName(int columnNum) {
        int first;

        int last;

        String result = "";

        if (columnNum > 256)

            columnNum = 256;

        first = columnNum / 27;

        last = columnNum - (first * 26);

        if (first > 0)

            result = String.valueOf((char) (first + 64));

        if (last > 0)

            result = result + String.valueOf((char) (last + 64));

        return result;

    }


}
