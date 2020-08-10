package com.model;

public class TransDetails {
    int id_trans;
    int id_product;
    int product_qty;

    public TransDetails(int id_trans, int id_product, int product_qty) {
        this.id_trans = id_trans;
        this.id_product = id_product;
        this.product_qty = product_qty;
    }

    public int getId_trans() {
        return id_trans;
    }

    public void setId_trans(int id_trans) {
        this.id_trans = id_trans;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(int product_qty) {
        this.product_qty = product_qty;
    }
}
