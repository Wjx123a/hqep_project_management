package com.hqep.dataSharingPlatform.sjkflc.action;


import com.hqep.dataSharingPlatform.common.utils.PageData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/demo")
@RestController
public class demoController {


    @ApiOperation(value = "查询列表", notes = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/query", method = {RequestMethod.POST,RequestMethod.GET})
    public PageData query(@RequestBody PageData pd) {

        return pd;

    }
}
