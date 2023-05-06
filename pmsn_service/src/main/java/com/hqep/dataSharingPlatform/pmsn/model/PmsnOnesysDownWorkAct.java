package com.hqep.dataSharingPlatform.pmsn.model;

import java.util.Date;
import java.io.Serializable;

/**
 * (PmsnOnesysDownWorkAct)实体类
 *
 * @author sssJL
 * @since 2022-07-28 16:09:43
 */
public class PmsnOnesysDownWorkAct implements Serializable {
    private static final long serialVersionUID = -67531006731948806L;
    /**
     * 自建Id
     */
    private String id;
    /**
     * 流程ID
     */
    private String flowId;
    /**
     * 订单ID
     */
    private String demandId;
    /**
     * 处理用户名称
     */
    private String userName;
    /**
     * 用户组织机构路径
     */
    private String userOrgPath;
    /**
     * 是否处理（1：已处理；0：未处理）
     */
    private String isHandle;
    /**
     * 处理时间
     */
    private Date handleTime;
    /**
     * 节点标题
     */
    private String nodeTitle;
    /**
     * 节点名称（新建工单,运营经理多数据确认,互联网部管理员审核,自助下发,中台授权,运营经理负面清单确认,运营经理审核,并行处理,专责处理,工单完成,工单激活，运营平台处理）
     */
    private String nodeName;
    /**
     * 节点备注
     */
    private String nodeComment;
    /**
     * 节点序号
     */
    private String nodeSort;
    /**
     * 操作说明
     */
    private String operate;
    /**
     * 创建时间
     */
    private Date createTime;
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
        return "PmsnOnesysDownWorkAct{" +
                "id='" + id + '\'' +
                ", flowId='" + flowId + '\'' +
                ", demandId='" + demandId + '\'' +
                ", userName='" + userName + '\'' +
                ", userOrgPath='" + userOrgPath + '\'' +
                ", isHandle='" + isHandle + '\'' +
                ", handleTime=" + handleTime +
                ", nodeTitle='" + nodeTitle + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", nodeComment='" + nodeComment + '\'' +
                ", nodeSort='" + nodeSort + '\'' +
                ", operate='" + operate + '\'' +
                ", createTime=" + createTime +
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

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserOrgPath() {
        return userOrgPath;
    }

    public void setUserOrgPath(String userOrgPath) {
        this.userOrgPath = userOrgPath;
    }

    public String getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeComment() {
        return nodeComment;
    }

    public void setNodeComment(String nodeComment) {
        this.nodeComment = nodeComment;
    }

    public String getNodeSort() {
        return nodeSort;
    }

    public void setNodeSort(String nodeSort) {
        this.nodeSort = nodeSort;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
