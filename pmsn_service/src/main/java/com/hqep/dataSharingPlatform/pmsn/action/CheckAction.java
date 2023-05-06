package com.hqep.dataSharingPlatform.pmsn.action;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.CheckService;
import com.hqep.dataSharingPlatform.pmsn.service.UpPlateformService;
import com.hqep.dataSharingPlatform.pmsn.unit.RCheckMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 两级数据目录一致性校验方案
 * 总部调用省侧代码
 */
@RestController
@RequestMapping("/check")
public class CheckAction {

    @Autowired
    CheckService service;

    @Autowired
    private UpPlateformService upPlateformService;



    @RequestMapping("/getToken")
    public  Map<String,Object> createToken(String appId, String appKey, String timestamp) throws Exception {
        System.out.println("调用本地：查询token信息createToken");
        try {
            //用MD5加密生成秘钥串
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes  = md.digest((appId+appKey+timestamp).getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            String token = bigInt.toString(16);
            return RCheckMsg.successMsg( "接口返回成功",token);
        } catch (Exception e) {
            System.out.println(e);
            return RCheckMsg.errorMsg(500,"接口失败",null);
        }
    }


    /**
     * 生成token
     * @param timestamp
     * @return
     * @throws Exception
     */

    @RequestMapping("/login")
    public  Map<String,Object> login(String userName, String password, String timestamp) throws Exception {
        try {
            if (!"G_CHECK_USER".equals(userName)) {
                return RCheckMsg.errorMsg(500,"账号或者密码错误",null);
            }
            if (!"9e12a324e0a011ecabe7e84dd0b414aa".equals(password)) {
                return RCheckMsg.errorMsg(500,"账号或者密码错误",null);
            }
            timestamp = null;
            //用MD5加密生成秘钥串
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes  = md.digest((userName+password+timestamp).getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            String token = bigInt.toString(16);
            return RCheckMsg.successMsg( token,"接口返回成功");
        } catch (Exception e) {
            System.out.println(e);
            return RCheckMsg.errorMsg(500,"接口返回失败",null);
        }
    }


    /**
     * 查询资源目录树信息
     | 参数名称   | 说明                     | 是否必须 | 类型   | schema |
     | ---------- | ------------------------ | -------- | ------ | ------ |
     | orgCode    | 单位编码（按照统一编码） | false    | string |        |
     | systemCode | 系统编码（按照统一编码） | false    | string |        |
     * @return
     */
    @RequestMapping("/queryCatalogTree")
    public Map<String,Object> queryCatalogTree(ServletRequest request){
        System.out.println("调用本地：查询资源目录树信息queryCatalogTree");
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            Map<String,Object> tokenCheck = checkAuthorizeToken(req);
            if(!(boolean)tokenCheck.get("success")) {
                return tokenCheck;
            }
            PageData pd = new PageData();
            String orgCode = req.getParameter("orgCode");
            String systemCode = req.getParameter("systemCode");
            pd.put("orgCode",orgCode);
            pd.put("systemCode",systemCode);
            System.out.println("参数orgCode:"+orgCode+",systemCode:"+systemCode);
            List<Map<String,String>> list =  service.queryCatalogTree(pd);
            return RCheckMsg.successMsg(list,"查询成功!");
            } catch (Exception e) {
                e.printStackTrace();
                return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
            }
    }

    /**
     * 查询表列表信息
     * @param
    | 参数名称     | 说明                     | 类型   | 是否必须 |
    | ------------ | ------------------------ | ------ | -------- |
    | pageNum      | 当前页                   | int    | true     |
    | pageSize     | 每页的数量               | int    | true     |
    | orgCode      | 单位编码（按照统一编码） | string | false    |
    | systemCode   | 系统编码                 | string | false    |
    | dbName       | 数据源名称               | string | false    |
    | tableName    | 表英文名                 | string | false    |
    | oneCatalog   | 一级目录名称             | string | false    |
    | twoCatalog   | 二级目录名称             | string | false    |
    | threeCatalog | 三级目录名称             | string | false    |
     * @return
     */
    @RequestMapping("/queryTableList")
    public Map<String,Object>  queryTableList(ServletRequest request){
        System.out.println("调用本地：查询表列表信息queryTableList");
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            Map<String,Object> tokenCheck = checkAuthorizeToken(req);
            if(!(boolean)tokenCheck.get("success")) {
                return tokenCheck;
            }
            PageData pd = new PageData();
            try {
                int pageSize = Integer.parseInt(req.getParameter("pageSize"));
                int pageNum =  Integer.parseInt(req.getParameter("pageNum"));
                System.out.println(pageSize);
                System.out.println(pageNum);
                int rowstart = (pageNum - 1) * pageSize + 1;
                int rowend = pageNum*pageSize;
                System.out.println(rowstart);
                System.out.println(rowend);
                String orgCode = req.getParameter("orgCode");
                String systemCode = req.getParameter("systemCode");
                String dbName = req.getParameter("dbName");
                String tableName = req.getParameter("tableName");
                String oneCatalog = req.getParameter("oneCatalog");
                String twoCatalog = req.getParameter("twoCatalog");
                String threeCatalog = req.getParameter("threeCatalog");
                pd.put("pageSize",pageSize);
                pd.put("pageNum",pageNum);
                pd.put("rowstart",rowstart);
                pd.put("rowend",rowend);
                pd.put("orgCode",orgCode);
                pd.put("systemCode",systemCode);
                pd.put("dbName",dbName);
                pd.put("tableName",tableName);
                pd.put("oneCatalog",oneCatalog);
                pd.put("twoCatalog",twoCatalog);
                pd.put("threeCatalog",threeCatalog);
                System.out.println("参数pageSize:"+pageSize+",rowstart:"+pageNum+",rowstart:"+pageNum
                        +",rowend:"+rowend+",orgCode:"+orgCode
                        +",systemCode:"+systemCode+",dbName:"+dbName
                        +",tableName:"+tableName+",oneCatalog:"+oneCatalog
                        +",twoCatalog:"+twoCatalog+",threeCatalog:"+threeCatalog);
            } catch (Exception e) {
                e.printStackTrace();
                return RCheckMsg.errorMsg(-110,null,"参数验证失败");
            }

            List<Map<String,String>> list =  service.queryTableList(pd);
            int total = service.queryTableListCount(pd);
            Map pagination = new LinkedHashMap();
            pagination.put("pageNum", pd.get("pageNum"));
            pagination.put("pageSize", pd.get("pageSize"));
            pagination.put("total", total);
            pagination.put("list", list);
            return RCheckMsg.successMsg(pagination,"查询成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }


    /**
     * 查询表属性信息
     *
     | 参数名称 | 说明       | 类型   | 是否必须 |
     | -------- | ---------- | ------ | -------- |
     | pageNum  | 当前页     | int    | true     |
     | pageSize | 每页的数量 | int    | true     |
     | tableIds  |表ID集合英文逗号分隔，最大100个ID       | String | true     |

     * @return
     */
    @RequestMapping("/queryTableProList")
    public Map<String,Object> queryTableProList(ServletRequest request){
        System.out.println("调用本地：查询表属性信息queryTableProList");
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            Map<String,Object> tokenCheck = checkAuthorizeToken(req);
            if(!(boolean)tokenCheck.get("success")) {
                return tokenCheck;
            }
            PageData pd = new PageData();
            try {
                int pageSize = Integer.parseInt(req.getParameter("pageSize"));
                int pageNum =  Integer.parseInt(req.getParameter("pageNum"));
                System.out.println(pageSize);
                System.out.println(pageNum);
                int rowstart = (pageNum - 1) * pageSize + 1;
                int rowend = pageNum*pageSize;
                String tableId = req.getParameter("tableIds");
                if (tableId == null || "".equals(tableId)) {
                    return RCheckMsg.errorMsg(-110,null,"参数验证失败");
                }
                String [] tableIds = tableId.split(",");
                pd.put("pageSize",pageSize);
                pd.put("pageNum",pageNum);
                pd.put("rowstart",rowstart);
                pd.put("rowend",rowend);
                pd.put("tableIds",tableIds);
                System.out.println("参数pageSize:"+pageSize+",pageNum:"+pageNum
                        +",rowstart:"+rowstart+",rowend:"+rowend+",tableId:"+tableId);
            } catch (Exception e) {
                e.printStackTrace();
                return RCheckMsg.errorMsg(-110,null,"参数验证失败");
            }
            List<Map<String,String>> list = service.queryTableProList(pd);
            int total = service.queryTableProListCount(pd);
            Map pagination = new LinkedHashMap();
            pagination.put("pageNum", pd.get("pageNum"));
            pagination.put("pageSize", pd.get("pageSize"));
            pagination.put("total", total);
            pagination.put("list", list);
            return RCheckMsg.successMsg(pagination,"查询成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }

    }

    private Map<String,Object> checkAuthorizeToken(HttpServletRequest req) {
        try {
            String token = req.getHeader("token");
            // String token = c7ed9ec40c684cd38242c30d033a7d61;
            String appId = "G_CHECK_USER"; // req.getParameter ("appId");
            String timestamp = req.getParameter ("timestamp");
            //获取本地保存的省公司秘钥
            String appKey = "9e12a324e0a011ecabe7e84dd0b414aa";
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");//设置日期格式
//            Date now = new Date();
            // timestamp = sdf.format(now);
//            System.out.println(timestamp);

            //用MD5加密生成秘钥串
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes  = md.digest((appId+appKey+timestamp).getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            String checkToken = bigInt.toString(16);
            System.out.println(checkToken);
            if(!StringUtils.equals(token, checkToken)) {
                return RCheckMsg.errorMsg(-101,"token无效或已过期",null);
            }
            return RCheckMsg.successMsg("接口返回成功",checkToken);
        } catch (Exception ex) {
            System.out.println(ex);
            return RCheckMsg.errorMsg(-101,"token无效或已过期",null);
        }
    }



    @RequestMapping("/queryDept")
    public List<Map<String,String>> queryDept(ServletRequest request){
        PageData pd = null;
        try {
            pd = new PageData();
            pd.put("test",11);
            System.out.println(service.queryDept(null));
            return service.queryDept(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行省侧统计结果
     * 用于生成 上报国网统计表
     * 方便张一飞省侧自查数据
     * @param request
     * @return
     */
    @RequestMapping("/operation")
    public Map<String,Object> operation(ServletRequest request){
        PageData pd = null;
        try {
            upPlateformService.insert_merge_t_dm_province_catalog_num();
            return RCheckMsg.successMsg(null,"执行成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }


    @ResponseBody
    @RequestMapping("/querytZbCheck")
    public Map<String,Object> querytZbCheck(@RequestBody PageData pd){
//        PageData pd = new PageData();
        try {
            PageData resultPd = new PageData();
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            int rowstart = (index - 1) * size + 1;
            int rowend = size*index;
            pd.put("rowstart",rowstart);
            pd.put("rowend",rowend);
            String BATCH_TIME = (String) pd.get("BATCH_TIME");
            if(BATCH_TIME == null || "".equals(BATCH_TIME)) {
                resultPd.put("data", new ArrayList<>());
                PageData paginationPd = new PageData();
                paginationPd.put("total", 0);
                paginationPd.put("pageSize", size);
                paginationPd.put("current", index);
                // 页码
                resultPd.put("pagination", paginationPd);
                resultPd.put("error", null);
                Map<String,Object> test = RCheckMsg.successMsg(resultPd,"请先选择查询批次!");
                return test;
            }
            List<PageData> list = service.queryt_dm_zb_catalog_num_zl(pd);
//            System.out.println(list);
            int total = service.queryt_dm_zb_catalog_num_count_zl(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", total);
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
            Map<String,Object> test = RCheckMsg.successMsg(resultPd,"查询成功!");
            return test;
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }


    @ResponseBody
    @RequestMapping("/querytZbCheckCl")
    public Map<String,Object> querytZbCheckCl(@RequestBody PageData pd){
//        PageData pd = new PageData();
        try {
            PageData resultPd = new PageData();
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            int rowstart = (index - 1) * size + 1;
            int rowend = size*index;
            pd.put("rowstart",rowstart);
            pd.put("rowend",rowend);
            List<PageData> list = service.queryt_dm_zb_catalog_num_cl(pd);
//            System.out.println(list);
            int total = service.queryt_dm_zb_catalog_num_count_cl(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", total);
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
            Map<String,Object> test = RCheckMsg.successMsg(resultPd,"查询成功!");
            return test;
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }


    /**
     * 执行总部存量数据统计结果
     * 用于生成 总部存量数据差异统计结果
     * 该表属于自建表 用于生成统计结果 方便查询速度
     * @param request
     * @return
     */
    @RequestMapping("/operationZbCheckCl")
    public Map<String,Object> operationZbCheckCl(ServletRequest request){
        PageData pd = null;
        try {
            upPlateformService.inser_t_dm_zb_catalog_num_cl();
            return RCheckMsg.successMsg(null,"执行成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }


    @ResponseBody
    @RequestMapping("/querytprovinceZl")
    public Map<String,Object> querytprovinceZl(@RequestBody PageData pd){
//        PageData pd = new PageData();
        try {
            PageData resultPd = new PageData();
            int size = pd.getInt("pageSize");
            int index = pd.getInt("pageIndex");
            int rowstart = (index - 1) * size + 1;
            int rowend = size*index;
            pd.put("rowstart",rowstart);
            pd.put("rowend",rowend);
            List<PageData> list = service.queryt_t_dm_province_catalog_num_zl(pd);
            int total = service.queryt_t_dm_province_catalog_num_count_zl(pd);
            resultPd.put("data", list);
            PageData paginationPd = new PageData();
            paginationPd.put("total", total);
            paginationPd.put("pageSize", size);
            paginationPd.put("current", index);
            // 页码
            resultPd.put("pagination", paginationPd);
            resultPd.put("error", null);
            Map<String,Object> test = RCheckMsg.successMsg(resultPd,"查询成功!");
            return test;
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg( -111 ,null,"业务逻辑校验失败");
        }
    }








}
