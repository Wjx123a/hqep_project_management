package com.hqep.dataSharingPlatform.pmsn.action;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hqep.dataSharingPlatform.pmsn.service.PersonService;
import com.hqep.dataSharingPlatform.common.utils.JwtUtil;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.common.utils.ResponseJsonApiUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @类名: PersonAction
 * @功能描述 人员信息模块
 * @作者信息 Wang_XD
 * @创建时间 2019/9/18
 */
@RequestMapping("/personAction")
@RestController
@Api(description = "人员信息模块")
public class PersonAction {

    @Resource
    protected HttpServletResponse response;

    @Autowired
    private PersonService personService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @方法名:putDemo
     * @功能描述:创建用户信息
     * @作者信息：Wang_XD
     * @创建时间:14:55 2019/9/18
     **/
    @ApiOperation(value = "创建用户信息", notes = "创建用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = false, value = "颁发token验证信息",
                    defaultValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZGIyMjQ0Yi1jMzY2LTQxM2ItOTAxZS01OTA1OTVkMDYxODciLCJpc3MiOiJkb25nZG9uZ0NlbnRlciIsInN1YiI6Ijk2NDlkMzgyOGFhNDRmY2U5OWQwNTAwYjI5ZjdhNzcxIiwiaWF0IjoxNTcxMDM2OTY0LCJuYW1lIjoiYWFhIiwicm9sZUlkIjoiMTMwMiIsInJvbGVOYW1lIjoi5pmu6YCa55So5oi3IiwicGhvbmUiOiIxNzc0NTY4NDU2OCIsImlzQWRtaW4iOiIwIiwiZXhwIjoxNTcxOTAwOTY0fQ.cAhu1uJfFsLKYtHa9WNNiQNZRWYe8l7iJmqnjMQv5i4"),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PutMapping("/savePersonInfo")
    public @ResponseBody
    PageData savePersonInfo(@RequestBody PageData pd, @RequestHeader String Authorization) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        Integer savePersonInfo = 0;
        PageData pageData = new PageData();
        try {
            if (!pd.isEmpty()) {
                savePersonInfo = personService.savePersonInfo(pd);
                if (savePersonInfo > 0) {
                    pageData.put("data", "创建成功!");
                    pageData.put("error", null);
                    status = HttpStatus.OK.value();
                    response.setContentType("application/vnd.api+json");
                } else if (savePersonInfo == -1) {
                    status = HttpStatus.PRECONDITION_REQUIRED.value();
                    pageData.put("data", new PageData());
                    pageData.put("error", new PageData("code", status, "text", "密码不能为空!"));
                    response.setContentType("application/vnd.api+json");
                } else if (savePersonInfo == -2) {
                    status = HttpStatus.BAD_REQUEST.value();
                    pageData.put("data", new PageData());
                    pageData.put("error", new PageData("code", status, "text", "登陆用户名已存在!"));
                    response.setContentType("application/vnd.api+json");
                } else {
                    status = HttpStatus.BAD_REQUEST.value();
                    pageData.put("data", new PageData());
                    pageData.put("error", new PageData("code", status, "text", "保存失败!"));
                    response.setContentType("application/vnd.api+json");
                }
            } else {
                status = HttpStatus.PRECONDITION_REQUIRED.value();
                pageData.put("data", new PageData());
                pageData.put("error", new PageData("code", status, "text", "参数不能为空!"));
                response.setContentType("application/vnd.api+json");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageData.put("error", ResponseJsonApiUtil.getErrorMsg("Exception", e.getClass().getSimpleName()));
        } finally {
            response.setStatus(status);
            return pageData;
        }
    }

    /**
     * @方法名:boolenUsablePerson
     * @功能描述:验证用户名是否存在
     * @作者信息：Wang_XD
     * @创建时间:16:01 2019/10/12
     **/
    @ApiOperation(value = "验证用户名是否存在", notes = "验证用户名是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "loginNum", dataType = "String", required = false, value = "客户端传入登录名", defaultValue = ""),
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = false, value = "颁发token验证信息",
                    defaultValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZGIyMjQ0Yi1jMzY2LTQxM2ItOTAxZS01OTA1OTVkMDYxODciLCJpc3MiOiJkb25nZG9uZ0NlbnRlciIsInN1YiI6Ijk2NDlkMzgyOGFhNDRmY2U5OWQwNTAwYjI5ZjdhNzcxIiwiaWF0IjoxNTcxMDM2OTY0LCJuYW1lIjoiYWFhIiwicm9sZUlkIjoiMTMwMiIsInJvbGVOYW1lIjoi5pmu6YCa55So5oi3IiwicGhvbmUiOiIxNzc0NTY4NDU2OCIsImlzQWRtaW4iOiIwIiwiZXhwIjoxNTcxOTAwOTY0fQ.cAhu1uJfFsLKYtHa9WNNiQNZRWYe8l7iJmqnjMQv5i4"),})
    @ApiResponses({@ApiResponse(code = 200, message = "指示客服端的请求已经成功收到，解析，接受"),
            @ApiResponse(code = 201, message = "资源已被创建"), @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 406, message = "不是指定的数据类型"),
            @ApiResponse(code = 500, message = "服务器内部错误")})
    @PostMapping("/boolenUsablePerson")
    public @ResponseBody
    PageData boolenUsablePerson(@RequestParam(value = "loginNum",required = true) String loginNum, @RequestHeader String Authorization) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        Integer savePersonInfo = 0;
        PageData pageData = new PageData();
        try {
            if (loginNum != null && !"".equals(loginNum)) {
                boolean usable = personService.boolenUsablePersonService(loginNum);
                pageData.put("data", usable);
                pageData.put("error", null);
                status = HttpStatus.OK.value();
                response.setContentType("application/vnd.api+json");
            } else {
                status = HttpStatus.PRECONDITION_REQUIRED.value();
                pageData.put("data", new PageData());
                pageData.put("error", new PageData("code", status, "text", "参数不能为空!"));
                response.setContentType("application/vnd.api+json");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageData.put("error", ResponseJsonApiUtil.getErrorMsg("Exception", e.getClass().getSimpleName()));
        } finally {
            response.setStatus(status);
            return pageData;
        }
    }


    @ApiOperation(value = "查询用户管理首页", notes = "查询用户管理首页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "颁发token验证信息",
                    defaultValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZGIyMjQ0Yi1jMzY2LTQxM2ItOTAxZS01OTA1OTVkMDYxODciLCJpc3MiOiJkb25nZG9uZ0NlbnRlciIsInN1YiI6Ijk2NDlkMzgyOGFhNDRmY2U5OWQwNTAwYjI5ZjdhNzcxIiwiaWF0IjoxNTcxMDM2OTY0LCJuYW1lIjoiYWFhIiwicm9sZUlkIjoiMTMwMiIsInJvbGVOYW1lIjoi5pmu6YCa55So5oi3IiwicGhvbmUiOiIxNzc0NTY4NDU2OCIsImlzQWRtaW4iOiIwIiwiZXhwIjoxNTcxOTAwOTY0fQ.cAhu1uJfFsLKYtHa9WNNiQNZRWYe8l7iJmqnjMQv5i4"),})
    @PostMapping("/queryUserList")
    public @ResponseBody
    PageData queryUserList(@RequestBody PageData pd) {
        PageData allPd = new PageData();
        try {
            if (pd.get("pageSize") != null && pd.get("pageIndex") != null) {
                int size = (int) pd.get("pageSize");
                int index = (int) pd.get("pageIndex");
                Page page = PageHelper.startPage(index, size);
                List<PageData> userList = personService.queryUserInfo(pd);
                allPd.put("data", userList);
                PageData paginationPd = new PageData();
                paginationPd.put("total", page.getTotal());
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                allPd.put("pagination", paginationPd);
                allPd.put("error", null);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            allPd.put("data", new ArrayList<>());
            allPd.put("error", "查询用户管理首页失败");
        } finally {
            return allPd;
        }
    }

    @ApiOperation(value = "修改用户状态", notes = "修改用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "颁发token验证信息",
                    defaultValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZGIyMjQ0Yi1jMzY2LTQxM2ItOTAxZS01OTA1OTVkMDYxODciLCJpc3MiOiJkb25nZG9uZ0NlbnRlciIsInN1YiI6Ijk2NDlkMzgyOGFhNDRmY2U5OWQwNTAwYjI5ZjdhNzcxIiwiaWF0IjoxNTcxMDM2OTY0LCJuYW1lIjoiYWFhIiwicm9sZUlkIjoiMTMwMiIsInJvbGVOYW1lIjoi5pmu6YCa55So5oi3IiwicGhvbmUiOiIxNzc0NTY4NDU2OCIsImlzQWRtaW4iOiIwIiwiZXhwIjoxNTcxOTAwOTY0fQ.cAhu1uJfFsLKYtHa9WNNiQNZRWYe8l7iJmqnjMQv5i4"),})
    @PostMapping("/updateUserStatus")
    public @ResponseBody
    PageData updateUserStatus(@RequestBody PageData pd) {
        PageData allPd = new PageData();
        try {
            personService.updateUserStatus(pd);
            allPd.put("data", "success");
            allPd.put("error", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            allPd.put("data", new ArrayList<>());
            allPd.put("error", "修改用户状态失败");
        } finally {
            return allPd;
        }
    }

    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "颁发token验证信息",
                    defaultValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZGIyMjQ0Yi1jMzY2LTQxM2ItOTAxZS01OTA1OTVkMDYxODciLCJpc3MiOiJkb25nZG9uZ0NlbnRlciIsInN1YiI6Ijk2NDlkMzgyOGFhNDRmY2U5OWQwNTAwYjI5ZjdhNzcxIiwiaWF0IjoxNTcxMDM2OTY0LCJuYW1lIjoiYWFhIiwicm9sZUlkIjoiMTMwMiIsInJvbGVOYW1lIjoi5pmu6YCa55So5oi3IiwicGhvbmUiOiIxNzc0NTY4NDU2OCIsImlzQWRtaW4iOiIwIiwiZXhwIjoxNTcxOTAwOTY0fQ.cAhu1uJfFsLKYtHa9WNNiQNZRWYe8l7iJmqnjMQv5i4"),})
    @PostMapping("/resetPassword")
    public @ResponseBody
    PageData resetPassword(@RequestBody PageData pd, @RequestHeader String Authorization) {
        PageData allPd = new PageData();
        try {
            Jws<Claims> parseJwt = JwtUtil.parseJwt(Authorization);
            String name = (String) parseJwt.getBody().get("name");
            String id = (String) parseJwt.getBody().get("personId");
            pd.put("optId", id);
            pd.put("optName", name);
            pd.put("optExplain", "重置密码");
            personService.resetPassword(pd);
            allPd.put("data", "success");
            allPd.put("error", null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            allPd.put("data", new ArrayList<>());
            allPd.put("error", "修改用户状态失败");
        } finally {
            return allPd;
        }
    }

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/insertRole", method = RequestMethod.POST)
    public PageData insertRole(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            personService.insertRole(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("新增角色出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "新增角色出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public PageData updateRole(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            personService.updateRole(pd);
            resultPd.put("data", "success");
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("修改角色出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改角色出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryRole", method = RequestMethod.POST)
    public PageData queryRole(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            Page page = PageHelper.startPage(index, size);
            List<PageData> list = personService.queryRole(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", page.getTotal());
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询角色列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询角色列表出错");
        }
        return resultPd;
    }

    @ApiOperation(value = "查询单位列表", notes = "查询单位列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryCompany", method = RequestMethod.POST)
    public PageData queryCompany(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<PageData> list = personService.queryCompany(pd);
            resultPd.put("data", list);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询单位列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询单位列表出错");
        }
        return resultPd;
    }

}
