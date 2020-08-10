package com.model;

import java.sql.Date;

public class User {
    String email;
    String user_pwd;
    String full_name;
    String sex;
    Date birth_date;
    String phone_number;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", full_name='" + full_name + '\'' +
                ", sex='" + sex + '\'' +
                ", birth_Date=" + birth_date +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirth_Date() {
        return birth_date;
    }

    public void setBirth_Date(Date birth_Date) {
        this.birth_date = birth_Date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public User(String email, String user_pwd, String full_name, String sex, Date birth_Date, String phone_number) {
        this.email = email;
        this.user_pwd = user_pwd;
        this.full_name = full_name;
        this.sex = sex;
        this.birth_date = birth_Date;
        this.phone_number = phone_number;
    }
}
