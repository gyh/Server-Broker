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
        
     //  User u = (User) JSONUtils.parse(users);
     // User u = (User) JSONUtils.parse(str);
        
  
        return new JsonResult(1,"",u,"");
       // return null;
    }


    @RequestMapping(value="/updateUser")
    @ResponseBody
    public JsonResult updateUser(HttpServletRequest request,@RequestBody String users) throws Exception {
    	 User u =  JSON.parseObject( URLDecoder.decode(users).replace("Data=", ""), User.class);
    	int i = userService.updateUserBycondition(u);
    	 if(i>0)
          	return new JsonResult(1,"修改成功",u,"");
          else
          	return new JsonResult(0,"修改失败，请重新修改",u,"");


    }
    @RequestMapping(value="/register")
    @ResponseBody
    public JsonResult updateReg(HttpServletRequest request,@RequestBody String users) throws Exception {
        
         String str =  URLDecoder.decode(users).replace("Data=", "");
         
         User u =  JSON.parseObject(str, User.class);
         int i = 0;
         User user = userService.selectByMobile(u.getMobile());
        if( user == null){
        	i =  userService.add(u);
        	 if(i>0)
             	return new JsonResult(1,"注册成功",u,"");
             else
             	return new JsonResult(0,"注册失败，请重新注册",u,"");
        }else{
        	return new JsonResult(0,"该手机号已被注册",u,"");
        }
        // userService.add(u);
     
       
        //return null;


    }
    @RequestMapping(value="/login")
    @ResponseBody
    public JsonResult login(HttpServletRequest request,@RequestBody String users) throws Exception {
       
       //  String str = ;
       User u = userService.login( JSON.parseObject( URLDecoder.decode(users).replace("Data=", ""), User.class));
        if(u != null)
        	return new JsonResult(1,"登陆成功",u,"");
        else
        	return new JsonResult(0,"用户名或密码错误",u,"");
        //return null;


    }
    
}
