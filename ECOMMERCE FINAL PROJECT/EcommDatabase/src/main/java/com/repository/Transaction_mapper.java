package com.repository;

import com.model.TransDetails;
import com.model.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface Transaction_mapper {

    final String insert = "INSERT INTO transaction (id_trans, id_user, id_seller, total_weight, shipment_fee, total_cost, trans_status, payment_method, orderTrackingNumber, orderRequest, orderReceived, orderShipped, orderArrived, orderConfirmed) VALUES (#{id_trans}, #{id_user}, #{id_seller}, #{total_weight}, #{shipment_fee}, #{total_cost}, #{trans_status}, #{payment_method}, #{orderTrackingNumber}, #{orderRequest}, #{orderReceived}, #{orderShipped}, #{orderArrived}, #{orderConfirmed})";
    final String insertDetails = "INSERT INTO trans_details (id_trans, id_product, product_qty) VALUES (#{id_trans}, #{id_product}, #{product_qty})";
    final String getDetailsByIdTrans = "SELECT * FROM trans_details where id_trans = #{id_trans}";
    final String getTransByLastInsert = "select * from transaction ORDER BY id_trans DESC LIMIT 1";
    final String updatePayMethod = "UPDATE transaction SET payment_method = #{payment_method}, trans_status = 'Awaiting Payment' WHERE id_trans = #{id_trans}";
    final String updateStatus = "UPDATE transaction SET trans_status =  #{trans_status} WHERE id_trans = #{id_trans}";
    final String updateOrderReceived = "UPDATE transaction SET orderReceived = #{orderReceived}, trans_status = 'Order Received By Seller' WHERE id_trans = #{id_trans}";
    final String updateOrderShipped = "UPDATE transaction SET orderShipped = #{orderShipped}, orderTrackingNumber = #{orderTrackingNumber}, trans_status = 'Order on Shipment' WHERE id_trans = #{id_trans}";
    final String updateOrderArrived = "UPDATE transaction SET orderArrived = #{orderArrived}, trans_status = 'Order Arrived' WHERE id_trans = #{id_trans}";
    final String updateOrderConfirmed = "UPDATE transaction SET orderConfirmed = #{orderConfirmed}, trans_status = 'Order Finished' WHERE id_trans = #{id_trans}";
    final String getByID = "SELECT * FROM transaction WHERE id_trans = #{id_trans}";
    final String getByIDUser = "SELECT * FROM transaction WHERE id_user = #{id_user}";
    final String getByIDSeller = "SELECT * FROM transaction WHERE id_seller = #{id_seller}";

    @Update(updateStatus)
    void updateStatus(Transaction transaction);
    @Update(updatePayMethod)
    void updatePayMethod(Transaction transaction);
    @Update(updateOrderReceived)
    void updateOrderReceived(Transaction transaction);
    @Update(updateOrderShipped)
    void updateOrderShipped(Transaction transaction);
    @Update(updateOrderArrived)
    void updateOrderArrived(Transaction transaction);
    @Update(updateOrderConfirmed)
    void updateOrderConfirmed(Transaction transaction);

    @Select(getDetailsByIdTrans)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "id_product", column = "id_product"),
            @Result(property = "product_qty", column = "product_qty")
    })
    TransDetails getDetailsById(int id_trans);

    @Select(getTransByLastInsert)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "id_seller", column = "id_seller"),
            @Result(property = "total_weight", column = "total_weight"),
            @Result(property = "shipment_fee", column = "shipment_fee"),
            @Result(property = "total_cost", column = "total_cost"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "trans_status", column = "trans_status"),
            @Result(property = "orderTrackingNumber", column = "orderTrackingNumber"),
            @Result(property = "orderRequest", column = "orderRequest"),
            @Result(property = "orderReceived", column = "orderReceived"),
            @Result(property = "orderShipped", column = "orderShipped"),
            @Result(property = "orderArrived", column = "orderArrived"),
            @Result(property = "orderConfirmed", column = "orderConfirmed")
    })
    Transaction getLastTrans();


    @Select(getByID)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "id_seller", column = "id_seller"),
            @Result(property = "total_weight", column = "total_weight"),
            @Result(property = "shipment_fee", column = "shipment_fee"),
            @Result(property = "total_cost", column = "total_cost"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "trans_status", column = "trans_status"),
            @Result(property = "orderTrackingNumber", column = "orderTrackingNumber"),
            @Result(property = "orderRequest", column = "orderRequest"),
            @Result(property = "orderReceived", column = "orderReceived"),
            @Result(property = "orderShipped", column = "orderShipped"),
            @Result(property = "orderArrived", column = "orderArrived"),
            @Result(property = "orderConfirmed", column = "orderConfirmed")
    })
    Transaction getById(int id_trans);

    @Select(getByIDUser)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "id_seller", column = "id_seller"),
            @Result(property = "total_weight", column = "total_weight"),
            @Result(property = "shipment_fee", column = "shipment_fee"),
            @Result(property = "total_cost", column = "total_cost"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "trans_status", column = "trans_status"),
            @Result(property = "orderTrackingNumber", column = "orderTrackingNumber"),
            @Result(property = "orderRequest", column = "orderRequest"),
            @Result(property = "orderReceived", column = "orderReceived"),
            @Result(property = "orderShipped", column = "orderShipped"),
            @Result(property = "orderArrived", column = "orderArrived"),
            @Result(property = "orderConfirmed", column = "orderConfirmed")
    })
    ArrayList<Transaction> getByIdUser(int id_user);

    @Select(getByIDSeller)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "id_seller", column = "id_seller"),
            @Result(property = "total_weight", column = "total_weight"),
            @Result(property = "shipment_fee", column = "shipment_fee"),
            @Result(property = "total_cost", column = "total_cost"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "trans_status", column = "trans_status"),
            @Result(property = "orderTrackingNumber", column = "orderTrackingNumber"),
            @Result(property = "orderRequest", column = "orderRequest"),
            @Result(property = "orderReceived", column = "orderReceived"),
            @Result(property = "orderShipped", column = "orderShipped"),
            @Result(property = "orderArrived", column = "orderArrived"),
            @Result(property = "orderConfirmed", column = "orderConfirmed")
    })
    ArrayList<Transaction> getByIdSeller(int id_seller);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id_user")
    void inputTrans(Transaction transaction);

    @Insert(insertDetails)
    @Options(useGeneratedKeys = true, keyProperty = "id_trans")
    void inputTransDetails(TransDetails transDetails);


}
