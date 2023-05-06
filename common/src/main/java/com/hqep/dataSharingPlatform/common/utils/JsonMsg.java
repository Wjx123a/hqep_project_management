package com.hqep.dataSharingPlatform.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.*;

/**
 * @Description: java类作用描述
 * @Author: shaowenqiang
 * @CreateDate: 2020/12/18$ 10:33$
 * @Version: 1.0
 */
public class JsonMsg{
    /**
     * 消息封装
     * true ==>> success false ==>> erro
     *
     * @param b 状态
     * @return Map
     */
    public static Map booleanToMap(boolean b) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("state", "error");
        if (b) {
            m.put("state", "success");
        }
        return m;
    }

    /**
     * 消息封装
     *
     * @param state  "success" 或 "error"
     * @param object 成功信息或失败提示
     * @return Map
     */
    public static Map toJosn(String state, Object object) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("state", state);
        m.put("msg", object);
        return m;
    }

    /**
     * 统一确认消息封装
     *
     * @param state    状态 boolean true成功 false 失败
     * @param trueMsg  成功信息
     * @param falseMsg 失败信息
     * @return Map
     */
    public static Map<String, Object> msgByBoolean(boolean state, Object trueMsg, Object falseMsg) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (state) {
            resultMap.put("state", "success");
            if (null != trueMsg) {
                resultMap.put("msg", trueMsg);
            }
        } else {
            resultMap.put("state", "error");
            if (null != falseMsg) {
                resultMap.put("msg", falseMsg);
            }
        }
        return resultMap;
    }

    /**
     * 创建e-charts使用的数据格式
     *
     * @return Map
     */
    public static Map<String, List> creatMap() {
        Map<String, List> map = new HashMap<>();
        map.put("total", new ArrayList());
        map.put("miss", new ArrayList());
        map.put("xdata", new ArrayList());
        map.put("ydata", new ArrayList());
        map.put("yaxis", new ArrayList());
        map.put("data", new ArrayList());
        map.put("legend", new ArrayList());
        map.put("start", new ArrayList());
        map.put("end", new ArrayList());
        return map;
    }

    /**
     * 将List封装为EasyUI列表  需使用PageHelper
     *
     * @param list pageHelpe List
     * @return Map 符合EasyUI Table 的数据样式
     */
    public static Map<String, Object> msgByListForEasyuiTable(List list) {
        return isPage(list);
    }

    /**
     * 将List封装为Bootstrap列表
     *
     * @param list pageHelpe List
     * @return Map 符合BootstrapTable 的数据样式
     */
    public static Map<String, Object> msgByListForBootstrap(List list) {
        return isPage(list);
    }

    public static Map<String, Object> msgByListForVueTable(List list) {
        return isPage(list);
    }
    public static Map<String, Object> msgByListForVueTable_2(List list,long total) {
        Map resultMap = new LinkedHashMap();
        resultMap.put("total", total);
        resultMap.put("rows", list == null ? new ArrayList<>() : list);
        return resultMap;
    }
    public static Map isPage(List list) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        if (list instanceof Page) {
            PageInfo pageInfo = new PageInfo<>(list);
            resultMap.put("total", pageInfo.getTotal());
            resultMap.put("rows", pageInfo.getList() == null ? new ArrayList<>() : pageInfo.getList());
        } else {
            resultMap.put("total", list.size());
            resultMap.put("rows", list == null ? new ArrayList<>() : list);
        }
        return resultMap;
    }


    public static Map successObj(Object obj) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("state", "success");
        resultMap.put("msg", obj);
        resultMap.put("data", obj);
        return resultMap;
    }

    public static Map errorObj(Object obj) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("state", "error");
        resultMap.put("msg", obj);
        return resultMap;
    }
    //================统一格式开始

    /**
     * 创建错误返回对象
     * @param code 错误码（500,200,428)
     * @param text 错误提示信息
     * @return
     */
    public static Map creatErrorMap(String code,String text){
        Map result = new LinkedHashMap();
        Map errorMap = new LinkedHashMap();
        errorMap.put("code",code);
        errorMap.put("text",text);
        errorMap.put("message",text);
        result.put("error",errorMap);
        result.put("data",new LinkedHashMap<>());
        result.put("state", "error");
        return result;
    }

    public static Map creatErrorMapJoinInfo(String code,String text,Object o){
        Map result = new LinkedHashMap();
        Map errorMap = new LinkedHashMap();
        errorMap.put("code",code);
        errorMap.put("text",text);
        result.put("error",errorMap);
        result.put("data",new LinkedHashMap<>());
        result.put("opertionData",o);
        result.put("state", "error");
        return result;
    }

    public static Map creatSuccessMapForOpertion(Object opertionMsg){
        Map result = new LinkedHashMap();
        result.put("status", "succeed");
        result.put("data", opertionMsg);
        result.put("message", opertionMsg);
        result.put("state", "success");
        result.put("error", null);
        return result;
    }

    public static Map creatSuccessMapForOpertionJoinInfo(Object opertionMsg,Object o){
        Map result = new LinkedHashMap();
        result.put("status", "succeed");
        result.put("data", opertionMsg);
        result.put("message", opertionMsg);
        result.put("opertionData", o);
        result.put("state", "success");
        result.put("error", null);
        return result;
    }

    /**
     * 返回List到前台的格式
     * @param list list
     * @return Map
     */
    public static Map creatSuccessMapForList(List list){
        Map result = new LinkedHashMap();

        Map pageMap = new LinkedHashMap();
        if (list instanceof Page) {
            PageInfo pageInfo = new PageInfo<>(list);
            pageMap.put("showCount",pageInfo.getPageSize());
            pageMap.put("totalPage",pageInfo.getPages());
            pageMap.put("totalResult", pageInfo.getTotal());
            pageMap.put("currentPage",pageInfo.getPageNum());
            pageMap.put("list",list);
            pageMap.put("pageInfo",pageInfo);
            result.put("data",pageMap);
        } else {
            result.put("data",list);
        }
        result.put("state", "success");
        result.put("error", null);
        return result;
    }

    /**
     * 返回Mapms 系统前台数据格式
     * @param flag
     * @return
     */
    public static Map getToMapmsMsg(boolean flag) {
        Map result = new LinkedHashMap();
        if (flag)
        {
            result.put("error", null);
            result.put("status", "succeed");
            result.put("data", "操作成功！");
            return result;
        } else {
            result.put("data", new HashMap<>());
            Map errorMap = new LinkedHashMap();
            errorMap.put("code","500");
            errorMap.put("text","操作失败！");
            result.put("error",errorMap);
            return result;
        }

    }
    /**
     * 返回Mapms 系统前台数据格式
     * @param flag
     * @param msg
     * @return
     */
    public static Map getToMapmsMsg(boolean flag,Object msg) {
        Map result = new LinkedHashMap();
        if (flag)
        {
            result.put("state","success");
            result.put("error", null);
            result.put("status", "succeed");
            if (msg != null && !"".equals(msg))
            {
                result.put("data", msg);
            } else {
                result.put("data", "操作成功！");
            }
            return result;
        } else {
            result.put("data", new HashMap<>());
            Map errorMap = new LinkedHashMap();
            errorMap.put("code","500");
            if (msg != null && !"".equals(msg))
            {
                errorMap.put("text",msg);
            } else {
                errorMap.put("data", "操作失败！");
            }
            result.put("state","error");
            result.put("error",errorMap);
            return result;
        }

    }
    //================统一格式结束

    public  static Map autoResultMap(int i,Object opertionInfo,String successMsg,String errorMsg){
        String str = StringBuilderUtil.concatString("有", String.valueOf(i), "条数据操作成功");
        if (i > 0) {
            Map result = new LinkedHashMap();
            result.put("state", "success");
            result.put("msg", str);
            result.put("successMsg", successMsg);
            result.put("errorMsg", successMsg);
            result.put("opertionNum", i);
            result.put("opertionData", opertionInfo);
            result.put("status", "succeed");
            result.put("error", null);
            return result;
        } else {
            Map result = new LinkedHashMap();
            result.put("state", "error");
            result.put("msg", str);
            result.put("successMsg", successMsg);
            result.put("errorMsg", errorMsg);
            result.put("opertionNum", i);
            result.put("opertionData", opertionInfo);
            result.put("status", "succeed");
            result.put("error", errorMsg);
            return result;
        }
    }
}

