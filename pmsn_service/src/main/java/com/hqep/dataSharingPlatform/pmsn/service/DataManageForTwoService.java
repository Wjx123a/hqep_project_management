package com.hqep.dataSharingPlatform.pmsn.service;

import com.hqep.dataSharingPlatform.common.utils.PageData;

/**
 * @program: hqep_project_management
 * @ClassName DataManageForTwoService
 * @author: sssJL
 * @create: 2022-11-01 13:09
 * @Version V1.0
 * @description:
 **/
public interface DataManageForTwoService {


    /**
     * 查询关系表
     * @param pd
     * @return
     */
    int query_t_up_catalog_table_rel(PageData pd);

    /**
     * 查询系统表
     * @param pd
     * @return
     */
    int query_t_up_system(PageData pd);

    /**
     * 查询表信息表
     * @param pd
     * @return
     */
    int query_t_up_table(PageData pd);

    /**
     * 查询列信息表
     * @param pd
     * @return
     */
    int query_t_up_column(PageData pd);


    /**
     * 插入关系表
     * @param pd
     * @return
     */
    int insert_t_up_catalog_table_rel(PageData pd);

    /**
     * 插入系统表
     * @param pd
     * @return
     */
    int insert_t_up_system(PageData pd);

    /**
     * 插入表信息表
     * @param pd
     * @return
     */
    int insert_t_up_table(PageData pd);

    /**
     * 插入列信息表
     * @param pd
     * @return
     */
    int insert_t_up_column(PageData pd);
}
