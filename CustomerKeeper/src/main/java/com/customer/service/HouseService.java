package com.customer.service;

import com.alibaba.fastjson.JSONObject;
import com.customer.dao.HouseMapper;
import com.customer.model.House;
import com.customer.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangpeng on 16/5/17.
 */
@Service
public class HouseService {
    private static final Logger logger = LoggerFactory.getLogger(HouseService.class);
    @Autowired
    public HouseMapper houseMapper;
    public JsonResult add(House house){
        logger.info("调用AddHouse接口开始");
        int i = houseMapper.insert(house);
        if(i!=1){
            logger.info("调用AddHouse接口错误");
            return new JsonResult(0,"error",null,"");
        }
        JsonResult jsonResult =  new JsonResult(0,"successfully",house,"");
        logger.info("调用AddHouse接口完成，result={}", JSONObject.toJSON(jsonResult));
        return jsonResult;
    }

    public JsonResult pagefindhouseByUserId(Long userid,int page,int size){
        logger.info("调用pagefindhouseByUserId接口开始");
        Map map = new HashMap<String,Object>();
        map.put("userId", userid);
        if(page>0&&size>0){
            map.put("page",(page-1)*size);
            map.put("size", size);
        }
        List<House> houses = houseMapper.pagefindhouseByUserId(map);

        if(houses==null){
            logger.info("调用pagefindhouseByUserId接口错误");
            return new JsonResult(0,"error",null,"");
        }
        JsonResult jsonResult =  new JsonResult(0,"successfully",houses,"");
        logger.info("调用pagefindhouseByUserId接口完成，result={}", JSONObject.toJSON(jsonResult));
        return jsonResult;
    }




}
