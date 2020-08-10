package com.repository;

import com.model.Address;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface Location_mapper {

    final String getByLocation = "SELECT loc_id FROM location WHERE location = #{location}";

    @Select(getByLocation)
    @Results(value = {
            @Result(property = "loc_id", column = "loc_id")
    })
    int getByLocation(String location);
}
