package com.hqep.dataSharingPlatform.interface_serv.action;


import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.common.utils.JsonMsg;
import com.hqep.dataSharingPlatform.interface_serv.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 字典信息
 * @Author: shaowenqiang
 * @CreateDate: 2021/03/20$ 9:42$
 * @Version: 1.0
 */
@RequestMapping("/dict")
@RestController
public class DictAction {

    @Autowired
    private DictService dicService;

    /**
     * 查询用户名列表/dict/getUserNameList
     *
     * @return
     */
    @RequestMapping("getUserNameList")
    @ResponseBody
//    @RequestLog("查询用户名列表")
    public Map getUserNameList(@RequestBody Map params) {
        return dicService.getUserNameList(params);
    }

    /**
     * 查询用户名列表  用于负面清单 /dict/getUserNameListForFmqd
     *
     * @return
     */
    @RequestMapping("getUserNameListForFmqd")
    @ResponseBody
//    @RequestLog("查询用户名列表用于负面清单")
    public Map getUserNameListForFmqd(@RequestBody Map params) {
        return dicService.getUserNameListForFmqd(params);
    }

    /**
     * 查询系统名列表/dict/getSystemNameList
     *
     * @return
     */
    @RequestMapping("getSystemNameList")
    @ResponseBody
//    @RequestLog("查询系统名列表")
    public Map getSystemNameList(@RequestBody Map params) {
        return dicService.getSystemNameList(params);
    }

    /**
     * 下发订单管理
     * 查询系统名列表/dict/getDownOrderSystemNameList
     *
     * @return
     */
    @RequestMapping("getDownOrderSystemNameList")
    @ResponseBody
//    @RequestLog("查询系统名列表")
    public Map getDownOrderSystemNameList(@RequestBody Map params) {
        try {
            return dicService.getDownOrderSystemNameList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询系统名列表 用于负面清单  /dict/getSystemNameListForFmqd
     *
     * @return
     */
    @RequestMapping("getSystemNameListForFmqd")
    @ResponseBody
//    @RequestLog("查询系统名列表用于负面清单")
    public Map getSystemNameListForFmqd(@RequestBody Map params) {
        return dicService.getSystemNameListForFmqd(params);
    }

    /**
     * 查询用户名列表/dict/getDeptNameList
     *
     * @return
     */
    @RequestMapping("getDeptNameList")
    @ResponseBody
//    @RequestLog("查询部门列表")
    public Map getDeptNameList(@RequestBody Map params) {
        return dicService.getDeptNameList(params);
    }


   /**
    * 下发订单管理
    * 查询用户名列表/dict/getDownOrderDeptNameList
    * @return
     */
    @RequestMapping("getDownOrderDeptNameList")
    @ResponseBody
//    @RequestLog("查询部门列表")
    public Map getDownOrderDeptNameList(@RequestBody Map params) {
        try {
            return dicService.getDownOrderDeptNameList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询该表是否在数据中台中/dict/getCountOfSjzt
     *
     * @return num 大于0则 是 等于0 则 否
     */
    @RequestMapping("getCountOfSjzt")
    @ResponseBody
//    @RequestLog("查询该表是否在数据中台中")
    public Map getCountOfSjzt(@RequestBody Map params) {
        if(params.containsKey("bsskj") && ("共享层".equals(params.get("bsskj")) || "分析层".equals(params.get("bsskj")))) {
            return JsonMsg.successObj(1);
        }
        if (!params.containsKey("simple_name") || null==(params.get("simple_name")) || "".equals(params.get("simple_name"))) {
            return JsonMsg.errorObj("不包含 系统简称信息(simple_name)");
        }
        if (!params.containsKey("table_ename") || null==(params.get("simple_name")) || "".equals(params.get("table_ename"))) {
            return JsonMsg.errorObj("不包含 系统表名称(table_ename)");
        }
        return dicService.getCountOfSjzt(params);
    }
}


