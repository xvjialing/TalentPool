package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by xjl on 2016/9/11.
 */
public class EduInfo {
    private String educationId;
    private String id;
    private String schoolName;
    private String startDate;
    private String endDate;
    private String major;
    private String description;
    private String educationLevel;

    public EduInfo(String educationId, String id, String schoolName, String startDate, String endDate, String major, String description, String educationLevel) {
        this.educationId = educationId;
        this.id = id;
        this.schoolName = schoolName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.major = major;
        this.description = description;
        this.educationLevel = educationLevel;
    }

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
}
