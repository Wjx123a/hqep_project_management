package com.hqep.dataSharingPlatform.sjkflc.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 数据上传_审核流程环节信息表(SjkflcOdsSjmlTUpAuditProcess)实体类
 *
 * @author sssJL
 * @since 2021-10-09 13:27:39
 */
public class SjkflcOdsSjmlTUpAuditProcess implements Serializable {
    private static final long serialVersionUID = 165692172007314898L;
    /**
     * id
     */
    private String id;
    /**
    * 单位名称:编码表中类别为单位的编码名称
    */
    private String orgName;
    /**
    * 单位编码:参考附件1编码表类别为单位的编码
    */
    private String orgCode;
    /**
    * 需求名称:需求提报时产生的工单任务名称
    */
    private String demandName;
    /**
    * 需求编码:需求提报时系统实际产生的工单编码，值唯一
    */
    private String demandCode;
    /**
    * 审核环节:填写共性、关键流转环节的名称,例如：数据归口审核、合同（协议）签署确认等
    */
    private String auditLink;
    /**
    * 审核单位:填写需求审核的单位名称
    */
    private String auditOrg;
    /**
    * 审核部门:填写审核部门的名称
    */
    private String auditDept;
    /**
    * 审核开始时间:日期型，如：YYYY-MM-DD HH24:MI:SS
    */
    private Date auditStartTime;
    /**
    * 审核完成时间:日期型，如：YYYY-MM-DD HH24:MI:SS
    */
    private Date auditEndTime;
    /**
    * 变更类型:此字段用于数据集成交换，表示数据更新类型，1表示新增，2表示修改，3表示删除
    */
    private String modifyType;
    /**
    * 批次时间:UEP上传交换时间
    */
    private Date batchTime;
    /**
     * 第几页
     */
    private int pageIndex;
    /**
     * 每页多少条
     */
    private int pageSize;
    /**
     * 是否上报
     */
    private String sb;

    @Override
    public String toString() {
        return "SjkflcOdsSjmlTUpAuditProcess{" +
                "id='" + id + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", demandName='" + demandName + '\'' +
                ", demandCode='" + demandCode + '\'' +
                ", auditLink='" + auditLink + '\'' +
                ", auditOrg='" + auditOrg + '\'' +
                ", auditDept='" + auditDept + '\'' +
                ", auditStartTime=" + auditStartTime +
                ", auditEndTime=" + auditEndTime +
                ", modifyType='" + modifyType + '\'' +
                ", batchTime=" + batchTime +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sb='" + sb + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getAuditLink() {
        return auditLink;
    }

    public void setAuditLink(String auditLink) {
        this.auditLink = auditLink;
    }

    public String getAuditOrg() {
        return auditOrg;
    }

    public void setAuditOrg(String auditOrg) {
        this.auditOrg = auditOrg;
    }

    public String getAuditDept() {
        return auditDept;
    }

    public void setAuditDept(String auditDept) {
        this.auditDept = auditDept;
    }

    public Date getAuditStartTime() {
        return auditStartTime;
    }

    public void setAuditStartTime(Date auditStartTime) {
        this.auditStartTime = auditStartTime;
    }

    public Date getAuditEndTime() {
        return auditEndTime;
    }

    public void setAuditEndTime(Date auditEndTime) {
        this.auditEndTime = auditEndTime;
    }

    public String getModifyType() {
        return modifyType;
    }

    public void setModifyType(String modifyType) {
        this.modifyType = modifyType;
    }

    public Date getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(Date batchTime) {
        this.batchTime = batchTime;
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
