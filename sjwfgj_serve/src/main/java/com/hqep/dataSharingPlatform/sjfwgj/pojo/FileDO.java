package com.hqep.dataSharingPlatform.sjfwgj.pojo;

import java.util.Date;

public class FileDO {

    private String ticketId;

    private String fileId;

    private String objectName;

    private Date createTime;

    private String fileName;

    private String fileContent;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId == null ? null : ticketId.trim();
    }

    public String getFileid() {
        return fileId;
    }

    public void setFileid(String fileid) {
        this.fileId = fileid == null ? null : fileid.trim();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent == null ? null : fileContent.trim();
    }

    @Override
    public String toString() {
        return "FilePojo{" +
                "ticketId='" + ticketId + '\'' +
                ", fileid='" + fileId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", createTime=" + createTime +
                ", fileName='" + fileName + '\'' +
                ", fileContent='" + fileContent + '\'' +
                '}';
    }
}