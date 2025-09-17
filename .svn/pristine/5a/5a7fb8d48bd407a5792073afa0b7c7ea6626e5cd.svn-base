package com.wanmi.sbc.common.util;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import java.util.Objects;
import java.util.function.Function;

/**
 * BaseResponse处理的工具类
 *
 * @author zhengyang
 * @date 2021/6/3 16:14
 */
public final class BaseResUtils {
    private BaseResUtils() {}

    /***
     * 从返回值中取得结果值，如果为空则返回NULL
     * @param response      基础返回值
     * @param <T>           泛型
     * @return              返回包装对象
     */
    public static <T> T getContextFromRes(BaseResponse<T> response) {
        if (Objects.nonNull(response) && Objects.nonNull(response.getContext())) {
            return response.getContext();
        }
        return null;
    }

    /***
     * 执行一个Function从Response中取得对象
     * 避免NullPointException
     * @param response  基础返回值
     * @param function  取值Function方法
     * @param <T>       Response输出泛型
     * @param <R>       Function输出泛型
     * @return          Response包装返回值
     */
    public static <T, R> T getResultFromRes(BaseResponse<R> response, Function<R, T> function) {
        if (Objects.nonNull(response) && Objects.nonNull(response.getContext())) {
            return function.apply(response.getContext());
        }
        return null;
    }

    /***
     * 从返回值中取得结果值，如果为空则返回NULL
     * @param response      基础返回值
     * @param errorCodeEnum      错误编码
     * @param <T>           泛型
     * @return              返回包装对象
     */
    public static <T> T getContextFromRes(BaseResponse<T> response, CommonErrorCodeEnum errorCodeEnum) {
        if (Objects.nonNull(response) && Objects.nonNull(response.getContext())) {
            return response.getContext();
        }
        throw new SbcRuntimeException(errorCodeEnum);
    }

    /***
     * 执行一个Function从Response中取得对象
     * 避免NullPointException
     * @param response  基础返回值
     * @param function  取值Function方法
     * @param errorCodeEnum      错误编码
     * @param <T>       Response输出泛型
     * @param <R>       Function输出泛型
     * @return          Response包装返回值
     */
    public static <T, R> T getResultFromRes(BaseResponse<R> response, Function<R, T> function, CommonErrorCodeEnum errorCodeEnum) {
        if (Objects.nonNull(response) && Objects.nonNull(response.getContext())) {
            return function.apply(response.getContext());
        }
        throw new SbcRuntimeException(errorCodeEnum);
    }
}
