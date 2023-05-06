package com.hqep.dataSharingPlatform.pmsn.model;

import java.io.Serializable;

/**
 * (SysMenuButton)实体类
 *
 * @author sssJL
 * @since 2022-11-02 13:44:07
 */
public class SysMenuButton implements Serializable {
    private static final long serialVersionUID = -85556376517014951L;
    /**
     * id
     */
    private String id;
    /**
     * 系统编码
     */
    private String buttonCode;
    /**
     * 系统名称
     */
    private String buttonName;
    /**
     * 一级目录
     */
    private String buttonDesc;
    /**
     * 二级目录
     */
    private String shortnum;
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

    public String getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonDesc() {
        return buttonDesc;
    }

    public void setButtonDesc(String buttonDesc) {
        this.buttonDesc = buttonDesc;
    }

    public String getShortnum() {
        return shortnum;
    }

    public void setShortnum(String shortnum) {
        this.shortnum = shortnum;
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
