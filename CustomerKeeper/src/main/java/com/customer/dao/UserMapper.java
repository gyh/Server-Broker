package com.customer.dao;

import com.customer.model.User;

public interface UserMapper {
    int insert(User record);

    int deleteByPrimaryKey(Long id);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

//    int updateByPrimaryKey(User record);
}