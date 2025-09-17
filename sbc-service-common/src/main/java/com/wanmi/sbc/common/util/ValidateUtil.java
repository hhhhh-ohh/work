package com.wanmi.sbc.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证器工具类
 * Created by daiyitian on 15/12/29.
 */
public final class ValidateUtil {

    public static final String NULL_EX_MESSAGE = "The validated object [%s] is null";

    public static final String BLANK_EX_MESSAGE = "The validated [%s] is blank";

    public static final String EMPTY_ARRAY_EX_MESSAGE = "The validated array [%s] is empty";

    public static final String EMPTY_COLLECTION_EX_MESSAGE = "The validated collection [%s] is empty";

    public static final String EMPTY_MAP_EX_MESSAGE = "The validated map [%s] is empty";

    public static final String STATE_EX_MESSAGE = "The validated state [%s] is false";

    public static final String ASSIGNABLE_EX_MESSAGE = "Cannot assign %s to %s";

    public static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive" +
            " range of %s to %s";
    public static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive" +
            " range of %s to %s";

    public static final String DATE_RANGE_EX_MESSAGE="The year or month is invalid: %d-%d";

    public static final String DATE_EX_MESSAGE="The date is invalid: %s";

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    //是否超过长度
    public static boolean isOverLen(Object value, int len) {
        return Objects.toString(value, StringUtils.EMPTY).length() > len;
    }

    //是否超过长度区间
    public static boolean isBetweenLen(Object value, int minLen, int maxLen) {
        int l = Objects.toString(value, StringUtils.EMPTY).length();
        return l >= minLen && l <= maxLen;
    }

    //是否是中文或英文
    public static boolean isChsEng(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("[a-zA-Z\\u4E00-\\u9FA5]+");
    }

    //是否是中文或英文、数字
    public static boolean isChsEngNum(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("[0-9a-zA-Z\\u4E00-\\u9FA5]+");
    }

    //是否是中文或英文、数字、浮点数
    public static boolean isChsEngFlt(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("([0-9a-zA-Z\\u4E00-\\u9FA5]|(\\d+(\\.\\d+)?))+");
    }

    //是否是英文、数字、特殊数字
    public static boolean isNotChs(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("[^\\u4E00-\\u9FA5]+");
    }

    //是否是数字、浮点数
    public static boolean isNumFlt(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("(\\d+(\\.\\d+)?)+");
    }

    //是否是浮点数(整数位最多10位，小数位最多2位)
    public static boolean isFloat(String value){
        return Objects.toString(value, StringUtils.EMPTY).matches("\\d{1,10}(\\.\\d{1,2})?");
    }

    //是否是数字
    public static boolean isNum(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("^[1-9][0-9]*$|0");
    }

    //是否是数字字符串
    public static boolean isStringNum(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("^[0-9][0-9]*$|0");
    }

    //是否是数字、英文
    public static boolean isNumAndEng(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("^[A-Za-z0-9]+");
    }

    //是否是保留两位小数的浮点数
    public static boolean isFloatNum(String value) {
        return Objects.toString(value, StringUtils.EMPTY).matches("^(([1-9]+)|([0-9]+\\.[0-9]{1,2}))$");
    }


    // 判别是否包含Emoji表情
    public static boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !(codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD);
    }

    /**
     * 是否是手机号码
     *
     * @param phone 手机号码
     * @return
     */
    public static boolean isPhone(String phone) {
        return Objects.toString(phone, StringUtils.EMPTY).matches("^1\\d{10}$");
    }

    /**
     * 验证验证码格式
     *
     * @param code
     * @return
     */
    public static boolean isVerificationCode(String code) {
        return Objects.toString(code, StringUtils.EMPTY).matches("^\\d{6}$");
    }

    /**
     * 验证邮箱格式
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return Objects.toString(email, StringUtils.EMPTY).matches("^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    /**
     * 验证 BigDecimal 是否是整数
     * @param bd BigDecimal
     * @return
     */
    public static boolean isInteger(BigDecimal bd) {
        return Objects.nonNull(bd)
                && (bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0);
    }

    /**
     * 验证 BigDecimal 是否在指定的范围，闭区间
     * @param bd BigDecimal
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static boolean isInRange(BigDecimal bd, double min, double max) {
        return Objects.nonNull(bd)
                && bd.compareTo(BigDecimal.valueOf(min)) >= 0 && bd.compareTo(BigDecimal.valueOf(max)) <= 0;
    }

    /**
     * 验证 Integer 是否在指定的范围，闭区间
     * @param x Integer
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static boolean isInRange(Integer x, int min, int max) {
        return Objects.nonNull(x) && x.compareTo(min) >= 0 && x.compareTo(max) <= 0;
    }

    /**
     * 验证 2个 Comparable 是否等值
     * @param a Comparable
     * @param b Comparable
     * @return
     */
    public static <T> boolean equals4Comparable(Comparable<T> a, Comparable<T> b) {
        if (a == null && b == null) {
            return true;
        }
        return (a != null && b != null && a.compareTo((T) b) == 0);
    }

    /**
     * @description 是否包含特殊字符
     * @author  edz
     * @date: 2023/1/3 15:00
     * @param str
     * @return boolean
     */
    public static boolean isSpeChs(String str){
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        //抽奖活动名称是否包含特殊字符
        return m.find();
    }
}
