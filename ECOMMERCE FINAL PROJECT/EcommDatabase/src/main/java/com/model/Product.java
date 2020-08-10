package com.model;

public class Product {
    int id_seller;
    int id_product;
    String product_name;
    String product_category;
    String product_desc;
    int product_quantity;
    float product_price;
    float product_weight;

    public Product(int id_seller, int id_product, String product_name, String product_category, String product_desc, int product_quantity, float product_price, float product_weight) {
        this.id_seller = id_seller;
        this.id_product = id_product;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_desc = product_desc;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_weight = product_weight;
    }

    public Product(int id_seller, String product_name) {
        this.id_seller = id_seller;
        this.product_name = product_name;
    }

    public Product(int id_seller, String product_name, String product_category, String product_desc, int product_quantity, float product_price, float product_weight) {
        this.id_seller = id_seller;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_desc = product_desc;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_weight = product_weight;
    }

    public int getId_seller() {
        return id_seller;
    }

    public void setId_seller(int id_seller) {
        this.id_seller = id_seller;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public float getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(float product_weight) {
        this.product_weight = product_weight;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_seller=" + id_seller +
                ", id_product=" + id_product +
                ", product_name='" + product_name + '\'' +
                ", product_category='" + product_category + '\'' +
                ", product_desc='" + product_desc + '\'' +
                ", product_quantity=" + product_quantity +
                ", product_price=" + product_price +
                ", product_weight=" + product_weight +
                '}';
    }
}
