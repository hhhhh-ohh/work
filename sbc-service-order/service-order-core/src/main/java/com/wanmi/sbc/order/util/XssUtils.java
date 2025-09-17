package com.wanmi.sbc.order.util;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * XSS工具类
 * Created by jinwei on 15/1/2016.
 */
public class XssUtils {

    /**
     * 转义 < >
     * @param value
     * @return
     */
    public static String replaceXss(String value){
        if(value != null && value.length() > 0){
            return value.replace("<", "&lt;").replace(">", "&gt;");
        }else {
            return value;
        }
    }

    /**
     * 替换LIKE通配符(暂支持mysql,其他数据库需要测试后才可使用)
     * @param value
     * @return
     */
    public static String replaceLikeWildcard(String value){
        return value.replaceAll("\\\\","\\\\\\\\").replaceAll("_","\\\\_").replaceAll("%","\\\\%");
    }


    /**
     * LIKE通配符（仅支持mongoDB）
     * @param str
     * @return
     */
    public static String mongodbLike(String str) {
        return String.format("^.*%s.*$", ObjectUtils.toString(str).trim());
    }

    /**
     * 判断是否含有mongoDB查询关键字或特殊符号
     * @param str
     * @return
     */
    public static boolean hasKeyWord(String str) {
        String regex = "[$%^&*\\\\+?.|()]";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).find();
    }

    public static Criteria regex(String colsName, String value){
        if (hasKeyWord(value)) {
            // 若查询参数中含有mongoDB关键字，为避免查询错误，则构造不存在的条件，返回空列表
            return new Criteria().andOperator(Criteria.where(colsName).is(null), Criteria.where(colsName).ne(null));
        }
        return Criteria.where(colsName).regex(Pattern.compile(mongodbLike(value), Pattern.CASE_INSENSITIVE));
    }
}
