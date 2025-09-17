package com.wanmi.sbc.common.util;


import java.util.Objects;

/**
 * XSS工具类
 * Created by jinwei on 15/1/2016.
 */
public class XssUtils {

    /**
     * javascript keyword
     */
    public static final String XSS_REGEX = "(?i)(eval\\(|data|embed|iframe|alert\\(|method|action|onsubmit|onunload|ondblclick|onmouseover|onmouseover|onclick|onload|onerror|onfocus|onblur|onchange=|onkeydown=|onkeyup|onkeypress|onmousedown|onmouseup|onmousemove|onmouseout|onreset|<script>|</script>)";

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
        return value.replaceAll("\\\\","\\\\\\\\").replace("_","\\_").replace("%","\\\\%");
    }

    /**
     * 替换ES的LIKE通配符(暂支持es,其他数据库需要测试后才可使用)
     * @param value
     * @return
     */
    public static String replaceEsLikeWildcard(String value){
        return value.replaceAll("\\\\","\\\\\\\\")
                .replaceAll("\\.","\\\\.")
                .replaceAll("\\[","\\\\[")
                .replace("]","\\]")
                .replaceAll("\\{","\\\\{")
                .replace("}","\\}")
                .replaceAll("\\?","\\\\?")
                .replaceAll("\\*","\\\\*");
    }


    /**
     * LIKE通配符（仅支持mongoDB）
     * @param str
     * @return
     */
    public static String mongodbLike(String str) {
        return String.format("^.*%s.*$", Objects.toString(str).trim());
    }
}
