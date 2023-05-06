package com.hqep.dataSharingPlatform.pmsn.dao;

import com.hqep.dataSharingPlatform.common.multipleData.sssjlDataSource;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UpPlateformDao {

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryApplication();
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryApplication_new();

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryApplicationDiff();
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryApplicationDiff_new();

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void instApplication(PageData pd);
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void instApplication_new(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryProcess();

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void instProcess(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryDemandTable();
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryDemandTable_new();


    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void instDemandTable(PageData pd);
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void instDemandTable_new(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    List<PageData> queryProcessBh();

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void updateProcessBh(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void delProcess(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void delProcessLzz(PageData pd);

    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void updateApplication(PageData pd);
    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_1)
    void updateApplication_new(PageData pd);

    /**
     * 执行省侧统计结果
     * 用于生成 上报国网统计表
     * 方便张一飞省侧自查数据
     */
    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    void insert_merge_t_dm_province_catalog_num();
//   @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
//    void inser_t_dm_province_catalog_num();
//
//    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
//    void select_t_dm_province_catalog_num();
//
//    @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
//    void update_t_dm_province_catalog_num();

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    void inser_t_dm_zb_catalog_num_check();

    // 服务器迁移标志=====
    // @sssjlDataSource(name=sssjlDataSource.DATA_SOURCE_3)
    void inser_t_dm_zb_catalog_num_cl();

}
