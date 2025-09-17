package com.wanmi.sbc.common.util;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

/**
 * @author lvzhenwei
 * @className RandomUtils
 * @description 生成指定位数的随机数
 * @date 2023/4/18 10:09 上午
 **/
public class RandomUtils {

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789";

    private static Random random = new Random();

    /**
     * @Author chenqi
     * @Description 生成指定位数的随机数 （字母加数字,去除0、l、o）
     * @Param [length]
     * @return java.lang.String
     **/
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String month = String.format("%02d",LocalDate.now().getMonthValue());
        String day = String.format("%02d",LocalDate.now().getDayOfMonth());
        String randomStr = generateRandomString(4);
        System.out.println("randomStr==="+randomStr);
    }
}
