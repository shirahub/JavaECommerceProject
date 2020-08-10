package com.model;


import java.sql.Date;

public class Seller {
    int id_user;
    int id_seller;
    String shop_name;
    String shop_location;
    int shop_rating = 0;
    int shop_products = 0;
    Date shop_open = new Date(System.currentTimeMillis());
    boolean shop_status = true;
    Float balance = 0f;

    @Override
    public String toString() {
        return "Seller{" +
                "id_user=" + id_user +
                ", id_seller=" + id_seller +
                ", shop_name='" + shop_name + '\'' +
                ", shop_location='" + shop_location + '\'' +
                ", shop_rating=" + shop_rating +
                ", shop_products=" + shop_products +
                ", shop_open=" + shop_open +
                ", shop_status=" + shop_status +
                ", balance=" + balance +
                '}';
    }

    public Seller(int id_user, String shop_name, String shop_location) {
        this.id_user = id_user;
        this.shop_name = shop_name;
        this.shop_location = shop_location;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_seller() {
        return id_seller;
    }

    public void setId_seller(int id_seller) {
        this.id_seller = id_seller;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_location() {
        return shop_location;
    }

    public void setShop_location(String shop_location) {
        this.shop_location = shop_location;
    }

    public int getShop_rating() {
        return shop_rating;
    }

    public void setShop_rating(int shop_rating) {
        this.shop_rating = shop_rating;
    }

    public int getShop_products() {
        return shop_products;
    }

    public void setShop_products(int shop_products) {
        this.shop_products = shop_products;
    }

    public Date getShop_open() {
        return shop_open;
    }

    public void setShop_open(Date shop_open) {
        this.shop_open = shop_open;
    }

    public boolean isShop_status() {
        return shop_status;
    }

    public void setShop_status(boolean shop_status) {
        this.shop_status = shop_status;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
