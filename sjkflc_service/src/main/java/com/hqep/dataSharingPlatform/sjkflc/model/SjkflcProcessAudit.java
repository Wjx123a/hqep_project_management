package com.hqep.dataSharingPlatform.sjkflc.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 审核流程意见表(SjkflcProcessAudit)实体类
 *
 * @author sssJL
 * @since 2021-10-08 15:05:30
 */
public class SjkflcProcessAudit implements Serializable {
    private static final long serialVersionUID = 370127379966620799L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 需求名称:外部单位需求的名称，建议以单位名称+应用场景名称组合命名
     */
    private String demandName;
    /**
     * 需求编码:需求编码与需求名称做关系映射，自动带出
     */
    private String demandCode;
    /**
     * 审核开始时间:该环节触发时，同步获取系统此刻时间戳
     */
    private String auditStartTime;
    /**
     * 审核结束时间:根据审批状态为‘已审批’时，同步获取系统此刻时间戳
     */
    private String auditEndTime;
    /**
     * 审核环节:取值范围默认为初审、保密合规审核、数据归口审核、合同（协议确认）、数据服务提供确认五个环节；如本单位有个性化环节，则新增取值选项
     */
    private String auditLink;
    /**
     * 审核单位:填写需求审核的单位名称
     */
    private String auditOrg;
    /**
     * 审核部门:审核人所属部门
     */
    private String auditDept;
    /**
     * 审核人:本流程节点所配置的用户名称
     */
    private String auditPeo;
    /**
     * 审批意见:默认为‘同意’，可手工修改为‘驳回’需注明驳回理由
     */
    private String auditOpinion;
    /**
     * 审核理由:审核人需手工录入、给出审核意见的理由
     */
    private String auditReason;
    /**
     * 审批状态:默认为‘未审批’，系统自动修改为‘已审批’
     */
    private String auditState;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人编码
     */
    private String createPersonCode;
    /**
     * 创建人
     */
    private String createPersonName;
    /**
     * 自定义字段1
     */
    private String zdyzd01;
    /**
     * 自定义字段2
     */
    private String zdyzd02;
    /**
     * 自定义字段2
     */
    private String zdyzd03;

    /**
     * 审批状态
     */
    private String spzt;
    /**
     * 第几页
     */
    private int pageIndex;
    /**
     * 每页多少条
     */
    private int pageSize;
    /**
     * 查询使用 附件code
     */
    private String htCode;

    @Override
    public String toString() {
        return "SjkflcProcessAudit{" +
                "id='" + id + '\'' +
                ", demandName='" + demandName + '\'' +
                ", demandCode='" + demandCode + '\'' +
                ", auditStartTime='" + auditStartTime + '\'' +
                ", auditEndTime='" + auditEndTime + '\'' +
                ", auditLink='" + auditLink + '\'' +
                ", auditOrg='" + auditOrg + '\'' +
                ", auditDept='" + auditDept + '\'' +
                ", auditPeo='" + auditPeo + '\'' +
                ", auditOpinion='" + auditOpinion + '\'' +
                ", auditReason='" + auditReason + '\'' +
                ", auditState='" + auditState + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createPersonCode='" + createPersonCode + '\'' +
                ", createPersonName='" + createPersonName + '\'' +
                ", zdyzd01='" + zdyzd01 + '\'' +
                ", zdyzd02='" + zdyzd02 + '\'' +
                ", zdyzd03='" + zdyzd03 + '\'' +
                ", spzt='" + spzt + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", htCode='" + htCode + '\'' +
                '}';
    }

    public String getHtCode() {
        return htCode;
    }

    public void setHtCode(String htCode) {
        this.htCode = htCode;
    }

    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }

    public String getAuditOrg() {
        return auditOrg;
    }

    public void setAuditOrg(String auditOrg) {
        this.auditOrg = auditOrg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getDemandCode() {
        return demandCode;
    }

    public void setDemandCode(String demandCode) {
        this.demandCode = demandCode;
    }

    public String getAuditStartTime() {
        return auditStartTime;
    }

    public void setAuditStartTime(String auditStartTime) {
        this.auditStartTime = auditStartTime;
    }

    public String getAuditEndTime() {
        return auditEndTime;
    }

    public void setAuditEndTime(String auditEndTime) {
        this.auditEndTime = auditEndTime;
    }

    public String getAuditLink() {
        return auditLink;
    }

    public void setAuditLink(String auditLink) {
        this.auditLink = auditLink;
    }

    public String getAuditDept() {
        return auditDept;
    }

    public void setAuditDept(String auditDept) {
        this.auditDept = auditDept;
    }

    public String getAuditPeo() {
        return auditPeo;
    }

    public void setAuditPeo(String auditPeo) {
        this.auditPeo = auditPeo;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatePersonCode() {
        return createPersonCode;
    }

    public void setCreatePersonCode(String createPersonCode) {
        this.createPersonCode = createPersonCode;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public String getZdyzd01() {
        return zdyzd01;
    }

    public void setZdyzd01(String zdyzd01) {
        this.zdyzd01 = zdyzd01;
    }

    public String getZdyzd02() {
        return zdyzd02;
    }

    public void setZdyzd02(String zdyzd02) {
        this.zdyzd02 = zdyzd02;
    }

    public String getZdyzd03() {
        return zdyzd03;
    }

    public void setZdyzd03(String zdyzd03) {
        this.zdyzd03 = zdyzd03;
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
