package com.hqep.dataSharingPlatform.pmsn.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.OneSysWorkService;
import com.hqep.dataSharingPlatform.pmsn.unit.RCheckMsg;
import com.hqep.dataSharingPlatform.pmsn.unit.RestfulUnits;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import utils.DateUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;

/***
 * create by sssjl
 * create time 2022-07-11
 * description  一级系统数据共享流程贯通
 */
@Controller
@RequestMapping("/oneSysWorkApi")
public class OneSysWorkApiAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OneSysWorkService oneSysWorkService;

    /**
     * 判断是否是测试
     * false 是测试
     * true 调用国网接口
     */
    private static boolean FLAG = true;
    /***
     * 网省创建订单接口
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/externalAddDemand")
    @ResponseBody
    public  Map<String,Object> externalAddDemand(@RequestBody PageData pd) {
        try {
            String token = "tokenid";
            String url = "http://10.167.1.42:8899/oneSys/getValueFunc";
            if (FLAG) {
                Map<String,Object> tokenInfo = this.getToken(FLAG);
                if (!(Boolean) tokenInfo.get("success") ) {
                    return RCheckMsg.errorMsg( -101,null,"获取国网Token的接口失败！");
                }
                token = (String) tokenInfo.get("data");
                url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/all/externalAddDemand";
            }
            JSONObject param = new JSONObject();
            // 接口传入参数
            String ids = pd.getString("id");
            Date curDate = new Date();
            String time = DateUtils.formatDate(curDate,"yyyy-MM-dd");
            String subTime = DateUtils.formatDate(curDate, "yyyy-MM-dd HH:mm:ss");
            if (!"".equals(ids)) {
                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                    PageData params = new PageData();
                    params.put("id",idArr[i]);
                    params.put("parentId",idArr[i]);
                    // 根据id得到当前的记录
                    List<Map<String, Object>> subList = oneSysWorkService.queryListOnesysWorkOrderForGwFields(params);
                    if (subList != null && subList.size()>0) {
                        Map<String, Object> demand = subList.get(0);
                        // 定义上传国网的数据参数
                        param = new JSONObject();
                        param.put("applyDept",demand.get("applyDept"));
                        param.put("applyTime",time);
                        param.put("applyType",demand.get("applyType"));
                        param.put("applyTypeSecond",demand.get("applyTypeSecond"));
                        param.put("demandDesc",demand.get("demandDesc"));
                        param.put("demandTitle",demand.get("demandTitle"));
                        param.put("dept",demand.get("dept"));
                        param.put("phone",demand.get("phone"));
                        List<Map<String, Object>> subTableList = oneSysWorkService.queryListOnesysWorkTablesForGwFields(params);
                        if (subTableList !=null && subTableList.size() >0) {
                            String jsonStr = JSON.toJSONString(subTableList);
                            param.put("tableList",subTableList);
                            // 调用接口 FLAG为false是 测试使用
                            Map result = RestfulUnits.postForObjectForJSONObject(url,token,param);
                            // 获取返回数据 存入数据库
                            if ((Boolean) result.get("success")) {
                                Object demandCode = ((Map)result.get("data")).get("demandCode");
                                Object demandId = ((Map)result.get("data")).get("demandId");
                                Object description = ((Map)result.get("data")).get("description");
                                Object status = ((Map)result.get("data")).get("status");
                                Object code = result.get("code");
                                params = new PageData();
                                params.put("id",idArr[i]);
                                params.put("applyTime",time);
                                params.put("demandCode",demandCode);
                                params.put("demandId",demandId);
                                params.put("description",description);
                                params.put("status",status);
                                params.put("code",code);
                                params.put("state","1");
                                params.put("subTime",subTime);
                                // 持久化返回数据
                                oneSysWorkService.updateDataToOnesysWorkOrder(params);
                            } else {
                                return RCheckMsg.errorMsg(500,"网省创建订单接口返回失败！","网省创建订单接口返回失败！");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg(500,"网省创建订单接口执行失败！","网省创建订单接口执行失败！");
        }
        return RCheckMsg.successMsg( "网省创建订单接口成功！","网省创建订单接口成功！");
    }



    /***
     * 网省上传附件接口设计
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public  Map<String,Object> uploadFile(String fileId,String demandId
            ,String taskId,Long createTime,String fileName
            , @RequestParam("fileContent") MultipartFile fileContent   ) throws Exception {
        try {
            String token = "tokenid";
            String url = "http://10.167.1.42:8899/oneSys/uploadFile";
            if (FLAG) {
                Map<String,Object> tokenInfo = this.getToken(FLAG);
                if (!(Boolean) tokenInfo.get("success")) {
                    return RCheckMsg.errorMsg( -101,null,"获取国网Token的接口失败！");
                }
                token = (String) tokenInfo.get("data");
                url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/resourceCatalog/all/uploadFile";
            }
            // 接口传入参数
            String OriginalFilename = fileContent.getOriginalFilename();
//            File file = new File("d://test1.xlsx");
//            File file = File.createTempFile("temp", fileContent.getOriginalFilename());
//            fileContent.transferTo(file);
//            FileSystemResource resource = new FileSystemResource(file);
            JSONObject param = new JSONObject();
            param.put("fileId",fileId);
            param.put("demandId",demandId);
            param.put("taskId",taskId);
            param.put("createTime",createTime);
            param.put("fileName",fileName);
//            param.put("fileContent",JSON.toJSONString(fileContent));
            File file = new File(Objects.requireNonNull(fileContent.getOriginalFilename()));
            FileUtils.copyInputStreamToFile(fileContent.getInputStream(), file);
            FileSystemResource resource = new FileSystemResource(file);
      //      param.put("fileContent",resource);

//            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//            String tempFilePath = System.getProperty("java.io.tmpdir")+fileContent.getOriginalFilename();
//            File file1 = new File(tempFilePath);
//            fileContent.transferTo(file1);
//            //把临时文件转换成FileSystemResource
//            FileSystemResource resource1 = new FileSystemResource(tempFilePath);
//            map.add("fileContent",resource1);

            // 调用接口
            Map result = RestfulUnits.postForObjectForJSONObject(url,token,param);
            return RCheckMsg.successMsg( result,"网省上传附件接口成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg(500,null,"网省上传附件接口失败！");
        }
    }

    /***
     * 网省上传附件接口设计
     * @return
     * @throws Exception
     */
/*    @RequestMapping("/uploadFile")
    @ResponseBody
    public  Map<String,Object> uploadFile(String fileId,String demandId
            ,String taskId,Long createTime,String fileName
            , @RequestParam("fileContent") MultipartFile fileContent   ) throws Exception {
    Map<String, Object> resultMap = new HashMap<>();
    File f = null;
       try{
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        // 设置请求体,注意是LinkenMultiValueMap
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        // 创建临时文件,用于远程调用参数传递
        f = File.createTempFile("temp", fileContent.getOriginalFilename());
           fileContent.transferTo(f);

        FileSystemResource fs = new FileSystemResource(f);
        param.add("fileContent", JSON.toJSONString(fs));
//        param.add("file", new FileSystemResource(localFile));
        param.add("fileId", "fileId");
       HttpEntity requestBody = new HttpEntity(param, headers);
        // 用HttpEntity封装整个请求报文
//        HttpEntity<MultiValueMap<String, String>> files = new HttpEntity<MultiValueMap<String, String>>(param,headers);
           RestTemplate template =new RestTemplate();
        // 拼接要调用的URL
       String url = "http://10.167.1.42:8899/oneSys/uploadFile";
       template.postForEntity(url, requestBody, String.class);

    } catch (Exception e) {

        // 打印异常
        e.printStackTrace();
    } finally {
        // 刪除临时文件
        f.deleteOnExit();
    }
       return null;
}
*/
    /***
     * 获取国网Token的接口
     * @param userName
     * @param password
     * @param timestamp
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> getToken(String userName, String password, String timestamp) {
        try {
            if (userName == null) {
                userName = "PEN_HLJ_USER";
            }
            if (password == null) {
                password = "b6da579dfe8511ec8ec4309c23ef34c8";
            }
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/auth/login";
            String token = null;
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("userName", userName);
            map.add("password", password);
            Map result = RestfulUnits.postForObject(url,token,map);
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return RCheckMsg.errorMsg(500,"接口返回失敗",null);
        }
    }


    /**
     * 公共方法
     * 调用其他国网接口前先获取token值
     * @return
     * @throws Exception
     */
    public Map<String,Object> getToken(boolean flag) throws Exception {
        try {
            if (!flag) {
                Map result = new HashMap();
                result.put("data","tokenid");
                result.put("success",true);
                result.put("code",200);
                return result;
            }
            String userName = "PEN_HLJ_USER";
            String password = "b6da579dfe8511ec8ec4309c23ef34c8";
            String url = "http://sgedc.sgcc.com.cn/api4/outapi/v1/auth/login";
            String token = null;
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("userName", userName);
            map.add("password", password);
            Map result = RestfulUnits.postForObject(url,token,map);
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return RCheckMsg.errorMsg(500,"接口返回失敗",null);
        }
    }


}
