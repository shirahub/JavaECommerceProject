package com.Merchant;

import com.Bank.Bank;
import org.apache.ibatis.annotations.*;

public interface Merchant_Repo {
    final String input = "INSERT INTO merchant (id_trans, nominal, status) VALUES (#{id_trans}, #{nominal}, #{status})";
    final String getByID = "SELECT * FROM merchant WHERE id_trans = #{id_trans}";

    @Insert(input)
    @Options(useGeneratedKeys = true, keyProperty = "id_trans")
    void inputToMerchantDB(Merchant merchant);

    @Select(getByID)
    @Results(value = {
            @Result(property = "id_trans", column = "id_trans"),
            @Result(property = "nominal", column = "nominal"),
            @Result(property = "status", column = "status")
    })
    Merchant getById(int id_trans);
}
