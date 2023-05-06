package com.hqep.dataSharingPlatform.sjkflc.model;


import java.io.Serializable;

/**
 * 数据开放流程-文件管理(SjkflcFile)实体类
 *
 * @author liuzg
 * @since 2021-10-17 12:18:20
 */
public class SjkflcFile implements Serializable {
    private static final long serialVersionUID = 705666005514345501L;
    /**
     * 主键ID
     */
    private String id;
    /**
     * 关联CODE
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private String flag;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件扩展名
     */
    private String fileSuffix;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 服务类型（local，fdfs）
     */
    private String fileServer;

        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
        
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
        
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
        
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
        
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
        
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
        
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
        
    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
        
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
        
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
        
    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
        
    public String getFileServer() {
        return fileServer;
    }

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

}
