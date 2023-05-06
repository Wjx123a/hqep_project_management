package com.hqep.dataSharingPlatform.isc.utils;

import com.google.gson.GsonBuilder;
import com.sgcc.isc.core.orm.complex.FunctionTree;
import com.sgcc.isc.core.orm.domain.BusinessApplication;
import com.sgcc.isc.core.orm.identity.User;
import com.sgcc.isc.core.orm.organization.BusinessOrganization;
import com.sgcc.isc.core.orm.role.OrganizationalRole;
import com.sgcc.isc.core.orm.role.Role;
import com.sgcc.isc.framework.common.entity.Paging;
import com.sgcc.isc.service.adapter.factory.AdapterFactory;
import com.sgcc.isc.service.adapter.helper.*;
import com.sgcc.isc.service.adapter.utils.AESUtils;
import com.sgcc.isc.service.adapter.utils.CASClient;
import com.sgcc.isc.service.adapter.utils.CASTicket;
import com.sgcc.isc.ualogin.client.util.IscSSOResourceUtil;
import com.sgcc.isc.ualogin.client.vo.IscSSOUserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 用于接入ISC管理接口
 * @Author: shaowenqiang
 * @CreateDate: 2021/9/28$ 9:13$
 * @Version: 1.0
 */
public class ISCUtil_new {
    private static String CURRENT_ENV="";//="DEV";    //    private static String CURRENT_ENV="PROD";
    private static String ISC_SSO_URL="http://10.166.3.29:7111/isc_sso";// = "http://10.166.8.82:17001/isc_sso"; //统一认证服务地址
    private static String APP_ID ="94c6880b80ee5570018166a411962b11";//= "ff8080814d339ab7014d6a0a0b19114b"; //统一认证服务地址
    private static String ISC_FRONTMV_SERV_URL="http://10.166.3.163/isc_frontmv_serv";// = "http://10.166.8.84:17001/isc_frontmv_serv"; //接口地址9
    private static String JSON_PATH="";//项目资源路径地址
    public ISCUtil_new() {
        try {
            Properties properties = new Properties();
            properties.load(ISCUtil_new.class.getResourceAsStream("/isc/iscinfo.properties"));
            CURRENT_ENV = properties.getProperty("CURRENT_ENV");
            ISC_SSO_URL = properties.getProperty("ISC_SSO_URL");
            APP_ID = properties.getProperty("APP_ID");
            ISC_FRONTMV_SERV_URL = properties.getProperty("ISC_FRONTMV_SERV_URL");
            JSON_PATH=this.getClass().getClassLoader().getResource("").getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static String prod_get_userinfo(String username, String password, HttpServletRequest request){
        new ISCUtil_new();
        if("DEV".equals(CURRENT_ENV)){
             System.out.println("DEV-开始使用测试数据");
            return test_get_userinfo(username,password,request);
        }
        String server = ISC_SSO_URL; //统一认证服务地址
//        username = "00070977";
//        password = "sgcc@1234";
//        String service = "http://172.16.190.173:7004/sgpms/";//业务应用访问地址
        String service = "http://10.166.8.83:17001/isc_mp";//业务应用访问地址
//        String unit = "sgcc";
        System.out.println("调用ISC_SSO服务进行登录认证开始...");
//        password = new String(Base64Util.encode(password.getBytes("UTF-8")));
        try {
            password = AESUtils.encrypt(password, AESUtils.keySeed);
        } catch (Exception e) {
            System.err.println("prod_get_userinfo==>密码加密失败");
        }
//        CASTicket t = CASClient.getTicket(server + "/v1/tickets", username, password, unit,service);
        CASTicket t = CASClient.getTicketByAES(server + "/v1/tickets", username, password, service);
        String tgt = t.getTicketGrantingTicket();
        System.out.println("tgt:"+tgt);
        String st = t.getServiceTicket();
        System.out.println("st:" + st);
        String userInfo = t.getUserInfo();
        System.out.println("userInfo:"+userInfo);
        String getErrorMessage = t.getErrorMessage();
        System.out.println("getErrorMessage:"+getErrorMessage);
//        Session session = SecurityUtils.getSubject().getSession();
//        SecurityUtils.getSubject().getSession().setTimeout(1800000);
//        session.setAttribute("isc_userinfo", userInfo);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*30);
        session.setAttribute("isc_userinfo", userInfo);
        System.out.println("插入session:"+ session.getAttribute("isc_userinfo"));

//        ISCUtil.getOrgRolesByUserId(request);
//        ISCUtil.getOrgsPathByOrgName();
//        ISCUtil.getBusiOrgByUserId(request);
//        ISCUtil.getUserByNamePassWord(username,password);
//        ISCUtil.getBusinessOrgByUserId(ISCUtil.getISCUserInfoBean(request).getIscUserId(), ISCUtil.getIscAppId());
//        ISCUtil.getBusinessOrgByUserId(ISCUtil.getISCUserInfoBean(request).getIscUserId(), ISCUtil.getIscAppId());

        return userInfo;
    }

    /**
     * 开发时使用 制作ISC假性登录
     *
     * @param username
     * @param password
     * @throws Exception
     */
    public static String test_get_userinfo(String username, String password, HttpServletRequest request) {
//        String userInfo = "{\"provinceId\":\"hl\",\"logintime\":1632796960617,\"name\":\"夏连成\",\"pwdBeforeDecode\":\"\",\"baseOrgId\":\"0A5EE26309BE75E0E0530100007FD2BA\",\"iscUserId\":\"EA0850F3643DC5BAE040007F0100203D\",\"iscAdCode\":\"xialc\",\"passWord\":\"\",\"scop\":\"0\",\"iscUserSourceId\":\"00070977\",\"ip\":\"10.167.179.179\"}";
        String userInfo = "{\"provinceId\":\"hl\",\"logintime\":1647242124531,\"name\":\"胡航帆\",\"pwdBeforeDecode\":\"\",\"baseOrgId\":\"EC3D682521F40C97E0430100007F78C9\",\"iscUserId\":\"EA0850F326E8C5BAE040007F0100203D\",\"iscAdCode\":\"00010002\",\"passWord\":\"\",\"scop\":\"0\",\"iscUserSourceId\":\"00010002\",\"ip\":\"10.167.77.88\"}";
//        Session session = SecurityUtils.getSubject().getSession();
//        SecurityUtils.getSubject().getSession().setTimeout(1800000);
//        session.setAttribute("isc_userinfo", userInfo);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*30);
        session.setAttribute("isc_userinfo", userInfo);
        return userInfo;
    }


    /**
     * 从业务系统Session中获取ISC用户信息
     *
     * @return
     */
    public static Map getISCUserInfo() {
//        String userInfo = SecurityUtils.getSubject().getSession().getAttribute("isc_userinfo").toString();
//        Map m = new GsonBuilder().serializeNulls().create().fromJson(userInfo, Map.class);
//        return m;
        return null;
    }

    /**
     * 从业务系统Session中获取ISC用户信息实体
     *
     * @return
     */
    public static IscSSOUserBean getISCUserInfoBean(HttpServletRequest request) {
        IscSSOUserBean iscSSOUserBean = null;
        try {
//            String user = SecurityUtils.getSubject().getSession().getAttribute("isc_userinfo").toString();
            /*获取当前用户登录信息*/
//            iscSSOUserBean = IscSSOResourceUtil.transferIscUserBean(user);
//            iscSSOUserBean =  new GsonBuilder().serializeNulls().create().fromJson(user, IscSSOUserBean.class);
//            /*当前登录用户ID*/
//            String userid = iscSSOUserBean.getIscUserId();
            // System.out.println(userid);
            /*当前登录用户账号*/
//            String loginName = iscSSOUserBean.getIscUserSourceId();
            // System.out.println(loginName);
            System.out.println("进入方法后获取session:"+ request.getSession().getAttribute("isc_userinfo"));
            String userinfo = request.getSession().getAttribute("isc_userinfo").toString();
            System.out.println("getISCUserInfoBean==>userinfo:"+userinfo);
            /*获取当前用户登录信息*/
            iscSSOUserBean =  new GsonBuilder().serializeNulls().create().fromJson(userinfo, IscSSOUserBean.class);
            /*当前登录用户ID*/
            String userid = iscSSOUserBean.getIscUserId();
            System.out.println(userid);
            /*当前登录用户账号*/
            String loginName = iscSSOUserBean.getIscUserSourceId();
            System.out.println(loginName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // System.out.println("session中ISC用户信息转换实体失败！");
            e.printStackTrace();
        }
        return iscSSOUserBean;
    }



    /**
     * 通用用户ID获取用户记录
     * @return
     */
    public static  List<User> getUserByIds(HttpServletRequest request){
//        if("DEV".equals(CURRENT_ENV))
//            return new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getUserByIds"), List.class);
        List<User> ulist=null;
        try{
            // System.out.println("进入ISC进行身份获取");
            //身份服务
            IIdentityService ids = AdapterFactory.getIdentityService();
//            ids.getUserOrgPathByUserId()
            String userId = ISCUtil_new.getISCUserInfoBean(request).getIscUserId();
            ulist = ids.getUserByIds(new String[]{userId});
            // System.out.println("通过用户ID集合查询用户信息");
            // System.out.println(JsonUtil.toJson(ulist));
        } catch(Exception e) {
            // System.out.println("通过用户ID集合查询用户信息失败！");
            e.printStackTrace();
        }
        return ulist;
    }

    /**
     * 通过登录名取得用户记录
     * @return
     */
    public static List<User> getUsersByLoginCode(HttpServletRequest request){
        if("DEV".equals(CURRENT_ENV))
            return new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getUsersByLoginCode"), List.class);

        List<User> ulist=null;
        try{
            //身份服务
            IIdentityService ids = AdapterFactory.getIdentityService();
            String loginCode =  ISCUtil_new.getISCUserInfoBean(request).getIscUserSourceId();
            ulist = ids.getUsersByLoginCode(loginCode);
            // System.out.println("通过用户登录账号取得用户信息：");
            // System.out.println(JsonUtil.toJson(ulist));
        } catch(Exception e){
            // System.out.println("通过用户登录账号取得用户信息失败！");
            e.printStackTrace();
        }
        return ulist;
    }


    /**
     * 取得注册的业务应用-取得权限平台注册的所有业务系统信息
     * @return
     */
    public static List<BusinessApplication> getBusinessApplication(){
//        if("DEV".equals(CURRENT_ENV))
//            return new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getBusinessApplication"), List.class);

        List<BusinessApplication> appList=null;
        try{
            //业务域服务
            IDomainService ds = AdapterFactory.getDomainService();
            appList = ds.getBusinessApplication();
            // System.out.println("取得权限平台注册的所有业务系统信息：");
            // System.out.println(JsonUtil.toJson(appList));
        } catch(Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return appList;
    }

    /**
     * 取得注册的业务应用-取得权限平台注册的所有组织机构信息
     * @return
     */
    public static List<List<BusinessOrganization>> getOrgsPathByOrgName(){
        new ISCUtil_new();
        if("DEV".equals(CURRENT_ENV))
            return new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getOrgsPathByOrgName"), List.class);
        List<List<BusinessOrganization>> resultList=null;
        try{
            resultList = AdapterFactory.getOrganizationService().getOrgsPathByOrgName(APP_ID, "%");
        } catch(Exception e){
            System.err.println("获取所有组织机构和部门 getOrgsPathByOrgName:::"+"失败");
            e.printStackTrace();
        }
//        System.err.println("获取所有组织机构和部门 getOrgsPathByOrgName:::"+"===============================");
//        System.err.println("获取所有组织机构和部门 getOrgsPathByOrgName:::"+resultList);
//        System.err.println("获取所有组织机构和部门 getOrgsPathByOrgName:::"+"===============================");
        return resultList;
    }



    /**
     * 日登录人数
     * @return
     */
    public static String getRunMonitorService(){
//        if("DEV".equals(CURRENT_ENV)){
//            return "12";
//        }
        String dln=null;
        try{
            //sg-i6000运行指标服务
            IRunMonitorService rms = AdapterFactory.getRunMonitorService();
            //        systemId 业务系统ID，unicode  业务组织单元统一编码，2018-07-20 11:30:30 时间点
            dln= rms.getBusinessDayLoginNum("systemId", "unicode", "2018-07-20 11:30:30");
            // System.out.println("日登录人数：" + dln);
        } catch(Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  dln;
    }

    /**
     * 用户所在的业务组织单元
     * @return
     */
    public static List<BusinessOrganization> getBusiOrgByUserId(HttpServletRequest request){
//        if("DEV".equals(CURRENT_ENV)){
//            List<BusinessOrganization> orgs =new ArrayList<>();
//            BusinessOrganization bo=new BusinessOrganization();
//            bo.setName("密山供电公司（直供）");
//            bo.setId("ff8080814d3399b4014d8a5dd87019d8");
//            orgs.add(bo);
//            return orgs;
//        }
        List<BusinessOrganization> orgs=null;
        try{
            //业务组织单元服务
            IOrganizationService os = AdapterFactory.getOrganizationService();
            String userId= ISCUtil_new.getISCUserInfoBean(request).getIscUserId();
            String appId= ISCUtil_new.APP_ID;
            orgs = os.getBusiOrgByUserId(userId, appId);
            if (orgs != null && orgs.size() > 0) {
                // System.out.println("用户所在的业务组织单元：" + orgs.get(0).getName() + "---" + orgs.get(0).getId());
            }
            // System.out.println(orgs);
        } catch(Exception e){
            e.printStackTrace();
        }
        return orgs;
    }
    /**
     * 资源服务-当前用户在当前系统下的菜单树
     * @return
     */
    public static FunctionTree getFuncTree(HttpServletRequest request){
        FunctionTree ft=null;
        if("DEV".equals(CURRENT_ENV))return new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getFuncTree"), FunctionTree.class);
        try {
            //资源服务
            IResourceService rs=AdapterFactory.getResourceService();
            String userId= ISCUtil_new.getISCUserInfoBean(request).getIscUserId();
            String appId= ISCUtil_new.APP_ID;
            ft=rs.getFuncTree(userId, appId, null);
            // System.out.println("资源服务-当前用户在当前系统下的菜单树："+JsonUtil.toJson(ft));
        } catch (Exception e) {
            // System.out.println("资源服务-当前用户在当前系统下的菜单树！");
            e.printStackTrace();
        }
        System.err.println("资源服务-当前用户在当前系统下的菜单树 getFuncTree:::"+"===============================");
        System.err.println("资源服务-当前用户在当前系统下的菜单树 getFuncTree:::"+ft);
        System.err.println("资源服务-当前用户在当前系统下的菜单树 getFuncTree:::"+"===============================");

        return ft;
    }

    /**
     * 角色服务-当前用户在当前系统下的组织角色
     */
    public static List<OrganizationalRole> getOrgRolesByUserId1(String userId){
        if("DEV".equals(CURRENT_ENV)) {
            List<Map> organizationalRoles =
                    new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getOrgRolesByUserId"),List.class );
            List<OrganizationalRole> resultList = new ArrayList<>();
            for(Map m:organizationalRoles){
                resultList.add(new GsonBuilder().serializeNulls().create().fromJson(m.toString(),OrganizationalRole.class));
            }
            return resultList;
        }
        List<OrganizationalRole> ors=null;
        try {
            //角色服务
            IRoleService ros=AdapterFactory.getRoleService();
            System.out.println("角色服务通过==========");
            //业务域服务
//            String userId= "2EC66480CBF20899E0533603A60A069C";
            String appId= ISCUtil_new.APP_ID;
            ors=ros.getOrgRolesByUserId(userId, appId, null);
             System.out.println("当前用户在当前系统下的组织角色："+ ors);
        } catch (Exception e) {
             System.out.println("当前用户在当前系统下的组织角色失败！");
            e.printStackTrace();
        }

        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+"===============================");
        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+ors);
        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+"===============================");

        return ors;
    }

    /**
     * 角色服务-当前用户在当前系统下的组织角色
     */
    public static List<OrganizationalRole> getOrgRolesByUserId2(){
        if("DEV".equals(CURRENT_ENV)) {
            List<Map> organizationalRoles =
                    new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getOrgRolesByUserId"),List.class );
            List<OrganizationalRole> resultList = new ArrayList<>();
            for(Map m:organizationalRoles){
                resultList.add(new GsonBuilder().serializeNulls().create().fromJson(m.toString(),OrganizationalRole.class));
            }
            return resultList;
        }
        List<OrganizationalRole> ors=null;
        try {
            System.err.println("获取当前用户具有的角色列表开始");
            //角色服务
            IRoleService ros=AdapterFactory.getRoleService();
            System.out.println("角色服务通过==========");
            //业务域服务
            String userId= "30035976";
            String appId= ISCUtil_new.APP_ID;
            System.out.println("userId:"+ userId);
            System.out.println("appId:"+ appId);
            ors=ros.getOrgRolesByUserId(userId, appId, null);
             System.out.println("当前用户在当前系统下的组织角色getOrgRolesByUserId2："+ors);
        } catch (Exception e) {
             System.out.println("当前用户在当前系统下的组织角色失败！");
            e.printStackTrace();
        }

        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+"===============================");
        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+ors);
        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+"===============================");

        return ors;
    }

    /**
     * 角色服务-当前用户在当前系统下的组织角色
     */
    public static List<OrganizationalRole> getOrgRolesByUserId( HttpServletRequest request){
        System.out.println("角色服务-当前用户在当前系统下的组织角色开始===========");
        if("DEV".equals(CURRENT_ENV)) {
            List<Map> organizationalRoles =
                    new GsonBuilder().serializeNulls().create().fromJson(readJsonData("getOrgRolesByUserId"),List.class );
            List<OrganizationalRole> resultList = new ArrayList<>();
            for(Map m:organizationalRoles){
                resultList.add(new GsonBuilder().serializeNulls().create().fromJson(m.toString(),OrganizationalRole.class));
            }
            return resultList;
        }
        List<OrganizationalRole> ors=null;
        System.out.println("角色服务-当前用户在当前系统下的组织角色===== 远程调用:" );
        try {
            //角色服务
            IRoleService ros=AdapterFactory.getRoleService();
            System.out.println("角色服务-当前用户在当前系统下的组织角色===== 远程调用:角色服务" );
            //业务域服务
            String userId= ISCUtil_new.getISCUserInfoBean(request).getIscUserId();
            String appId= ISCUtil_new.APP_ID;
            System.out.println("角色服务-当前用户在当前系统下的组织角色===== 远程调用:userId：" + userId +"appId："+appId);
            ors=ros.getOrgRolesByUserId(userId, appId, null);
             System.out.println("当前用户在当前系统下的组织角色："+ ors);
        } catch (Exception e) {
            System.out.println("当前用户在当前系统下的组织角色失败！");
            e.printStackTrace();
        }
        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+"===============================");
        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+ors);
        System.err.println("获取当前用户具有的角色列表 getOrgRolesByUserId:::"+"===============================");
        return ors;
    }


    /**
     * ͨ扩展通用服务
     * @return
     */
    public static Object callService(HttpServletRequest request){
        Object o=null;
        try {
            //ͨ扩展通用服务
            IExternService es=AdapterFactory.getExternService();
            String userId= ISCUtil_new.getISCUserInfoBean(request).getIscUserId();
            String appId= ISCUtil_new.APP_ID;
            //扩展通用接口调用示例
            String uri="/user/getObjectsByUserId";
            Map<Object, Object> pm=new HashMap<Object, Object>();
            pm.put("userId", userId);
            pm.put("systemId", appId);
            o=es.callService(uri, pm);
            // System.out.println("当前用户相关的对象信息："+JsonUtil.toJson(o));
        } catch (Exception e) {
            // System.out.println("当前用户相关的对象信息获取失败！");
            e.printStackTrace();
        }
        return o;
    }
    /**
     * 读取json文件并且转换成字符串
     * @param
     * @return
     * @throws IOException
     */
    public static String readJsonData(String pactFile) {
        pactFile=JSON_PATH+"isc/json/"+pactFile+".json";
        // System.out.println(pactFile);
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(pactFile);//"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            System.err.println("找不到Json文件 " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);
            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return strbuffer.toString();
    }

    /**
     * @Author hanyf
     * @Description //提取组织角色名称
     * @Date 10:44 2021/10/15
     * @Param []
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> getOrgNames(HttpServletRequest request) {
        List<String> orgRoleNames = new ArrayList<>();
        try {
            // 当前用户在当前系统下的组织角色
            List<OrganizationalRole> organi = getOrgRolesByUserId(request);
            List<String> names = organi.stream().map(OrganizationalRole::getName).collect(Collectors.toList());
            for (String name : names) {
                if (name.contains("_")) {
                    String name1 = name.substring(0, name.indexOf("_"));
                    String orgRoleName = name.substring(name1.length()+1);
                    orgRoleNames.add(orgRoleName);
                } else {
                    orgRoleNames.add(name);
                }
            }
        } catch (Exception e) {
             System.out.println("提取组织角色名称方法出错， ISCUtilNew->getOrgNames");
            e.printStackTrace();
        }
        return orgRoleNames;
    }


//    List resultList = AdapterFactory.getOrganizationService().getOrgsPathByOrgName(systemId, "%");

//    List orgList = AdapterFactory.getOrganizationService().getBusiOrgByUserId(user.getId(), PlatformConfigUtil.getString("ISC_APPID"));

//AdapterFactory.getRoleService().getRoleByRoleId(ywRole)
//     AdapterFactory.getRoleService().getOrgRolesByUserId(userId, PlatformConfigUtil.getString("ISC_APPID"), null);

//    (BusinessOrganization)AdapterFactory.getOrganizationService().getBusiOrgsByIds(new String[] { orgId }).get(0);
//List urls = AdapterFactory.getResourceService().getDeployUrlByFuncId(funID);

//    AdapterFactory.getIdentityService().userLoginAuth(realUserName, realPassword);

//    List listUser = AdapterFactory.getIdentityService().getUsersByOrg(appId, orgId, null, null);




    public static String getIscAppId() {
        return "8a2688d37f49ad59017f76fe3986014c";
    }

    public static List<User> getUsersByOrg(String appId, String orgId) {
        try {
            List listUser = AdapterFactory.getIdentityService().getUsersByOrg(appId, orgId, null, null);
            return listUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> getUsersByIds(String[] userIds) {
        try {
            List listUser = AdapterFactory.getIdentityService().getUserByIds(userIds);
            return listUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Paging getUsers() {
        try {
            Paging PagUser = AdapterFactory.getIdentityService().getUsers(getIscAppId(), null, null, 1, 20, true);
            return PagUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过用户名和密码获取用户信息
     * @param userName
     * @param passWord
     * @return
     */
    public static User getUserByNamePassWord(String userName, String passWord) {
        try {
            User user = AdapterFactory.getIdentityService().userLoginAuth(userName, passWord);
            System.err.println("getUserByNamePassWord::"+"==================================================");
            System.err.println("getUserByNamePassWord::"+user);
            System.err.println("getUserByNamePassWord::"+"==================================================");
            return user;
        } catch (Exception localException) {
        }
        return null;
    }

    public static List<BusinessOrganization> getBusinessOrgByUserId(String appId, String userId) {
        try {
            List<BusinessOrganization> listOrg = AdapterFactory.getOrganizationService().getBusiOrgByUserId(userId,
                    appId);

            System.err.println(" getBusinessOrgByUserId:::"+"===============================");
            System.err.println(" getBusinessOrgByUserId:::"+listOrg);
            System.err.println(" getBusinessOrgByUserId:::"+"===============================");
            return listOrg;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static Paging getSubBussOrgs(String busiOrgId, Map<String, Object> filterStr, String[] orderStr, int pageSize, int pageNo, boolean isCalculate) {
        try {
            return AdapterFactory.getOrganizationService().getSubBussOrgs(busiOrgId, filterStr, orderStr, pageSize, pageNo, isCalculate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getOrgNameById(String orgId) {
        try {
            return ((BusinessOrganization) AdapterFactory.getOrganizationService().getBusiOrgsByIds(new String[]{orgId}).get(0)).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<BusinessOrganization> getSubOrg(String orgId) {
        try {
            return (List) AdapterFactory.getOrganizationService().getSubBussOrgs(orgId, null, null, 2147483647, 1, true).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BusinessOrganization getOwnOrg(String orgId) {
        try {
            List busiLs = AdapterFactory.getOrganizationService().getBusiOrgsByIds(new String[]{orgId});
            if (busiLs.size() > 0)
                return (BusinessOrganization) busiLs.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<BusinessOrganization> getOrgPathByOrgId(String orgId) {
        try {
            return AdapterFactory.getOrganizationService().getOrgPathByOrgId(orgId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<BusinessOrganization> getBusiOrgBySystemId(String systemId) {
        List list = new ArrayList();
        try {
            list = AdapterFactory.getOrganizationService().getChildOrgsBySystemId(systemId, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }




    public static List<OrganizationalRole> getOrgRolesByBusiOrgId(String systemId, String busiOrgId, Map<String, String> param, String[] orderStr) {
        try {
            return AdapterFactory.getRoleService().getOrgRolesByBusiOrgId(systemId, busiOrgId, param, orderStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> getUsersByOrgRole(String orgRoleId, Map<String, String> param, String[] orderStr) {
        try {
            return AdapterFactory.getIdentityService().getUsersByOrgRole(orgRoleId, param, orderStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<OrganizationalRole> getOrgRolesByUserId(String userId, String systemId, Map<String, String> param) {
        try {
            return AdapterFactory.getRoleService().getOrgRolesByUserId(userId, systemId, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过角色获取菜单ID
     * @param roleId
     * @return
     */
    public static FunctionTree getFuncTreeByRoleId(String roleId) {
        try {
            FunctionTree ft= AdapterFactory.getResourceService().getFuncTreeByRoleId(roleId);
            System.err.println(" getBusinessOrgByUserId:::"+"===============================");
            System.err.println(" getBusinessOrgByUserId:::"+ft);
            System.err.println(" getBusinessOrgByUserId:::"+"===============================");
            return ft;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Role> getRoleByRoleId(String roleId) {
        try {
            return AdapterFactory.getRoleService().getRoleByRoleId(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FunctionTree getFuncTree(String userId, String systemId, String funcCategory) {
        try {
            return AdapterFactory.getResourceService().getFuncTree(userId, systemId, funcCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}



