package com.hqep.dataSharingPlatform.pmsn.service.impl;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.dao.DataManageForTwoDao;
import com.hqep.dataSharingPlatform.pmsn.service.DataManageForTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: hqep_project_management
 * @ClassName DataManageForTwoServiceImpl
 * @author: sssJL
 * @create: 2022-11-01 13:09
 * @Version V1.0
 * @description:
 **/
@Service
public class DataManageForTwoServiceImpl implements DataManageForTwoService {

    @Autowired
    DataManageForTwoDao dataManageForTwoDao;

    @Override
    public int query_t_up_catalog_table_rel(PageData pd) {
        return dataManageForTwoDao.query_t_up_catalog_table_rel(pd);
    }

    @Override
    public int query_t_up_system(PageData pd) {
        return dataManageForTwoDao.query_t_up_system(pd);
    }

    @Override
    public int query_t_up_table(PageData pd) {
        return dataManageForTwoDao.query_t_up_table(pd);
    }

    @Override
    public int query_t_up_column(PageData pd) {
        return dataManageForTwoDao.query_t_up_column(pd);
    }


    /**
     * 插入关系表
     * @param pd
     * @return
     */
    @Override
    public int insert_t_up_catalog_table_rel(PageData pd){
        return dataManageForTwoDao.insert_t_up_catalog_table_rel(pd);
    }

    /**
     * 插入系统表
     * @param pd
     * @return
     */
    @Override
    public int insert_t_up_system(PageData pd){
        return dataManageForTwoDao.insert_t_up_system(pd);
    }

    /**
     * 插入表信息表
     * @param pd
     * @return
     */
    @Override
    public int insert_t_up_table(PageData pd){
        return dataManageForTwoDao.insert_t_up_table(pd);
    }

    /**
     * 插入列信息表
     * @param pd
     * @return
     */
    @Override
    public int insert_t_up_column(PageData pd){
        return dataManageForTwoDao.insert_t_up_column(pd);
    }
}
