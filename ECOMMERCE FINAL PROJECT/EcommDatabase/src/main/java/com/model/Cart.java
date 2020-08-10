package com.model;

public class Cart {
    int id_user;
    int id_product;
    int quantity;


    public Cart(int id_product, int quantity) {
        this.id_product = id_product;
        this.quantity = quantity;
    }

    public Cart(int id_user, int id_product, int quantity) {
        this.id_user = id_user;
        this.id_product = id_product;
        this.quantity = quantity;
    }

    public Cart() {

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
