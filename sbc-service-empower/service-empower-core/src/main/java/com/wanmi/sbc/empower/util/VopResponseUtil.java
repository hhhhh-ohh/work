package com.wanmi.sbc.empower.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.empower.api.response.vop.base.VopBaseResponse;
import com.wanmi.sbc.empower.api.response.vop.base.VopOriginListResponse;
import com.wanmi.sbc.empower.api.response.vop.base.VopOriginResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: xufan
 * @Date: 2020/3/7 16:30
 * @Description: 结果转换
 */
@Slf4j
public class VopResponseUtil {

    /**
     * 结果转换方法
     *
     * @param originResult 原始结果
     * @param clazz        类型对象
     * @param <T>          类型
     * @return
     */
    public static <T> VopBaseResponse<T> convertResult(String originResult, Class<T> clazz) {
        VopOriginResponse originResponse = JSONObject.parseObject(originResult, VopOriginResponse.class);
        VopBaseResponse<T> response = new VopBaseResponse<>();
        if (Objects.isNull(originResponse)) {
            response.setSuccess(false);
            return response;
        }
        response.setResultCode(originResponse.getResultCode());
        response.setResultMessage(originResponse.getResultMessage());
        response.setSuccess(originResponse.getSuccess());
        if (Objects.nonNull(originResponse.getResult())) {
            if (originResponse.getResult() instanceof JSON) {
                response.setResult(JSON.toJavaObject((JSON) (originResponse.getResult()), clazz));
            } else if (originResponse.getResult() instanceof String) {
                response.setResult(JSON.parseObject(originResponse.getResult().toString(), clazz));
            } else {
                response.setResult(StringUtil.cast(originResponse.getResult(), clazz));
            }
        }
        return response;
    }


    /**
     * 结果转换方法
     *
     * @param originResult 原始结果
     * @param clazz        类型对象
     * @param <T>          类型
     * @return
     */
    public static <T> VopBaseResponse<List<T>> convertListResult(String originResult, Class<T> clazz) {
        originResult = originResult.replace("\\\"", "")
                .replace("\\\\", "")
                .replace("\\n", "");

        VopOriginListResponse originResponse = JSONObject.parseObject(originResult, VopOriginListResponse.class);
        List<T> result = Objects.nonNull(originResponse.getResult())
                ? originResponse.getResult().toJavaList(clazz) : new ArrayList<>();
        VopBaseResponse<List<T>> response = new VopBaseResponse<>();
        response.setResultCode(originResponse.getResultCode());
        response.setResultMessage(originResponse.getResultMessage());
        response.setSuccess(originResponse.getSuccess());
        response.setResult(result);
        return response;
    }

    /**
     * 结果转换方法
     *
     * @param originResult 原始结果
     * @param clazz        类型对象
     * @param <T>          类型
     * @return
     */
    public static <T> VopBaseResponse<List<T>> convertArrayResult(String originResult, Class<T> clazz) {
        originResult = originResult.replace("\"[", "[")
                .replace("]\"", "]")
                .replace("\\\"", "\"")
                .replace("\\\\", "")
                .replace("\\n", "");

        VopOriginListResponse originResponse = JSONObject.parseObject(originResult, VopOriginListResponse.class);
        List<T> result = Objects.nonNull(originResponse.getResult())
                ? originResponse.getResult().toJavaList(clazz) : new ArrayList<>();
        VopBaseResponse<List<T>> response = new VopBaseResponse<>();
        response.setResultCode(originResponse.getResultCode());
        response.setResultMessage(originResponse.getResultMessage());
        response.setSuccess(originResponse.getSuccess());
        response.setResult(result);
        return response;
    }
}

