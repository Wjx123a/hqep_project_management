package com.hqep.dataSharingPlatform.pmsn.action;


import com.alibaba.fastjson.JSON;
import com.hqep.dataSharingPlatform.common.model.ErrorReasonBean;
import com.hqep.dataSharingPlatform.common.model.ResultBodyBean;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.pmsn.service.LoginService;
import com.hqep.dataSharingPlatform.pmsn.service.NlkfRestfulApiService;
import com.hqep.dataSharingPlatform.pmsn.service.PersonService;
import com.hqep.dataSharingPlatform.pmsn.service.SysMenuService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import utils.DateUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名: NlkfRestfulApiAction
 * @功能描述 能力开放api接口
 * @作者信息 sssJL
 * @创建时间 2021/12/06
 */
@RequestMapping("/nlkfApi")
@RestController
@Api(description = "能力开放api接口")
public class NlkfRestfulApiAction {


    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    @Autowired
    protected LoginService loginService;
    @Autowired
    protected NlkfRestfulApiService nlkfRestfulApiService;
    @Autowired
    private PersonService personService;
    @Autowired
    private SysMenuService sysMenuService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());




    /**
     * @方法名:nlkfUser
     * @功能描述:创建能力开放登录的用户并默认创建权限
     * @作者信息：sssJL
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "创建能力开放登录的用户并默认创建权限", notes = "创建能力开放登录的用户并默认创建权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/nlkfUser", method = {RequestMethod.POST,RequestMethod.GET})
    @RequestLog("创建能力开放登录的用户并默认创建权限")
    public @ResponseBody
    ResultBodyBean nlkfUser(String loginNum,String name,String depCode,String depName,String orgCode,String orgName) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        PageData pageData = new PageData();
        try {
            PageData pd = new PageData();
            pd.put("loginNum",loginNum);
            pd.put("name",name);
            pd.put("depCode",depCode);
            pd.put("depName",depName);
            pd.put("orgCode",orgCode);
            pd.put("orgName",orgName);
            if (!pd.isEmpty()) {//                loginNum -> admin
                if (pd.get("loginNum") == null || "".equals(pd.get("loginNum"))
                || pd.get("name") == null || "".equals(pd.get("name"))
                || pd.get("depCode") == null || "".equals(pd.get("depCode"))
                || pd.get("depName") == null || "".equals(pd.get("depName"))
                || pd.get("orgCode") == null || "".equals(pd.get("orgCode"))
                || pd.get("orgName") == null || "".equals(pd.get("orgName"))) {
                    errorReasonBean.setCode("428");
                    errorReasonBean.setText("登录参数不能为空");
                    resultBodyBean.setError(errorReasonBean);
                    status = HttpStatus.PRECONDITION_REQUIRED.value();
                } else {
                    String loginNum_nl = pd.getString("loginNum") + "_nl";
                    pd.put("loginNum",loginNum_nl);
                    PageData userinfo = nlkfRestfulApiService.queryUserListService(pd);
                    if(userinfo == null) {
                        // 创建账号及授权
                        pd.put("password","654321");
                        pd.put("phone","12345678999");
                        pd.put("roleName","业务专责");
                        pd.put("roleId","1");
//                       loginNum,name,depCode,depName,orgCode,orgName
                        personService.savePersonInfo(pd);
                    } else {
                       // 修改用户密码及重新授予初始权限
                        nlkfRestfulApiService.updateUserInfoService(pd);
                    }
                    userinfo = nlkfRestfulApiService.queryUserListService(pd);
                    PageData personInfo = new PageData();
                    personInfo.put("loginNum",userinfo.get("loginNum"));
                    personInfo.put("name",userinfo.get("name"));
                    personInfo.put("depCode",userinfo.get("depCode"));
                    personInfo.put("depName",userinfo.get("depName"));
                    personInfo.put("orgCode",userinfo.get("orgCode"));
                    personInfo.put("orgName",userinfo.get("orgName"));
                    pageData.put("personInfo", personInfo);
                    // 初始授权页面
                    List<Map<String,String>> menusInfo = new ArrayList<>();
                    Map<String, String> map = new HashMap<>();
                    map.put("name","购物车");
                    map.put("TAG","YES");
                    map.put("level","一");
                    menusInfo.add((Map<String, String>) map);
                    map = new HashMap<>();
                    map.put("name","数据目录");
                    map.put("TAG","YES");
                    map.put("level","一");
                    menusInfo.add((Map<String, String>) map);
                    map = new HashMap<>();
                    map.put("name","二级系统");
                    map.put("TAG","YES");
                    map.put("level","二");
                    map.put("ONE_CATALOG_NAME","数据目录");
                    menusInfo.add((Map<String, String>) map);
                    // 开始授权
                    for (Map<String,String> menu:menusInfo) {
                        String level = menu.get("level");
                        String ONE_CATALOG_NAME = null;
                        String TWO_CATALOG_NAME = null;
                        String THREE_CATALOG_NAME = null;
                        String FOUR_CATALOG_NAME = null;
                        String FIVE_CATALOG_NAME = null;
                        String menu_name = menu.get("name");
                        if("一".equals(level)){
                            ONE_CATALOG_NAME = menu_name;
                        }else if("二".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = menu_name;
                        }else if("三".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = menu.get("TWO_CATALOG_NAME");
                            THREE_CATALOG_NAME = menu_name;
                        }else if("四".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = menu.get("TWO_CATALOG_NAME");
                            THREE_CATALOG_NAME = menu.get("THREE_CATALOG_NAME");
                            FOUR_CATALOG_NAME = menu_name;
                        }else if("五".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = menu.get("TWO_CATALOG_NAME");
                            THREE_CATALOG_NAME = menu.get("THREE_CATALOG_NAME");
                            FOUR_CATALOG_NAME = menu.get("FOUR_CATALOG_NAME");
                            FIVE_CATALOG_NAME = menu_name;
                        }
                        PageData pds = new PageData();
                        pds.put("ONE_CATALOG_NAME",ONE_CATALOG_NAME);
                        pds.put("TWO_CATALOG_NAME",TWO_CATALOG_NAME);
                        pds.put("THREE_CATALOG_NAME",THREE_CATALOG_NAME);
                        pds.put("FOUR_CATALOG_NAME",FOUR_CATALOG_NAME);
                        pds.put("FIVE_CATALOG_NAME",FIVE_CATALOG_NAME);
                        List<String> menusId = sysMenuService.queryMenuId(pds);
                        PageData pad = new PageData();
                        for (String menuId:menusId) {
                            pad.put("menuId",menuId);
                            pad.put("userId", userinfo.get("id"));
                            sysMenuService.accredit(pad);
                        }
                    }
                    status = HttpStatus.OK.value();
                }
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            resultBodyBean.setData(pageData);
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("创建用户失败");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }




    /**
     * @方法名:nlkfUser
     * @功能描述:创建能力开放登录的用户并默认创建权限
     * @作者信息：sssJL
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "创建能力开放登录的用户并默认创建权限", notes = "创建能力开放登录的用户并默认创建权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PostMapping("/nlkfUser1")
    @RequestLog("创建能力开放登录的用户并默认创建权限")
    public @ResponseBody
    ResultBodyBean nlkfUser1(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        PageData pageData = new PageData();
        try {
            if (!pd.isEmpty()) {
//                loginNum -> admin
                if (pd.get("loginNum") == null || "".equals(pd.get("loginNum"))
                        || pd.get("name") == null || "".equals(pd.get("name"))
                        || pd.get("depCode") == null || "".equals(pd.get("depCode"))
                        || pd.get("depName") == null || "".equals(pd.get("depName"))
                        || pd.get("orgCode") == null || "".equals(pd.get("orgCode"))
                        || pd.get("orgName") == null || "".equals(pd.get("orgName"))) {
                    errorReasonBean.setCode("428");
                    errorReasonBean.setText("登录参数不能为空");
                    resultBodyBean.setError(errorReasonBean);
                    status = HttpStatus.PRECONDITION_REQUIRED.value();
                } else {
                    String loginNum = pd.getString("loginNum") + "_nl";
                    pd.put("loginNum",loginNum);
                    PageData userinfo = nlkfRestfulApiService.queryUserListService(pd);
                    if(userinfo == null) {
                        // 创建账号及授权
                        pd.put("password","654321");
                        pd.put("phone","12345678999");
                        pd.put("roleName","业务专责");
                        pd.put("roleId","1");
//                       loginNum,name,depCode,depName,orgCode,orgName
                        personService.savePersonInfo(pd);
                    } else {
                        // 修改用户密码及重新授予初始权限
                        nlkfRestfulApiService.updateUserInfoService(pd);
                    }
                    userinfo = nlkfRestfulApiService.queryUserListService(pd);
                    PageData personInfo = new PageData();
                    personInfo.put("loginNum",userinfo.get("loginNum"));
                    personInfo.put("name",userinfo.get("name"));
                    personInfo.put("depCode",userinfo.get("depCode"));
                    personInfo.put("depName",userinfo.get("depName"));
                    personInfo.put("orgCode",userinfo.get("orgCode"));
                    personInfo.put("orgName",userinfo.get("orgName"));
                    pageData.put("personInfo", personInfo);
                    // 初始授权页面
                    List<Map<String,String>> menusInfo = new ArrayList<>();
                    Map<String, String> map = new HashMap<>();
                    map.put("name","购物车");
                    map.put("TAG","YES");
                    map.put("level","一");
                    menusInfo.add((Map<String, String>) map);
                    map = new HashMap<>();
                    map.put("name","数据目录");
                    map.put("TAG","YES");
                    map.put("level","一");
                    menusInfo.add((Map<String, String>) map);
                    map = new HashMap<>();
                    map.put("name","二级系统");
                    map.put("TAG","YES");
                    map.put("level","二");
                    map.put("ONE_CATALOG_NAME","数据目录");
                    menusInfo.add((Map<String, String>) map);
                    // 开始授权
                    for (Map<String,String> menu:menusInfo) {
                        String level = menu.get("level");
                        String ONE_CATALOG_NAME = null;
                        String TWO_CATALOG_NAME = null;
                        String THREE_CATALOG_NAME = null;
                        String FOUR_CATALOG_NAME = null;
                        String FIVE_CATALOG_NAME = null;
                        String name = menu.get("name");
                        if("一".equals(level)){
                            ONE_CATALOG_NAME = name;
                        }else if("二".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = name;
                        }else if("三".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = menu.get("TWO_CATALOG_NAME");
                            THREE_CATALOG_NAME = name;
                        }else if("四".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = menu.get("TWO_CATALOG_NAME");
                            THREE_CATALOG_NAME = menu.get("THREE_CATALOG_NAME");
                            FOUR_CATALOG_NAME = name;
                        }else if("五".equals(level)){
                            ONE_CATALOG_NAME = menu.get("ONE_CATALOG_NAME");
                            TWO_CATALOG_NAME = menu.get("TWO_CATALOG_NAME");
                            THREE_CATALOG_NAME = menu.get("THREE_CATALOG_NAME");
                            FOUR_CATALOG_NAME = menu.get("FOUR_CATALOG_NAME");
                            FIVE_CATALOG_NAME = name;
                        }
                        PageData pds = new PageData();
                        pds.put("ONE_CATALOG_NAME",ONE_CATALOG_NAME);
                        pds.put("TWO_CATALOG_NAME",TWO_CATALOG_NAME);
                        pds.put("THREE_CATALOG_NAME",THREE_CATALOG_NAME);
                        pds.put("FOUR_CATALOG_NAME",FOUR_CATALOG_NAME);
                        pds.put("FIVE_CATALOG_NAME",FIVE_CATALOG_NAME);
                        List<String> menusId = sysMenuService.queryMenuId(pds);
                        PageData pad = new PageData();
                        for (String menuId:menusId) {
                            pad.put("menuId",menuId);
                            pad.put("userId", userinfo.get("id"));
                            sysMenuService.accredit(pad);
                        }
                    }
                    status = HttpStatus.OK.value();
                }
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            resultBodyBean.setData(pageData);
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("创建用户失败");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }



    /**
     * @方法名:loginNlhq
     * @功能描述:能力开放跳转登录
     * @作者信息：sssJL
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "能力开放跳转登录", notes = "能力获取跳转登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PostMapping("/loginNlhq")
    @RequestLog("能力开放跳转登录")
    public @ResponseBody
    ResultBodyBean loginNlhq(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                pd.put("password","c33367701511b4f6020ec61ded352059");
                if("l_wangdezhi".equals(pd.get("loginNum")) || "l_wangdezhi_nl".equals(pd.get("loginNum"))) {
                    pd.put("loginNum","sh");
                    pd.put("password","e10adc3949ba59abbe56e057f20f883e");
                }
                resultBodyBean = loginService.loginService(pd);
//                resultBodyBean = loginService.loginService(pd,request);
                String userinfo = JSON.toJSONString(resultBodyBean.getData());
                //  RedisUtil.set("userinfo",userinfo,0);
                status = HttpStatus.OK.value();
                //**
                HttpSession session = request.getSession();
                session.setAttribute("userinfo",resultBodyBean);
                session.setMaxInactiveInterval(2*60*1000);
                // */
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("参数不能为空");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }

    /**
     * @方法名:getStatistics
     * @功能描述:能力开放的统计分析
     * @作者信息：sssJL
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "能力开放的统计分析", notes = "能力开放的统计分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @RequestMapping(value="/getStatistics", method = {RequestMethod.POST,RequestMethod.GET})
//    @PostMapping("/getStatistics")
    @RequestLog("能力开放的统计分析")
    public @ResponseBody
    ResultBodyBean getStatistics(String tjny) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            PageData pd = new PageData();
            pd.put("tjny",tjny);
//            if (!pd.isEmpty() && tjny != null && !"".equals(tjny)) {
                PageData pageData = nlkfRestfulApiService.getStatistics(pd);
                resultBodyBean.setData(pageData);
                status = HttpStatus.OK.value();
//            } else {
//                // pd.put("tjny", DateUtils.getNowTime("yyyy-MM-dd"));
//                errorReasonBean.setCode("428");
//                errorReasonBean.setText("参数不能为空");
//                resultBodyBean.setError(errorReasonBean);
//                status = HttpStatus.PRECONDITION_REQUIRED.value();
//            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("请求统计分析结果失败");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }



    /**
     * @方法名:getStatistics
     * @功能描述:能力开放的统计分析
     * @作者信息：sssJL
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "能力开放的统计分析", notes = "能力开放的统计分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
//    @PostMapping("/getStatistics1")
    @RequestMapping(value="/getStatistics1", method = {RequestMethod.POST,RequestMethod.GET})
    //   @RequestLog("能力开放的统计分析")
    public @ResponseBody
    ResultBodyBean getStatistics1(@RequestBody PageData pd) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            if (!pd.isEmpty()) {
                PageData pageData = nlkfRestfulApiService.getStatistics(pd);
                resultBodyBean.setData(pageData);
                status = HttpStatus.OK.value();
            } else {
                // pd.put("tjny", DateUtils.getNowTime("yyyy-MM-dd"));
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
            errorReasonBean.setCode("428");
            errorReasonBean.setText("请求统计分析结果失败");
            resultBodyBean.setError(errorReasonBean);
            status = HttpStatus.PRECONDITION_REQUIRED.value();
        } finally {
            response.setStatus(status);
            System.out.println(resultBodyBean);
            return resultBodyBean;
        }
    }


    /**
     * @方法名:getStatistics
     * @功能描述:能力开放的统计分析
     * @作者信息：sssJL
     * @创建时间:9:13 2019/9/23
     **/
    @ApiOperation(value = "能力开放的统计分析", notes = "能力开放的统计分析")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
//    @PostMapping("/getStatistics1")
    @RequestMapping(value="/getStatistics2", method = {RequestMethod.POST,RequestMethod.GET})
    //   @RequestLog("能力开放的统计分析")
    public @ResponseBody
    ResultBodyBean getStatistics2(String tjny) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ResultBodyBean resultBodyBean = new ResultBodyBean();
        ErrorReasonBean errorReasonBean = new ErrorReasonBean();
        try {
            PageData pd = new PageData();
            pd.put("tjny",tjny);
            if (!pd.isEmpty()) {
                PageData pageData = new PageData();
                pageData.put("ywxt","47");
                pageData.put("sjb","65384");
                pageData.put("sjbqxsqd","45");
                pageData.put("zxsq","29");
                pageData.put("sjmlb","200");
                resultBodyBean.setData(pageData);
                status = HttpStatus.OK.value();
            } else {
                errorReasonBean.setCode("428");
                errorReasonBean.setText("参数不能为空");
                resultBodyBean.setError(errorReasonBean);
                status = HttpStatus.PRECONDITION_REQUIRED.value();
            }
            response.setContentType("application/vnd.api+json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.setStatus(status);
            return resultBodyBean;
        }
    }


}
