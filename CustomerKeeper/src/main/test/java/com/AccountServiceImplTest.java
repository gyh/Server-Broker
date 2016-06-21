package com;

import com.alibaba.fastjson.JSONObject;
import com.customer.model.Customer;
import com.customer.model.House;
import com.customer.model.User;
import com.customer.service.CustomerService;
import com.customer.service.HouseService;
import com.customer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangpeng on 16/5/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-applicationContext.xml" })
public class AccountServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    HouseService houseService;
    @Autowired
    CustomerService customerService;

    @Test
    public void loadXmL(){
        System.out.println("==================测试开始==================");
        System.out.println("==================================================");
        System.out.println("==================================================");
        System.out.println("==================================================");
//        User user = new User();
//        user.setMobile("13121345061");
//        user.setId(2l);
//        user.setUsername("wangzh");
//        user.setPasswd("123456");
//        userService.add(user);
    //    houseService.pagefindhouseByUserId(10L,1,3);
//        Account account = accountService.getAccountById(10);
//        System.out.println(JSONObject.toJSONString(account));
//
//        Account account2 = accountService.getAccountByUserId(101);
//        System.out.println(JSONObject.toJSONString(account2));
//
      //    int num = accountService.addAccount(102);
//        System.out.println("result======"+num);
//        List<AccountLog> ss = accountService.getAccountLogItemsByUserId(0, 1, 2);
////        accountLog.setUserId(101L);
////        accountLog.setTradeCode("ZC10000");
//        AccountLog accountLogByCondition = accountService.getAccountLogByCondition(accountLog);
//
        User user = new User();
        user.setId(2L);
        user.setMobile("13121345061");
        user.setPasswd("111111");
//        int recharge = userService.updateUserBycondition(user)
        int add = userService.add(user);

//        System.out.println("=========i=="+recharge);
        System.out.println("result======"+ JSONObject.toJSON(user));
        System.out.println("===================测试完成==============================="+add);

    }
}