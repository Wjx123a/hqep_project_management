package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.pmsn.dao.AllTableDao;
import com.hqep.dataSharingPlatform.pmsn.service.AllTableService;
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
public class AllTableServiceImpl implements AllTableService {

    private final static String XTMC_ERROR = "第[%s]行, 系统名称不能为空！";
    private final static String SSBM_ERROR = "第[%s]行, 所属部门不能为空！";
    private final static String YH_ERROR = "第[%s]行, 用户不能为空！";
    private final static String YWBM_ERROR = "第[%s]行, 英文表名不能为空！";
    private final static String ZWBM_ERROR = "第[%s]行, 中文表名不能为空！";
    private final static String BJS_ERROR = "第[%s]行, 表解释不能为空！";
    private final static String YWZDMC_ERROR = "第[%s]行, 字段英文名称不能为空！";
    private final static String ZWZDMC_ERROR = "第[%s]行, 字段中文名称不能为空！";
    private final static String ZDJS_ERROR = "第[%s]行, 字段解释不能为空！";
    private final static String ZDLX_ERROR = "第[%s]行, 字段类型不能为空！";
    private final static String ZDCD_ERROR = "第[%s]行, 字段长度不能为空！";
    private final static String SFWK_ERROR = "第[%s]行, 是否为空不能为空！";

    @Autowired
    private AllTableDao dao;

    @Override
    public List<PageData> queryParmList(PageData pd) throws ServiceException {
        try {
            return dao.queryParmList(pd);
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 360000, rollbackFor = Exception.class)
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
            // 所属部门
//            Cell ssbmCell = row.getCell(++column);
//            if (ssbmCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(ssbmCell))) {
//                String errMsg = String.format(SSBM_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 用户
//            Cell yhCell = row.getCell(++column);
//            if (yhCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(yhCell))) {
//                String errMsg = String.format(YH_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }

            // 英文表名
//            Cell ywbmCell = row.getCell(++column);
//            if (ywbmCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(ywbmCell))) {
//                String errMsg = String.format(YWBM_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 中文表名
//            Cell zwbmCell = row.getCell(++column);
//            if (zwbmCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zwbmCell))) {
//                String errMsg = String.format(ZWBM_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 表解释
//            Cell bjsCell = row.getCell(++column);
//            if (bjsCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(bjsCell))) {
//                String errMsg = String.format(BJS_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 英文字段名称
//            Cell ywzdmcCell = row.getCell(++column);
//            if (ywzdmcCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(ywzdmcCell))) {
//                String errMsg = String.format(YWZDMC_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 中文字段名称
//            Cell zwzdmcCell = row.getCell(++column);
//            if (zwzdmcCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zwzdmcCell))) {
//                String errMsg = String.format(ZWZDMC_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 字段解释
//            Cell zdjsCell = row.getCell(++column);
//            if (zdjsCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zdjsCell))) {
//                String errMsg = String.format(ZDJS_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 字段类型
//            Cell zdlxCell = row.getCell(++column);
//            if (zdlxCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zdlxCell))) {
//                String errMsg = String.format(ZDLX_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 字段长度
//            Cell zdcdCell = row.getCell(++column);
//            if (zdcdCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(zdcdCell))) {
//                String errMsg = String.format(ZDCD_ERROR, rowNum+1);
//                errorMessage.add(errMsg);
//            }
            // 是否为空
//            Cell sfwkCell = row.getCell(++column);
//            if (sfwkCell == null || Strings.isNullOrEmpty(ExcelUtil.parseExcel(sfwkCell))) {
//                String errMsg = String.format(SFWK_ERROR, rowNum+1);
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
//            pageData.put("xtmc", ExcelUtil.parseExcel(row.getCell(colNum)).trim());
            String xtmcValue = ExcelUtil.parseExcel(row.getCell(colNum));
            if (null == xtmcValue) {
                xtmcValue = "";
            }
            pageData.put("xtmc", xtmcValue.trim());

            // 所属部门
//            pageData.put("ssbm", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String ssbmValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == ssbmValue) {
                ssbmValue = "";
            }
            pageData.put("ssbm", ssbmValue.trim());

            // 用户
//            pageData.put("yh", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String yhValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == yhValue) {
                yhValue = "";
            }
            pageData.put("yh", yhValue.trim());

            // 英文表名
//            pageData.put("ywbm", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String ywbmValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == ywbmValue) {
                ywbmValue = "";
            }
            pageData.put("ywbm", ywbmValue.trim());

            // 中文表名
//            pageData.put("zwbm", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String zwbmValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zwbmValue) {
                zwbmValue = "";
            }
            pageData.put("zwbm", zwbmValue.trim());

            // 表解释
//            pageData.put("bjs", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String bjsValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == bjsValue) {
                bjsValue = "";
            }
            pageData.put("bjs", bjsValue.trim());

            // 英文字段名称
//            pageData.put("ywzdmc", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String ywzdmcValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == ywzdmcValue) {
                ywzdmcValue = "";
            }
            pageData.put("ywzdmc", ywzdmcValue.trim());

            // 中文字段名称
//            pageData.put("zwzdmc", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String zwzdmcValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zwzdmcValue) {
                zwzdmcValue = "";
            }
            pageData.put("zwzdmc", zwzdmcValue.trim());

            // 字段解释
//            pageData.put("zdjs", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String zdjsValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zdjsValue) {
                zdjsValue = "";
            }
            pageData.put("zdjs", zdjsValue.trim());

            // 字段类型
//            pageData.put("zdlx", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String zdlxValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zdlxValue) {
                zdlxValue = "";
            }
            pageData.put("zdlx", zdlxValue.trim());

            // 字段长度
//            pageData.put("zdcd", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String zdcdValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == zdcdValue) {
                zdcdValue = "";
            }
            pageData.put("zdcd", zdcdValue.trim());

            // 是否为空
//            pageData.put("sfwk", ExcelUtil.parseExcel(row.getCell(++colNum)).trim());
            String sfwkValue = ExcelUtil.parseExcel(row.getCell(++colNum));
            if (null == sfwkValue) {
                sfwkValue = "";
            }
            pageData.put("sfwk", sfwkValue.trim());

            dataList.add(pageData);
        }
        return dataList;
    }

    @Override
    public int queryCount(String param) {
        return dao.queryCount(param);
    }

    @Override
    public int queryListCount(PageData pd) throws ServiceException {
        try {
            return dao.queryListCount(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    //获取数据标签
    @Override
    public List querbq(String table_name) throws ServiceException {
        return dao.querybq(table_name);
    }
    //获取数据标签
    @Override
    public String querxxbq(String table_name) throws ServiceException {
        return dao.queryxxbq(table_name);
    }

    /**
     * 20210302第二次修改
     */

    // 根据系统查询表信息
    @Override
    public List<PageData> queryTableListBySytem(PageData pd) throws ServiceException {
        try {
            return dao.queryTableListBySytem(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 根据系统查询表信息总条数
    @Override
    public int queryTableListBySytemCount(PageData pd) throws ServiceException {
        try {
            return dao.queryTableListBySytemCount(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 根据系统查询表信息-详情
    @Override
    public List<PageData> queryTableListBySytemDetail(PageData pd) throws ServiceException {
        try {
            return dao.queryTableListBySytemDetail(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    //    // 一级目录=》 根据系统查询表信息-详情
    @Override
    public List<PageData> queryTableListByOneSytemDetail(PageData pd) throws ServiceException {
        try {
            return dao.queryTableListByOneSytemDetail(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询数据表的总计条数(申请热度) 暂时没用
    @Override
    public int queryTableAllApplyCount(PageData pd) throws ServiceException {
        try {
            return dao.queryTableAllApplyCount(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询当前表是否已经收藏
    @Override
    public int queryIsCollection(PageData pd) throws ServiceException {
        try {
            return dao.queryIsCollection(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    // 查询当前表是否已经收藏  贴源层使用
    @Override
    public int queryIsCollectionTyc(PageData pd) throws ServiceException {
        try {
            return dao.queryIsCollectionTyc(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询共享层表信息
    @Override
    public List<PageData> queryGxcTableList(PageData pd) throws ServiceException {
        try {
            return dao.queryGxcTableList(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 查询共享层表信息-字段
    @Override
    public List<PageData> queryGxcTableListColumn(PageData pd) throws ServiceException {
        try {
            return dao.queryGxcTableListColumn(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // 贴源层是否接入表
    @Override
    public int queryZjztTableCount(PageData pd) throws ServiceException {
        try {
            return dao.queryZjztTableCount(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    //
    // 一级目录系统是否接入表
    @Override
    public int queryOneSysZjztTableCount(PageData pd) throws ServiceException {
        try {
            return dao.queryOneSysZjztTableCount(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void insertrc(PageData pd) throws ServiceException {
        dao.insertrc(pd);
    }
    //搜索热词
    @Override
    public List<PageData> queryrc(PageData pd) throws ServiceException {
        return dao.queryrc(pd);
    }

    /**
     * 根据贴源层查询表信息
     */
    @Override
    public List<PageData> queryTableListByTYCSystem(PageData pd) {
        try {
            return dao.queryTableListByTYCSystem(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据共享层查询表信息
     */
    @Override
    public List<PageData> queryTableListByGXCXTY(PageData pd) {
        try {
            return dao.queryTableListByGXCXTY(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据分析层查询表的详情信息
     */
    @Override
    public List<PageData> queryTableListByFXCXTYForDetails(PageData pd) {
        try {
            return dao.queryTableListByFXCXTYForDetails(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据贴源层查询表的详情信息
     */
    @Override
    public List<PageData> queryTableListByTYCXTYForDetails(PageData pd){
        try {
            return dao.queryTableListByTYCXTYForDetails(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据共享层查询表的详情信息
     */
    @Override
    public List<PageData> queryTableListByGXCXTYForDetails(PageData pd) {
        try {
            return dao.queryTableListByGXCXTYForDetails(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据共享层查询表信息求和
     */
    @Override
    public int queryTableListByGXCXTYCount(PageData pd) {
        try {
            return dao.queryTableListByGXCXTYCount(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询 共享层的表是否已经收藏
     */
    @Override
    public int queryIsCollectionByGXCXTY(PageData pd) throws ServiceException {
        try {
            return dao.queryIsCollectionByGXCXTY(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 根据分析层查询表信息
     */
    @Override
    public List<PageData> queryTableListByFXCXTY(PageData pd) {
        try {
            return dao.queryTableListByFXCXTY(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询 共享层的表是否已经收藏
     */
    @Override
    public int queryIsCollectionByFXCXTY(PageData pd) throws ServiceException {
        try {
            return dao.queryIsCollectionByFXCXTY(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 查询 查询一级目录是否已经收藏
     */
    @Override
    public int queryIsCollectionForOneSys(PageData pd) throws ServiceException {
        try {
            return dao.queryIsCollectionForOneSys(pd);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
