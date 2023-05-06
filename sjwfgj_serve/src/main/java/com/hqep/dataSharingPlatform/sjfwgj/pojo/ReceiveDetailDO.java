package com.hqep.dataSharingPlatform.sjfwgj.pojo;

import java.util.Date;

public class ReceiveDetailDO {
    //工单编号
    private String ticketId;
    //表id
    private String tableId;
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
    private String  createTime;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId == null ? null : ticketId.trim();
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public String getObjectcName() {
        return objectCName;
    }

    public void setObjectcName(String objectcName) {
        this.objectCName = objectcName == null ? null : objectcName.trim();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType == null ? null : deployType.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getIsFmqd() {
        return isFmqd;
    }

    public void setIsFmqd(String isFmqd) {
        this.isFmqd = isFmqd == null ? null : isFmqd.trim();
    }

    public String getIsDatawork() {
        return isDatawork;
    }

    public void setIsDatawork(String isDatawork) {
        this.isDatawork = isDatawork == null ? null : isDatawork.trim();
    }

    public String getDemandFreq() {
        return demandFreq;
    }

    public void setDemandFreq(String demandFreq) {
        this.demandFreq = demandFreq == null ? null : demandFreq.trim();
    }

    public String getFirstTopicName() {
        return firstTopicName;
    }

    public void setFirstTopicName(String firstTopicName) {
        this.firstTopicName = firstTopicName == null ? null : firstTopicName.trim();
    }

    public String getSecondTopicName() {
        return secondTopicName;
    }

    public void setSecondTopicName(String secondTopicName) {
        this.secondTopicName = secondTopicName == null ? null : secondTopicName.trim();
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc == null ? null : topicDesc.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getApplicationDept() {
        return applicationDept;
    }

    public void setApplicationDept(String applicationDept) {
        this.applicationDept = applicationDept == null ? null : applicationDept.trim();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName == null ? null : applicationName.trim();
    }

    public String getRangeDesc() {
        return rangeDesc;
    }

    public void setRangeDesc(String rangeDesc) {
        this.rangeDesc = rangeDesc == null ? null : rangeDesc.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getProblemBelong() {
        return problemBelong;
    }

    public void setProblemBelong(String problemBelong) {
        this.problemBelong = problemBelong == null ? null : problemBelong.trim();
    }

    public String getProblemGenera() {
        return problemGenera;
    }

    public void setProblemGenera(String problemGenera) {
        this.problemGenera = problemGenera == null ? null : problemGenera.trim();
    }

    public String getProblemClassify() {
        return problemClassify;
    }

    public void setProblemClassify(String problemClassify) {
        this.problemClassify = problemClassify == null ? null : problemClassify.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ReceivePojo{" +
                "ticketId='" + ticketId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", objectcName='" + objectCName + '\'' +
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
                '}';
    }
}