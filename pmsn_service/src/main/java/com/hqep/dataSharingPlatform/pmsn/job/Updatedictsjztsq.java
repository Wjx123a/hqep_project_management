package com.hqep.dataSharingPlatform.pmsn.job;

import com.hqep.dataSharingPlatform.pmsn.service.UpdatedictsjztsqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 从pull_column_dict抽到dict_tables_sjzt的第一列
 * @program: hqep_project_management
 * @description: 更新dict_sjzt_sq表的数据
 * @author:dhr create:2021-10-19 15-42
 **/
@Component
public class Updatedictsjztsq {
    @Autowired
    private UpdatedictsjztsqService service;

    @Scheduled(cron ="0 15 22 * * ? ")
    public void updatedictsjztssq() {
        System.out.println("开始更新数据updatedictsjztsq");
        long begin = System.currentTimeMillis();
        service.Updatedictsjztsq();
        long end = System.currentTimeMillis();
        long t = (end-begin)/1000;
        System.out.println("更新结束，共耗时："+t+"秒");

    }



}

