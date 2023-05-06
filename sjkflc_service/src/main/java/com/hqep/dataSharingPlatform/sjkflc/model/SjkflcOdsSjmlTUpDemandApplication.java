package com.hqep.dataSharingPlatform.sjkflc.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 数据上传_需求提报环节信息表(SjkflcOdsSjmlTUpDemandApplication)实体类
 *
 * @author sssJL
 * @since 2021-10-09 13:22:56
 */
public class SjkflcOdsSjmlTUpDemandApplication implements Serializable {
    private static final long serialVersionUID = -16435124357279013L;
    /**
    * 单位名称：编码表中类别为单位的编码名称
    */
    private String id;
    /**
    * 单位名称：编码表中类别为单位的编码名称
    */
    private String orgName;
    /**
    * 单位编码：参考附件1编码表类别为单位的编码
    */
    private String orgCode;
    /**
    * 需求名称：需求提报时产生的工单任务名称
    */
    private String demandName;
    /**
    * 需求编码：需求提报时系统实际产生的工单编码，值唯一
    */
    private String demandCode;
    /**
    * 需求类别名称：包括：政府监管类需求、公益服务类需求、商务增值类需求、公共开放类需求
    */
    private String demandTypeName;
    /**
    * 需求类别编码：对应需求类别名称，包括：07001表示政府监管类需求、07002表示公益服务类需求、07003表示商务增值类需求、07004表示公共开放类需求
    */
    private String demandTypeCode;
    /**
    * 需求单位名称：提出需求的外部单位
    */
    private String demandOrgName;
    /**
    * 需求单位类型：需求单位类型：政府、非营利组织、商业机构、上下游企业、其他
    */
    private String demandOrgType;
    /**
    * 需求描述：具体的数据申请原因、数据用途等
    */
    private String demandDesc;
    /**
    * 数据内容：外部需求单位希望获取的数据内容概要描述
    */
    private String demandData;
    /**
    * 数据提供方式：包括：原始数据、产品服务、分析报告、各类报表、其他
    */
    private String provideForm;
    /**
    * 数据提供载体：纸质介质、电子介质、数据接口、其他，如涉及多个值，需用逗号隔开填写。
    */
    private String provideCarrier;
    /**
    * 需求承接部门或单位：对接需求的内部接收部门或单位
    */
    private String undertakeDept;
    /**
    * 数据产生部门：据产生的业务部门，如涉及多个值，需用逗号隔开填写。
    */
    private String sourceDept;
    /**
    * 数据提供频度：定期/不定期/月/季度/半年/一年
    */
    private String dataFreq;
    /**
    * 需求开始时间：提出需求的开始时间，日期型，如：YYYY-MM-DD HH24:MI:SS
    */
    private Date demandStartTime;
    /**
    * 需求完成时间：需求的完成关闭时间，日期型，如：YYYY-MM-DD HH24:MI:SS
    */
    private Date demandEndTime;
    /**
    * 变更类型：此字段用于数据集成交换，表示数据更新类型，1表示新增，2表示修改，3表示删除
    */
    private String modifyType;
    /**
    * 批次时间：UEP上传交换时间
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
        return "SjkflcOdsSjmlTUpDemandApplication{" +
                "id='" + id + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", demandName='" + demandName + '\'' +
                ", demandCode='" + demandCode + '\'' +
                ", demandTypeName='" + demandTypeName + '\'' +
                ", demandTypeCode='" + demandTypeCode + '\'' +
                ", demandOrgName='" + demandOrgName + '\'' +
                ", demandOrgType='" + demandOrgType + '\'' +
                ", demandDesc='" + demandDesc + '\'' +
                ", demandData='" + demandData + '\'' +
                ", provideForm='" + provideForm + '\'' +
                ", provideCarrier='" + provideCarrier + '\'' +
                ", undertakeDept='" + undertakeDept + '\'' +
                ", sourceDept='" + sourceDept + '\'' +
                ", dataFreq='" + dataFreq + '\'' +
                ", demandStartTime=" + demandStartTime +
                ", demandEndTime=" + demandEndTime +
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

    public String getDemandTypeName() {
        return demandTypeName;
    }

    public void setDemandTypeName(String demandTypeName) {
        this.demandTypeName = demandTypeName;
    }

    public String getDemandTypeCode() {
        return demandTypeCode;
    }

    public void setDemandTypeCode(String demandTypeCode) {
        this.demandTypeCode = demandTypeCode;
    }

    public String getDemandOrgName() {
        return demandOrgName;
    }

    public void setDemandOrgName(String demandOrgName) {
        this.demandOrgName = demandOrgName;
    }

    public String getDemandOrgType() {
        return demandOrgType;
    }

    public void setDemandOrgType(String demandOrgType) {
        this.demandOrgType = demandOrgType;
    }

    public String getDemandDesc() {
        return demandDesc;
    }

    public void setDemandDesc(String demandDesc) {
        this.demandDesc = demandDesc;
    }

    public String getDemandData() {
        return demandData;
    }

    public void setDemandData(String demandData) {
        this.demandData = demandData;
    }

    public String getProvideForm() {
        return provideForm;
    }

    public void setProvideForm(String provideForm) {
        this.provideForm = provideForm;
    }

    public String getProvideCarrier() {
        return provideCarrier;
    }

    public void setProvideCarrier(String provideCarrier) {
        this.provideCarrier = provideCarrier;
    }

    public String getUndertakeDept() {
        return undertakeDept;
    }

    public void setUndertakeDept(String undertakeDept) {
        this.undertakeDept = undertakeDept;
    }

    public String getSourceDept() {
        return sourceDept;
    }

    public void setSourceDept(String sourceDept) {
        this.sourceDept = sourceDept;
    }

    public String getDataFreq() {
        return dataFreq;
    }

    public void setDataFreq(String dataFreq) {
        this.dataFreq = dataFreq;
    }

    public Date getDemandStartTime() {
        return demandStartTime;
    }

    public void setDemandStartTime(Date demandStartTime) {
        this.demandStartTime = demandStartTime;
    }

    public Date getDemandEndTime() {
        return demandEndTime;
    }

    public void setDemandEndTime(Date demandEndTime) {
        this.demandEndTime = demandEndTime;
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
