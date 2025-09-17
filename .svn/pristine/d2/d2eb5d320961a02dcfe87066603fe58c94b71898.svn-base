package com.wanmi.sbc.mq.constant;

/**
 * @author zhanggaolei
 * @className RedisConstant
 * @description TODO
 * @date 2021/9/23 3:57 下午
 **/
public class RedisConstant {

    public static final String DELAY_ID = "mq:delayId";

    public static final String DELAY_DATA = "mq:delayData:";

    public static final String DELAY_ZSET = "mq:delayZset:";

    public static final String DELAY_ZSET_IP = DELAY_ZSET+MqConstant.IP;


    public static String getId(String zsetField){
        String[] arr = zsetField.split(":");
        String key = arr[arr.length-1];
        return key;
    }
}
