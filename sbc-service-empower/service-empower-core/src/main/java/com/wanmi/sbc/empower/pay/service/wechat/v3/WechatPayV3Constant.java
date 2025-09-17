package com.wanmi.sbc.empower.pay.service.wechat.v3;

/**
 * @author wur
 * @className WechatPayV3Constant
 * @description 微信支付V3版本 常量
 * @date 2022/11/28 11:07
 **/
public final class WechatPayV3Constant {

    private WechatPayV3Constant(){}

    public  static final String RETURN_CODE = "code";
    public  static final String RETURN_MESSAGE = "message";
    public  static final String NATIVE_CODE_URL = "code_url";
    public  static final String H5_URL = "h5_url";

    public  static final String JSAPI_PREPAY_ID = "prepay_id";

    public static final String SCENE_INFO = "scene_info";
    public static final String PAYER = "payer";
    public static final String AMOUNT = "amount";
    public static final String TOTAL = "total";
    public static final String CURRENCY = "currency";


    public  static final String PAY_RETURN_SUCCESS = "SUCCESS";

    /**===================================  支付下单接口Begin  =====================================*/

    /**
     * Native下单API(PC扫码支付)  https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_4_1.shtml
     */
    public  static final String NATIVE_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";

    /**
     * H5下单API  https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_3_1.shtml
     */
    public  static final String H5_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/h5";

    /**
     * JSAPI下单 https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_2_1.shtml
     */
    public  static final String APP_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";

    /**
     * APP下单API https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_1.shtml
     */
    public  static final String JSAPI_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

    /**===================================  支付下单接口End  =====================================*/



    /**===================================  支付单操作接口Begin  =====================================*/

    /**
     * 支付单查询API  https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_2.shtml   根据订单Id查询
     */
    public  static final String QUERY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/%s?mchid=%s";

    /**
     * 关闭支付单API  https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_3.shtml
     */
    public  static final String CLOSE_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/%s/close";

    /**
     * 申请退款API  https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_2_9.shtml
     */
    public  static final String REFUNDS_URL = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";


    /**
     * 获取微信平台证书列表
     */
    public  static final String CERTIFICATES = "https://api.mch.weixin.qq.com/v3/certificates";

    /**===================================  支付单操作接口End  =====================================*/

}
