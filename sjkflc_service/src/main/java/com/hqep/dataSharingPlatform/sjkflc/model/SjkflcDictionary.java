package com.hqep.dataSharingPlatform.sjkflc.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 数据字典表(SjkflcDictionary)实体类
 *
 * @author sssJL
 * @since 2021-10-13 15:34:07
 */
public class SjkflcDictionary implements Serializable {
    private static final long serialVersionUID = -95507528087066368L;
    /**
     * 字典表ID(使用UUID随机生成32位字符串)
     */
    private String dictId;
    /**
     * 字典表名称
     */
    private String dictName;
    /**
     * 字典表描述
     */
    private String dictDesc;
    /**
     * Y:正在使用;N:不再使用
     */
    private String disable;
    /**
     * 排序
     */
    private String dictSort;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 第几页
     */
    private int pageIndex;
    /**
     * 每页多少条
     */
    private int pageSize;


    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

    public String getDictSort() {
        return dictSort;
    }

    public void setDictSort(String dictSort) {
        this.dictSort = dictSort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
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
