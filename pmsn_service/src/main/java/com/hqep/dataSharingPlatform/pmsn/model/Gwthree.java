package com.hqep.dataSharingPlatform.pmsn.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Gwthree {
    @JsonProperty(value = "Ticketid")
    private String Ticketid;
    private String Result;
    private String handleTime;
    private String operateTitle;
    private String nodeName;
    private String createTime;
    private String fileId;
    private String fileName;
    private String fileContent;

    public String getTicketid() {
        return Ticketid;
    }

    public void setTicketid(String ticketid) {
        Ticketid = ticketid;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return "Gwthree{" +
                "Ticketid='" + Ticketid + '\'' +
                ", Result='" + Result + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", operateTitle='" + operateTitle + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileContent='" + fileContent + '\'' +
                '}';
    }
}
