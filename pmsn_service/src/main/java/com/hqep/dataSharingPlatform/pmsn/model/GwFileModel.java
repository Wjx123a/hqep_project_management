package com.hqep.dataSharingPlatform.pmsn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class GwFileModel {
    private String fileId;
    @JsonProperty(value = "Ticketid")
    private String Ticketid;
    private String createTime;
    private String fileName;
    private MultipartFile fileContent;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTicketid() {
        return Ticketid;
    }

    public void setTicketid(String ticketid) {
        Ticketid = ticketid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MultipartFile getFileContent() {
        return fileContent;
    }

    public void setFileContent(MultipartFile fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return "GwFileModel{" +
                "fileId='" + fileId + '\'' +
                ", Ticketid='" + Ticketid + '\'' +
                ", createTime='" + createTime + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileContent=" + fileContent +
                '}';
    }
}
