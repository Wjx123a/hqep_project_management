package com.hqep.dataSharingPlatform.sjkflc.service.Impl;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.dao.ReportCenterDao;
import com.hqep.dataSharingPlatform.sjkflc.model.CreateTableAndTabbleName;
import com.hqep.dataSharingPlatform.sjkflc.service.ReportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.PageData2Web;
import utils.PageDataCheckParams;

import java.util.List;

@Service
public class ReportCenterServiceImpl implements ReportCenterService {

    @Autowired
    private ReportCenterDao reportCenterDao;
    /***
     * 创建表
     * @return
     */
    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_2)
    public int createTable(CreateTableAndTabbleName model) {
        return reportCenterDao.createTable(model);
    }

    /***
     * 查询表是否存在
     * @return
     */
    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_2)
    public List<PageData> queryExistsTable(PageData pd) {
        return reportCenterDao.queryExistsTable(pd);
    }



    /***
     * 授权表
     * @return
     */
    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_2)
    public PageData grantQueryTableForUser(PageData pd)  {
        PageData resultPd = new PageData();
        boolean flag = false;
        try {
            // grant select on table ${dbname}.${tablename} to ${username}@'%';
            // grant select on table extractdb.${tablename} to qqhr@'%';
            if(PageDataCheckParams.checkParamNull(pd,"dbname")) {
                pd.put("dbname","extractdb");
            } else {
                pd.put("dbname","fixed_report");
            }
            String params[] = {"tablename","username","dbname"};
            if(PageDataCheckParams.checkParamListNull(pd,params)) {
                resultPd = PageData2Web.WebForBooleanObj(flag,"相关参数不能为空，请检查参数！");
                return resultPd;
            }
            if (pd.get("tablename") != null && !"".equals(pd.get("tablename"))) {
                if (pd.get("tablename").toString().indexOf(";")>-1) {
                    resultPd = PageData2Web.WebForBooleanObj(flag,"授权表名不合规，请与业务人员联系！");
                    return resultPd;
                }
            }
            int i = reportCenterDao.grantQueryTableForUser(pd);
            System.out.println(i);
            flag = true;
            resultPd = PageData2Web.WebForBooleanObj(flag,"授权成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultPd = PageData2Web.WebForBooleanObj(flag,"授权表失败，请与业务人员联系！");
        }
        return resultPd;
    }
    /***
     * 取消授权表
     * @return
     */
    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_2)
    public PageData revokeQueryTableForUser(PageData pd) {
        PageData resultPd = new PageData();
        boolean flag = false;
        try {
            if(PageDataCheckParams.checkParamNull(pd,"dbname")) {
                pd.put("dbname","extractdb");
            } else {
                pd.put("dbname","fixed_report");
            }
            String params[] = {"tablename","username","dbname"};
            if(PageDataCheckParams.checkParamListNull(pd,params)) {
                resultPd = PageData2Web.WebForBooleanObj(flag,"相关参数不能为空，请检查参数！");
                return resultPd;
            }
            if (pd.get("tablename") != null && !"".equals(pd.get("tablename"))) {
                if (pd.get("tablename").toString().indexOf(";")>-1) {
                    resultPd = PageData2Web.WebForBooleanObj(flag,"取消授权表名不合规，请与业务人员联系！");
                    return resultPd;
                }
            }
            int i = reportCenterDao.revokeQueryTableForUser(pd);
            System.out.println(i);
            flag = true;
            resultPd = PageData2Web.WebForBooleanObj(flag,"取消授权成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultPd = PageData2Web.WebForBooleanObj(flag,"取消授权表失败，请与业务人员联系！");
        }
        return resultPd;
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public int inertGrantForLogs(PageData pd) {
        return reportCenterDao.inertGrantForLogs(pd);
    }

}
