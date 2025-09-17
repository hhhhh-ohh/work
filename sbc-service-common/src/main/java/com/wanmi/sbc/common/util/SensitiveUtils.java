package com.wanmi.sbc.common.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 处理敏感信息工具类
 * @author: Geek Wang
 * @createDate: 2019/2/26 14:33
 * @version: 1.0
 */
public class SensitiveUtils{

    /**
     * 判断手机号正则
     */
    private static Pattern pattern = Pattern.compile("^1\\d{10}$");

    /**
     * 正则表达式：验证邮箱
     */
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证网络图片资源
     */
    private static final String REGEX_NETWORK_IMAGE = "(https?:[^:<>\"]*\\/)([^:<>\"]*)(\\." +
            "(bmp|jpg|jpeg|heif|png|tif|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw|WMF|webp))";

    /**
     * 正则表达式：验证网络视频资源
     */
    private static final String REGEX_NETWORK_VIDEO = "(https?:[^:<>\"]*\\/)([^:<>\"]*)(\\." +
            "(avi|wmv|rm|rmvb|mpeg1|mpeg2|mpeg4|mp4|3gp|asf|swf|vob|dat|mov|m4v|flv|f4v|mkv|mts|ts|imv|amv|xv|qsv))";
    
    private static final int PHONE_NUM = 11;

    /**
     * 判断是否为手机号
     * @param mobile    手机号码
     * @return
     */
    public static boolean isMobilePhone(String mobile) {
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        if (mobile.contains(Constants.LOGGED_OUT)){
            String[] split = mobile.split(Constants.LOGGED_OUT);
            return split[0].length() == PHONE_NUM && pattern.matcher(split[0]).matches();
        }
        // 跟前端保持一致，只校验开头为1及长度11位
        return mobile.length() == PHONE_NUM && pattern.matcher(mobile).matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobilePhone("15951896352"));
        System.out.println(handlerTxtWithNetworkSourceAddress("src=\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs" +
                ".com/3ebd1866f69fe015609bbfcd7195e793.png\",src=\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs" +
                ".com/3ebd1866f69fe015609bbfcd7195e793.mp4\""));
    }

    /**
     * 处理后带有网络资源地址的文本
     * @param txt 文本
     * @return 返回处理后的文本
     */
    public static String handlerTxtWithNetworkSourceAddress(String txt) {
        if (StringUtils.isEmpty(txt)) {
            return txt;
        }
        // 清空网络图片资源地址
        txt = txt.replaceAll(REGEX_NETWORK_IMAGE, "");
        // 清空网络视频资源地址
        txt = txt.replaceAll(REGEX_NETWORK_VIDEO, "");
        return txt;
    }

    /**
     * 处理手机号码
     * @param mobilePhone 手机号码
     * @return  返回处理后的手机号码,如:132****8856
     */
    public static String handlerMobilePhone(String mobilePhone) {

        if (StringUtils.isEmpty(mobilePhone)) {
            return mobilePhone;
        }
        if(isMobilePhone(mobilePhone)){
            mobilePhone = mobilePhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return mobilePhone;
    }

    /**
     * 处理手机号码
     * @param mobilePhone 手机号码
     * @return  返回处理后的手机号码,如:*8856
     */
    public static String handlerMobilePhoneWithHideSeven(String mobilePhone) {
        if (StringUtils.isEmpty(mobilePhone)) {
            return mobilePhone;
        }
        if(isMobilePhone(mobilePhone)){
            mobilePhone = mobilePhone.replaceAll("\\d{7}(\\d{4})", "*$1");
        }
        return mobilePhone;
    }

    /**
     * 判断是否为邮箱
     * @param email    邮箱
     * @return
     */
    public static boolean isEmail(String email) {
        if(StringUtils.isEmpty(email)){
            return false;
        }
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        if (pattern.matcher(email).matches()) {
            return true;
        }
        return false;
    }
}


