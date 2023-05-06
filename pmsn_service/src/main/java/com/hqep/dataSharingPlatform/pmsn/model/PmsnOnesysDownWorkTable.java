package com.hqep.dataSharingPlatform.pmsn.model;

import java.util.Date;
import java.io.Serializable;

/**
 * (PmsnOnesysDownWorkTable)实体类
 *
 * @author sssJL
 * @since 2022-07-28 16:09:45
 */
public class PmsnOnesysDownWorkTable implements Serializable {
    private static final long serialVersionUID = 934342962775436605L;
    /**
     * 自建ID
     */
    private String id;
    /**
     * 订单明细ID
     */
    private String demandDetailId;
    /**
     * 订单ID
     */
    private String demandId;
    /**
     * 表ID
     */
    private String objectId;
    /**
     * 表英文名
     */
    private String objectName;
    /**
     * 表中文名
     */
    private String objectCname;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 部署方式(0:一级部署；1：两级部署；2：二级部署；3: 1.5级部署)
     */
    private String deployType;
    /**
     * 所属专业
     */
    private String deptName;
    /**
     * 是否负面清单（1：是；0：否）
     */
    private String isFmqd;
    /**
     * 是否接入中台（1：是；0：否）
     */
    private String isDatawork;
    /**
     * 需求频率（0: 按需;1: 1日/次;2: 1周/次;3: 1月/次;4: 1季/次;5: 半年/次;6: 1年/次;7: 一次性）
     */
    private String demandFreq;
    /**
     * 一级场景名称
     */
    private String firstTopicName;
    /**
     * 二级场景名称
     */
    private String secondTopicName;
    /**
     * 场景说明
     */
    private String topicDesc;
    /**
     * 数据层级（包含8个层级：总部源端业务系统
     * 总部中台贴源层
     * 总部中台共享层
     * 总部中台分析层
     * 省侧源端业务系统
     * 省侧中台贴源层
     * 省侧中台共享层
     * 省侧中台分析层）
     */
    private String orgName;
    /**
     * 应用所属部门
     */
    private String applicationDept;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 应用范围(0: 总部;1：省公司；2：总部+省公司)
     */
    private String rangeDesc;
    /**
     * 数据来源
     */
    private String createTime;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 第几页
     */
    private int pageIndex;
    /**
     * 每页多少条
     */
    private int pageSize;

    @Override
    public String toString() {
        return "PmsnOnesysDownWorkTable{" +
                "id='" + id + '\'' +
                ", demandDetailId='" + demandDetailId + '\'' +
                ", demandId='" + demandId + '\'' +
                ", objectId='" + objectId + '\'' +
                ", objectName='" + objectName + '\'' +
                ", objectCname='" + objectCname + '\'' +
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
                ", createTime='" + createTime + '\'' +
                ", createtime=" + createtime +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemandDetailId() {
        return demandDetailId;
    }

    public void setDemandDetailId(String demandDetailId) {
        this.demandDetailId = demandDetailId;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
