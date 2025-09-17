package com.wanmi.sbc.empower.util;

import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.SpringContextHolder;
import com.wanmi.sbc.empower.api.response.vop.base.VopBaseResponse;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author zhengyang
 * @className VopHttpUtils
 * @description VopHttp请求封装
 * @date 2021/5/10 14:11
 **/
public class VopHttpUtils {

    /***
     * Redis工具类
     */
    private static RedisUtil redisUtil = SpringContextHolder.getBean(RedisUtil.class);

    /***
     * 请求指定的URL并返回一个指定类型的返回值(请求参数默认带Token)
     * @param url
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> VopBaseResponse<T> postDataWithToken(String url, Object obj, Class<T> clazz) {
        return VopResponseUtil.convertResult(postDataWithToken(url, obj), clazz);
    }

    /***
     * 请求指定的URL并返回一个指定类型的List返回值(请求参数默认带Token)
     * @param url
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> VopBaseResponse<List<T>> postListWithToken(String url, Object obj, Class<T> clazz) {
        return VopResponseUtil.convertListResult(postDataWithToken(url, obj), clazz);
    }

    /***
     * 请求指定的URL并返回一个指定类型的List返回值(请求参数默认带Token)
     * @param url
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> VopBaseResponse<List<T>> postArrayWithToken(String url, Object obj, Class<T> clazz) {
        return VopResponseUtil.convertArrayResult(postDataWithToken(url, obj), clazz);
    }

    /***
     * 请求指定的URL并返回一个指定类型的返回值
     * @param url
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> VopBaseResponse<T> postDataWithOutToken(String url,
                                                            Object obj, Class<T> clazz) {
        return VopResponseUtil.convertResult(HttpUtils.postData(url, obj), clazz);
    }

    /***
     * 请求指定的URL并返回Response字符(请求参数默认带Token)
     * @param url
     * @param obj
     * @return
     */
    public static String postDataWithToken(String url, Object obj) {
        String token = redisUtil.getString(CacheKeyConstant.JDVOP_ACCESS_TOKEN);
        // 取得Token并设置
        if (Objects.nonNull(obj)) {
            Field tokenField = ReflectionUtils.findField(obj.getClass(), "token");
            if (Objects.nonNull(tokenField)) {
                ReflectionUtils.makeAccessible(tokenField);
                ReflectionUtils.setField(tokenField, obj, token);
            }
        }
        return HttpUtils.postData(url, obj);
    }
}
