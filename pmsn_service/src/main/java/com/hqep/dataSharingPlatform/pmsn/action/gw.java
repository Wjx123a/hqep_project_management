package com.hqep.dataSharingPlatform.pmsn.action;




import com.hqep.dataSharingPlatform.pmsn.model.GwListModel;
import com.hqep.dataSharingPlatform.pmsn.model.Gwmodel;
import com.hqep.dataSharingPlatform.pmsn.model.Gwthree;
import com.hqep.dataSharingPlatform.pmsn.service.gwService;
import com.hqep.dataSharingPlatform.pmsn.unit.RCheckMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import java.io.*;
import java.util.*;

@RequestMapping("/dataUpload")
@RestController
@EnableWebMvc
public class gw {

    /**
     * 临时文件路径前缀
     */
    private static final String TEMP_FILEPATH = "D:\\";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private gwService service;

    @ApiOperation(value = "总部同步省侧数据上传需求申请接口", notes = "总部同步省侧数据上传需求申请接口")
    @ResponseBody
    @RequestMapping(value = "/demandApplicationToProvincial", method = {RequestMethod.POST})
    public Map demandApplicationToProvincial(@RequestBody Gwmodel gd) {
        Map map = new HashMap();
        try{
            if(gd.getTicketid().length()>0){
                Map map1 = new LinkedHashMap();
                map1.put("Ticketid",gd.getTicketid());
                map1.put("Title",gd.getTitle());
                map1.put("Description",gd.getDescription());
                map1.put("projectname",gd.getProjectname());
                map1.put("SLAcompletetime",gd.getSLAcompletetime());
                for(GwListModel a : gd.getTableList()){
                    map1.put("transfertype",a.getTransfertype());
                    map1.put("source_sysname",a.getSource_sysname());
                    map1.put("tablename",a.getTablename());
                    map1.put("fieldnum",a.getFieldnum());
                    map1.put("target_tablename",a.getTarget_tablename());
                    map1.put("target_fieldnum",a.getTarget_fieldnum());
                    map1.put("trigger_method",a.getTrigger_method());
                    map1.put("trigger_time",a.getTrigger_time());
                    map1.put("identification_field",a.getIdentification_field());
                    map1.put("datanum",a.getDatanum());
                    map1.put("frequency",a.getFrequency());
                    map1.put("split_logic",a.getSplit_logic());
                    map1.put("deal_paramater",a.getDeal_paramater());
                    map1.put("note",a.getNote());
                    map1.put("priorty",a.getPriorty());
                }
                service.insert(map1);
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
                                      @RequestParam String Ticketid,//工单编号
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
                // 获取临时文件所需要转存的文件夹路劲，不存在则创建
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
                map1.put("Ticketid",Ticketid);
                map1.put("createTime",createTime);
                map1.put("fileName",fileName);
                map1.put("fileContent",TEMP_FILEPATH+"\\"+uuid);
                service.insertfile(map1);
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


    @ApiOperation(value = "接收省侧数据上传处理日志接口", notes = "接收省侧数据上传处理日志接口")
    @RequestMapping(value = "/demandWorkflowFromProvincial")
    public Map demandWorkflowFromProvincial(ServletRequest request) throws ServiceException {
            //设置请求头
            HttpServletRequest req = (HttpServletRequest) request;
            String url = "http://22.9.34.0:18080/gw/api4/dataUpload/demandWorkflowFromProvincial";
            String token = req.getHeader("token");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            //设置请求体，注意是LinkedMultiValueMap
           // FileSystemResource fileSystemResource = new FileSystemResource(filepath);
            Map gt =  service.selectdate();
            Map form = new LinkedMultiValueMap<>();;
            form.put("Ticketid",gt.get("Result"));
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

    @ApiOperation(value = "接收省侧数据上传处理附件信息", notes = "接收省侧数据上传处理附件信息")

    @RequestMapping(value = "/demandFileFromProvincial", method = {RequestMethod.POST,RequestMethod.GET})
    public Map demandFileFromProvincial(ServletRequest request) throws ServiceException {
        //设置请求头
        HttpServletRequest req = (HttpServletRequest) request;
        String url = "http://22.9.34.0:18080/gw/api4/dataUpload/demandFileFromProvincial";
        String token = req.getHeader("token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //设置请求体，注意是LinkedMultiValueMap
        // FileSystemResource fileSystemResource = new FileSystemResource(filepath);
        Map gt =  service.selectfile();
        Map form = new LinkedMultiValueMap<>();
        form.put("",gt.get("Ticketid") );
        form.put("Ticketid",gt.get("Ticketid") );
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

    @ApiOperation(value = "接收省侧上传的表血缘关系信息", notes = "接收省侧上传的表血缘关系信息")
    @ResponseBody
    @RequestMapping(value = "/consanguinityFromProvincial4", method = {RequestMethod.POST,RequestMethod.GET})
    public Map consanguinityFromProvincial() {
        Map map = new HashMap();
        try{

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

    /**
     * 流写入文件
     *
     * @param inputStream 文件输入流
     * @param file        输出文件
     */
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

    /**
     * 调用接口
     * @param url
     * @param token
     * @param map
     * @return
     */
    public Map<String,Object> postForObject(String url, String token, MultiValueMap<String, Object> map) {
        // 请求地址
//        String url = "http://localhost:8080/auth/getToken";

        // 请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("token",token);
        //提交参数设置
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("appId", "01000");
//        map.add("appKey", "cde18afb42556eeed09783b437eece17");

        // 组装请求体
        HttpEntity<MultiValueMap<String, Object>> request =
                new HttpEntity<MultiValueMap<String, Object>>(map, headers);
        RestTemplate template =new RestTemplate();
        // 发送post请求，并输出结果
        Map<String,Object> result = template.postForObject(url, request, Map.class);
        System.out.println(result);
        return result;
    }


}
