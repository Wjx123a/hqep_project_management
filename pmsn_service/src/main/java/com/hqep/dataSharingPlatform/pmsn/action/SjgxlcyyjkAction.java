package com.hqep.dataSharingPlatform.pmsn.action;

import com.hqep.dataSharingPlatform.common.utils.PageData;
import com.hqep.dataSharingPlatform.pmsn.service.SjgxlcyyjkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequestMapping("/sjgxlcyyjk")
@RestController
@Api(description = "数据共享流程运营监测")
public class SjgxlcyyjkAction {


    @Autowired
    private SjgxlcyyjkService service;

    /**
     * 数据共享流程运营监测_环节总览_环节工单情况
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryLinkOverview", method = RequestMethod.POST)
    public PageData queryLinkOverview(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryLinkOverview(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_环节总览_环节工单情况出错");
        }
        return resultPd;
    }

    /**
     * 数据共享流程运营监测_规模分析_需求受理情况and工单受理情况
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryScaleAnalysisOrderNum", method = RequestMethod.POST)
    public PageData queryScaleAnalysisOrderNum(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryScaleAnalysisOrderNum(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_规模分析_需求受理情况and工单受理情况出错");
        }
        return resultPd;
    }

    /**
     * 数据共享流程运营监测_规模分析_省公司工单申请情况统计
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryScaleAnalysisOrderTypeForProvince", method = RequestMethod.POST)
    public PageData queryScaleAnalysisOrderTypeForProvince(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryScaleAnalysisOrderTypeForProvince(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_规模分析_省公司工单申请情况统计出错");
        }
        return resultPd;
    }

    /**
     * 数据共享流程运营监测_规模分析_地市工单申请情况统计
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryScaleAnalysisOrderTypeForCity", method = RequestMethod.POST)
    public PageData queryScaleAnalysisOrderTypeForCity(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryScaleAnalysisOrderTypeForCity(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_规模分析_地市工单申请情况统计出错");
        }
        return resultPd;
    }

    /**
     * 数据共享流程运营监测_规模分析_需求受理情况统计
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryScaleAnalysisOrderMonth", method = RequestMethod.POST)
    public PageData queryScaleAnalysisOrderMonth(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryScaleAnalysisOrderMonth(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_规模分析_需求受理情况统计出错");
        }
        return resultPd;
    }


    /**
     * 数据共享流程运营监测_效率分析_全流程流转办理时长
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryFlowTimeForType", method = RequestMethod.POST)
    public PageData queryFlowTimeForType(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryFlowTimeForType(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_效率分析_全流程流转办理时长出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_效率分析_各流转环节办理时长情况
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryFlowTimeForNode", method = RequestMethod.POST)
    public PageData queryFlowTimeForNode(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            LinkedList<Map> list = service.queryFlowTimeForNode(pd);
            resultPd.put("data",list);
            System.out.println(list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_效率分析_各流转环节办理时长情况出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_效率分析_工单全流程流转时长月度趋势情况
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryFlowTimeForMonth", method = RequestMethod.POST)
    public PageData queryFlowTimeForMonth(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryFlowTimeForMonth(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_效率分析_工单全流程流转时长月度趋势情况出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_效率分析_工单全流程流转时长按单位统计
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryFlowTimeForCity", method = RequestMethod.POST)
    public PageData queryFlowTimeForCity(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryFlowTimeForCity(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_效率分析_工单全流程流转时长按单位统计出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_效率分析_业务审核环节审批时长分布
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryFlowTimeForBusiness", method = RequestMethod.POST)
    public PageData queryFlowTimeForBusiness(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryFlowTimeForBusiness(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_效率分析_业务审核环节审批时长分布出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_热度分析_热点系统
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDegreeHeatForSystem", method = RequestMethod.POST)
    public PageData queryDegreeHeatForSystem(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryDegreeHeatForSystem(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_热度分析_热点系统出错");
        }
        return resultPd;
    }

    /**
     * 数据共享流程运营监测_热度分析_负面清单热点数据表
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDegreeHeatForFmqdTable", method = RequestMethod.POST)
    public PageData queryDegreeHeatForFmqdTable(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryDegreeHeatForFmqdTable(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_热度分析_负面清单热点数据表出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_热度分析_非负面清单热点数据表
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryDegreeHeatForNotFmqdTable", method = RequestMethod.POST)
    public PageData queryDegreeHeatForNotFmqdTable(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryDegreeHeatForNotFmqdTable(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_热度分析_非负面清单热点数据表出错");
        }
        return resultPd;
    }

    /**
     * 数据共享流程运营监测_质量分析_省公司被驳回
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryQualityForProvince", method = RequestMethod.POST)
    public PageData queryQualityForProvince(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryQualityForProvince(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_质量分析_省公司被驳回出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_质量分析_地市公司被驳回
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryQualityForCity", method = RequestMethod.POST)
    public PageData queryQualityForCity(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryQualityForCity(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_质量分析_地市公司被驳回出错");
        }
        return resultPd;
    }
    /**
     * 数据共享流程运营监测_预警异动_预警工单数据统计
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryWarningForWarning", method = RequestMethod.POST)
    public PageData queryWarningForWarning(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryWarningForWarning(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_预警异动_预警工单数据统计出错");
        }
        return resultPd;
    }

    /**
     * 数据共享流程运营监测_预警异动_异动工单数据统计
     * @param pd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryWarningForException", method = RequestMethod.POST)
    public PageData queryWarningForException(@RequestBody PageData pd) {
        PageData resultPd = new PageData();
        try {
            List<Map> list = service.queryWarningForException(pd);
            resultPd.put("data",list);
            resultPd.put("error", null);
        } catch (Exception e) {
            e.printStackTrace();
            resultPd.put("data", new ArrayList<>());
            resultPd.put("error", "查询数据共享流程运营监测_预警异动_异动工单数据统计出错");
        }
        return resultPd;
    }



}
