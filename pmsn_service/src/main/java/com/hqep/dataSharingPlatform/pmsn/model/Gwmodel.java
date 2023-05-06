package com.hqep.dataSharingPlatform.pmsn.model;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Gwmodel implements Serializable {
    @JsonProperty(value = "Ticketid")
    private String Ticketid;
    @JsonProperty(value = "Title")
    private String Title;
    @JsonProperty("Description")
    private String Description;

    private String projectname;
    @JsonProperty("SLAcompletetime")
    private String SLAcompletetime;

    private List<GwListModel> tableList;
    @JsonProperty(value = "Ticketid")
    public String getTicketid() {
        return Ticketid;
    }

    public void setTicketid(String ticketid) {
        Ticketid = ticketid;
    }
    @JsonProperty(value = "Title")
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    @JsonProperty("Description")
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
    @JsonProperty("SLAcompletetime")
    public String getSLAcompletetime() {
        return SLAcompletetime;
    }

    public void setSLAcompletetime(String SLAcompletetime) {
        this.SLAcompletetime = SLAcompletetime;
    }

    public List<GwListModel> getTableList() {
        return tableList;
    }

    public void setTableList(List<GwListModel> tableList) {
        this.tableList = tableList;
    }

    @Override
    public String toString() {
        return "Gwmodel{" +
                "Ticketid='" + Ticketid + '\'' +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", projectname='" + projectname + '\'' +
                ", SLAcompletetime='" + SLAcompletetime + '\'' +
                ", tableList=" + tableList +
                '}';
    }





}
