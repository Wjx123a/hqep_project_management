package com.hqep.dataSharingPlatform.sjfwgj.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class LogDO {
    //工单编号
    @JsonProperty(value = "Ticketid")
    private String ticketId;
    //处理结果
    @JsonProperty(value = "result")
    private String result;
    //处理时间
    @JsonProperty(value = "handleTime")
    private String handleTime;
    @JsonProperty(value = "operateTitle")
    private String operateTitle;
    @JsonProperty(value = "nodeName")
    private String nodeName;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId == null ? null : ticketId.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String  getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String  handleTime) {
        this.handleTime = handleTime;
    }

    public String getOperateTitle() {
        return operateTitle;
    }

    public void setOperateTitle(String operateTitle) {
        this.operateTitle = operateTitle == null ? null : operateTitle.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }
    @Override
    public String toString() {
        return "LogPojo{" +
                "ticketId='" + ticketId + '\'' +
                ", result='" + result + '\'' +
                ", handleTime=" + handleTime +
                ", operateTitle='" + operateTitle + '\'' +
                ", nodeName='" + nodeName + '\'' +
                '}';
    }
}