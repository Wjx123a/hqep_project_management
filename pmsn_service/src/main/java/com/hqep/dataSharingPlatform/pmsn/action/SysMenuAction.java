package com.hqep.dataSharingPlatform.pmsn.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.interface_serv.annotation.RequestLog;
import com.hqep.dataSharingPlatform.pmsn.model.SysMenuButton;
import com.hqep.dataSharingPlatform.pmsn.service.SysMenuButtonService;
import com.hqep.dataSharingPlatform.pmsn.service.SysMenuService;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.TUpCatalogTableRelService;
import com.hqep.dataSharingPlatform.pmsn.unit.RestfulHttpClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/sysMenu")
@RestController
@Api(description = "系统菜单")
public class SysMenuAction {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;
    @Resource
    protected HttpServletRequest request;

    @Autowired
    private SysMenuService service;
    @Autowired
    private SysMenuButtonService sysMenuButtonService;

    /**
     * 服务对象
     */
    @Autowired
    private TUpCatalogTableRelService tUpCatalogTableRelService;


    @ApiOperation(value = "菜单查询列表", notes = "菜单查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @RequestLog("菜单查询列表")
    @ResponseBody
    @RequestMapping(value = "/querySysMenu", method = RequestMethod.POST)
    public PageData querySysMenu(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
//            String iscMenu = null;
            if (pd.get("isIsc") !=null && !"".equals(pd.get("isIsc")) && !"null".equals(pd.get("isIsc"))) {
//                Map<String,String> map = new HashMap();
//                map.put("iscUserId", (String) pd.get("isIsc"));
//                String NR_REST_API = "http://10.166.8.157:8081/isctools/isc/FuncTree";
//                iscMenu = new RestfulHttpClient().getRestApiFuncTree(map,NR_REST_API);
            }
            // 如果进入数据目录管理
            if ("数据目录管理".equals(pd.get("ONE_CATALOG_NAME"))
                    && ( pd.get("TWO_CATALOG_NAME")!= null && !"".equals(pd.get("TWO_CATALOG_NAME")))) {
                PageData pdList = this.querySjmuglMenu(pd);
                List sysdata = (List) pdList.get("data");
                // 如果存在下一节点 自动查询返回
                if (sysdata !=null && sysdata.size()>0) {
                    return this.querySjmuglMenu(pd);
                } else {
                    //如果不存在下一个节点 那么返回当前节点后结束
                    PageData pdOne = new PageData();
                    pdOne.put("ONE_CATALOG_NAME",pd.get("ONE_CATALOG_NAME"));
                    pdOne.put("TWO_CATALOG_NAME",pd.get("TWO_CATALOG_NAME"));
                    pdOne.put("THREE_CATALOG_NAME",pd.get("THREE_CATALOG_NAME"));
                    pdOne.put("FOUR_CATALOG_NAME",pd.get("FOUR_CATALOG_NAME"));
                    pdOne.put("FIVE_CATALOG_NAME",pd.get("FIVE_CATALOG_NAME"));
                    pdOne.put("SIX_CATALOG_NAME",pd.get("SIX_CATALOG_NAME"));
                    pdOne.put("level",pd.get("menu_level"));
                    pdOne.put("name","");
                    List<PageData> relist = new ArrayList<>();
                    relist.add(pdOne);
                    PageData repd = new PageData();
                    repd.put("data",relist);
                    return repd;
                }
            }
            if ("数据中台".equals(pd.get("ONE_CATALOG_NAME"))
                    && ( pd.get("TWO_CATALOG_NAME")!= null && "中台贴源层".equals(pd.get("TWO_CATALOG_NAME")))) {
                if(pd.get("THREE_CATALOG_NAME") == null || "".equals(pd.get("THREE_CATALOG_NAME"))) {
                    List<PageData> menuList = tUpCatalogTableRelService.queryTycMenu(pd);
                    System.out.println("查询中台贴源层菜单！");
                    resultPd.put("data",menuList);
                    return resultPd;
                } else {
                    List<PageData> menuList = tUpCatalogTableRelService.queryTycMenuForLast(pd);
                    System.out.println("查询中台贴源层菜单的最后一级！");
                    resultPd.put("data",menuList);
                    return resultPd;
                }
            }
            /*if("共享数据集".equals(pd.get("ONE_CATALOG_NAME"))){
                List<PageData> list = service.queryGxsjj(pd);
                if(list !=null && list.size()>0){
                    resultPd.put("data",list);
                    return resultPd;
                }
            }*/
            List<PageData> list = service.querySysMenus(pd);
            if(list.size()!=0){
                if(list.get(0).get("name")!=null){
                    resultPd.put("data",list);
                }else{
                    resultPd.put("data", new ArrayList<>());
                }
            }else{
                resultPd.put("data", new ArrayList<>());
            }
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询菜单列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询菜单表列表出错");
        }
        return resultPd;
    }

    public PageData  querySjmuglMenu(PageData pd) {
        PageData resultPd = new PageData();
        // 判断是部署第六级目录
        // 不是第六级目录
        if (pd.get("SIX_CATALOG_NAME") == null || "".equals(pd.get("SIX_CATALOG_NAME"))) {
            // 判断是部署第五级目录
            // 不是第五级目录
            if (pd.get("FIVE_CATALOG_NAME") == null || "".equals(pd.get("FIVE_CATALOG_NAME"))) {
                // 判断是部署第四级目录
                // 不是第四级目录
                if (pd.get("FOUR_CATALOG_NAME") == null || "".equals(pd.get("FOUR_CATALOG_NAME"))) {
                    // 判断是部署第三级目录
                    // 不是第三级目录
                    if (pd.get("THREE_CATALOG_NAME") == null || "".equals(pd.get("THREE_CATALOG_NAME"))) {
                        pd.put("menu_level", 3);
                        resultPd = this.querySjmuglMenuForChild(pd);
                    } else {
                        // 是第三级目录
                        pd.put("menu_level", 4);
                        resultPd = this.querySjmuglMenuForChild(pd);
                    }
                } else {
                    // 是第四级目录
                    pd.put("menu_level", 5);
                    resultPd = this.querySjmuglMenuForChild(pd);
                }
            } else {
                // 是第五级目录
                pd.put("menu_level", 6);
                resultPd = this.querySjmuglMenuForChild(pd);
            }
        } else {
            // 是第六级目录
            pd.put("menu_level", 7);
            resultPd = this.querySjmuglMenuForChild(pd);
        }
        return resultPd;
    }
    public PageData querySjmuglMenuForChild(PageData pd) {
        PageData resultPd = new PageData();
        if ("一级目录管理".equals(pd.get("TWO_CATALOG_NAME"))) {
            List<PageData> list = tUpCatalogTableRelService.queryOneLevelList(pd);
            System.out.println(list);
            resultPd.put("data",list);
            return resultPd;
        }else if ("二级目录管理".equals(pd.get("TWO_CATALOG_NAME"))){
            List<PageData> list  = tUpCatalogTableRelService.queryTwoLevelList(pd);
            System.out.println(list);
            resultPd.put("data",list);
            return resultPd;
        } else {
            resultPd.put("data", new ArrayList<>());
            return resultPd;
        }

    }



    @ApiOperation(value = "对用户进行授予查看菜单的权限", notes = "对用户进行授予查看菜单的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/accredit", method = RequestMethod.POST)
    public PageData accredit(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        /*pd.put("userId","7f604c55142c4c63a6956fcbc4c6458c");
        pd.put("menuId","116");*/

        try {
            PageData pad = new PageData();
            List<Map<String,String>> menusInfo = (List<Map<String, String>>) pd.get("a");
            // 对按钮授权
            if (menusInfo!=null && menusInfo.size()>0) {
                for (Map<String,String> menu:menusInfo) {
                    if ("数据目录导入导出".equals(menu.get("name"))) {
                        List<SysMenuButton> listButton = sysMenuButtonService.queryList(new SysMenuButton());
                        for (int i = 0; i < listButton.size(); i++) {
                            pad.put("menuId",listButton.get(i).getId());
                            pad.put("userId", pd.get("userId"));
                            service.accredit(pad);
                        }
                    }
                    if ("数据目录导入导出".equals(menu.get("ONE_CATALOG_NAME"))) {
                        SysMenuButton button = new SysMenuButton();
                        button.setButtonName(menu.get("name"));
                        List<SysMenuButton> listButton = sysMenuButtonService.queryList(button);
                        for (int i = 0; i < listButton.size(); i++) {
                            pad.put("menuId",listButton.get(i).getId());
                            pad.put("userId", pd.get("userId"));
                            service.accredit(pad);
                        }
                    }
                }

            }
            // 对菜单授权
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
                List<String> menusId = service.queryMenuId(pds);
                for (String menuId:menusId) {
                    pad.put("menuId",menuId);
                    pad.put("userId", pd.get("userId"));
                    service.accredit(pad);
                }


            }


            resultPd.put("success","授权成功！");
            resultPd.put("error",null);
        }catch (Exception e){
            logger.error("授权错误！", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "授权错误！");
        }
        return resultPd;
    }



    @ApiOperation(value = "对用户进行授予查看菜单的权限", notes = "对用户进行授予查看菜单的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/removeAccredit", method = RequestMethod.POST)
    public PageData removeAccredit(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        /*pd.put("userId","7f604c55142c4c63a6956fcbc4c6458c");
        pd.put("menuId","116");*/

        try {
            PageData pad = new PageData();
            List<Map<String,String>> menusInfo = (List<Map<String, String>>) pd.get("a");
//            removeAccredit
            // 对按钮授权
            if (menusInfo!=null && menusInfo.size()>0) {
                for (Map<String,String> menu:menusInfo) {
                    if ("数据目录导入导出".equals(menu.get("name"))) {
                        List<SysMenuButton> listButton = sysMenuButtonService.queryList(new SysMenuButton());
                        for (int i = 0; i < listButton.size(); i++) {
                            pad.put("menuId",listButton.get(i).getId());
                            pad.put("userId", pd.get("userId"));
                            service.removeAccredit(pad);
                        }
                    }
                    if ("数据目录导入导出".equals(menu.get("ONE_CATALOG_NAME"))) {
                        SysMenuButton button = new SysMenuButton();
                        button.setButtonName(menu.get("name"));
                        List<SysMenuButton> listButton = sysMenuButtonService.queryList(button);
                        for (int i = 0; i < listButton.size(); i++) {
                            pad.put("menuId",listButton.get(i).getId());
                            pad.put("userId", pd.get("userId"));
                            service.removeAccredit(pad);

                        }
                    }
                }

            }


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
                List<String> menusId = service.queryMenuId(pds);
                for (String menuId:menusId) {
                    pad.put("menuId",menuId);
                    pad.put("userId", pd.get("userId"));
                    service.removeAccredit(pad);
                }


            }


            resultPd.put("success","授权成功！");
            resultPd.put("error",null);
        }catch (Exception e){
            logger.error("授权错误！", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "授权错误！");
        }
        return resultPd;
    }


    @ApiOperation(value = "对用户进行取消查看菜单的权限", notes = "对用户进行取消查看菜单的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/delAccredit", method = RequestMethod.POST)
    public PageData delAccredit(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        pd.put("userId","7f604c55142c4c63a6956fcbc4c6458c");
        pd.put("menuId","116");
        try{
            service.delAccredit(pd);
        }catch (Exception e){
            logger.error("授权错误！", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "授权错误！");
        }
        return resultPd;
    }


    @ApiOperation(value = "查询所有的用户", notes = "查询所有的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryUser", method = RequestMethod.POST)
    public PageData queryUser(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try{
            List<PageData> userList = service.queryUser(pd);
            resultPd.put("data",userList);
        }catch (Exception e){
            logger.error("查询用户错误！", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询用户错误！");
        }
        return resultPd;
    }
    @ApiOperation(value = "修改用户状态", notes = "修改用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public PageData updateStatus(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            service.updateStatus(pd);
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("修改失败！", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "修改失败！");
        }
        return resultPd;
    }
    @ApiOperation(value = "菜单查询列表无权限", notes = "菜单查询列表无权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/querySysMenuNoAuth", method = RequestMethod.POST)
    public PageData querySysMenuNoAuth(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        pd.put("userId","7f604c55142c4c63a6956fcbc4c6458c");
        try {
            List<PageData> list = service.querySysMenuNoAuth(pd);
            if(list.size()!=0){
                if(list.get(0).get("name")!=null){
                    resultPd.put("data",list);
                }else{
                    resultPd.put("data", new ArrayList<>());
                }
            }else{
                resultPd.put("data", new ArrayList<>());
            }
            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询菜单列表出错", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询菜单表列表出错");
        }
        return resultPd;
    }
    @ApiOperation(value = "查询用户带权限的菜单id", notes = "查询用户带权限的菜单id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryAuthMenuId", method = RequestMethod.POST)
    public PageData queryAuthMenuId(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        //pd.put("userId","7f604c55142c4c63a6956fcbc4c6458c");
        try {
            List<PageData> list = service.queryAuthMenuId(pd);
            if (pd.size() < 1 || "1".equals(pd.get("menu_level")) ) {
                PageData button = new PageData();
                button.put("TAG","NO");
                button.put("level","一");
                button.put("name","数据目录导入导出");
                list.add(button);
            }
            if ("2".equals(pd.get("menu_level")) && ("数据目录导入导出".equals(pd.get("one_catalog_name")))) {
                List<SysMenuButton> listButton = sysMenuButtonService.queryList(new SysMenuButton());
                list = new ArrayList<>();
                if (listButton!=null && listButton.size() >0) {
                    for (int i = 0; i < listButton.size(); i++) {
                        PageData button = new PageData();
                        button.put("ONE_CATALOG_NAME","数据目录导入导出");
                        button.put("name",listButton.get(i).getButtonName());
                        button.put("TAG","NO");
                        button.put("level","二");
                        list.add(button);
                    }
                } else  {
                    PageData button = new PageData();
                    button.put("ONE_CATALOG_NAME","数据目录导入导出");
                    button.put("TAG","NO");
                    button.put("level","一");
                    button.put("name","");
                    list.add(button);
                }
            }
            if(list.size()!=0){
                resultPd.put("data",list);
            }else{
                resultPd.put("data", new ArrayList<>());
            }

            resultPd.put("error", null);
        } catch (Exception e) {
            logger.error("查询权限菜单id错误！", e);
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询权限菜单id错误");
        }
        return resultPd;
    }
    @ApiOperation(value = "查询用户按钮", notes = "查询用户按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = false, value = "客户端传入JSON字符串", defaultValue = ""),})
    @ResponseBody
    @RequestMapping(value = "/queryAuthButtonId", method = RequestMethod.POST)
    public PageData queryAuthButtonId(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            if (pd.get("userId") !=null && !"".equals(pd.get("userId"))) {
                SysMenuButton sysMenuButton = new SysMenuButton();
                sysMenuButton.setId(pd.getString("userId"));
                sysMenuButton.setButtonCode(pd.getString("button_code"));
                if (sysMenuButtonService.queryListAuth(sysMenuButton).size() > 0) {
                    resultPd.put("data", true);
                    resultPd.put("error", null);
                    resultPd.put("code", "200");
                    return resultPd;
                } else {
                    resultPd.put("data", true);
                    resultPd.put("error", "无权限");
                    resultPd.put("code", "500");
                }
            } else {
                resultPd.put("data", true);
                resultPd.put("error", "参数异常");
                resultPd.put("code", "500");
                return resultPd;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询权限菜单id错误");
        }
        return resultPd;
    }



    }
