package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by xjl on 2016/9/11.
 */
public class WorkInfo {

    private String ExperienceId;
    private String id;
    private String companyName;
    private String startTime;

    public String getExperienceId() {
        return ExperienceId;
    }

    public void setExperienceId(String experienceId) {
        ExperienceId = experienceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private String endTime;
    private String address;
    private String description;
    private String position;
    private String industry;
    private String reason;

    public WorkInfo(String experienceId, String id, String companyName, String startTime, String endTime, String address, String description, String position, String industry, String reason) {
        ExperienceId = experienceId;
        this.id = id;
        this.companyName = companyName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.description = description;
        this.position = position;
        this.industry = industry;
        this.reason = reason;
    }
}
