package com.Bank;

import org.apache.ibatis.annotations.*;

public interface Bank_Repo {

    final String input = "INSERT INTO bank (id_trans, nominal, status) VALUES (#{id_trans}, #{nominal}, #{status})";
    final String getByID = "SELECT * FROM bank WHERE id_trans = #{id_trans}";

    @Insert(input)
    @Options(useGeneratedKeys = true, keyProperty = "id_trans")
    void inputToBankDB(Bank bank);

    @Select(getByID)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "nominal", column = "nominal"),
            @Result(property = "status", column = "status")
    })
    Bank getById(int id_trans);

}
