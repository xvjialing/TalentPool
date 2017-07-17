package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by xjl on 2016/9/11.
 */
public class BoleInfo {
    private String boleId;
    private String projectId;
    private String id;
    private String boleName;
    private String phoneNumber;
    private String reliability;

    public String getBoleId() {
        return boleId;
    }

    public void setBoleId(String boleId) {
        this.boleId = boleId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoleName() {
        return boleName;
    }

    public void setBoleName(String boleName) {
        this.boleName = boleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public BoleInfo(String boleId, String projectId, String id, String boleName, String phoneNumber, String reliability) {

        this.boleId = boleId;
        this.projectId = projectId;
        this.id = id;
        this.boleName = boleName;
        this.phoneNumber = phoneNumber;
        this.reliability = reliability;
    }
}
