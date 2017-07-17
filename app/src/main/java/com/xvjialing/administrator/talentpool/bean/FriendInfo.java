package com.xvjialing.administrator.talentpool.bean;

/**
 * Created by xjl on 2016/9/11.
 */
public class FriendInfo {
    private String friendId;
    private String friendName;
    private String address;
    private String skill;
    private String company;
    private String position;
    private String status;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public FriendInfo(String friendId, String friendName, String address, String skill, String company, String position, String status) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.address = address;
        this.skill = skill;
        this.company = company;
        this.position = position;
        this.status = status;
    }
}
