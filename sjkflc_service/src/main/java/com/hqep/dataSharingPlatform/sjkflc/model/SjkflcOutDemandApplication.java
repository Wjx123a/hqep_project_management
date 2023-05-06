package com.hqep.dataSharingPlatform.sjkflc.model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 外部需求受理申请单(SjkflcOutDemandApplication)实体类
 *
 * @author sssJL
 * @since 2021-10-06 14:18:58
 */
public class SjkflcOutDemandApplication implements Serializable {
    private static final long serialVersionUID = -59463617538602686L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 需求名称:需求提报时产生的工单任务名称
     */
    private String demandName;
    /**
     * 需求编码:需求提报时系统实际产生的工单编码，值唯一
     */
    private String demandCode;
    /**
     * 需求单位名称:提出需求的外部单位
     */
    private String demandOrgName;
    /**
     * 需求单位类型:需求单位类型：政府、非营利组织、商业机构、上下游企业、其他
     */
    private String demandOrgType;
    /**
     * 需求开始时间:提出需求的开始时间，日期型，如：YYYY-MM-DD HH24:MI:SS
     */
    private String demandStartTime;
    /**
     * 需求完成时间:需求的完成关闭时间，日期型，如：YYYY-MM-DD HH24:MI:SS
     */
    private String demandEndTime;
    /**
     * 数据申请原因:具体的数据申请原因、数据用途等
     */
    private String demandDescReason;
    /**
     * 数据用途:具体的数据申请原因、数据用途等
     */
    private String demandDescPurpose;
    /**
     * 数据内容简述
     */
    private String demandContent;
    /**
     * 需求类别名称:包括：政府监管类需求、公益服务类需求、商务增值类需求、公共开放类需求
     */
    private String demandTypeName;
    /**
     * 需求类别编码:对应需求类别名称，包括：07001表示政府监管类需求、07002表示公益服务类需求、07003表示商务增值类需求、07004表示公共开放类需求
     */
    private String demandTypeCode;
    /**
     * 数据提供方式:包括：原始数据、产品服务、分析报告、各类报表、其他
     */
    private String provideForm;
    /**
     * 数据提供载体:纸质介质、电子介质、数据接口、其他，如涉及多个值，需用逗号隔开填写。
     */
    private String provideCarrier;
    /**
     * 数据提供频度:定期/不定期/月/季度/半年/一年
     */
    private String dataFreq;
    /**
     * 需求单位联系人
     */
    private String demandNamePeo;
    /**
     * 需求单位联系电话
     */
    private String demandNameTel;
    /**
     * 需求承接方单位名称:对接需求的内部接收部门或单位
     */
    private String undertakeDeptBig;
    /**
     * 需求承接方部门名称:对接需求的内部接收部门或单位
     */
    private String undertakeDeptSmall;
    /**
     * 需求承接方联系人
     */
    private String undertakeDeptPeo;
    /**
     * 需求承接方邮箱
     */
    private String undertakeDeptMail;
    /**
     * 需求承接方联系电话
     */
    private String undertakeDeptTel;
    /**
     * 数据产生部门名称:据产生的业务部门，如涉及多个值，需用逗号隔开填写。
     */
    private String sourceDept;
    /**
     * 数据使用截止时间
     */
    private String dataEndTime;
    /**
     * 是否涉密
     */
    private String smFlag;
    /**
     * 是否技术支撑
     */
    private String sfzc;
    /**
     * 上报状态
     */
    private String sbState;
    /**
     * 上报状态名称
     */
    private String sbStateName;
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
     * 查询使用 附件code
     */
    private String fjCode;
    /**
     * 第几页
     */
    private int pageIndex;
    /**
     * 每页多少条
     */
    private int pageSize;
    /**
     * 查询使用 非草稿状态
     */
    private String sbStateNo;
    /**
     * 查询使用 附件list
     */
    private List fjList;
    /**
     * 查询使用 审批list
     */
    private List spList;

    /**
     * 表英文名:数据表英文名称
     */
    private String tableEname;
    /**
     * 表中文名:数据表中文名称
     */
    private String tableCname;
    /**
     * 表业务描述:数据表中文业务描述，能够准确反映该数据表的业务内容信息。
     */
    private String tableDesc;
    /**
     * 表所属系统名称:填写编码表中业务系统简称
     */
    private String simpleName;
    /**
     * 表所属系统编码:填写编码表中类别为业务系统的编码
     */
    private String systemCode;

    @Override
    public String toString() {
        return "SjkflcOutDemandApplication{" +
                "id='" + id + '\'' +
                ", demandName='" + demandName + '\'' +
                ", demandCode='" + demandCode + '\'' +
                ", demandOrgName='" + demandOrgName + '\'' +
                ", demandOrgType='" + demandOrgType + '\'' +
                ", demandStartTime='" + demandStartTime + '\'' +
                ", demandEndTime='" + demandEndTime + '\'' +
                ", demandDescReason='" + demandDescReason + '\'' +
                ", demandDescPurpose='" + demandDescPurpose + '\'' +
                ", demandContent='" + demandContent + '\'' +
                ", demandTypeName='" + demandTypeName + '\'' +
                ", demandTypeCode='" + demandTypeCode + '\'' +
                ", provideForm='" + provideForm + '\'' +
                ", provideCarrier='" + provideCarrier + '\'' +
                ", dataFreq='" + dataFreq + '\'' +
                ", demandNamePeo='" + demandNamePeo + '\'' +
                ", demandNameTel='" + demandNameTel + '\'' +
                ", undertakeDeptBig='" + undertakeDeptBig + '\'' +
                ", undertakeDeptSmall='" + undertakeDeptSmall + '\'' +
                ", undertakeDeptPeo='" + undertakeDeptPeo + '\'' +
                ", undertakeDeptMail='" + undertakeDeptMail + '\'' +
                ", undertakeDeptTel='" + undertakeDeptTel + '\'' +
                ", sourceDept='" + sourceDept + '\'' +
                ", dataEndTime='" + dataEndTime + '\'' +
                ", smFlag='" + smFlag + '\'' +
                ", sfzc='" + sfzc + '\'' +
                ", sbState='" + sbState + '\'' +
                ", sbStateName='" + sbStateName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createPersonCode='" + createPersonCode + '\'' +
                ", createPersonName='" + createPersonName + '\'' +
                ", zdyzd01='" + zdyzd01 + '\'' +
                ", zdyzd02='" + zdyzd02 + '\'' +
                ", fjCode='" + fjCode + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sbStateNo='" + sbStateNo + '\'' +
                ", fjList=" + fjList +
                ", spList=" + spList +
                ", tableEname='" + tableEname + '\'' +
                ", tableCname='" + tableCname + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", systemCode='" + systemCode + '\'' +
                '}';
    }

    public String getTableEname() {
        return tableEname;
    }

    public void setTableEname(String tableEname) {
        this.tableEname = tableEname;
    }

    public String getTableCname() {
        return tableCname;
    }

    public void setTableCname(String tableCname) {
        this.tableCname = tableCname;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSbStateNo() {
        return sbStateNo;
    }

    public void setSbStateNo(String sbStateNo) {
        this.sbStateNo = sbStateNo;
    }

    public String getSfzc() {
        return sfzc;
    }

    public void setSfzc(String sfzc) {
        this.sfzc = sfzc;
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

    public String getDemandStartTime() {
        return demandStartTime;
    }

    public void setDemandStartTime(String demandStartTime) {
        this.demandStartTime = demandStartTime;
    }

    public String getDemandEndTime() {
        return demandEndTime;
    }

    public void setDemandEndTime(String demandEndTime) {
        this.demandEndTime = demandEndTime;
    }

    public String getDemandDescReason() {
        return demandDescReason;
    }

    public void setDemandDescReason(String demandDescReason) {
        this.demandDescReason = demandDescReason;
    }

    public String getDemandDescPurpose() {
        return demandDescPurpose;
    }

    public void setDemandDescPurpose(String demandDescPurpose) {
        this.demandDescPurpose = demandDescPurpose;
    }

    public String getDemandContent() {
        return demandContent;
    }

    public void setDemandContent(String demandContent) {
        this.demandContent = demandContent;
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

    public String getDataFreq() {
        return dataFreq;
    }

    public void setDataFreq(String dataFreq) {
        this.dataFreq = dataFreq;
    }

    public String getDemandNamePeo() {
        return demandNamePeo;
    }

    public void setDemandNamePeo(String demandNamePeo) {
        this.demandNamePeo = demandNamePeo;
    }

    public String getDemandNameTel() {
        return demandNameTel;
    }

    public void setDemandNameTel(String demandNameTel) {
        this.demandNameTel = demandNameTel;
    }

    public String getUndertakeDeptBig() {
        return undertakeDeptBig;
    }

    public void setUndertakeDeptBig(String undertakeDeptBig) {
        this.undertakeDeptBig = undertakeDeptBig;
    }

    public String getUndertakeDeptSmall() {
        return undertakeDeptSmall;
    }

    public void setUndertakeDeptSmall(String undertakeDeptSmall) {
        this.undertakeDeptSmall = undertakeDeptSmall;
    }

    public String getUndertakeDeptPeo() {
        return undertakeDeptPeo;
    }

    public void setUndertakeDeptPeo(String undertakeDeptPeo) {
        this.undertakeDeptPeo = undertakeDeptPeo;
    }

    public String getUndertakeDeptMail() {
        return undertakeDeptMail;
    }

    public void setUndertakeDeptMail(String undertakeDeptMail) {
        this.undertakeDeptMail = undertakeDeptMail;
    }

    public String getUndertakeDeptTel() {
        return undertakeDeptTel;
    }

    public void setUndertakeDeptTel(String undertakeDeptTel) {
        this.undertakeDeptTel = undertakeDeptTel;
    }

    public String getSourceDept() {
        return sourceDept;
    }

    public void setSourceDept(String sourceDept) {
        this.sourceDept = sourceDept;
    }

    public String getDataEndTime() {
        return dataEndTime;
    }

    public void setDataEndTime(String dataEndTime) {
        this.dataEndTime = dataEndTime;
    }

    public String getSmFlag() {
        return smFlag;
    }

    public void setSmFlag(String smFlag) {
        this.smFlag = smFlag;
    }

    public String getSbState() {
        return sbState;
    }

    public void setSbState(String sbState) {
        this.sbState = sbState;
    }

    public String getSbStateName() {
        return sbStateName;
    }

    public void setSbStateName(String sbStateName) {
        this.sbStateName = sbStateName;
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

    public String getFjCode() {
        return fjCode;
    }

    public void setFjCode(String fjCode) {
        this.fjCode = fjCode;
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

    public List getFjList() {
        return fjList;
    }

    public void setFjList(List fjList) {
        this.fjList = fjList;
    }

    public List getSpList() {
        return spList;
    }

    public void setSpList(List spList) {
        this.spList = spList;
    }
}
