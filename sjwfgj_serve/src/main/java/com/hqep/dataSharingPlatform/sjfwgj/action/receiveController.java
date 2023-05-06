package com.hqep.dataSharingPlatform.sjfwgj.action;

import com.hqep.dataSharingPlatform.sjfwgj.pojo.ReceiveDO;
import com.hqep.dataSharingPlatform.sjfwgj.pojo.ReceiveDetailDO;
import com.hqep.dataSharingPlatform.sjfwgj.response.CommonReturnType;

import com.hqep.dataSharingPlatform.sjfwgj.service.QueryFile;
import com.hqep.dataSharingPlatform.sjfwgj.service.QueryLog;
import com.hqep.dataSharingPlatform.sjfwgj.service.ReceiveInfo;
import com.hqep.dataSharingPlatform.sjfwgj.utils.RCheckMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.rpc.ServiceException;
import java.io.*;
import java.util.*;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: receiveController
 * @author: wjx
 * @data: 2023/5/1 18:15 PM
 */
@RequestMapping("/qualityDemand")
@RestController
@EnableWebMvc
@Transactional
public class receiveController {
    @Autowired
    private ReceiveInfo receiveInfo;
    @Autowired
    private QueryFile queryFile;
    @Autowired
    private QueryLog queryLog;
    private static final String TEMP_FILEPATH = "D:\\";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @ApiOperation(value = "总部同步省侧数据质量问题治理接口", notes = "总部同步省侧数据质量问题治理接口")
    @ResponseBody
    @RequestMapping(value = "/demandApplicationToProvincia", method = {RequestMethod.POST})
    public Map demandApplicationToProvincia(@RequestBody ReceiveDO rd) {
        Map map = new HashMap();
        try{
            if(rd.getTicketId().length()>0){
                Map map1 = new LinkedHashMap();
                Map map2 = new LinkedHashMap();
                map1.put("Ticketid",rd.getTicketId());
                map1.put("Title",rd.getTitle());
                map1.put("Description",rd.getDescription());
                map1.put("projectname",rd.getProjectName());
                map1.put("SLAcompletetime",rd.getSLAcompletetime());
                map1.put("taskStatus",rd.getSLAcompletetime());
                receiveInfo.insert(map1);
                for(ReceiveDetailDO a : rd.getTableList()){
                    map2.put("Ticketid",a.getTicketId());
                    map2.put("objectName",a.getObjectName());
                    map2.put("objectCname",a.getObjectcName());
                    map2.put("systemName",a.getSystemName());
                    map2.put("deployType",a.getDeployType());
                    map2.put("deptName",a.getDeptName());
                    map2.put("isFmqd",a.getIsFmqd());
                    map2.put("isDatawork",a.getIsDatawork());
                    map2.put("demandFreq",a.getDemandFreq());
                    map2.put("firstTopicName",a.getFirstTopicName());
                    map2.put("secondTopicName",a.getSecondTopicName());
                    map2.put("applicationDept",a.getApplicationDept());
                    map2.put("applicationName",a.getApplicationName());
                    map2.put("rangeDesc",a.getRangeDesc());
                    map2.put("dataSource",a.getDataSource());
                    map2.put("problemBelong",a.getProblemBelong());
                    map2.put("problemGenera",a.getProblemGenera());
                    map2.put("problemClassify",a.getProblemClassify());
                    map2.put("createTime",a.getCreateTime());
                    receiveInfo.insertDetail(map2);
                }
                map.put("success", true);
                map.put("mes","成功");
                map.put("data","OK");
                map.put("code",200);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("接口一插入数据错误", e);
            map.put("fail", false);
            map.put("mes","失败");
            map.put("data","OK");
            map.put("code",200);
        }
        return map;
    }

    @ApiOperation(value = "总部同步省侧附件接口", notes = "总部同步省侧附件接口")
    @ResponseBody
    @RequestMapping(value = "/demandFileToProvincial", method = {RequestMethod.POST})
    public Map demandFileToProvincial(HttpServletRequest httpServletRequest,
                                      @RequestParam("fileContent") MultipartFile fileContent,
                                      @RequestParam String fileId,//MID
                                      @RequestParam String ticketId,//工单编号
                                      @RequestParam String createTime,//创建时间
                                      @RequestParam String fileName//附件名称
    ) {
        Map map = new HashMap();
        // 文件无效，无需转存
        File toFile = null;
        try{
            if(fileName.length()>0){
                Map map1 = new LinkedHashMap();
                // 文件格式
                String fileFormat = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
                // 生成文件id
                String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
                // 文件全路径（绝对路径+文件名称）
                toFile = new File(TEMP_FILEPATH + File.separator + uuid + fileFormat);
                String absolutePath = null;
                try {
                    // 获取临时文件所需要转存的文件夹路径，不存在则创建
                    absolutePath = toFile.getCanonicalPath();
                    String dirPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
                    File dir = new File(dirPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    // 流写入
                    InputStream ins = fileContent.getInputStream();
                    inputStreamToFile(ins, toFile);
                    ins.close();

                } catch (IOException e) {
                    logger.error("文件写入本地报错", e);
                    e.printStackTrace();
                }
                map1.put("'fileId",fileId);
                map1.put("Ticketid",ticketId);
                map1.put("createTime",createTime);
                map1.put("fileName",fileName);
                map1.put("fileContent",TEMP_FILEPATH+"\\"+uuid);
                queryFile.insertfile(map1);
            }
            map.put("success", true);
            map.put("mes","成功");
            map.put("data","OK");
            map.put("code",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("fail", false);
            map.put("mes","失败");
            map.put("data","OK");
            map.put("code",200);
        }
        return map;
    }

    @ApiOperation(value = "接收省侧数据质量问题治理处理日志接口", notes = "接收省侧数据质量问题治理处理日志接口")
    @RequestMapping(value = "/linkinfoFromProvincial",method = {RequestMethod.POST})
    public Map linkinfoFromProvincial(ServletRequest request) throws ServiceException {
        //设置请求头
        HttpServletRequest req = (HttpServletRequest) request;
        String url = "http://22.9.34.0:18080/gw/api4/qualityDemand/demandWorkflowFromProvincial";
        String token = req.getHeader("token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //设置请求体，注意是LinkedMultiValueMap
        // FileSystemResource fileSystemResource = new FileSystemResource(filepath);
        Map gt =  queryLog.select();
        Map form = new LinkedMultiValueMap();
        form.put("Ticketid",gt.get("ticket_id"));
        form.put("Result", gt.get("result"));
        form.put("handleTime", gt.get("handle_time"));
        form.put("operateTitle", gt.get("operate_title"));
        form.put("nodeName",gt.get("node_name"));
        logger.info("附件信息" + form);
        for (int i = 0; i < form.size(); i++) {
            System.out.println(form.keySet());
        };
        //用HttpEntity封装整个请求报文
        HttpEntity<Map<String, Object>> files = new HttpEntity<>(form, headers);
        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<String> fileResult = restTemplate1.postForEntity(url, files, String.class);
        logger.info("返回参数: " + fileResult.getBody());
        return RCheckMsg.successMsg( fileResult,token);
    }


    @ApiOperation(value = "接收省侧数据质量问题治理解决方案附件信息", notes = "接收省侧数据质量问题治理解决方案附件信息")
    @ResponseBody
    @RequestMapping(value = "/demandFileFromProvincial", method = {RequestMethod.POST,RequestMethod.GET})
    public Map demandFileFromProvincial(ServletRequest request) {
        //设置请求头
        HttpServletRequest req = (HttpServletRequest) request;
        String url = "http://22.9.34.0:18080/gw/api4/qualityDemand/demandFileFromProvincial";
        String token = req.getHeader("token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //设置请求体，注意是LinkedMultiValueMap
        // FileSystemResource fileSystemResource = new FileSystemResource(filepath);
        Map gt =  queryFile.select();
        Map form = new LinkedMultiValueMap<>();
        form.put("Ticketid",gt.get("ticket_id") );
        form.put("Result", gt.get("Result"));
        form.put("handleTime", gt.get("handleTime"));
        form.put("operateTitle", gt.get("operateTitle"));
        form.put("nodeName",gt.get("nodeName"));
        form.put("createTime",gt.get("createTime"));
        logger.info("附件信息" + form);
        //用HttpEntity封装整个请求报文
        HttpEntity<Map<String, Object>> files = new HttpEntity<>(form, headers);
        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<String> fileResult = restTemplate1.postForEntity(url, files, String.class);
        logger.info("返回参数: " + fileResult.getBody());
        return RCheckMsg.successMsg( fileResult,token);
    }












    private static void inputStreamToFile(InputStream inputStream, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
