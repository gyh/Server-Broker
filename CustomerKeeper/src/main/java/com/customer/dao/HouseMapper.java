package com.customer.dao;

import com.customer.model.House;

import java.util.List;
import java.util.Map;

public interface HouseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(House record);

    int insertSelective(House record);

    House selectByPrimaryKey(Long id);

    List<House> pagefindhouseByUserId(Map<String,Object> map);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);
}