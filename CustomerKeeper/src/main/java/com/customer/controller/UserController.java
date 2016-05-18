package com.customer.controller;

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
    public JsonResult addUser(HttpServletRequest request,@RequestBody List<User> users) throws Exception {
        logger.info("调用adduser开始");

//        JsonResult result = new JsonResult();
        User user = new User();
        int i = userService.add(user);
        if(i!=1){
            return new JsonResult(0,"",user,"");
        }
        return new JsonResult(1,"",user,"");
    }


    @RequestMapping(value="/updateUser")
    @ResponseBody
    public JsonResult updateUser(HttpServletRequest request, Model model) throws Exception {

        return null;


    }

}
