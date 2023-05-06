package com.hqep.dataSharingPlatform.sjkflc.job;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpAuditProcess;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandApplication;
import com.hqep.dataSharingPlatform.sjkflc.model.SjkflcOdsSjmlTUpDemandTable;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpAuditProcessService;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpDemandApplicationService;
import com.hqep.dataSharingPlatform.sjkflc.service.SjkflcOdsSjmlTUpDemandTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * 更新数据对外开放的三张表定时任务
 */
@Component
@Lazy(false)
public class UpSjkflcPlateform {

    @Autowired
    private SjkflcOdsSjmlTUpDemandApplicationService sjkflcOdsSjmlTUpDemandApplicationService;
    @Autowired
    private SjkflcOdsSjmlTUpAuditProcessService sjkflcOdsSjmlTUpAuditProcessService;
    @Autowired
    private SjkflcOdsSjmlTUpDemandTableService sjmlTUpDemandTableService;

    // @Scheduled(cron ="0 */5 * * * ? ")
    public void instLjgt() {
        System.out.println("---------开始执行更新数据对外开放的三张表定时任务---------" +new Date());
        try{
            this.sjkflc_ods_sjml_t_up_demand_application();
            this.sjkflc_ods_sjml_t_up_audit_process();
            this.sjkflc_ods_sjml_t_up_demand_table();
            System.out.println("---------结束执行更新数据对外开放的三张表定时任务---------" +new Date());
            Thread.sleep(60000*1);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("插入ods_sjml_internal_up_demand_application失败！");
        }
    }

    //插入需求提报环节信息表
    public void sjkflc_ods_sjml_t_up_demand_application (){
        List<SjkflcOdsSjmlTUpDemandApplication> list = sjkflcOdsSjmlTUpDemandApplicationService.noExistence();
        System.out.println("查询sjkflc_ods_sjml_t_up_demand_application成功！");
        System.out.println(list);
        for (SjkflcOdsSjmlTUpDemandApplication pd:list) {
            pd.setModifyType("1");
            pd.setBatchTime(DateUtils.getNowDate());
            if(pd!=null){
                sjkflcOdsSjmlTUpDemandApplicationService.insert(pd);
                System.out.println("插入sjkflc_ods_sjml_t_up_demand_application成功！信息为："+pd);
            }
        }
    }

    //插入审核流程环节信息表
    public void sjkflc_ods_sjml_t_up_audit_process (){
        List<SjkflcOdsSjmlTUpAuditProcess> list = sjkflcOdsSjmlTUpAuditProcessService.noExistence();
        System.out.println("查询sjkflc_ods_sjml_t_up_audit_process成功！");
        for (SjkflcOdsSjmlTUpAuditProcess pd:list) {
            if(pd!=null){
                pd.setModifyType("1");
                pd.setBatchTime(DateUtils.getNowDate());
                sjkflcOdsSjmlTUpAuditProcessService.insert(pd);
                System.out.println("插入sjkflc_ods_sjml_t_up_audit_process成功！信息为："+pd);
            }
        }

    }
    //插入需求数据表信息表
    public void sjkflc_ods_sjml_t_up_demand_table (){
        List<SjkflcOdsSjmlTUpDemandTable> list = sjmlTUpDemandTableService.noExistence();
        System.out.println("查询sjkflc_ods_sjml_t_up_demand_table成功！");
        for (SjkflcOdsSjmlTUpDemandTable pd:list) {
            if(pd!=null){
                pd.setModifyType("1");
                pd.setBatchTime(DateUtils.getNowDate());
                sjmlTUpDemandTableService.insert(pd);
                System.out.println("插入sjkflc_ods_sjml_t_up_demand_table成功！信息为:"+pd);
            }
        }
    }
}
