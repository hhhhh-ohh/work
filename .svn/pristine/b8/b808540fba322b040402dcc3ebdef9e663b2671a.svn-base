package com.wanmi.sbc.common.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * 密码安全帮助类
 *
 * @author lihe 2013-7-4 下午5:30:05
 * @see
 */
public final class SecurityUtil {

    private static final BigInteger PRIVATE_D = new BigInteger(
            "3206586642942415709865087389521403230384599658161226562177807849299468150139");

    private static final BigInteger N = new BigInteger(
            "7318321375709168120463791861978437703461807315898125152257493378072925281977");

    private static Random random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private SecurityUtil() {

    }

    /**
     * 解析前台传送的加密字符
     *
     * @param str
     * @return
     * @author lihe 2012-9-26 上午10:51:28
     * @see
     */
    public static String getDecryptLoginPassword(String str) {
        byte ptext[] = HexUtil.toByteArray(str);
        BigInteger encryC = new BigInteger(ptext);

        BigInteger variable = encryC.modPow(PRIVATE_D, N);
        // 计算明文对应的字符串
        byte[] mt = variable.toByteArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = mt.length - 1; i > -1; i--) {
            buffer.append((char) mt[i]);
        }
        return buffer.substring(0, buffer.length() - 10);
    }

    /**
     * 生成密码安全码
     *
     * @return
     * @author lihe 2012-9-27 上午9:40:51
     * @see
     */
    public static String getNewPsw() {
        String s1 = MD5Util.md5Hex(String.valueOf(System.currentTimeMillis()));
        String s2 = UUIDUtil.getUUID();
        return s1 + s2;
    }

    /**
     * 生成加密后的密码
     *
     * @param usercode
     * @param logpwd
     * @param psw
     * @return
     * @author lihe 2012-9-27 上午9:58:10
     * @see
     */
    public static String getStoreLogpwd(String usercode, String logpwd, String psw) {
//        return MD5Util.md5Hex(usercode + MD5Util.md5Hex(logpwd) + psw);
        return MD5Util.md5Hex(usercode + logpwd + psw);
    }

    /**
     * @description 生成加密后的密码
     * @author malianfeng
     * @date 2021/9/15 15:03
     * @param usercode 用户ID
     * @param loginPwdChars 登录密码字符数组
     * @param psw 盐值
     * @return java.lang.String
     */
    public static String getStoreLogpwd(String usercode, char[] loginPwdChars, String psw) {
        byte[] pwdBytes = CharArrayUtils.toBytes(loginPwdChars);
//        MD5Util.md5Hex(usercode + MD5Util.md5Hex(pwdBytes) + psw);
        return MD5Util.md5Hex(usercode + new String(pwdBytes) + psw);
    }

    /**
     * 生成随机的MD5密文
     *
     * @return
     */
    public static String getNewToken() {
        return MD5Util.md5Hex(java.util.UUID.randomUUID().toString());
    }

    /**
     * 生成随机正整数
     *
     * @param maxInt
     * @return
     */
    public static int getRandomInt(int maxInt) {
        return random.nextInt(maxInt);
    }

    /**
     * 生成签名
     *
     * @param srcStr
     * @return
     */
    public static String getDigest(String srcStr) {
        return MD5Util.md5Hex(MD5Util.md5Hex(srcStr) + (srcStr.hashCode() + srcStr.length()) + "19880322");
    }

    /**
     * 生成指定长度的验证码
     * @param len
     * @return
     */
    public static String getVerifyCode(int len){
        StringBuilder sb = new StringBuilder(len );
        for (int i=0;i<len;i++){
            sb.append(getRandomInt(10));
        }
        return sb.toString();
    }


    /**
     * 返回编码前的原始字符数组，并擦除base64字符数组
     * 例：['M','T','I','z','N','D','U','2'] => ['1','2','3','4','5','6']
     * @param base64Chars base64字符数组
     * @return originalChars原始字符数组
     */
    public static char[] decodeAndWipeBase64Chars(char[] base64Chars){
        // 0. 将base64Chars字符数组，转成字节数组base64Bytes
        byte[] base64Bytes = CharArrayUtils.toBytes(base64Chars);
        // 1. 将base64Bytes解码为原始字节数组originalBytes
        byte[] originalBytes = Base64.getUrlDecoder().decode(base64Bytes);
        // 2. 将originalBytes转为originalChars
        char[] originalChars = CharArrayUtils.toChars(originalBytes);
        // 3. 擦除base64Chars
        CharArrayUtils.wipe(base64Chars);
        // 4. 擦除originalBytes
        CharArrayUtils.wipe(originalBytes);
        return originalChars;
    }

    public static void main(String[] args) {
        // 测试base64字符数组转原始字符数组
        char[] base64Chars = new char[]{'M','T','I','z','N','D','U','2'};
        char[] originalChars = decodeAndWipeBase64Chars(base64Chars);
        System.out.println(originalChars);
        System.out.println(base64Chars);
    }
}
