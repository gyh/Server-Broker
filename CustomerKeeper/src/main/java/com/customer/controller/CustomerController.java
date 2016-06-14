package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.customer.common.enums.OptTypsEnum;
import com.customer.model.Customer;
import com.customer.model.User;
import com.customer.service.CustomerService;
import com.customer.service.UserService;
import com.customer.utils.InputDataVO;
import com.customer.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * Created by wangpeng on 16/5/17.
 */

/**
 * 客户控制器
 */
@Controller
@RequestMapping("/cutomer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private UserService userService;

    private CustomerService customerService;


    @RequestMapping(value="/synDada")
    @ResponseBody
    public JsonResult synData(HttpServletRequest request,@RequestBody String reqData) throws Exception {
        logger.info("同步数据开始!请求参数={}",reqData);
        //解析json  返回用户数据
        InputDataVO inputVO = JSON.parseObject( URLDecoder.decode(reqData).replace("Data=", ""), InputDataVO.class);

        //根据类型不同，进行不同操作
        int result = toSynDatda(inputVO);

        return new JsonResult(result,"",null,null);
    }

    private int toSynDatda(InputDataVO inputVO) {

        String optType = inputVO.getOptType();
        String optObj = inputVO.getOptObject();

        Customer customer = JSON.parseObject(inputVO.getOptData(), Customer.class);
        if(null == customer){
            logger.info("Json中的操作数据转化为customer对象错误");
            return 0;
        }

        //添加
        if(OptTypsEnum.ADD.getKey().equals(optType)){

            return customerService.add(customer);


            //删除
        }else if(OptTypsEnum.DEL.getKey().equals(optType)){

            return customerService.deleteById(customer);



            //修改
        }else if(OptTypsEnum.UPDATE.getKey().equals(optType)){

            return customerService.update(customer);

        }else {

            return 0;
        }



    }


    @RequestMapping(value="/getData")
    @ResponseBody
    public JsonResult getData(HttpServletRequest request,@RequestBody String reqData) throws Exception {
        logger.info("获取数据开始，请求参数={}",reqData);
        //解析json  返回用户数据
        InputDataVO inputVO = JSON.parseObject( URLDecoder.decode(reqData).replace("Data=", ""), InputDataVO.class);

        String userId =inputVO.getUserId();
        if(StringUtils.isEmpty(userId)){
            logger.info("数据入参错误！userid={}",userId);
            return new JsonResult(0,"参数userId为空",null,null);
        }
        return customerService.findCustomerByUserId(userId);
    }


}
