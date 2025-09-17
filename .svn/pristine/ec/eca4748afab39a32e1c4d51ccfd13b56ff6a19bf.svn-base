package com.wanmi.sbc.common.util;

/**
 * @author xuyunpeng
 * @className PageUtil
 * @description
 * @date 2022/7/18 5:28 PM
 **/
public class PageUtil {
    /**
     * 计算页码
     *
     * @param count
     * @param size
     * @return
     */
    public static long calPage(long count, int size) {
        long page = count / size;
        if (count % size == 0) {
            return page;
        } else {
            return page + 1;
        }
    }
}
