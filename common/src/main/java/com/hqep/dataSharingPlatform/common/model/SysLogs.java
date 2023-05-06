package com.hqep.dataSharingPlatform.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hqep.dataSharingPlatform.common.vo.RequestJsonParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "sys_logs")
@ApiModel(value = "SysLogs")
public class SysLogs extends RequestJsonParam implements Serializable{

    private static final long serialVersionUID = 3378121087616511388L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("管理者姓名")
    private String managerName;

    @ApiModelProperty("具体操作")
    private String operation;

    @ApiModelProperty("操作方法")
    private String method;

    @ApiModelProperty("使用参数")
    private String params;

    @ApiModelProperty("耗时")
    private Long time;

    @ApiModelProperty("使用IP地址")
    private String ip;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;


    @ApiModelProperty("用户id")
    private String managerid;

    @ApiModelProperty("操作编号")
    private String operationcode;

    @Override
    public String toString() {
        return "SysLogs{" +
                "id=" + id +
                ", managerName='" + managerName + '\'' +
                ", operation='" + operation + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", time=" + time +
                ", ip='" + ip + '\'' +
                ", createdTime=" + createdTime +
                ", managerid='" + managerid + '\'' +
                ", operationcode='" + operationcode + '\'' +
                '}';
    }

    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public String getOperationcode() {
        return operationcode;
    }

    public void setOperationcode(String operationcode) {
        this.operationcode = operationcode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}
