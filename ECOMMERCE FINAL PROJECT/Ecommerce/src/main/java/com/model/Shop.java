package com.model;


import java.sql.Date;

public class Shop {
    String shop_name;
    String shop_location;
    int shop_rating = 0;
    int shop_products = 0;
    Date shop_open = new Date(System.currentTimeMillis());

    @Override
    public String toString() {
        return "Seller{" +
                "shop_name='" + shop_name + '\'' +
                ", shop_location='" + shop_location + '\'' +
                ", shop_rating=" + shop_rating +
                ", shop_products=" + shop_products +
                ", shop_open=" + shop_open +
                '}';
    }

    public Shop(String shop_name, String shop_location, int shop_rating, int shop_products, Date shop_open) {
        this.shop_name = shop_name;
        this.shop_location = shop_location;
        this.shop_rating = shop_rating;
        this.shop_products = shop_products;
        this.shop_open = shop_open;
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
}
