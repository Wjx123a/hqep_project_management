package com.hqep.dataSharingPlatform.sjfwgj.action;

import com.hqep.dataSharingPlatform.sjfwgj.service.impl.queryLogimpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: queryLogController
 * @author: wjx
 * @data: 2023/4/25 20:11 PM
 */
@RequestMapping("/qualityDemand")
@RestController
@Api(value = "insert", tags = { "日志处理接口" })
public class queryLogController {
    @Autowired
    private queryLogimpl queryLog;
    @GetMapping(value = "/insert")
    @ApiOperation("插入数据")
    public void select(@RequestParam String ticketId,
                       @RequestParam String result,
                       @RequestParam Date handleTime,
                       @RequestParam String operateTitle,
                       @RequestParam String nodeTime
                       ){

    }
}
