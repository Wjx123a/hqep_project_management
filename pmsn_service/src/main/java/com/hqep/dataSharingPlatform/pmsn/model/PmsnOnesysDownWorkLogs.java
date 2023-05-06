package com.hqep.dataSharingPlatform.pmsn.model;

import java.util.Date;
import java.io.Serializable;

/**
 * (PmsnOnesysDownWorkLogs)实体类
 *
 * @author sssJL
 * @since 2022-07-28 16:09:31
 */
public class PmsnOnesysDownWorkLogs implements Serializable {
    private static final long serialVersionUID = 878256298096303512L;

    /**
     * 自建ID
     */
    private String id;
    /**
     * 日志ID
     */
    private String logId;
    /**
     * 订单ID
     */
    private String demandId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 日志内容
     */
    private String logContent;
    /**
     * 日志备注
     */
    private String remark;
    /**
     * 自建接收时间
     */
    private Date downDate;
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
        return "PmsnOnesysDownWorkLogs{" +
                "id='" + id + '\'' +
                ", logId='" + logId + '\'' +
                ", demandId='" + demandId + '\'' +
                ", createTime=" + createTime +
                ", userName='" + userName + '\'' +
                ", logContent='" + logContent + '\'' +
                ", remark='" + remark + '\'' +
                ", downDate=" + downDate +
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

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDownDate() {
        return downDate;
    }

    public void setDownDate(Date downDate) {
        this.downDate = downDate;
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
