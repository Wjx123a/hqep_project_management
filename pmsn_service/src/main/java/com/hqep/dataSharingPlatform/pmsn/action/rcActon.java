package com.hqep.dataSharingPlatform.pmsn.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.AllTableService;
import com.hqep.dataSharingPlatform.pmsn.service.rcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: hqep_project_management
 * @description: 热词展示
 * @author:dhr create:2021-10-27 14-20
 **/
@RequestMapping("/queryrc")
@RestController
@Api(description = "热词展示")
public class rcActon {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private rcService service;

    @ApiOperation(value = "查询热词", notes = "查询热词")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/rc", method = RequestMethod.POST)
    public PageData queryrc(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.queryrc(pd);
            if(list.size()!=0){
                    resultPd.put("data",list);

            }else{
                resultPd.put("data", new ArrayList<>());
            }

            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询热词出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询热词出错");
        }
        return resultPd;
    }


    @ApiOperation(value = "查询下拉选内容", notes = "查询下拉选内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/querySelectOption", method = RequestMethod.POST)
    public PageData querySelectOption(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = service.querySelectOption(pd);
            if(list.size()!=0){
                    resultPd.put("data",list);
            }else{
                resultPd.put("data", new ArrayList<>());
            }
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询下拉选内容出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询下拉选内容出错");
        }
        return resultPd;
    }



}

