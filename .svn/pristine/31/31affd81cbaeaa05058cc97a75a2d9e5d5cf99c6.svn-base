package com.wanmi.sbc.common.util;

import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by pingchen on 15/12/29.
 */
public final class StringUtil {

    public static final String SQL_LIKE_CHAR = "%";

    public static final String ES_LIKE_CHAR = "*";

    private static final Pattern CH_PATTERN = Pattern.compile("([\u4E00-\u9FA5]{2,})");

    private static final Pattern EN_PATTERN = Pattern.compile("([A-Za-z]{2,})");

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 特殊字符转换
     */
    private static final Map<String, String> SPECIAL_CHAR_MAP = new HashMap<String, String>() {
        {
            put("<", "＜");
            put(">", "＞");
            put("\\", "＼");
            put("|", "｜");
            put("*", "＊");
            put("?", "？");
            put("/", "／");
            put(":", "：");
            put(".", "．");
            put("%", "％");
            put("=", "＝");
            put("&", "＆");
            put("'", "＇");
            put("\"", "＂");
            put("+", "＋");
            put("-", "－");
            put("#", "＃");
        }
    };

    /**
     * 对象转换类型
     * @param object 值
     * @param clazz 对象
     * @return 返回值，否则为null
     */
    public static <T> T cast(Object object, Class<T> clazz){
        if(object != null && clazz.isInstance(object)) {
            return clazz.cast(object);
        }
        return null;
    }

    /**
     * 数组转换类型
     * @param objects 对象数组
     * @param index 索引
     * @param clazz 类型
     * @return 返回值，否则为null
     */
    public static <T> T cast(Object[] objects, int index , Class<T> clazz){
        return cast(objects[index], clazz);
    }

    public static String trunc(String str, int len, String afterPrefix) {
        if (StringUtils.isNotBlank(str) && str.length() > len) {
            return str.substring(0, len).concat(afterPrefix);
        }
        return str;
    }

    /**
     * 提取中文
     * @param str
     * @return
     */
    public static List<String> pickChs(String str) {
        List<String> strList = new ArrayList<>();
        //使用正则表达式
        Matcher matcher = CH_PATTERN.matcher(str);
        while (matcher.find()) {
            strList.add(matcher.group());
        }
        return strList;
    }

    /**
     * 提取英文
     * @param str
     * @return
     */
    public static List<String> pickEng(String str) {
        List<String> strList = new ArrayList<>();
        //使用正则表达式
        Matcher matcher = EN_PATTERN.matcher(str);
        while (matcher.find()) {
            strList.add(matcher.group());
        }
        return strList;
    }

    /**
     * 去除首尾指定字符
     * @param str   字符串
     * @param element   指定字符
     * @return
     */
    public static String trimLast(String str, String element, String defaultString){
        int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
        return str;
    }

    public static String generateNonceStr(int length) {
        char[] nonceChars = new char[length];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String convertSpecialChar(String str) {
        if(StringUtils.isBlank(str)) {
            return str;
        }
        String tmp = str;
        Set<String> set = SPECIAL_CHAR_MAP.keySet();
        for (String key : set) {
            tmp = tmp.replace(key, SPECIAL_CHAR_MAP.get(key));
        }
        return tmp;
    }
}
