package com.hqep.dataSharingPlatform.common.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2021/3/26$ 10:47$
 * @Version: 1.0
 */
public class ConfigInfo {
    /**
     * ip白名单
     */
    public static List IPWhiteList (){
        List<String> ipList=new ArrayList();
        ipList.add("10.167.1.110");// 110 测试服务器
        ipList.add("10.167.1.44");// 邵文强开发机器
        ipList.add("10.167.1.42");// 孙金龙开发机器
        ipList.add("10.167.1.43");// 孙金龙开发机器
        ipList.add("10.167.1.128");// 王雨婷开发机器
        ipList.add("10.167.1.75");// 陈美玲开发机器
        ipList.add("10.167.1.50");// 50 项目部署服务器
        ipList.add("113.9.195.129");// 域名
        ipList.add("10.167.183.16");// 正式服务器nginx部署地址
        return ipList;
    }

    /**
     *
     * 高权用户IP 只有这些IP能登录大数据中心角色
     * @return
     */
    public static List PowerIPList (){
        List<String> ipList=new ArrayList();
        ipList.add("10.167.1.75");
        ipList.add("10.167.1.44");
        ipList.add("10.167.1.50");// 50 项目部署服务器
        ipList.add("113.9.195.129");// 域名
        ipList.add("10.167.183.16");// 正式服务器nginx部署地址
        return ipList;
    }

    /**
     *
     * 高权角色名称
     * @return
     */
    public static List powerRoleNameList (){
        List<String> ipList=new ArrayList();
//        ipList.add("管理员");
        ipList.add("大数据中心审批");
        return ipList;
    }
}
