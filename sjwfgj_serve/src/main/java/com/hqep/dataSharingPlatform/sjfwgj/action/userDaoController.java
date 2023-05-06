package com.hqep.dataSharingPlatform.sjfwgj.action;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.UserDO;
import com.hqep.dataSharingPlatform.sjfwgj.service.impl.Testimpl;
import com.sun.glass.ui.Application;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: userDaoController
 * @author: wjx
 * @data: 2023/4/23 11:16 PM
 */
@RequestMapping("/test")
@RestController
@Api(value = "test", tags = { "test接口" })
public class userDaoController {
    @Autowired
    private Testimpl testimpl;
//    @RequestMapping(value = "/queryById",method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded")
    @PostMapping(value = "/queryById")
    @ApiOperation("通过主键查询数据")
    public UserDO select(@RequestParam Integer id){
        return testimpl.selectByPrimaryKey(id);
    }
    
}
