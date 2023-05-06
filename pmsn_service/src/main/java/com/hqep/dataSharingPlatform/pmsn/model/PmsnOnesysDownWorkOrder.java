package com.hqep.dataSharingPlatform.pmsn.model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * (PmsnOnesysDownWorkOrder)实体类
 *
 * @author sssJL
 * @since 2022-07-28 16:09:44
 */
public class PmsnOnesysDownWorkOrder implements Serializable {
    private static final long serialVersionUID = 140162156614559633L;

    /**
     * 自定义ID
     */
    private String id;
    /**
     * 订单ID
     */
    private String demandId;
    /**
     * 父订单ID,如果父订单不为空，则说明这是子订单
     */
    private String parentId;
    /**
     * 订单编码
     */
    private String demandCode;
    /**
     * 订单名称
     */
    private String demandTitle;
    /**
     * 订单描述
     */
    private String demandDesc;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 需求状态 (0:待处理; 1:处理中; 2:已完成; 9:已删除)状态3,4,5都属于处理中状态。只是总部这边有需要特殊标识。
     */
    private String status;
    /**
     * 申请用户ID
     */
    private String applyUserId;
    /**
     * 申请用户名称
     */
    private String applyUserName;
    /**
     * 申请用户组织结构
     */
    private String applyOrgName;
    /**
     * 申请用户部门
     */
    private String applyDeptName;
    /**
     * 申请类型分类
     */
    private String applyTypeName;
    /**
     * 申请类型二级分类
     */
    private String applyTypeSecondName;
    /**
     * 申请表集合
     */
    private List<Map<String, Object>> tableList;
    /**
     * 申请时间
     */
    private Date createTime;
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
        return "PmsnOnesysDownWorkOrder{" +
                "id='" + id + '\'' +
                ", demandId='" + demandId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", demandCode='" + demandCode + '\'' +
                ", demandTitle='" + demandTitle + '\'' +
                ", demandDesc='" + demandDesc + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", applyUserId='" + applyUserId + '\'' +
                ", applyUserName='" + applyUserName + '\'' +
                ", applyOrgName='" + applyOrgName + '\'' +
                ", applyDeptName='" + applyDeptName + '\'' +
                ", applyTypeName='" + applyTypeName + '\'' +
                ", applyTypeSecondName='" + applyTypeSecondName + '\'' +
                ", tableList=" + tableList +
                ", createTime=" + createTime +
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

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDemandCode() {
        return demandCode;
    }

    public void setDemandCode(String demandCode) {
        this.demandCode = demandCode;
    }

    public String getDemandTitle() {
        return demandTitle;
    }

    public void setDemandTitle(String demandTitle) {
        this.demandTitle = demandTitle;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyOrgName() {
        return applyOrgName;
    }

    public void setApplyOrgName(String applyOrgName) {
        this.applyOrgName = applyOrgName;
    }

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }

    public String getApplyTypeName() {
        return applyTypeName;
    }

    public void setApplyTypeName(String applyTypeName) {
        this.applyTypeName = applyTypeName;
    }

    public String getApplyTypeSecondName() {
        return applyTypeSecondName;
    }

    public void setApplyTypeSecondName(String applyTypeSecondName) {
        this.applyTypeSecondName = applyTypeSecondName;
    }

    public List<Map<String, Object>> getTableList() {
        return tableList;
    }

    public void setTableList(List<Map<String, Object>> tableList) {
        this.tableList = tableList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
