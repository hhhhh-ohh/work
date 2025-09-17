package com.wanmi.sbc.empower.bean.constant;

/**
 * @author zhengyang
 * @className PayServiceConstants
 * @description 支付Service名称引用
 * @date 2021/5/7 10:24
 **/
public final class PayServiceConstants {

    private PayServiceConstants(){

    }

    /***
     * 支付宝Service
     */
    public static final String ALIPAY_SERVICE = "aliPayService";

    /***
     * 微信Service
     */
    public static final String WECHAT_SERVICE = "wechatPayService";

    /***
     * 微信支付 V3版本
     */
    public static final String WECHAT_V3_SERVICE = "wechatPayV3Service";

    /***
     * 银联云闪付Service
     */
    public static final String UNION_CLOUD_SERVICE = "unionCloudPayService";

    /***
     * 微信视频号
     */
    public static final String WECHAT_CHANNELS = "wechatChannelsPayService";

    /***
     * 拉卡拉支付
     */
    public static final String LAKALA_SERVICE = "lakalaPayService";

    /***
     * 拉卡拉收银台支付
     */
    public static final String LAKALA_CASH_SERVICE = "lakalaCasherPayService";
}
