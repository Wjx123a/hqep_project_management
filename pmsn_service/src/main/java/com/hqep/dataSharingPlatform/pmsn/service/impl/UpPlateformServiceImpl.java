package com.hqep.dataSharingPlatform.pmsn.service.impl;


import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.QueryGdDao;
import com.hqep.dataSharingPlatform.pmsn.dao.UpPlateformDao;
import com.hqep.dataSharingPlatform.pmsn.service.UpPlateformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpPlateformServiceImpl implements UpPlateformService {

    @Autowired
    private UpPlateformDao dao;
    @Autowired
    private QueryGdDao gdDao;


    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryProcess() {
        return dao.queryProcess();
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryApplication() {
        return dao.queryApplication();
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryApplication_new() {
        return dao.queryApplication_new();
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryApplicationDiff() {
        return dao.queryApplicationDiff();
    }
    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryApplicationDiff_new() {
        return dao.queryApplicationDiff_new();
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryDemandTable() {
        return dao.queryDemandTable();
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryDemandTable_new() {
        return dao.queryDemandTable_new();
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void instApplication(PageData pd) {
        dao.instApplication(pd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public  void instApplication_new(PageData pd) {
        dao.instApplication_new(pd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void instDemandTable(PageData pd) {
        dao.instDemandTable(pd);
        // 添加同步国网表标记
        PageData gdPd = new PageData("lcspid", pd.getString("lcspid"));
        gdDao.updateWorkOrder(gdPd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void instDemandTable_new(PageData pd) {
        dao.instDemandTable_new(pd);
        // 添加同步国网表标记
        PageData gdPd = new PageData("lcspid", pd.getString("lcspid"));
        gdDao.updateWorkOrder_new(gdPd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void instProcess(PageData pd) {
        dao.instProcess(pd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public List<PageData> queryProcessBh() {
        return dao.queryProcessBh();
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void updateProcessBh(PageData pd) {
        dao.updateProcessBh(pd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void delProcess(PageData pd) {
        dao.delProcess(pd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void delProcessLzz(PageData pd) {
        dao.delProcessLzz(pd);
    }

    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void updateApplication(PageData pd) {
        dao.updateApplication(pd);
    }
    @Override
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    public void updateApplication_new(PageData pd) {
        dao.updateApplication_new(pd);
    }



    /**
     * 执行省侧统计结果
     * 用于生成 上报国网统计表
     * 方便张一飞省侧自查数据
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public  void insert_merge_t_dm_province_catalog_num() {
        dao.insert_merge_t_dm_province_catalog_num();
    }
//    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
//    @Override
//    public  void inser_t_dm_province_catalog_num() {
//        dao.inser_t_dm_province_catalog_num();
//    }
//    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
//    @Override
//    public  void select_t_dm_province_catalog_num() {
//        dao.select_t_dm_province_catalog_num();
//    }
//    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
//    @Override
//    public  void update_t_dm_province_catalog_num() {
//        dao.update_t_dm_province_catalog_num();
//    }
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public  void inser_t_dm_zb_catalog_num_check() {
        dao.inser_t_dm_zb_catalog_num_check();
    }

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    @Override
    public  void inser_t_dm_zb_catalog_num_cl() {
        dao.inser_t_dm_zb_catalog_num_cl();
    }


}
