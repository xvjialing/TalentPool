package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by xjl on 2016/9/11.
 */
public class ProjectInfo2 {
    private String projectName;
    private String startDate;
    private String endDate;
    private String projectRole;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    public ProjectInfo2(String projectName, String startDate, String endDate, String projectRole) {

        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectRole = projectRole;
    }
}
