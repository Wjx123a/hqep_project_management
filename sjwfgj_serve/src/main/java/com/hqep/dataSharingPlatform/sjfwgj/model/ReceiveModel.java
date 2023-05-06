package com.hqep.dataSharingPlatform.sjfwgj.model;

import java.util.Date;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: ReceiveModel
 * @author: wjx
 * @data: 2023/5/1 15:41 PM
 */
public class ReceiveModel {
    //工单编号
    private String ticketId;
    //表英文名
    private String objectName;
    //表中文名
    private String objectCName;
    //表系统名称
    private String systemName;
    //一级部署
    private String deployType;
    //所属专业
    private String deptName;
    //常值1
    private String isFmqd;
    //常值1
    private String isDatawork;
    //需求频率
    private String demandFreq;
    //一级场景名称
    private String firstTopicName;
    //二级场景名称
    private String secondTopicName;
    //场景说明
    private String topicDesc;
    //数据层级
    private String orgName;
    //应用所属部门
    private String applicationDept;
    //应用名称
    private String applicationName;
    //应用范围
    private String rangeDesc;
    //数据来源
    private String dataSource;
    //问题归属
    private String problemBelong;
    //问题大类
    private String problemGenera;
    //问题小类
    private String problemClassify;
    //创建时间
    private String createTime;
    private String title;
    private String description;
    //应用名称
    private String projectName;
    //1
    private String taskStatus;
    //0未处理 1被挂起 2已处理
    private String flag;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectcName() {
        return objectCName;
    }

    public void setObjectcName(String objectcName) {
        this.objectCName = objectcName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getIsFmqd() {
        return isFmqd;
    }

    public void setIsFmqd(String isFmqd) {
        this.isFmqd = isFmqd;
    }

    public String getIsDatawork() {
        return isDatawork;
    }

    public void setIsDatawork(String isDatawork) {
        this.isDatawork = isDatawork;
    }

    public String getDemandFreq() {
        return demandFreq;
    }

    public void setDemandFreq(String demandFreq) {
        this.demandFreq = demandFreq;
    }

    public String getFirstTopicName() {
        return firstTopicName;
    }

    public void setFirstTopicName(String firstTopicName) {
        this.firstTopicName = firstTopicName;
    }

    public String getSecondTopicName() {
        return secondTopicName;
    }

    public void setSecondTopicName(String secondTopicName) {
        this.secondTopicName = secondTopicName;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getApplicationDept() {
        return applicationDept;
    }

    public void setApplicationDept(String applicationDept) {
        this.applicationDept = applicationDept;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getRangeDesc() {
        return rangeDesc;
    }

    public void setRangeDesc(String rangeDesc) {
        this.rangeDesc = rangeDesc;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getProblemBelong() {
        return problemBelong;
    }

    public void setProblemBelong(String problemBelong) {
        this.problemBelong = problemBelong;
    }

    public String getProblemGenera() {
        return problemGenera;
    }

    public void setProblemGenera(String problemGenera) {
        this.problemGenera = problemGenera;
    }

    public String getProblemClassify() {
        return problemClassify;
    }

    public void setProblemClassify(String problemClassify) {
        this.problemClassify = problemClassify;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
        this.taskStatus = taskStatus;
    }
    @Override
    public String toString() {
        return "ReceiveModel{" +
                "ticketId='" + ticketId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", objectCName='" + objectCName + '\'' +
                ", systemName='" + systemName + '\'' +
                ", deployType='" + deployType + '\'' +
                ", deptName='" + deptName + '\'' +
                ", isFmqd='" + isFmqd + '\'' +
                ", isDatawork='" + isDatawork + '\'' +
                ", demandFreq='" + demandFreq + '\'' +
                ", firstTopicName='" + firstTopicName + '\'' +
                ", secondTopicName='" + secondTopicName + '\'' +
                ", topicDesc='" + topicDesc + '\'' +
                ", orgName='" + orgName + '\'' +
                ", applicationDept='" + applicationDept + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", rangeDesc='" + rangeDesc + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", problemBelong='" + problemBelong + '\'' +
                ", problemGenera='" + problemGenera + '\'' +
                ", problemClassify='" + problemClassify + '\'' +
                ", createTime=" + createTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", projectName='" + projectName + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
