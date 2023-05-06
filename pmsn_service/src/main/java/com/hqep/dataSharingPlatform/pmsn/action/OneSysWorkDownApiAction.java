package com.hqep.dataSharingPlatform.pmsn.action;

import com.alibaba.fastjson.JSONObject;
import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.model.PmsnOnesysDownWorkAct;
import com.hqep.dataSharingPlatform.pmsn.model.PmsnOnesysDownWorkLogs;
import com.hqep.dataSharingPlatform.pmsn.model.PmsnOnesysDownWorkOrder;
import com.hqep.dataSharingPlatform.pmsn.service.OneSysWorkService;
import com.hqep.dataSharingPlatform.pmsn.unit.MapObjUtil;
import com.hqep.dataSharingPlatform.pmsn.unit.RCheckMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * create by sssjl
 * create time 2022-07-11
 * description  一级系统数据共享流程贯通
 */
@Controller
@RequestMapping("/OneSysWorkDown")
public class OneSysWorkDownApiAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OneSysWorkService oneSysWorkService;

    /***
     * 一级系统下发申请工单接口
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/downDemandWorkOrder")
    public  Map<String,Object> downDemandWorkOrder(@RequestBody PmsnOnesysDownWorkOrder model) {
        try {
            System.out.println("下发工单内容："+model);
            PageData pd =  MapObjUtil.object2PageDate(model,true);
//            List<Map> list = new ArrayList<>();
//            list = (List<Map>) pd.get("tableList");
            List<Map<String, Object>> tableList = model.getTableList();
            System.out.println(tableList);
//            List<Map> list = JSONObject.parseArray(tableList, Map.class);
            if (tableList != null && tableList.size()>0) {
                for (int i = 0; i < tableList.size(); i++) {
                    oneSysWorkService.insertOnesysDownTable(tableList.get(i));
                }
            }
            // 数据入库
            oneSysWorkService.insertOnesysDownOrder(pd);
            return RCheckMsg.successMsg( null,"一级系统下发审批流程节点成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg(500,null,"一级系统下发审批流程节点失败！");
        }
    }



    /***
     * 一级系统下发审批流程节点接口
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/downDemandWorkflow")
    public  Map<String,Object> downDemandWorkflow(@RequestBody PmsnOnesysDownWorkAct model) {
        try {
            System.out.println("下发审批流程节点内容："+model);
            PageData pd =  MapObjUtil.object2PageDate(model,false);
            // 数据入库
            oneSysWorkService.insertOnesysDownAct(pd);
            return RCheckMsg.successMsg( null,"一级系统下发审批流程节点执行成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg(500,null,"一级系统下发审批流程节点执行失败！");
        }
    }

    /***
     * 一级数据下发附件信息接口
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/downDemandFiles")
    public  Map<String,Object> downDemandFiles(String fileId, String demandId, Long createTime , String fileName
            , @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            System.out.println("下发附件信息内容：fileId"+fileId+",demandId"+demandId+",createTime"+createTime+",fileName");
            String tarPath = request.getSession().getServletContext().getRealPath(File.separator)
                    + File.separator + "fileWarehouse" + File.separator;
            // 文件仓库地址文件夹是否创建
            File dest = new File(tarPath);
            if (!dest.exists()) {
                dest.mkdirs();
            }
            // 接收的文件本地化存储
            String tarFileName = fileName+System.currentTimeMillis()+file.getOriginalFilename();
            File fileDest = new File(tarPath +tarFileName);
            if (!fileDest.exists()) {
                fileDest.createNewFile();
            }
            file.transferTo(fileDest); // 保存文件
            PageData pd = new PageData();
            pd.put("fileId",fileId);
            pd.put("demandId",demandId);
            pd.put("fileName",fileName);
            pd.put("createTime",createTime);
            pd.put("tarPath",tarPath);
            pd.put("tarFileName",tarFileName);
            // 数据入库
            oneSysWorkService.insertOnesysDownFiles(pd);
            return RCheckMsg.successMsg( null,"接收附件成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg(500,null,"接收附件失败！");
        }
    }

   /***
     * 一级数据下发日志信息接口
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/downDemandLogs")
    public  Map<String,Object> downDemandLogs(@RequestBody PmsnOnesysDownWorkLogs model) {
        try {
            System.out.println("下发日志内容："+model);
            PageData pd =  MapObjUtil.object2PageDate(model,true);
            oneSysWorkService.insertOnesysDownLogs(pd);
            return RCheckMsg.successMsg( null,"一级数据下发日志信息接口执行成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return RCheckMsg.errorMsg(500,null,"一级数据下发日志信息接口执行失败！");
        }
    }



}
