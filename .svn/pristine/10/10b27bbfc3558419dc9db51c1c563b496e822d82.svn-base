package com.wanmi.sbc.mq.constant;

import com.wanmi.sbc.common.util.auth.NetworkUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhanggaolei
 * @className MqConstant
 * @description
 * @date 2022/6/8 16:21
 **/
public class MqConstant {

    public static String IP;
    static {
        try {
            IP = NetworkUtil.getLocalHostLANAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static final int PAGE_SIZE=100;
}
