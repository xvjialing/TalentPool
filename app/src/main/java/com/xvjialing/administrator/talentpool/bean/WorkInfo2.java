package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by xjl on 2016/9/11.
 */
public class WorkInfo2 {
    private String companyName;
    private String startTime;
    private String endTime;
    private String position;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public WorkInfo2(String companyName, String startTime, String endTime, String position) {

        this.companyName = companyName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.position = position;
    }
}
