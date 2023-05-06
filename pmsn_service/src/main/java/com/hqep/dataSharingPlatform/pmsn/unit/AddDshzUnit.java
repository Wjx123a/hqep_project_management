package com.hqep.dataSharingPlatform.pmsn.unit;

import com.hqep.dataSharingPlatform.common.utils.PageData;

public class AddDshzUnit {
    public static PageData addDshz(PageData applyData) {
        if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司哈尔滨供电公司")) {
            applyData.put("dshz","_HRB");
            applyData.put("username","hrbuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司齐齐哈尔供电公司")) {
            applyData.put("dshz","_QQHE");
            applyData.put("username","qqhruser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司牡丹江供电公司")) {
            applyData.put("dshz","_MDJ");
            applyData.put("username","mdjuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司大庆供电公司")) {
            applyData.put("dshz","_DQ");
            applyData.put("username","dquser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司伊春供电公司")) {
            applyData.put("dshz","_YC");
            applyData.put("username","ycuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司绥化供电公司")) {
            applyData.put("dshz","_SH");
            applyData.put("username","shuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司鸡西供电公司")) {
            applyData.put("dshz","_JX");
            applyData.put("username","jxuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司鹤岗供电公司")) {
            applyData.put("dshz","_HG");
            applyData.put("username","hguser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司黑河供电公司")) {
            applyData.put("dshz","_HH");
            applyData.put("username","hhuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司大兴安岭供电公司")) {
            applyData.put("dshz","_DXAL");
            applyData.put("username","dxaluser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司双鸭山供电公司")) {
            applyData.put("dshz","_SYS");
            applyData.put("username","sysuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司七台河供电公司")) {
            applyData.put("dshz","_QTH");
            applyData.put("username","qthuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司佳木斯供电公司")) {
            applyData.put("dshz","_JMS");
            applyData.put("username","jmsuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司建三江供电公司")) {
            applyData.put("dshz","_JSJ");
            applyData.put("username","jsjuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司宝泉岭供电公司")) {
            applyData.put("dshz","_BQL");
            applyData.put("username","bqluser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司兴凯湖供电公司")) {
            applyData.put("dshz","_XKH");
            applyData.put("username","xkhuser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司红兴隆供电公司")) {
            applyData.put("dshz","_HXL");
            applyData.put("username","hxluser");
        } else if (applyData.get("sqrssdw").equals("国网黑龙江省电力有限公司九三供电公司")) {
            applyData.put("dshz","_JS");
            applyData.put("username","jsuser");
        }
        return applyData;
    }



}
