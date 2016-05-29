package com.customer.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.customer.model.User;
import com.customer.service.UserService;
import com.customer.utils.JsonResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.net.URLDecoder;
import java.util.List;

/**
 * Created by wangpeng on 16/5/17.
 */

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;



    @RequestMapping(value="/addUser")
    @ResponseBody
    public JsonResult addUser(HttpServletRequest request,@RequestBody String users) throws Exception {
        logger.info("调用adduser开始");

        System.out.println(URLDecoder.decode(users));
        //   User u = (User) JSONObject.parse(users);
       String str =  URLDecoder.decode(users).replace("Data=", "");
        User u =  JSON.parseObject(str, User.class);
     // User u = (User) JSONUtils.parse(str);
        
        System.out.println(u.getUsername() + "&&&&&&&&&&&&&&&" + u.getPassword());

    /* for(User u : users){
    	 System.out.println(u.getUserNmae());
     }*/
//        JsonResult result = new JsonResult();
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        /*int i = userService.add(user);
        if(i!=1){
            return new JsonResult(0,"",user,"");
        }*/
        return new JsonResult(1,"",user,"");
       // return null;
    }


    @RequestMapping(value="/updateUser")
    @ResponseBody
    public JsonResult updateUser(HttpServletRequest request, Model model) throws Exception {
         System.out.println("***************************************************************");
        return null;


    }

}
