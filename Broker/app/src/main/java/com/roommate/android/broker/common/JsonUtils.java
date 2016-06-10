package com.roommate.android.broker.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by WinXP on 16-6-10.
 */
public class JsonUtils {

    private static final String RESULTSTR = "code";

    private static final String MESSAGE = "message";

    private static final String DATA = "data";

    public static final Integer CODE_OK = 1;             //成功
    public static final Integer CODE_FAILURE = 0;        //失败


    /**
     * 判断返回是否成功
     * @param result
     * @return
     */
    public static boolean isResultSuccess(String result){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(result);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int code = jsonObject.get(RESULTSTR).getAsInt();

        if(code == CODE_OK){
            return true;
        }
        return false;
    }

    /**
     * 获取返回信息
     * @param result
     * @return
     */
    public static String resultMsg(String result){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(result);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String msg = jsonObject.get(MESSAGE).getAsString();
        return msg;
    }

    /**
     * 获取数据
     * @param result
     * @return
     */

    public static JsonObject getData(String result){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(result);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject data = jsonObject.get(DATA).getAsJsonObject();
        return data;
    }
}
