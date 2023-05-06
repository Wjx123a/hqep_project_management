package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

import java.util.List;

public interface UpPlateformService {


    List<PageData> queryProcess();

    List<PageData> queryApplication();
    List<PageData> queryApplication_new();

    List<PageData> queryApplicationDiff();
    List<PageData> queryApplicationDiff_new();

    List<PageData> queryDemandTable();
    List<PageData> queryDemandTable_new();

    void instApplication(PageData pd);
    void instApplication_new(PageData pd);

    void instDemandTable(PageData pd);
    void instDemandTable_new(PageData pd);

    void instProcess(PageData pd);

    List<PageData> queryProcessBh();

    void updateProcessBh(PageData pd);

    void delProcess(PageData pd);

    void delProcessLzz(PageData pd);

    void updateApplication(PageData pd);
    void updateApplication_new(PageData pd);


    /**
     * 执行省侧统计结果
     * 用于生成 上报国网统计表
     * 方便张一飞省侧自查数据
     */
    void insert_merge_t_dm_province_catalog_num();

//    void inser_t_dm_province_catalog_num();
//
//    void select_t_dm_province_catalog_num();
//
//    void update_t_dm_province_catalog_num();

    void  inser_t_dm_zb_catalog_num_check();

    void  inser_t_dm_zb_catalog_num_cl();


}



















