package com.ant.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangpeng-ds3 on 2016/3/29.
 */
@Controller
public class RegisterController {
    @RequestMapping(value = "/ck/login.jsp", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String login(@RequestParam(value = "username", required = false)String username,
                      @RequestParam(value = "password", required = false)String password) throws Exception {
        return "Hello "+username+",Your password is: "+password;
    }
}
