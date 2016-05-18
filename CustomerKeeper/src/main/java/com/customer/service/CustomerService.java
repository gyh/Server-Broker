package com.customer.service;

import com.alibaba.fastjson.JSONObject;
import com.customer.dao.CustomerMapper;
import com.customer.dao.HouseMapper;
import com.customer.model.Customer;
import com.customer.model.House;
import com.customer.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangpeng on 16/5/17.
 */
@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    public CustomerMapper customerMapper;
    public JsonResult add(Customer customer){
        logger.info("调用AddHouse接口开始");
        int i = customerMapper.insert(customer);
        if(i!=1){
            logger.info("调用AddHouse接口错误");
            return new JsonResult(0,"error",null,"");
        }
        JsonResult jsonResult =  new JsonResult(0,"successfully",customer,"");
        logger.info("调用AddHouse接口完成，result={}", JSONObject.toJSON(jsonResult));
        return jsonResult;

    }




}
