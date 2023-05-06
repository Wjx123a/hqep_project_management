package com.hqep.dataSharingPlatform.pmsn.model;

import java.util.Date;
import java.io.Serializable;

/**
 * (PmsnOnesysDownWorkFiles)实体类
 *
 * @author sssJL
 * @since 2022-07-28 16:09:44
 */
public class PmsnOnesysDownWorkFiles implements Serializable {
    private static final long serialVersionUID = 264273557895000276L;

    /**
     * 自建id
     */
    private String id;
    /**
     * 附件ID
     */
    private String fileId;
    /**
     * 订单ID
     */
    private String demandId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 附件内容（文件流）
     */
    private String file;
    /**
     * 自建-目标路径
     */
    private String tarPath;
    /**
     * 自建-目标名称
     */
    private String tarFileName;
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
        return "PmsnOnesysDownWorkFiles{" +
                "id='" + id + '\'' +
                ", fileId='" + fileId + '\'' +
                ", demandId='" + demandId + '\'' +
                ", createTime=" + createTime +
                ", fileName='" + fileName + '\'' +
                ", file='" + file + '\'' +
                ", tarPath='" + tarPath + '\'' +
                ", tarFileName='" + tarFileName + '\'' +
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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTarPath() {
        return tarPath;
    }

    public void setTarPath(String tarPath) {
        this.tarPath = tarPath;
    }

    public String getTarFileName() {
        return tarFileName;
    }

    public void setTarFileName(String tarFileName) {
        this.tarFileName = tarFileName;
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
