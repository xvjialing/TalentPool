package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by xjl on 2016/9/11.
 */
public class EduInfo2 {
    private String schoolName;
    private String startDate;
    private String endDate;
    private String major;

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

    public EduInfo2(String schoolName, String startDate, String endDate, String major) {
        this.schoolName = schoolName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.major = major;

    }
}
