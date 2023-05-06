package com.hqep.dataSharingPlatform.sjkflc.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 数据上传_需求数据表信息表(SjkflcOdsSjmlTUpDemandTable)实体类
 *
 * @author sssJL
 * @since 2021-10-11 10:13:30
 */
public class SjkflcOdsSjmlTUpDemandTable implements Serializable {
    private static final long serialVersionUID = -60624519918436484L;
    /**
     * 主键id
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
        return "SjkflcOdsSjmlTUpDemandTable{" +
                "id='" + id + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", demandName='" + demandName + '\'' +
                ", demandCode='" + demandCode + '\'' +
                ", tableEname='" + tableEname + '\'' +
                ", tableCname='" + tableCname + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", systemCode='" + systemCode + '\'' +
                ", modifyType='" + modifyType + '\'' +
                ", batchTime=" + batchTime +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", sb=" + sb +
                '}';
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
