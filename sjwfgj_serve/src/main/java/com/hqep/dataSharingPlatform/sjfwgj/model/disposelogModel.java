package com.hqep.dataSharingPlatform.sjfwgj.model;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: disposeLogDao
 * @author: wjx
 * @data: 2023/4/25 19:04 PM
 */

import java.util.Date;

//返回接口  p://22.9.34.0:18080/gw/api4/qualityDemand/linkinfoFromProvincial
public class disposelogModel {
    private String ticketId;//     工单编号
    private String result;//       处理结果（0受理工单、1处理中、2处理完成、3分派错误，退回、4处理意见反馈）
    private Date handleTime;//     处理完成时间
    private String operateTitle;//处理日志说明
    private String nodeName;//     处理环节（任务处理）

    public disposelogModel() {
    }

    public disposelogModel(String ticketId, String result, Date handleTime,
                           String operateTitle, String nodeName) {
        this.ticketId = ticketId;
        this.result = result;
        this.handleTime = handleTime;
        this.operateTitle = operateTitle;
        this.nodeName = nodeName;
    }

    @Override
    public String toString() {
        return "DemandTableDto{" +
                "ticketId='" + ticketId + '\'' +
                ", Result='" + result + '\'' +
                ", handleTime=" + handleTime +
                ", operateTitle='" + operateTitle + '\'' +
                ", nodeName='" + nodeName + '\'' +
                '}';
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        result = result;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getOperateTitle() {
        return operateTitle;
    }

    public void setOperateTitle(String operateTitle) {
        this.operateTitle = operateTitle;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
