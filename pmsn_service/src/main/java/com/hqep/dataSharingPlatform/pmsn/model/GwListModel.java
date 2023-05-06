package com.hqep.dataSharingPlatform.pmsn.model;

import java.io.Serializable;

public class GwListModel implements Serializable {
    private String transfertype;
    private String source_sysname;
    private String tablename;
    private String fieldnum;
    private String target_tablename;
    private String target_fieldnum;
    private String trigger_method;
    private String trigger_time;
    private String identification_field;
    private String datanum;
    private String frequency;
    private String split_logic;
    private String deal_paramater;
    private String note;
    private String priorty;

    public String getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(String transfertype) {
        this.transfertype = transfertype;
    }

    public String getSource_sysname() {
        return source_sysname;
    }

    public void setSource_sysname(String source_sysname) {
        this.source_sysname = source_sysname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getFieldnum() {
        return fieldnum;
    }

    public void setFieldnum(String fieldnum) {
        this.fieldnum = fieldnum;
    }

    public String getTarget_tablename() {
        return target_tablename;
    }

    public void setTarget_tablename(String target_tablename) {
        this.target_tablename = target_tablename;
    }

    public String getTarget_fieldnum() {
        return target_fieldnum;
    }

    public void setTarget_fieldnum(String target_fieldnum) {
        this.target_fieldnum = target_fieldnum;
    }

    public String getTrigger_method() {
        return trigger_method;
    }

    public void setTrigger_method(String trigger_method) {
        this.trigger_method = trigger_method;
    }

    public String getTrigger_time() {
        return trigger_time;
    }

    public void setTrigger_time(String trigger_time) {
        this.trigger_time = trigger_time;
    }

    public String getIdentification_field() {
        return identification_field;
    }

    public void setIdentification_field(String identification_field) {
        this.identification_field = identification_field;
    }

    public String getDatanum() {
        return datanum;
    }

    public void setDatanum(String datanum) {
        this.datanum = datanum;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSplit_logic() {
        return split_logic;
    }

    public void setSplit_logic(String split_logic) {
        this.split_logic = split_logic;
    }

    public String getDeal_paramater() {
        return deal_paramater;
    }

    public void setDeal_paramater(String deal_paramater) {
        this.deal_paramater = deal_paramater;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriorty() {
        return priorty;
    }

    public void setPriorty(String priorty) {
        this.priorty = priorty;
    }

    @Override
    public String toString() {
        return "GwListModel{" +
                "transfertype='" + transfertype + '\'' +
                ", source_sysname='" + source_sysname + '\'' +
                ", tablename='" + tablename + '\'' +
                ", fieldnum='" + fieldnum + '\'' +
                ", target_tablename='" + target_tablename + '\'' +
                ", target_fieldnum='" + target_fieldnum + '\'' +
                ", trigger_method='" + trigger_method + '\'' +
                ", trigger_time='" + trigger_time + '\'' +
                ", identification_field='" + identification_field + '\'' +
                ", datanum='" + datanum + '\'' +
                ", frequency='" + frequency + '\'' +
                ", split_logic='" + split_logic + '\'' +
                ", deal_paramater='" + deal_paramater + '\'' +
                ", note='" + note + '\'' +
                ", priorty='" + priorty + '\'' +
                '}';
    }
}
