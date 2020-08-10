package com.Emoney;

import com.Bank.Bank;
import org.apache.ibatis.annotations.*;

public interface Emoney_Repo {

    final String getByID = "SELECT * FROM emoney WHERE id_user = #{id_user}";
    final String updateBalance = "UPDATE emoney SET balance = #{balance} WHERE id_user = #{id_user}";


    @Select(getByID)
    @Results(value = {
            @Result(property = "id_user", column = "id_user"),
            @Result(property = "balance", column = "balance")
    })
    Emoney getById(int id_user);

    @Update(updateBalance)
    void updateBalance(Emoney emoney);
}
