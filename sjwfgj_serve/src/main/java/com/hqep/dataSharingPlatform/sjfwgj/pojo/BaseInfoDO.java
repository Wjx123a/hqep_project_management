package com.hqep.dataSharingPlatform.sjfwgj.pojo;

/**
 * @ProjectName: hqep_project_management
 * @ClassName: baseInfoDO
 * @author: wjx
 * @data: 2023/4/26 21:47 PM
 */
import java.util.Date;

public class BaseInfoDO {
    private String ticketId;      //工单编号
    private String objectName;    // 表英文名
    private String objectCname;   // 表中文名
    private String systemName;    //       系统名称
    private String deployType;    //       部署方式(0:一级部署；1：两级部署；2：二级部署；3: 1.5级部署)
    private String deptName;      //       所属专业
    private Integer  isFmqd;      //       是否负面清单（1：是；0：否)
    private Integer isDatawork;   //       是否接入中台（1：是；0：否）
    private String demandFreq;    //需求频率（0: 按需;1: 1日/次;2: 1周/次;3: 1月/次;4: 1季/次;5: 半年/次;6: 1年/次;7: 一次性）
    private String firstTopicName;  //   一级场景名称
    private String secondTopicName; //   二级场景名称
    private String topicDesc;       //     场景说明
    private String orgName;         //数据层级
    //（包含8个层级：总部源端业务系统 总部中台贴源层 总部中台共享层 总部中台分析层 省侧源端业务系统 省侧中台贴源层 省侧中台共享层 省侧中台分析层）
    private String applicationDept;//      应用所属部门
    private String applicationName;//      应用名称
    private String rangeDesc;      //      应用范围(0: 总部;1：省公司；2：总部+省公司)
    private String dataSource;     //      数据来源
    private String problemBelong;  //      问题归属
    private String problemGenera;  //      问题大类
    private String problemClassify;//      问题小类
    private Date createTime;//     创建时间

    public BaseInfoDO() {
    }

    public BaseInfoDO(String ticketId, String objectName, String objectCname, String systemName,
                 String deployType, String deptName, Integer isFmqd, Integer isDatawork,
                 String demandFreq, String firstTopicName, String secondTopicName, String topicDesc,
                 String orgName, String applicationDept, String applicationName, String rangeDesc,
                 String dataSource, String problemBelong, String problemGenera, String problemClassify,
                 Date createTime) {
        this.ticketId = ticketId;
        this.objectName = objectName;
        this.objectCname = objectCname;
        this.systemName = systemName;
        this.deployType = deployType;
        this.deptName = deptName;
        this.isFmqd = isFmqd;
        this.isDatawork = isDatawork;
        this.demandFreq = demandFreq;
        this.firstTopicName = firstTopicName;
        this.secondTopicName = secondTopicName;
        this.topicDesc = topicDesc;
        this.orgName = orgName;
        this.applicationDept = applicationDept;
        this.applicationName = applicationName;
        this.rangeDesc = rangeDesc;
        this.dataSource = dataSource;
        this.problemBelong = problemBelong;
        this.problemGenera = problemGenera;
        this.problemClassify = problemClassify;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Table{" +
                "ticketId='" + ticketId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", objectCname='" + objectCname + '\'' +
                ", systemName='" + systemName + '\'' +
                ", deployType='" + deployType + '\'' +
                ", deptName='" + deptName + '\'' +
                ", isFmqd=" + isFmqd +
                ", isDatawork=" + isDatawork +
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

    public String getObjectCname() {
        return objectCname;
    }

    public void setObjectCname(String objectCname) {
        this.objectCname = objectCname;
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

    public Integer getIsFmqd() {
        return isFmqd;
    }

    public void setIsFmqd(Integer isFmqd) {
        this.isFmqd = isFmqd;
    }

    public Integer getIsDatawork() {
        return isDatawork;
    }

    public void setIsDatawork(Integer isDatawork) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
