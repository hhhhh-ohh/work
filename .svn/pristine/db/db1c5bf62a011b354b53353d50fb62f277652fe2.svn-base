package com.wanmi.sbc.empower.util;

import com.alibaba.fastjson2.JSONObject;

/**
 * @className JsonUtils
 * @description JSON工具类
 * @author zhengyang
 * @date 2021/5/6 15:22
 **/
public final class JsonUtils {
    private JsonUtils(){
        
    }

    /***
     * 根据参数创建一个JSON字符串
     * @param key 键
     * @param val 值
     * @return
     */
    public static String buildJsonStr(String key,Object val){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, val);
        return jsonObject.toJSONString();
    }
}
