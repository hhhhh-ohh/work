package com.wanmi.sbc.dbreplay.utils;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.capture.MongoAutoGenerateIdInfo;
import com.wanmi.sbc.dbreplay.bean.capture.MongoManualGenerateIdInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2019-08-19 16:44
 */
public class JsonUtil {

    /**
     * 处理特殊字符
     * @param s
     * @return
     */
    public static String deal$(String s){
        return s.replace("$oid", "oid")
                .replace("$date","date")
                .replace("Invalid date", "");
    }

    /**
     * 处理更新特殊字符
     * @param s
     * @return
     */
    public static String dealUpdate$(String s){
        //通过shell脚本执行更新，外层会有$set嵌套,需要剥离
        if(s.contains("$set")){
            int index = StringUtils.ordinalIndexOf(s, "{", 2);
            return deal$(s.substring(index, s.length()-1));
        }else {
            return deal$(s);
        }

    }

    public static String delOutField(String source,String delStr){
        if(source.contains(delStr)) {
            int index = StringUtils.ordinalIndexOf(source, "{", 2);
            return source.substring(index, source.length()-1);
        }
        return source;
    }

    /**
     * 解析手动生成的id字段
     * @param s
     * @return
     */
    public static MongoManualGenerateIdInfo parseManualId(String s){
        return JSONObject.parseObject(deal$(s), MongoManualGenerateIdInfo.class);
    }

    /**
     * 解析自动生成的id字段
     * @param s
     * @return
     */
    public static MongoAutoGenerateIdInfo parseAutoId(String s){
        return JSONObject.parseObject(deal$(s), MongoAutoGenerateIdInfo.class);
    }

    /**
     *获取多层json的值
     * @Author zgl
     * @param jsonObject
     * @param path
     * @return key对应的值
     */
    public static <T> T getValue(JSONObject jsonObject, String path){
        if(jsonObject.containsKey(path)){       //该步骤为了oplog更新时$set类型可以直接是多级路径作为key如
                                                // （"info.a":"abc",而不是"info":{"a":"abc"}，如果是以上类型则表示替换info整个对象）,
            return (T) jsonObject.get(path);
        }
        String[] keyWord = path.split("\\.");
        for (int i = 0; i < keyWord.length; i++) {
            if(i == keyWord.length-1){
                return (T) jsonObject.get(keyWord[i]);
            }
            jsonObject = (JSONObject) jsonObject.get(keyWord[i]);
        }
        return null;
    }

    /**
     * 判断多层json的key是否存在
     * @Author zgl
     * @param jsonObject
     * @param path
     * @return
     */
    public static boolean containsKey(JSONObject jsonObject, String path){
        if(jsonObject.containsKey(path)){       //该步骤为了oplog更新时$set类型可以直接是多级路径作为key如
                                                // （"info.a":"abc",而不是"info":{"a":"abc"}，如果是以上类型则表示替换info整个对象）,
            return true;
        }
        String[] keyWord = path.split("\\.");
        for (int i = 0; i < keyWord.length; i++) {
            if(!jsonObject.containsKey(keyWord[i])){
                return false;
            }
            if(i == keyWord.length-1){
                return true;
            }
            jsonObject = (JSONObject) jsonObject.get(keyWord[i]);
        }
        return true;
    }
}
