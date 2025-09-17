package com.wanmi.sbc.common.constant;

/**
 * @ClassName RedisKeyConstant
 * @Description redis key对应常量
 * @Author lvzhenwei
 * @Date 2019/6/15 14:15
 **/
public final class RedisKeyConstant {

    private RedisKeyConstant(){

    /**
     * 退款通知+rid
     */

    }

    /**
     * 非常重要不要删除
     */
    public static volatile byte[] C_BYTES;

    //订单快照加锁
    public static final String CUSTOMER_TRADE_SNAPSHOT_LOCK_KEY = "customer:trade:snapshot:lock:";

    //打印单配置
    public static final String STORE_PRINT_SETTING_KEY = "store:printSetting:";

    public static final String TRADE_COUPON_SNAPSHOT = "trade:coupon:snapshot:";

    public static final String TRADE_ITME_SNAPSHOT = "trade:item:snapshot:";

    /**
     * 预约---商品预约数量对应key前缀
     */
    public static final String APPOINTMENT_SALE_GOODS_INFO_COUNT = "appointmentSaleGoodsInfoCount:";

    /**
     * 抢购---商品抢购库存对应key前缀
     */
    public static final String RUSH_SALE_GOODS_INFO_COUNT = "rushSaleGoodsInfoStock:";

    /**
     * 抢购---商品抢购商品信息对应key前缀
     */
    public static final String APPOINTMENT_SALE_GOODS_INFO = "appointmentSaleGoodsInfo:";

    /**
     * 抢购---商品抢购商品资格对应key前缀
     */
    public static final String APPOINTMENT_SALE_GOODS_QUALIFICATIONS = "appointmentSaleGoodsQualifications:";

    /**
     * 库存在redis中key的前缀，后缀为skuId
     */
    public static final String GOODS_INFO_STOCK_PREFIX = "GOODS_INFO_STOCK:";

    /**
     * 第三方平台渠道订单---验证支付并自动退款， 后缀为订单id
     */
    public static final String THIRD_PLATFORM_CHECKED_PEY_AUTO_REFUND = "thirdPlatformCheckPayStatus:";

    /**
     * 第三方平台渠道订单---限制重复消费
     */
    public static final String THIRD_PLATFORM_MQ_REPEATED = "thirdPlatformMqRepeated:";

    public static final String GOODS_DETAIL_CACHE = "goodsdetail:";

    /**
     * 二维码缓存
     */
    public static final String QR_CODE_CACHE = "qrCode_image_cache:";

    public static final String QR_CODE_LINK = "QR_CODE_LINK:";

    /**
     * 授信账户回退记录ID
     */
    public static final String CREDIT_RESET_RECORD = "CREDIT_RESET_RECORD";

    /***
     * 授信名称
     */
    public static final String CREDIT_NAME = "CREDIT_NAME";

    /***
     *  微信个人信息
     */
    public static final String WX_USER_INFO = "WX_USER_INFO:";

    /***
     *  微信分享
     */
    public static final String WX_SHARE_FLAG = "WX_SHARE_FLAG:";

    /**
     * 开放平台数据缓存对应的key值
     */
    public static final String OPEN_API_SETTING_KEY = "appKey:";

    /**
     * 开放平台数据key对应的值操作人
     */
    public static final String OPEN_API_OPERATOR_BY_KEY = "appOperatorKey:";

    /**
     * 增值服务
     */
    public static final String VALUE_ADDED_SERVICES = "value_added_services";

    /**
     * VOP增值服务
     */
    public static final String VOP_CHANNEL_CONFIG = "third_platform_vop";

    /**
     * LINKED_MALL增值服务
     */
    public static final String LINKED_MALL_CHANNEL_CONFIG = "third_platform_linked_mall";

    /**
     * 商品秒杀抢购商品信息对应key前缀,key+goodsInfoId
     */
    public static final String FLASH_SALE_GOODS_INFO_KEY = "flash_sale_goods_info:";

    /**
     * 商品秒杀抢购商品库存对应key前缀,key+goodsInfoId
     */
    public static final String FLASH_SALE_GOODS_INFO_STOCK_KEY = "flash_sale_stock:";

    /**
     * 商品秒杀抢购已抢购商品数量对应key前缀
     */
    public static final String FLASH_SALE_GOODS_HAVE_BUYING_KEY = "flash_sale_buy:";

    /**
     * 客服数据企微客服key +storeId
     */
    public static final String CUSTOMER_SERVICE_WECHAT_INFO = "customer_service_wechat_info:";

    /**
     * 客服数据企微客服key +storeId
     */
    public static final String CUSTOMER_SERVICE_QIYU_INFO = "customer_service_qiyu_info:";

    /**
     * 营销活动插件
     */
    public static final String MARKETING_PLUGIN_KEY = "marketing_plugin_cache";

    /**
     * sku对应的营销活动缓存
     */
    public static final String GOODS_INFO_MARKETING_KEY = "goods_info_marketing:";

    /**
     * 是否展示我知道了
     */
    public static final String IS_KNOW_SHOW_KEY = "is_know_show_key";

    /**
     * 平台数媒
     */
    public static final String STATISTICS_SETTING = "statistics_setting:";

    /**
     * 开放平台二维码参数
     */
    public static final String OPEN_QR_CODE_KEY = "OPEN_QR_CODE:";

    /**
     * 注册协议
     */
    public static final String REGISTRATION_AGREEMENT  = "setting:registration_agreement:";

    /**
     * 注销协议
     */
    public static final String CANCELLATION_AGREEMENT = "setting:cancellation_agreement:";

    /**
     * 满返券库存对应key前缀,key+returnDetailId
     */
    public static final String FULL_RETURN_COUPON_NUM_KEY = "full_return_coupon_num:";

    /**
     * Redis中分销缓存Key
     */
    public static final String DIS_SETTING = "DIS_SETTING:";

    /**
     * seata事务id
     */
    public static final String XID = "XID:";

    /**
     * seata tcc事务商品库存key
     */
    public static final String STOCK_TCC = XID+"STOCK:";

    /**
     * seata tcc事务赠品库存key
     */
    public static final String GIF_STOCK_TCC = XID+"GIF_STOCK:";

    /**
     * 退款通知+rid
     */
    public static final String REFUND_SUCCESS = "refund_success:";

    /**
     * vop商品分类id
     */
    public static final String VOP_CATE_ID = "vop_cate_id:";

    /**
     * 新人购优惠券库存
     */
    public static final String NEW_CUSTOMER_COUPON_STOCK = "NEW_CUSTOMER_COUPON_STOCK:";

    /**
     * seata tcc事务新人券库存key
     */
    public static final String NEWCOMER_STOCK_TCC = XID+"NEWCOMER_STOCK:";

    /**
     * 新人券领取
     */
    public static final String NEWCOMER_STOCK_FETCH = "NEWCOMER_STOCK_FETCH:";

    /**
     * 拉卡拉支付配置
     */
    public static final String LAKALA_PAY_SETTING = "lakala_pay_setting:";


    /**
     * 分账回调
     */
    public static final String LEDGER_CALL_BACK = "ledger_call_back:";

    /**
     * 拉卡拉设置  24小时key
     */
    public static final String LAKALA_SETTING_ONCE = "LAKALA_SETTING_ONCE";

    /**
     * 周期购信息
     */
    public static final String BUY_CYCLE_TRADE_INFO = "BUY_CYCLE_TRADE_INFO:";

    /**
     * 买家修改收货地址key
     */
    public static final String TRADE_BUYER_MODIFY_CONSIGNEE_KEY = "trade_buyer_modify_consignee_key:";

    /**
     * 礼品卡批量发卡上传excel文件名称，2小时，上传人维度
     */
    public static final String GIFT_CARD_BATCH_SEND_UPLOAD_FILE_NAME = "GIFT_CARD_BATCH_SEND_UPLOAD_FILE_NAME:";

    /**
     * 商品库存预警开关  24小时key
     */
    public static final String STOCK_WARNING_ONCE = "STOCK_WARNING_ONCE:";

    /**
     * 社区团购跟团号
     */
    public static final String COMMUNITY_TRADE_NO = "COMMUNITY_TRADE_NO:";

    /**
     * 支付回调统计数据更新
     */
    public static final String COMMUNITY_STATISTICS_PAY_CALLBACK_UPDATE = "COMMUNITY_STATISTICS_PAY_CALLBACK_UPDATE_";

    /**
     * 退单统计数据更新
     */
    public static final String COMMUNITY_STATISTICS_RETURN_UPDATE = "COMMUNITY_STATISTICS_RETURN_UPDATE_";

    /**
     * 佣金结算统计数据更新
     */
    public static final String COMMUNITY_STATISTICS_COMMISSION_UPDATE = "COMMUNITY_STATISTICS_COMMISSION_UPDATE_";

    /**
     * 领券分布式锁key
     */
    public static final String FETCH_COUPON = "FETCH_COUPON:";

    /**
     * 拉卡拉收银台支付配置
     */
    public static final String LAKALA_CASHER_PAY_SETTING = "lakala_casher_pay_setting:";

    /**
     * 拉卡拉收银台支付url
     */
    public static final String LAKALA_CASHER_PAY_URL = "lakala_casher_pay_url:";


    /**
     *  微信提供某交易单号对应的运单号 微信后台会跟踪运单的状态变化
     */
    public static final String WAYBILL_DETAIL_CACHE = "waybill_detail:";


    /**
     *  校服小助手下单送奖励去重缓存
     */
    public static final String XFXZSD_ORDER_REWORD_CACHE = "XFXZSD_ORDER_REWORD:";
}
