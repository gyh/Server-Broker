package com.customer.service;

import com.alibaba.fastjson.JSONObject;
import com.customer.dao.UserMapper;
import com.customer.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangpeng on 16/5/17.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserMapper userMapper;

    public int add(User user) {
        logger.info("调用addUser开始");
        int insert = userMapper.insert(user);
        logger.info("调用addUser完成");
        return insert;

    }

    public User findUserById(Long id){
        logger.info("调用findUserById开始");
        User user = userMapper.selectByPrimaryKey(id);
        logger.info("调用findUserById完成，result={}", JSONObject.toJSON(user));
        return user;
    }

    public int updateUserBycondition(User user){
        logger.info("调用updateUserBycondition开始");
        int i = userMapper.updateByPrimaryKeySelective(user);
        logger.info("调用updateUserBycondition结束");
        return i;
    }


}
