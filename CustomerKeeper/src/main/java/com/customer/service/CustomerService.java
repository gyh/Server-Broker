package com.customer.service;

import com.alibaba.fastjson.JSONObject;
import com.customer.dao.CustomerMapper;
import com.customer.dao.HouseMapper;
import com.customer.model.Customer;
import com.customer.model.House;
import com.customer.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangpeng on 16/5/17.
 */
@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    public CustomerMapper customerMapper;
    public int add(Customer customer){
        logger.info("调用AddCustomer接口开始");
        int result = customerMapper.insert(customer);
        if(result!=1){
            logger.info("调用AddCustomer接口错误");
            return 0;
        }
//        JsonResult jsonResult =  new JsonResult(0,"successfully",customer,"");
        logger.info("调用AddCustomer接口完成，result={}", result);
        return result;

    }

    public int deleteById(Customer customer){
        logger.info("调用deleteCustomer接口开始");
        Long id = customer.getUserId();
        if(id==0){
            logger.info("调用deleteCustomer接口错误,customerId错误");
            return 0;
        }
        int result = customerMapper.deleteByPrimaryKey(id);
        if(result!=1){
            logger.info("调用AddCustomer接口错误");
            return 0;
        }
//        JsonResult jsonResult =  new JsonResult(0,"successfully",customer,"");
        logger.info("调用AddCustomer接口完成，result={}", result);
        return result;

    }
    public int update(Customer customer){
        logger.info("调用deleteCustomer接口开始");
        int result = customerMapper.updateByPrimaryKeySelective(customer);
        if(result!=1){
            logger.info("调用AddCustomer接口错误");
            return 0;
        }
//        JsonResult jsonResult =  new JsonResult(0,"successfully",customer,"");
        logger.info("调用AddCustomer接口完成，result={}", result);
        return result;

    }



    public JsonResult findCustomerByUserId(String userId){
        if(StringUtils.isEmpty(userId)){
            return null;
        }
        List<Customer> customers = customerMapper.selectByUserId(Long.parseLong(userId));

        return new JsonResult(1,"successfully",JSONObject.toJSON(customers),"");

    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

}
