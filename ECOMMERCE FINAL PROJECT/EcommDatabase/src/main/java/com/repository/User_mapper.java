package com.repository;

import com.model.User;
import org.apache.ibatis.annotations.*;


public interface User_mapper {

    //final String getById = "SELECT * FROM user WHERE id_user = #{id_user}";
    final String insert = "INSERT INTO user (id_user, user_pwd, user_status, email, full_name, sex, birth_date, phone_number, isSeller, isSessionActive) VALUES (#{id_user}, #{user_pwd}, #{user_status}, #{email}, #{full_name}, #{sex}, #{birth_date}, #{phone_number}, #{isSeller}, #{isSessionActive})";
    final String update = "UPDATE user SET user_pwd = #{user_pwd} WHERE email = #{email}";
    final String login = "UPDATE user SET isSessionActive = true WHERE email = #{email}";
    final String logout = "UPDATE user SET isSessionActive = false WHERE email = #{email}";
    final String accountConfirmation = "UPDATE user SET user_status = true WHERE email = #{email}";
    final String getByEmail = "SELECT * FROM user WHERE email = #{email}";
    final String getByPhone = "SELECT * FROM user WHERE phone_number = #{phone_number}";
    final String becomeSeller ="UPDATE user SET isSeller = true WHERE email = #{email}";

    @Select(getByPhone)
    @Results(value = {
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "email", column = "email"),
            @Result(property = "user_pwd", column = "user_pwd")
    })
    User getByPhone(String phone_number);


    @Select(getByEmail)
    @Results(value = {
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "email", column = "email"),
            @Result(property = "user_pwd", column = "user_pwd")
    })
    User getByEmail(String email);


    @Select(getByEmail)
    @Results(value = {
            @Result(property = "user_pwd", column = "user_pwd"),
            @Result(property = "user_status", column = "user_status"),
            @Result(property = "email", column = "email"),
            @Result(property = "full_name", column = "full_name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "birth_date", column = "birth_date"),
            @Result(property = "phone_number", column = "phone_number")
    })
    User seeProfile(String email);

    @Update(update)
    void update(User user);

    @Update(login)
    void login(User user);

    @Update(logout)
    void logout(User user);

    @Update(accountConfirmation)
    void accountConfirmation(User user);

    @Update(becomeSeller)
    void becomeSeller(User user);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id_user")
    void inputToDB(User user);


}
