package com.hqep.dataSharingPlatform.pmsn.job;


import com.hqep.dataSharingPlatform.pmsn.service.UpPlateformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * create by sssjl
 * create time 2022/06/01 17:29
 * 两级贯通的 新增的增量统计表
 * t_dm_province_catalog_num
 */
@Component
@Lazy(false)
public class UpPlateformStatistics {

    @Autowired
    private UpPlateformService service;


    @Scheduled(cron ="0 21 21 * * ? ")
    public void inser_t_dm_province_catalog_num() {
        System.out.println("开始更新数据insert_merge_t_dm_province_catalog_num");
        long begin = System.currentTimeMillis();
        service.insert_merge_t_dm_province_catalog_num();
        long end = System.currentTimeMillis();
        long t = (end-begin)/1000;
        System.out.println("更新结束，共耗时："+t+"秒");

    }
}
