package com.repository;

import com.model.Cart;
import com.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface Cart_mapper {

    final String insert = "INSERT INTO cart (id_user, id_product, quantity) VALUES (#{id_user}, #{id_product}, #{quantity})";
    final String update = "UPDATE cart SET quantity = #{quantity} WHERE id_user = #{id_user} AND id_product = #{id_product}";
    final String getByID = "SELECT * FROM cart WHERE id_user = #{id_user}";
    final String getByIDProduct = "SELECT * FROM cart WHERE id_user = #{id_user} and id_product = #{id_product}";
    final String deleteByID = "DELETE from cart WHERE id_user = #{id_user}";
    final String deleteByIDProduct = "DELETE from cart WHERE id_user = #{id_user} and id_product = #{id_product}";

    @Select(getByID)
    @Results(value = {
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "id_product", column = "id_product"),
            @Result(property = "quantity", column = "quantity")
    })
    ArrayList<Cart> getById(int id_user);

    @Select(getByIDProduct)
    @Results(value = {
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "id_product", column = "id_product"),
            @Result(property = "quantity", column = "quantity")
    })
    Cart getByIdProduct(Cart cart);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id_user")
    void inputtoCart(Cart cart);

    @Update(update)
    void updateProduct(Cart cart);

    @Delete(deleteByID)
    void deleteCart(int id_user);

    @Delete(deleteByIDProduct)
    void deleteAProduct(Cart cart);


}
