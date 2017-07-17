package com.xvjialing.administrator.talentpool.bean;

import java.util.List;

/**
 * Created by xjl on 2017/1/28.
 */

public class LoginBean {

    /**
     * lp : 0
     * data : {"id":[{"id":"142","username":"xjl","password":"123","address":"福建省厦门市思明区","status":null,"birthday":null,"workStatus":"在职，看看新机会","educationLevel":null,"realname":"nznxn","sex":"男","touxiang":"Uploads/Public/upload/2016-09-13/57d7e85c69f64.jpg","work_Length":"2-3年","language":null,"phone":null}]}
     */

    private int lp;
    private DataBean data;

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<IdBean> id;

        public List<IdBean> getId() {
            return id;
        }

        public void setId(List<IdBean> id) {
            this.id = id;
        }

        public static class IdBean {
            /**
             * id : 142
             * username : xjl
             * password : 123
             * address : 福建省厦门市思明区
             * status : null
             * birthday : null
             * workStatus : 在职，看看新机会
             * educationLevel : null
             * realname : nznxn
             * sex : 男
             * touxiang : Uploads/Public/upload/2016-09-13/57d7e85c69f64.jpg
             * work_Length : 2-3年
             * language : null
             * phone : null
             */

            private String id;
            private String username;
            private String password;
            private String address;
            private Object status;
            private Object birthday;
            private String workStatus;
            private Object educationLevel;
            private String realname;
            private String sex;
            private String touxiang;
            private String work_Length;
            private Object language;
            private Object phone;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public String getWorkStatus() {
                return workStatus;
            }

            public void setWorkStatus(String workStatus) {
                this.workStatus = workStatus;
            }

            public Object getEducationLevel() {
                return educationLevel;
            }

            public void setEducationLevel(Object educationLevel) {
                this.educationLevel = educationLevel;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getTouxiang() {
                return touxiang;
            }

            public void setTouxiang(String touxiang) {
                this.touxiang = touxiang;
            }

            public String getWork_Length() {
                return work_Length;
            }

            public void setWork_Length(String work_Length) {
                this.work_Length = work_Length;
            }

            public Object getLanguage() {
                return language;
            }

            public void setLanguage(Object language) {
                this.language = language;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }
        }
    }
}
