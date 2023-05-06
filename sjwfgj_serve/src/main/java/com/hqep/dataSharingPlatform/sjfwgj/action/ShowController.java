package com.hqep.dataSharingPlatform.sjfwgj.action;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.ReceiveDO;
import com.hqep.dataSharingPlatform.sjfwgj.pojo.UserDO;
import com.hqep.dataSharingPlatform.sjfwgj.service.QueryLog;
import com.hqep.dataSharingPlatform.sjfwgj.service.ReceiveInfo;
import com.hqep.dataSharingPlatform.sjfwgj.service.impl.Testimpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: ShowController
 * @author: wjx
 * @data: 2023/5/4 20:56 PM
 */
@RequestMapping("/qualityDemand")
@RestController
@EnableWebMvc
public class ShowController {
    @Autowired
    private QueryLog queryLog;
    @Autowired
    private ReceiveInfo receiveInfo;
    //    @RequestMapping(value = "/queryById",method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/selectLog")
    @ApiOperation("查询日志数据")
    public Map selectLog(){
        return queryLog.select();
    }
    @PostMapping(value = "/selectTable")
    @ApiOperation("查询大表数据")
    public List<Map> selectTable(){
        return receiveInfo.list();
    }

    @PostMapping(value = "/selectDetailTable")
    @ApiOperation("查询小表数据")
    public List selectDetailTable(){
        return receiveInfo.listDetail();
    }
}
