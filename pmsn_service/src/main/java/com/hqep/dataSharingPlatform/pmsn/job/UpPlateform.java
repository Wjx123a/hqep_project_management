package com.hqep.dataSharingPlatform.pmsn.job;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.UpPlateformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 两级贯通的三张表定时任务
 */
@Component
@Lazy(false)
public class UpPlateform {

    @Autowired
    private UpPlateformService service;

    @Scheduled(cron ="0 */5 * * * ? ")
    public void instLjgt() {
        System.out.println("---------开始执行两级贯通表监测工作---------" +new Date());
        try{
            this.ods_sjml_internal_up_demand_application();
            this.ods_sjml_internal_up_audit_process();
            this.ods_sjml_internal_up_demand_table();
            this.ods_sjml_internal_up_demand_application_new();
            this.ods_sjml_internal_up_demand_table_new();
//            this.queryProcessBh();
            System.out.println("---------结束执行两级贯通表监测工作---------" +new Date());
            Thread.sleep(60000*1);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("插入ods_sjml_internal_up_demand_application失败！");
        }
    }

    public void queryProcessBh(){
        try{
            List<PageData> list = service.queryProcessBh();
            for (PageData pd:list) {
                service.updateProcessBh(pd);
                System.out.println("修改ods_sjml_internal_up_audit_process成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("修改ods_sjml_internal_up_audit_process失败！");
        }

    }
    //插入审核流程环节信息表
    public void ods_sjml_internal_up_audit_process (){
        List<PageData> list = service.queryProcess();
        System.out.println("查询ods_sjml_internal_up_audit_process成功！");
        for (PageData pd:list) {
            if(pd!=null){
//                if(pd.get("audit_status").equals("流转中")){
//                    service.delProcess(pd);
//                   System.out.println("删除ods_sjml_internal_up_audit_process成功！");
//                }
//                if(pd.get("audit_status").equals("已完成")&&pd.get("audit_link").equals("需求受理")){
//                    service.delProcessLzz(pd);
//                    System.out.println("删除ods_sjml_internal_up_audit_process需求受理流转中成功！");
//                }

                service.instProcess(pd);
                System.out.println("插入ods_sjml_internal_up_audit_process成功！信息为："+pd);

            }
        }

    }
    //插入需求提报环节信息表
    public void ods_sjml_internal_up_demand_application (){
        List<PageData> list= service.queryApplication();
        System.out.println("查询ods_sjml_internal_up_demand_application成功！");
        if (list.size() == 0) {
            // 查询同一工单不同批次结果
            List<PageData> list1 = service.queryApplicationDiff();
            for (PageData pd : list1) {
                service.updateApplication(pd);
                System.out.println("修改ods_sjml_internal_up_demand_application信息为："+pd);
            }
//            // 查询同一工单不同批次结果
//            List<PageData> list2 = service.queryApplicationDiff_new();
//            for (PageData pd : list2) {
//                service.updateApplication_new(pd);
//                System.out.println("修改ods_sjml_internal_up_demand_application_new信息为："+pd);
//            }
        } else {
            for (PageData pd:list) {
                if(pd!=null){
                    service.instApplication(pd);
                    System.out.println("插入ods_sjml_internal_up_demand_application信息为："+pd);
//                    if (pd.get("apply_code") != null && !"".equals(pd.get("apply_code"))) {
//                        service.instApplication_new(pd);
//                    }
//                    System.out.println("插入ods_sjml_internal_up_demand_application_new信息为："+pd);
                }
            }
        }
    }

    //插入需求提报环节信息表 (共享监测改造)
    public void ods_sjml_internal_up_demand_application_new (){
        List<PageData> list= service.queryApplication_new();
        System.out.println("查询ods_sjml_internal_up_demand_application_new成功！");
        if (list.size() == 0) {
            // 查询同一工单不同批次结果
            List<PageData> list2 = service.queryApplicationDiff_new();
            for (PageData pd : list2) {
                service.updateApplication_new(pd);
                System.out.println("修改ods_sjml_internal_up_demand_application_new信息为："+pd);
            }
        } else {
            for (PageData pd:list) {
                if(pd!=null){
                    service.instApplication_new(pd);
                    System.out.println("插入ods_sjml_internal_up_demand_application_new信息为："+pd);
                }
            }
        }
    }

    //插入需求数据表信息表
    public void ods_sjml_internal_up_demand_table (){
        List<PageData> list = service.queryDemandTable();
        System.out.println("查询ods_sjml_internal_up_demand_table成功！");
        for (PageData pd:list) {
            if(pd!=null){
                service.instDemandTable(pd);
//                System.out.println("插入ods_sjml_internal_up_demand_table成功！信息为:"+pd);
            }
        }
    }
    //插入需求数据表信息表 (共享监测改造)
    public void ods_sjml_internal_up_demand_table_new (){
        List<PageData> list = service.queryDemandTable_new();
        System.out.println("查询ods_sjml_internal_up_demand_table_new成功！");
        for (PageData pd:list) {
            if(pd!=null){
                service.instDemandTable_new(pd);
                System.out.println("插入ods_sjml_internal_up_demand_table_new成功！信息为:"+pd);
            }
        }
    }




}
