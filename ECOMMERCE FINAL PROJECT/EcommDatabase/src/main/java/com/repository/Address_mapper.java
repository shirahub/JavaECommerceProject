package com.repository;

import com.model.Address;
import com.model.Seller;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface Address_mapper {

    final String insert = "INSERT INTO address (id_user, address_name, receiver_name, phone_number, address, province, zipcode) VALUES (#{id_user}, #{address_name}, #{receiver_name}, #{phone_number}, #{address}, #{province}, #{zipcode})";
    final String update = "UPDATE address SET address_name = #{address_name}, receiver_name = #{receiver_name}, phone_number = #{phone_number}, address = #{address}, province = #{province}, zipcode = #{zipcode} WHERE id_address = #{id_address} AND id_user = #{id_user}";
    final String getByID = "SELECT * FROM address WHERE id_user = #{id_user}";
    final String getByIDAddress = "SELECT * FROM address WHERE id_address = #{id_address}";
    final String getByIDUserAddress = "SELECT * FROM address WHERE id_address = #{id_address} AND id_user = #{id_user}";
    final String getByName = "SELECT * FROM address WHERE address_name = #{address_name} AND id_user = #{id_user}";
    final String deleteByID = "DELETE from address WHERE id_address = #{id_address} AND id_user = #{id_user}";



    @Select(getByID)
    @Results(value = {
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "address_name", column = "address_name"),
            @Result(property = "receiver_name", column = "receiver_name"),
            @Result(property = "phone_number", column = "phone_number"),
            @Result(property = "address", column = "address"),
            @Result(property = "province", column = "province"),
            @Result(property = "zipcode", column = "zipcode"),
            @Result(property = "id_address", column = "id_address")
    })
    ArrayList<Address> getById(int id_user);

    @Select(getByName)
    @Results(value = {
            @Result(property = "address_name", column = "address_name"),
            @Result(property = "receiver_name", column = "receiver_name"),
            @Result(property = "phone_number", column = "phone_number"),
            @Result(property = "address", column = "address"),
            @Result(property = "province", column = "province"),
            @Result(property = "zipcode", column = "zipcode")
    })
    Address getByName(Address address);

    @Select(getByIDAddress)
    @Results(value = {
            @Result(property = "address_name", column = "address_name"),
            @Result(property = "receiver_name", column = "receiver_name"),
            @Result(property = "phone_number", column = "phone_number"),
            @Result(property = "address", column = "address"),
            @Result(property = "province", column = "province"),
            @Result(property = "zipcode", column = "zipcode")
    })
    Address getByIdAddress(Address address);

    @Select(getByIDUserAddress)
    @Results(value = {
            @Result(property = "address_name", column = "address_name"),
            @Result(property = "receiver_name", column = "receiver_name"),
            @Result(property = "phone_number", column = "phone_number"),
            @Result(property = "address", column = "address"),
            @Result(property = "province", column = "province"),
            @Result(property = "zipcode", column = "zipcode")
    })
    Address getByIDUserAddress(Address address);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id_user")
    void inputAddress(Address address);

    @Update(update)
    void updateAddress(Address address);

    @Delete(deleteByID)
    void deleteAddress(Address address);

}
