package com.hqep.dataSharingPlatform.sjkflc.model;

import java.io.Serializable;

/**
 * 涉密数据判定表(SjkflcSecretRelated)实体类
 *
 * @author sssJL
 * @since 2021-10-27 15:06:53
 */
public class SjkflcSecretRelated implements Serializable {
    private static final long serialVersionUID = -68140363031597782L;
    /**
     * id
     */
    private String id;
    /**
     * 序号
     */
    private String xh;
    /**
     * 01统一部署、02二级部署
     */
    private String sysBsType;
    /**
     * 选择数据溯源表格的所属业务系统
     */
    private String sysName;
    /**
     * 选择表英文名称后自动带出
     */
    private String tableChName;
    /**
     * 选择对应的表单名称
     */
    private String tableEnName;
    /**
     * 对应的表中字段中文名
     */
    private String tableChFieldName;
    /**
     * 对应的表中字段英文名
     */
    private String tableEnFieldName;
    /**
     * 01是、02否
     */
    private String mgFlag;
    /**
     * 指数据产生部门
     */
    private String dataZrDept;
    /**
     * 第几页
     */
    private int pageIndex;
    /**
     * 每页多少条
     */
    private int pageSize;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getSysBsType() {
        return sysBsType;
    }

    public void setSysBsType(String sysBsType) {
        this.sysBsType = sysBsType;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getTableChName() {
        return tableChName;
    }

    public void setTableChName(String tableChName) {
        this.tableChName = tableChName;
    }

    public String getTableEnName() {
        return tableEnName;
    }

    public void setTableEnName(String tableEnName) {
        this.tableEnName = tableEnName;
    }

    public String getTableChFieldName() {
        return tableChFieldName;
    }

    public void setTableChFieldName(String tableChFieldName) {
        this.tableChFieldName = tableChFieldName;
    }

    public String getTableEnFieldName() {
        return tableEnFieldName;
    }

    public void setTableEnFieldName(String tableEnFieldName) {
        this.tableEnFieldName = tableEnFieldName;
    }

    public String getMgFlag() {
        return mgFlag;
    }

    public void setMgFlag(String mgFlag) {
        this.mgFlag = mgFlag;
    }

    public String getDataZrDept() {
        return dataZrDept;
    }

    public void setDataZrDept(String dataZrDept) {
        this.dataZrDept = dataZrDept;
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
