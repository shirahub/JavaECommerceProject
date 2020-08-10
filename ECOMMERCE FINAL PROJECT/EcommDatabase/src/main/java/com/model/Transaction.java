package com.model;

import java.util.Date;

public class Transaction {
    int id_trans;
    int id_user;
    int id_seller;
    float total_weight;
    float shipment_fee;
    float total_cost;
    String trans_status = "Order Requested"; //user:orderRequest -> seller:orderReceived -> seller:orderShipped -> seller:orderArrived -> user:orderConfirmed
    String payment_method;
    String orderTrackingNumber;
    Date orderRequest = new Date();
    Date orderReceived;
    Date orderShipped;
    Date orderArrived;
    Date orderConfirmed;

    public Transaction(int id_trans, int id_user, int id_seller, float total_weight, float shipment_fee, float total_cost, String trans_status, String payment_method, String orderTrackingNumber, Date orderRequest, Date orderReceived, Date orderShipped, Date orderArrived, Date orderConfirmed) {
        this.id_trans = id_trans;
        this.id_user = id_user;
        this.id_seller = id_seller;
        this.total_weight = total_weight;
        this.shipment_fee = shipment_fee;
        this.total_cost = total_cost;
        this.trans_status = trans_status;
        this.payment_method = payment_method;
        this.orderTrackingNumber = orderTrackingNumber;
        this.orderRequest = orderRequest;
        this.orderReceived = orderReceived;
        this.orderShipped = orderShipped;
        this.orderArrived = orderArrived;
        this.orderConfirmed = orderConfirmed;
    }

    public Transaction(int id_user, int id_seller, float total_weight, float shipment_fee, float total_cost) {
        this.id_user = id_user;
        this.id_seller = id_seller;
        this.total_weight = total_weight;
        this.shipment_fee = shipment_fee;
        this.total_cost = total_cost;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }


    public int getId_trans() {
        return id_trans;
    }

    public void setId_trans(int id_trans) {
        this.id_trans = id_trans;
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

    public float getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(float total_weight) {
        this.total_weight = total_weight;
    }

    public float getShipment_fee() {
        return shipment_fee;
    }

    public void setShipment_fee(float shipment_fee) {
        this.shipment_fee = shipment_fee;
    }

    public float getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost = total_cost;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getOrderTrackingNumber() {
        return orderTrackingNumber;
    }

    public void setOrderTrackingNumber(String orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }

    public Date getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(Date orderRequest) {
        this.orderRequest = orderRequest;
    }

    public Date getOrderReceived() {
        return orderReceived;
    }

    public void setOrderReceived(Date orderReceived) {
        this.orderReceived = orderReceived;
    }

    public Date getOrderShipped() {
        return orderShipped;
    }

    public void setOrderShipped(Date orderShipped) {
        this.orderShipped = orderShipped;
    }

    public Date getOrderArrived() {
        return orderArrived;
    }

    public void setOrderArrived(Date orderArrived) {
        this.orderArrived = orderArrived;
    }

    public Date getOrderConfirmed() {
        return orderConfirmed;
    }

    public void setOrderConfirmed(Date orderConfirmed) {
        this.orderConfirmed = orderConfirmed;
    }
}
