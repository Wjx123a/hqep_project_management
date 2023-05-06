package com.hqep.dataSharingPlatform.pmsn.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class bbzxPushJob {

//    @Scheduled(cron ="0 */5 * * * ? ")
    public void updatedictsjztssq() {
        long begin = System.currentTimeMillis();
//        System.out.println("推送报表中心开始==========="+ begin);
        long end = System.currentTimeMillis();
        long t = (end-begin)/1000;
//        System.out.println("推送报表中心开始结束==========="+ end + "共耗时："+t+"秒");
    }

}
