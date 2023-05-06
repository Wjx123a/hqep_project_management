package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.pmsn.service.FmqdService;
import com.hqep.dataSharingPlatform.pmsn.dao.FmqdDao;
import com.hqep.dataSharingPlatform.common.utils.ExcelUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.rpc.ServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FmqdServiceImpl implements FmqdService {

    private final static String XTMC_ERROR = "第[%s]行, 系统名称不能为空！";
    private final static String MGXX_ERROR = "第[%s]行, 敏感信息不能为空！";
    private final static String XTYH_ERROR = "第[%s]行, 系统用户不能为空！";
    private final static String SJBYWMC_ERROR = "第[%s]行, 数据表英文名称不能为空！";
    private final static String SJBZS_ERROR = "第[%s]行, 数据表表注释不能为空！";
    private final static String SJBZWMC_ERROR = "第[%s]行, 数据表中文名称不能为空！";
    private final static String ZDYWMC_ERROR = "第[%s]行, 字段英文名称不能为空！";
    private final static String ZDZWMC_ERROR = "第[%s]行, 字段中文名称不能为空！";
    private final static String ZDZS_ERROR = "第[%s]行, 字段注释不能为空！";
    private final static String MGZDLX_ERROR = "第[%s]行, 敏感字段类型不能为空！";

    @Autowired
    private FmqdDao dao;

    /**
     * 负面清单页面查询条件系统名称
     * @param pd
     * @return
     * @throws ServiceException
     */
    @Override
    public List<PageData> queryParmSystemName(PageData pd) throws ServiceException {
        try {
            return dao.queryParmSystemName(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public List<PageData> queryParmList(PageData pd) throws ServiceException {
        try {
            return dao.queryParmList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    /**
     * 负面清单页面表格查询 导出数据
     * @param pd
     * @return
     * @throws ServiceException
     */
    @Override
    public List<PageData> queryParmListForExcel(PageData pd) throws ServiceException {
        try {
            return dao.queryParmListForExcel(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PageData> queryParmList_column(PageData pd) throws ServiceException {
        try {
            return dao.queryParmList_column(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateParm(PageData pd) throws ServiceException {
        try {
            dao.updateParm(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 逐条添加保存(暂时没用)
    @Override
    public boolean insertPerCon(List<Map<String,Object>> list) {
        boolean flag = false;
        for (Map<String,Object> map:
                list) {
            flag = dao.insertPerCon(map);
        }
        return flag;
    }

    // 批量添加保存
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void insertPlan(List<PageData> list) throws ServiceException {
        try {
            dao.insertPlan(list);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> errorMsg(Sheet sheet) {
        List<String> errorMessage = new ArrayList<>();
        // 行数
        int rowNum, column;
        int rows = sheet.getPhysicalNumberOfRows();
        if (rows == 1) {
            errorMessage.add("文件中数据不存在! 请添加后导入");
        }
        // 错误信息
        // 检查excel文件
        for (Row row : sheet) {
            // 记录行号
            rowNum = row.getRowNum();
            // 跳过表头
            if(rowNum == 0){
                continue;
            }
            // 初始列号
            column = 0;
            // 系统名称
//            Cell xtmcCell = row.getCell(column);
//            if (xtmcCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(xtmcCell))) {
//                String errMsg = String.format(XTMC_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 敏感信息
//            Cell mgxxCell = row.getCell(++column);
//            if (mgxxCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(mgxxCell))) {
//                String errMsg = String.format(MGXX_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 系统用户
//            Cell xtyhCell = row.getCell(++column);
//            if (xtyhCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(xtyhCell))) {
//                String errMsg = String.format(XTYH_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 数据表英文名称
//            Cell sjbywmcCell = row.getCell(++column);
//            if (sjbywmcCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(sjbywmcCell))) {
//                String errMsg = String.format(SJBYWMC_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 数据表注释
//            Cell sjbzsCell = row.getCell(++column);
//            if (sjbzsCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(sjbzsCell))) {
//                String errMsg = String.format(SJBZS_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 数据表中文名称
//            Cell zjbzwmcCell = row.getCell(++column);
//            if (zjbzwmcCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zjbzwmcCell))) {
//                String errMsg = String.format(SJBZWMC_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 字段英文名称
//            Cell zdywmcCell = row.getCell(++column);
//            if (zdywmcCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zdywmcCell))) {
//                String errMsg = String.format(ZDYWMC_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 字段中文名称
//            Cell zdzwmcCell = row.getCell(++column);
//            if (zdzwmcCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zdzwmcCell))) {
//                String errMsg = String.format(ZDZWMC_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 字段注释
//            Cell zdzsCell = row.getCell(++column);
//            if (zdzsCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zdzsCell))) {
//                String errMsg = String.format(ZDZS_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 敏感字段类型
//            Cell mgzdlxCell = row.getCell(++column);
//            if (mgzdlxCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(mgzdlxCell))) {
//                String errMsg = String.format(MGZDLX_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
        }
        return errorMessage;
    }

    @Override
    public List<PageData> buildList(Sheet sheet) throws ServiceException {
        PageData pd = new PageData();
        List<PageData> dataList = new ArrayList<>();

        // 初始行号, 列号
        int rowNum, colNum;
        // 组装数据
        for (Row row : sheet) {
            // 记录行号
            rowNum = row.getRowNum();
            // 跳过表头
            if(rowNum == 0){
                continue;
            }
            // 清除列号
            colNum = 0;
            PageData pageData = new PageData();

            // 系统名称
            String xtmcValue = ExcelUtil.parseExcel(row.getCell(colNum));
            if (null == xtmcValue) {
                xtmcValue = "";
            }
            pageData.put("xtmc", xtmcValue.trim());

            // 敏感信息
            String mgxxValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == mgxxValue) {
                mgxxValue = "";
            }
            pageData.put("mgxx", mgxxValue.trim());

            // 系统用户
            String xtyhValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == xtyhValue) {
                xtyhValue = "";
            }
            pageData.put("xtyh", xtyhValue.trim());

            // 数据表英文名称
            String sjbywmcValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == sjbywmcValue) {
                sjbywmcValue = "";
            }
            pageData.put("sjbywmc", sjbywmcValue.trim());

            // 数据表注释
            String sjbzsValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == sjbzsValue) {
                sjbzsValue = "";
            }
            pageData.put("sjbzs", sjbzsValue.trim());

            // 数据表中文名称
            String sjbzwmcValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == sjbzwmcValue) {
                sjbzwmcValue = "";
            }
            pageData.put("sjbzwmc", sjbzwmcValue.trim());

            // 字段英文名称
            String zdywmcValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zdywmcValue) {
                zdywmcValue = "";
            }
            pageData.put("zdywmc", zdywmcValue.trim());

            // 字段中文名称
            String zdzwmcValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zdzwmcValue) {
                zdzwmcValue = "";
            }
            pageData.put("zdzwmc", zdzwmcValue.trim());

            // 字段注释
            String zdzsValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zdzsValue) {
                zdzsValue = "";
            }
            pageData.put("zdzs", zdzsValue.trim());

            // 敏感字段类型
            String mgzdlxValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == mgzdlxValue) {
                mgzdlxValue = "";
            }
            pageData.put("mgzdlx", mgzdlxValue.trim());

            dataList.add(pageData);
        }
        return dataList;
    }

    @Override
    public int queryCount(String param) {
        return dao.queryCount(param);
    };
}
