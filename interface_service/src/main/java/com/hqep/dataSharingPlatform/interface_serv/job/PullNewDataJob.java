package com.hqep.dataSharingPlatform.interface_serv.job;

import com.hqep.dataSharingPlatform.interface_serv.service.PullNewDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description: springmvc定时任务
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/18$ 14:32$
 * @Version: 1.0
 */
@Component
public class PullNewDataJob {
    @Autowired
    private PullNewDataService pullNewDataService;

    /**
     * 拉取所有用户更新进入本地mysql中
     * 定时：每日执行
     */
    @Scheduled(cron = "0 15 22 * * ?") // 间隔20秒执行 之后改为 每天执行
    public void pullUsersByDay(){
        String printTime = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("抓取远程用户信息 InsertOrUpdateUsers 定时任务 开始时间:" + printTime);
        Map params = new LinkedHashMap();
        Map map = pullNewDataService.InsertOrUpdateUsers(params);
//        System.out.println(map.toString());
        printTime = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("抓取远程用户信息 InsertOrUpdateUsers 定时任务 结束时间:" + printTime);
    }

    /**
     * 拉取所有用户更新进入本地mysql中
     * 定时：每日执行
     */
    @Scheduled(cron = "0 15 22 * * ? ") // 间隔20秒执行 之后改为 每天执行
    public void pullColumnsByDay(){
        String printTime = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("抓取远程字段字典信息 InsertOrUpdateColumns 定时任务 开始时间:" + printTime);
        Map params = new LinkedHashMap();
        params.put("pageIndex",1);
        Map map = pullNewDataService.InsertOrUpdateColumns(params);
//        System.out.println(map.toString());
        printTime = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("抓取远程字段字典信息 InsertOrUpdateColumns 定时任务 结束时间:" + printTime);
    }

}
