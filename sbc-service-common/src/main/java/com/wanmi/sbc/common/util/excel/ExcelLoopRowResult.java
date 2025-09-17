package com.wanmi.sbc.common.util.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * 循环单行返回值
 * @className ExcelLoopRowResult
 * @author zhengyang
 * @date 2021/8/2 17:11
 **/
@Data
@AllArgsConstructor
public class ExcelLoopRowResult<T> {

    /***
     * 单行返回值，isError为true的时候为空
     */
    private T result;

    /**
     * 单行是否校验成功
     */
    private boolean isError;

    /***
     * 创建一个返回值对象
     * @param isError   本行是否错误
     * @param result    返回值
     * @param <T>       泛型
     * @return          返回值
     */
    public static <T> ExcelLoopRowResult build(boolean isError, T result){
        return new ExcelLoopRowResult(result, isError);
    }
}
