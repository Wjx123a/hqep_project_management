package com.hqep.dataSharingPlatform.interface_serv.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.common.model.SysLogs;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.common.vo.JsonResult;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.interface_serv.service.SysLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * (SysLogs)表控制层
 *
 * @author shaowenqiang
 * @since 2020-11-04 21:38:12
 */
@RestController
@RequestMapping("/sysLogs")
@Api(tags = "")
public class SysLogsAction {

    private static Logger log = LoggerFactory.getLogger(Activiti6Manage.class);

    @Resource
    private SysLogsService sysLogsService;

    /**
     * 通过ID查询单条 SysLogs 数据
     *
     * @param model 包含ID的实例对象
     * @return JsonResult包裹的实例对象
     */
    @ApiOperation(value = "通过ID查询单条 SysLogs 数据", notes = "通过ID查询单条 SysLogs 数据")
    @ResponseBody
    @PostMapping("queryById")
    public JsonResult queryById(@RequestBody SysLogs model) {
        log.info("通过ID查询单条 SysLogs 数据 Action层(queryById) 开始");
        SysLogs sysLogs;
        try {
            long id = model.getId();
            sysLogs = sysLogsService.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通过ID查询单条 SysLogs 数据 Action层(queryById) 出错");
            return JsonResult.fail("查询出错");
        }
//         finally {
        log.info("通过ID查询单条 SysLogs 数据 Action层(queryById) 结束");
        return JsonResult.success("查询成功", sysLogs);
//         }
    }

    /**
     * 通过实体作为筛选条件查询 SysLogs 数据列表
     *
     * @param model 实例对象
     * @return JsonResult包裹的实例对象
     */
    @ApiOperation(value = "通过实体作为筛选条件查询 SysLogs 数据列表", notes = "通过实体作为筛选条件查询 SysLogs 数据列表")
    @ResponseBody
    @PostMapping("queryAll")
//    @RequestLog("查询操作日志")
    public JsonResult queryAll(@RequestBody SysLogs model) {
        log.info("通过实体作为筛选条件查询 SysLogs 数据列表 Action层(queryAll) 开始");
        Page page = null;
        try {
            page = PageHelper.startPage(model.getPageNum(), model.getPageSize(), "created_time desc");
            sysLogsService.queryAll(model);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通过实体作为筛选条件查询 SysLogs 数据列表 Action层(queryAll) 出错");
            return JsonResult.fail("查询出错");
        }
        //finally {
        log.info("通过实体作为筛选条件查询 SysLogs 数据列表 Action层(queryAll) 结束");
        return JsonResult.success("查询成功", page);
        //}
    }


    /**
     * 查询所有登录信息_最外层的表格数据
     * 最外层的表格数据
     * @param model 实例对象
     * @return JsonResult包裹的实例对象
     */
    @ApiOperation(value = "查询所有登录信息_最外层的表格数据", notes = "查询所有登录信息_最外层的表格数据")
    @ResponseBody
    @PostMapping("queryAllForLogin")
//    @RequestLog("查询操作日志")
    public JsonResult queryAllForLogin(@RequestBody SysLogs model) {
        log.info("查询所有登录信息_最外层的表格数据开始");
        Page page = null;
        try {
            page = PageHelper.startPage(model.getPageNum(), model.getPageSize(), "created_time desc");
            sysLogsService.queryAllForLogin(model);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询所有登录信息_最外层的表格数据出错");
            return JsonResult.fail("查询出错");
        }
        //finally {
        log.info("查询所有登录信息_最外层的表格数据结束");
        return JsonResult.success("查询成功", page);
        //}
    }


    /**
     * 根据登录用户查询最新的登录记录的详情
     * @param model 实例对象
     * @return JsonResult包裹的实例对象
     */
    @ApiOperation(value = "根据登录用户查询最新的登录记录的详情", notes = "根据登录用户查询最新的登录记录的详情")
    @ResponseBody
    @PostMapping("queryAllForLoginToDetail")
//    @RequestLog("查询操作日志")
    public JsonResult queryAllForLoginToDetail(@RequestBody SysLogs model) {
        log.info("根据登录用户查询最新的登录记录的详情开始");
        Page page = null;
        try {
            page = PageHelper.startPage(model.getPageNum(),model.getPageSize());
            sysLogsService.queryAllForLoginToDetail(model);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据登录用户查询最新的登录记录的详情出错");
            return JsonResult.fail("查询出错");
        }
        //finally {
        log.info("根据登录用户查询最新的登录记录的详情结束");
        return JsonResult.success("查询成功", page);
        //}
    }

    /**
     * 根据登录用户查询最新的登录记录
     *
     * @param model 实例对象
     * @return JsonResult包裹的实例对象
     */
    @ApiOperation(value = "根据登录用户查询最新的登录记录", notes = "根据登录用户查询最新的登录记录")
    @ResponseBody
    @PostMapping("queryNewLastForPerson")
//    @RequestLog("查询操作日志")
    public JsonResult queryNewLastForPerson(@RequestBody SysLogs model) {
        log.info("根据登录用户查询最新的登录记录开始");
        Page page = null;
        try {
            page = PageHelper.startPage(model.getPageNum(), model.getPageSize() );
            sysLogsService.queryNewLastForPerson(model);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据登录用户查询最新的登录记录出错");
            return JsonResult.fail("查询出错");
        }
        //finally {
        log.info("根据登录用户查询最新的登录记录结束");
        return JsonResult.success("查询成功", page);
        //}
    }



    /**
     * 新增 SysLogs 数据
     *
     * @param sysLogs 实例对象
     * @return JsonResult包裹的实例对象
     */
    @ApiOperation(value = "新增 SysLogs 数据", notes = "新增 SysLogs 数据")
    @ResponseBody
    @PostMapping("insert")
    public JsonResult insert(@RequestBody SysLogs sysLogs) {
        log.info("新增 SysLogs 数据 Action层(insert) 开始");
        int i;
        StringBuffer s = new StringBuffer("新增成功条数据");
        try {
            i = sysLogsService.insert(sysLogs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增 SysLogs 数据 Action层(insert) 出错");
            return JsonResult.fail("新增失败");
        }
        //finally {
        log.info("新增 SysLogs 数据 Action层(insert) 结束");
        return JsonResult.success((s.insert(4, i).toString()), sysLogs);
        //}
    }

    /**
     * 修改 SysLogs 数据
     *
     * @param sysLogs 实例对象
     * @return 影响行数
     */
    @ApiOperation(value = "修改 SysLogs 数据", notes = "修改 SysLogs 数据")
    @ResponseBody
    @PostMapping("update")
    public JsonResult update(@RequestBody SysLogs sysLogs) {
        log.info("修改 SysLogs 数据 Action层(update) 开始");
        int i;
        StringBuffer s = new StringBuffer("更新成功条数据");
        try {
            i = sysLogsService.update(sysLogs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改 SysLogs 数据 Action层(update) 出错");
            return JsonResult.fail("修改失败");
        }
        //finally {
        log.info("修改 SysLogs 数据 Action层(update) 结束");
        return JsonResult.success((s.insert(4, i).toString()), sysLogs);
        //}
    }

    /**
     * 通过主键删除 SysLogs 数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @ApiOperation(value = "通过主键删除 SysLogs 数据据", notes = "通过主键删除 SysLogs 数据")
    @ResponseBody
    @PostMapping("deleteById")
    public JsonResult deleteById(@RequestParam Long id) {
        log.info("通过主键删除 SysLogs 数据 Action层(deleteById) 开始");
        int i;
        StringBuffer s = new StringBuffer("删除成功条数据");
        try {
            i = sysLogsService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通过主键删除 SysLogs 数据 Action层(deleteById) 出错");
            return JsonResult.fail("删除失败");
        }
        //finally {
        log.info("通过主键删除 SysLogs 数据 Action层(deleteById) 结束");
        return JsonResult.success((s.insert(4, i).toString()));
        //}
    }

    // 根据id查询传入参数json
    @ApiOperation(value = "根据id查询传入参数json", notes = "根据id查询传入参数json")
    @ResponseBody
    @PostMapping("queryParamsById")
    public PageData queryParamsById(@RequestBody SysLogs model) {
        log.info("根据id查询传入参数json");
        PageData resultPd = new PageData();
        try {
//            model.setId(Long.parseLong(id));
//            String str = sysLogsService.queryParamsById(id);
            List<SysLogs> list  = sysLogsService.queryAll(model);
            if (list != null && list.size() > 0 ) {
                model = list.get(0);
                String jsonStr =  model.getParams();
                if ("用户提交申请".equals(model.getOperation())
                        || "审批通过".equals(model.getOperation())) {
                    jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
                }
                if ("调用授权方法".equals(model.getOperation())) {
                    jsonStr = "[" + jsonStr + "]";
                }
//                jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
                System.out.println("str==============" + model);
                System.out.println("str==============" + jsonStr);
//                JSONArray jsonArray = JSONArray.fromObject(jsonStr);
//                JSONObject jsonObject = new JSONObject();
//                for (int i = 0; i < jsonArray.size(); i++) {
//                    jsonObject = jsonArray.getJSONObject(i);
//                    Iterator<String> iterator = jsonObject.keys();
//                    while(iterator.hasNext())
//                    {
//                        String key = (String)iterator.next();
//                        Object value = jsonObject.get(key);
//                        resultPd.put(key, value);
//                    }
//                }
                resultPd.put("jsonStr",jsonStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据id查询传入参数json出错");
            resultPd.put("error", "根据id查询传入参数json出错");
        }
        log.info("根据id查询传入参数json结束");
        return resultPd;
    }

    /**
     * 查询 系统日志 操作 下拉选的内容
     *
     * @param model 实例对象
     * @return PageData 返回结果
     */
    @ApiOperation(value = "查询 系统日志 操作 下拉选的内容", notes = "查询 系统日志 操作 下拉选的内容")
    @ResponseBody
    @PostMapping("/querySysLogsSelectList")
    public JsonResult querySysLogsSelectList(@RequestBody SysLogs model) {
        log.info("进入查询 系统日志 操作 下拉选的内容");
        List<SysLogs> list = null;
        try {
           list = sysLogsService.querySysLogsSelectList(model);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询 系统日志 操作 下拉选的内容 出错");
            return JsonResult.fail("查询出错");
        }
        //finally {
        log.info("查询 系统日志 操作 下拉选的内容 结束");
        return JsonResult.success("查询成功", list);
        //}
    }
}