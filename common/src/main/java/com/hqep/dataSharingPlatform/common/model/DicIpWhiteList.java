package com.hqep.dataSharingPlatform.common.model;

import java.io.Serializable;

/**
 * (DicIpWhiteList)实体类
 *
 * @author 邵文强
 * @since 2021-03-29 17:01:48
 */
public class DicIpWhiteList extends RequestJsonParam implements Serializable {
    private static final long serialVersionUID = -44778200243447110L;


    /**
    * ip地址
    */
    private String ip;
    /**
    * 是否为高权用户（Y or N）
    */
    private String power;
    /**
    * 描述
    */
    private String ms;

    private String id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}