package com.wanmi.sbc.common.sensitiveword;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wur
 * @className SensitiveWordsSign
 * @description TODO
 * @date 2022/7/5 13:45
 **/
public class SensitiveWordsSignUtil {

    /**
     * 手机号脱敏 隐藏中间四位 例：131****9988
     * @return
     */
    public static String isPhone(String word) {
        StringBuffer words = new StringBuffer(word);
        if (StringUtils.isBlank(word)) {
            return word;
        }
        if (word.length() > 7) {
            return words.replace(3, 7, "****").toString();
        }
        if (word.length() > 4) {
            return words.replace(3, word.length()-1, "****").toString();
        }
        return words.toString();
    }

    /**
     * 收货地址脱敏  展示前面三位以及后面三位 例子：江苏省******18号
     *
     * @return
     */
    public static String isAddress(String word) {
        StringBuffer words = new StringBuffer(word);
        if (StringUtils.isBlank(word)) {
            return word;
        }
        if (word.length() > 7) {
            return words.replace(3, word.length()-3, "******").toString();
        }
        if (word.length() > 4) {
            return words.replace(3, word.length(), "******").toString();
        }
        return "******";
    }

    /**
     * 姓名、名称脱敏 展示第一位 例子：陈**
     *
     * @return
     */
    public static String isName(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        StringBuffer words = new StringBuffer(word);
        if (word.length() > 1) {
            return words.replace(1, word.length(), "**").toString();
        }
        words.append("**");
        return words.toString();
    }

    /**
     * 身份证号脱敏 保留前三后四
     * @return
     */
    public static String isIdcard(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        StringBuffer words = new StringBuffer(word);
        if (word.length() > 7) {
            return words.replace(3, word.length()-4, "******").toString();
        }
        if(word.length() > 4) {
            return words.replace(3, word.length()-1, "******").toString();
        }
        return words.append("******").toString();
    }

    /**
     * email 脱敏 隐藏@前面三个字符 892370***@qq。com
     *
     * @return
     */
    public static String isEmail(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        StringBuffer words = new StringBuffer(word);
        int index = words.indexOf("@");
        if (index >= 4) {
            return words.replace(index-3, index, "***").toString();
        }
        return words.toString();
    }

    /**
     * 银行卡号脱敏  保留前六后四
     * @return
     */
    public static String isBackNo(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        StringBuffer words = new StringBuffer(word);
        if (word.length() > 10) {
            return words.replace(6, word.length()-4, "*****").toString();
        }
        if (word.length() > 1) {
            return words.replace(1, word.length(), "*****").toString();
        }
        return words.append("******").toString();
    }
}