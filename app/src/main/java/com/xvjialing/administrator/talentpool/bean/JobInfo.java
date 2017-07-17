package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by Administrator on 2016/7/24.
 */
public class JobInfo {
    private String jobName;

    private String address;

    private String salary;

    private String workLength;

    private String publishingTime;

    private String companyName;

    private String size;

    private String companyLogo;

    private String degree;

    private String welfare;

    private String descrip;

    private String companyAddress;

    private String phoneNumber;

    private String website;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getWorkLength() {
        return workLength;
    }

    public void setWorkLength(String workLength) {
        this.workLength = workLength;
    }

    public String getPublishingTime() {
        return publishingTime;
    }

    public void setPublishingTime(String publishingTime) {
        this.publishingTime = publishingTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWelfare() {
        return welfare;
    }

    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    private String companyId;

    public JobInfo(String jobName, String address, String salary, String workLength, String publishingTime, String companyName, String size, String companyLogo, String degree, String welfare, String descrip, String companyAddress, String phoneNumber, String website, String companyId) {
        this.jobName = jobName;
        this.address = address;
        this.salary = salary;
        this.workLength = workLength;
        this.publishingTime = publishingTime;
        this.companyName = companyName;
        this.size = size;
        this.companyLogo = companyLogo;
        this.degree = degree;
        this.welfare = welfare;
        this.descrip = descrip;
        this.companyAddress = companyAddress;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.companyId = companyId;
    }
}
