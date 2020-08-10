package com.repository;

import com.model.Seller;
import com.model.User;
import org.apache.ibatis.annotations.*;

public interface Seller_mapper {

    final String insert = "INSERT INTO seller (id_user, id_seller, shop_name, shop_location, shop_rating, shop_products, shop_open, shop_status, balance) VALUES (#{id_user}, #{id_seller}, #{shop_name}, #{shop_location}, #{shop_rating}, #{shop_products}, #{shop_open}, #{shop_status}, #{balance})";
    final String updateLocation = "UPDATE seller SET shop_location = #{shop_location} WHERE shop_name = #{shop_name}";
    final String updateProduct = "UPDATE seller SET shop_products = shop_products+1 WHERE id_seller = #{id_seller}";
    final String updateProductDelete = "UPDATE seller SET shop_products = shop_products-1 WHERE id_seller = #{id_seller}";
    final String closeShop = "UPDATE seller SET shop_status = false WHERE shop_name = #{shop_name}";
    final String openShop = "UPDATE seller SET shop_status = true WHERE shop_name = #{shop_name}";
    final String getByName = "SELECT * FROM seller WHERE shop_name = #{shop_name}";
    final String getById = "SELECT * FROM seller WHERE id_user = #{id_user}";
    final String getByIdSeller = "SELECT * FROM seller WHERE id_seller = #{id_seller}";
    final String updateBalance = "UPDATE seller SET balance = #{balance}, shop_rating = #{shop_rating} WHERE id_seller = #{id_seller}";


    @Select(getByName)
    @Results(value = {
            @Result(property = "shop_name", column = "shop_name"),
            @Result(property = "shop_location", column = "shop_location"),
            @Result(property = "shop_rating", column = "shop_rating"),
            @Result(property = "shop_products", column = "shop_products"),
            @Result(property = "shop_open", column = "shop_open"),
            @Result(property = "shop_status", column = "shop_status"),
            @Result(property = "balance", column = "balance")
    })
    Seller getByName(String shop_name);

    @Select(getById)
    @Results(value = {
            @Result(property = "shop_name", column = "shop_name"),
            @Result(property = "shop_location", column = "shop_location"),
            @Result(property = "shop_rating", column = "shop_rating"),
            @Result(property = "shop_products", column = "shop_products"),
            @Result(property = "shop_open", column = "shop_open"),
            @Result(property = "shop_status", column = "shop_status"),
            @Result(property = "balance", column = "balance")
    })
    Seller getById(int id_user);

    @Select(getByIdSeller)
    @Results(value = {
            @Result(property = "shop_name", column = "shop_name"),
            @Result(property = "shop_location", column = "shop_location"),
            @Result(property = "shop_rating", column = "shop_rating"),
            @Result(property = "shop_products", column = "shop_products"),
            @Result(property = "shop_open", column = "shop_open"),
            @Result(property = "shop_status", column = "shop_status"),
            @Result(property = "balance", column = "balance")
    })
    Seller getByIdSeller(int id_seller);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id_user")
    void makeShop(Seller seller);

    @Update(updateLocation)
    void updateLocation(Seller seller);

    @Update(updateBalance)
    void updateBalance(Seller seller);

    @Update(updateProduct)
    void updateProduct(Seller seller);

    @Update(updateProductDelete)
    void UpdateProductDelete(Seller seller);

    @Update(closeShop)
    void closeShop(Seller seller);

    @Update(openShop)
    void openShop(Seller seller);


}
