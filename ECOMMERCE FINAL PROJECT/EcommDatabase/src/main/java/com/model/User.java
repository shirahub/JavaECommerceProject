package com.model;


import java.sql.Date;

public class User {
    int id_user; //primary key
    String email;
    String user_pwd;
    boolean user_status;
    String full_name;
    String sex;
    Date birth_date;
    String phone_number;
    boolean isSeller;
    boolean isSessionActive;

    @Override
    public String toString() {
//        return "User{" +
//                "email='" + email + '\'' +
//                ", user_pwd='" + user_pwd + '\'' +
//                ", user_status='" + user_status + '\'' +
//                ", full_name='" + full_name + '\'' +
//                ", sex='" + sex + '\'' +
//                ", birth_date='" + birth_date + '\'' +
//                ", phone_number=" + phone_number +
//                '}';
        return "\"user\":{\"email\":\"" + email + "\",\"user_pwd\":\"" + user_pwd + "\",\"full_name\":\"" + full_name + "\",\"sex\":\"" + sex + "\",\"birth_date\":\"" + birth_date + "\",\"phone_number\":\"" + phone_number + "\"}";
    }

    public User(int id_user, String email, String user_pwd, boolean user_status, String full_name, String sex, Date birth_date, String phone_number, boolean isSeller, boolean isSessionActive) {
        this.id_user = id_user;
        this.email = email;
        this.user_pwd = user_pwd;
        this.user_status = user_status;
        this.full_name = full_name;
        this.sex = sex;
        this.birth_date = birth_date;
        this.phone_number = phone_number;
        this.isSeller = isSeller;
        this.isSessionActive = isSessionActive;
    }

    public User(String email, String user_pwd, boolean user_status, String full_name, String sex, Date birth_date, String phone_number) {
        this.email = email;
        this.user_pwd = user_pwd;
        this.user_status = user_status;
        this.full_name = full_name;
        this.sex = sex;
        this.birth_date = birth_date;
        this.phone_number = phone_number;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public boolean getUser_status() {
        return user_status;
    }

    public void setUser_status(boolean user_status) {
        this.user_status = user_status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public boolean isSessionActive() {
        return isSessionActive;
    }

    public void setSessionActive(boolean sessionActive) {
        isSessionActive = sessionActive;
    }
}
