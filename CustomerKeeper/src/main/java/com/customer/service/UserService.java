package com.customer.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.customer.dao.UserMapper;
import com.customer.model.User;

import org.apache.commons.lang3.time.DateUtils;
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
        Date date = new Date();
       // SimpleDateFormat sdf = new SimpleDateFormat();
        user.setRegTime(date);
        int insert = userMapper.insert(user);
        System.out.println(user);
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
    
    public User login(User user){
    	User u = userMapper.selectLogin(user);
    	return u;
    }
    
    public User selectByMobile(String mobile){
    	User u = userMapper.selectByMobile(mobile);
    	return u;
    }

}
