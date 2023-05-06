package com.hqep.dataSharingPlatform.sjkflc.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.sjkflc.model.CreateTableAndTabbleName;
import com.hqep.dataSharingPlatform.sjkflc.model.CustomTable;
import com.hqep.dataSharingPlatform.sjkflc.service.ReportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.PageData2Web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ReportCenter")
public class ReportCenterController {
    @Autowired
    ReportCenterService reportCenterService;

    /***
     * 创建表
     * @return
     */
    @ResponseBody
    @RequestMapping("/createTable")
    public boolean createTable(CreateTableAndTabbleName model) {
        boolean flag = false;
        try {
            List<CustomTable> customTables = new ArrayList<>();
            CustomTable customTable = new CustomTable();
            customTable.setCreateTableFiledName("creater");
            customTable.setFieldType("varchar(255)");
            customTable.setComment("创建者");
            customTable.setChoose(true);
            customTables.add(customTable);
            model.setCustomTables(customTables);
            model.setTableName("aaaacustom_table_2");
            reportCenterService.createTable(model);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /***
     * 创建表
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryExistsTable")
    public List<PageData> queryExistsTable(@RequestBody PageData pd)  {
        List<PageData> pdList = new ArrayList<>();
        try {
            pdList = reportCenterService.queryExistsTable(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdList;
    }

    /***
     * 授权表
     * @return
     */
    @ResponseBody
    @RequestMapping("/grantQueryTableForUser")
    public PageData grantQueryTableForUser(@RequestBody PageData pd) {
        PageData resultPd = reportCenterService.grantQueryTableForUser(pd);
        return resultPd;
    }
    /***
     * 取消授权表
     * @return
     */
    @ResponseBody
    @RequestMapping("/revokeQueryTableForUser")
    public PageData revokeQueryTableForUser(@RequestBody PageData pd) {
        PageData resultPd = reportCenterService.revokeQueryTableForUser(pd);
        return resultPd;
    }

}
