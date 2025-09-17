package com.wanmi.ares.utils;

import java.util.regex.Pattern;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-9-24
 * \* Time: 14:44
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class CommUtils {

    private static final Pattern PATTERN = Pattern.compile("-?[0-9,]*");

    private CommUtils() {

    }

    //请求参数校验【数字+ ','】
    public static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim())) {
            return PATTERN.matcher(s).matches();
        } else {
            return false;
        }
    }
}
