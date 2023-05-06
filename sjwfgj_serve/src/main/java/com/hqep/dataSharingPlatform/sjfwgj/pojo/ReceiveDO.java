package com.hqep.dataSharingPlatform.sjfwgj.pojo;
import java.util.List;

public class ReceiveDO{
    //工单编号
    private String ticketId;

    private String title;

    private String description;
    //应用名称
    private String projectName;
    //1
    private String taskStatus;

    private String  SLAcompletetime;
    //0未处理 1被挂起 2已处理
    private String flag;
    private List<ReceiveDetailDO> tableList;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSLAcompletetime() {
        return SLAcompletetime;
    }

    public void setSLAcompletetime(String  SLAcompletetime) {
        this.SLAcompletetime = SLAcompletetime;
    }

    public List<ReceiveDetailDO> getTableList() {
        return tableList;
    }

    public void setTableList(List<ReceiveDetailDO> tableList) {
        this.tableList = tableList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    public String  getSlaCompleteTime() {
        return SLAcompletetime;
    }

    public void setSlaCompleteTime(String slaCompleteTime) {
        this.SLAcompletetime = slaCompleteTime;
    }

    @Override
    public String toString() {
        return "ReceiveDO{" +
                "ticketId='" + ticketId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", SLAcompletetime='" + SLAcompletetime + '\'' +
                ", flag='" + flag + '\'' +
                ", tableList=" + tableList +
                '}';
    }
}