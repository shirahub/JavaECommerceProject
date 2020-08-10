package com.repository;

import com.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface Product_mapper {

    final String insert = "INSERT INTO product (id_seller, id_product, product_name, product_category, product_desc, product_quantity, product_price, product_weight) VALUES (#{id_seller}, #{id_product}, #{product_name}, #{product_category}, #{product_desc}, #{product_quantity}, #{product_price}, #{product_weight})";
    final String update = "UPDATE product SET product_category = #{product_category}, product_desc = #{product_desc}, product_quantity = #{product_quantity}, product_price = #{product_price}, product_weight = #{product_weight} WHERE product_name = #{product_name} AND id_seller = #{id_seller}";
    final String getByID = "SELECT * FROM product WHERE id_seller = #{id_seller}";
    final String getByName = "SELECT * FROM product WHERE product_name = #{product_name}";
    final String getByIDProduct = "SELECT * FROM product WHERE id_product = #{id_product}";
    final String deleteByName = "DELETE from product WHERE product_name = #{product_name} AND id_seller = #{id_seller}";
    final String getByCategory = "SELECT * FROM product WHERE product_category = #{product_category}";
    //TODO search by name and price

    @Select(getByID)
    @Results(value = {
            @Result(property = "id_seller", column = "id_seller"),
            @Result(property = "id_product", column = "id_product"),
            @Result(property = "product_name", column = "product_name"),
            @Result(property = "product_category", column = "product_category"),
            @Result(property = "product_desc", column = "product_desc"),
            @Result(property = "product_quantity", column = "product_quantity"),
            @Result(property = "product_price", column = "product_price"),
            @Result(property = "product_weight", column = "product_weight")
    })
    ArrayList<Product> getById(int id_seller);

    @Select(getByName)
    @Results(value = {
            @Result(property = "id_seller", column = "id_seller"),
            @Result(property = "id_product", column = "id_product"),
            @Result(property = "product_name", column = "product_name"),
    })
    Product getByName(String product_name);

    @Select(getByIDProduct)
    @Results(value = {
            @Result(property = "id_seller", column = "id_seller"),
            @Result(property = "id_product", column = "id_product"),
            @Result(property = "product_name", column = "product_name"),
    })
    Product getByIdProduct(int id_product);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id_seller")
    void inputProduct(Product product);

    @Update(update)
    void updateProduct(Product product);

    @Delete(deleteByName)
    void deleteProduct(Product product);

}
