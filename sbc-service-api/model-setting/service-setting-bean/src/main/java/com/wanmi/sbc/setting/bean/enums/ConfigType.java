package com.wanmi.sbc.setting.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 系统配置KEY
 * Created by daiyitian on 2017/4/22.
 */
@ApiEnum(dataType = "java.lang.String")
public enum ConfigType {
    /**
     * yun:云
     */
    @ApiEnumProperty("yun:云")
    YUN("yun"),
    /**
     * kuaidi100
     */
    @ApiEnumProperty("kuaidi100")
    KUAIDI100("kuaidi100"),

    /**
     * 增值税资质审核
     */
    @ApiEnumProperty("增值税资质审核")
    TICKETAUDIT("ticket_aduit"),

    /**
     * 商家审核
     */
    @ApiEnumProperty("商家审核")
    SUPPLIERAUDIT("supplier_audit"),

    /**
     * 商家商品审核
     */
    @ApiEnumProperty("商家商品审核")
    SUPPLIERGOODSAUDIT("supplier_goods_audit"),

    /**
     * 自营商品审核
     */
    @ApiEnumProperty("自营商品审核")
    BOSSGOODSAUDIT("boss_goods_audit"),

    /**
     * 订单审核
     */
    @ApiEnumProperty("订单审核")
    ORDERAUDIT("order_audit"),

    /**
     * 客户审核
     */
    @ApiEnumProperty("客户审核")
    CUSTOMERAUDIT("customer_audit"),

    /**
     * 客户审核
     */
    @ApiEnumProperty("客户审核")
    CUSTOMERINFOAUDIT("customer_info_audit"),

    /**
     *用户设置
     */
    @ApiEnumProperty("用户设置")
    USERAUDIT("user_audit"),

    /**
     * 增值服务-企业购-设置
     */
    @ApiEnumProperty("增值服务-企业购-设置")
    VAS_IEP_SETTING("vas_iep_setting"),

    /**
     * 订单设置自动收货
     */
    @ApiEnumProperty("订单设置自动收货")
    ORDER_SETTING_AUTO_RECEIVE("order_setting_auto_receive"),

    /**
     * 退单自动审核
     */
    @ApiEnumProperty("退单自动审核")
    ORDER_SETTING_REFUND_AUTO_AUDIT("order_setting_refund_auto_audit"),

    /**
     * 退单自动收货
     */
    @ApiEnumProperty("退单自动收货")
    ORDER_SETTING_REFUND_AUTO_RECEIVE("order_setting_refund_auto_receive"),

    /**
     * 允许申请退单
     */
    @ApiEnumProperty("允许申请退单")
    ORDER_SETTING_APPLY_REFUND("order_setting_apply_refund"),

    /**
     * 卡券订单允许代客退单
     */
    @ApiEnumProperty("卡券订单允许代客退单")
    ORDER_SETTING_VIRTUAL_APPLY_REFUND("order_setting_virtual_apply_refund"),

    /**
     * PC商城商品列表默认展示大图或小图
     */
    @ApiEnumProperty("PC商城商品列表默认展示大图或小图")
    PC_GOODS_IMAGE_SWITCH("pc_goods_image_switch"),

    /**
     * PC商城商品列表展示维度SKU或者SPU
     */
    @ApiEnumProperty("PC商城商品列表展示维度SKU或者SPU")
    PC_GOODS_SPEC_SWITCH("pc_goods_spec_switch"),

    /**
     * 移动端商城商品列表默认展示大图或小图
     */
    @ApiEnumProperty("移动端商城商品列表默认展示大图或小图")
    MOBILE_GOODS_IMAGE_SWITCH("mobile_goods_image_switch"),

    /**
     * 移动端商城商品列表展示维度SKU或者SPU
     */
    @ApiEnumProperty("移动端商城商品列表展示维度SKU或者SPU")
    MOBILE_GOODS_SPEC_SWITCH("mobile_goods_spec_switch"),

    /**
     * 订单支付顺序设置（先款后货/不限）
     */
    @ApiEnumProperty("订单支付顺序设置（先款后货/不限）")
    ORDER_SETTING_PAYMENT_ORDER("order_setting_payment_order"),

    /**
     * 超时未支付取消订单
     */
    @ApiEnumProperty("超时未支付取消订单")
    ORDER_SETTING_TIMEOUT_CANCEL("order_setting_timeout_cancel"),

    /**
     * 超时自动评价订单
     */
    @ApiEnumProperty("超时自动评价订单")
    ORDER_SETTING_TIMEOUT_EVALUATE("order_setting_timeout_evaluate"),

    /**
     * 买家自助修改收货地址
     */
    @ApiEnumProperty("买家自助修改收货地址")
    ORDER_SETTING_BUYER_MODIFY_CONSIGNEE("order_setting_buyer_modify_consignee"),

    /**
     * 关于我们
     */
    @ApiEnumProperty("关于我们")
    ABOUT_US("about_us"),

    /**
     * app检测升级
     */
    @ApiEnumProperty("app检测升级")
    APP_UPDATE("app_update"),

    /**
     * app分享
     */
    @ApiEnumProperty("app分享")
    APP_SHARE("app_share"),

    /**
     * 小程序分享设置
     */
    @ApiEnumProperty("小程序分享设置")
    APPLET_SHARE_SETTING("applet_share_setting"),

    /**
     * 商品评价设置
     */
    @ApiEnumProperty("商品评价设置")
    GOODS_EVALUATE_SETTING("goods_evaluate_setting"),

    /**
     * 仅展示有货商品设置
     */
    @ApiEnumProperty("仅展示有货商品设置")
    GOODS_OUT_OF_STOCK_SHOW_SETTING("goods_out_of_stock_show_setting"),

    /**
     * 小程序基础配置
     */
    @ApiEnumProperty("小程序基础配置")
    SMALL_PROGRAM_SETTING_CUSTOMER("small_program_setting_customer"),

    /**
     * 成长值基础获取规则类型——签到
     */
    @ApiEnumProperty("成长值基础获取规则类型——签到")
    GROWTH_VALUE_BASIC_RULE_SIGN_IN("growth_value_basic_rule_sign_in"),

    /**
     * 成长值基础获取规则类型——注册
     */
    @ApiEnumProperty("成长值基础规则类型——注册")
    GROWTH_VALUE_BASIC_RULE_REGISTER("growth_value_basic_rule_register"),

    /**
     * 成长值基础规则类型——分享商品
     */
    @ApiEnumProperty("成长值基础规则类型——分享商品")
    GROWTH_VALUE_BASIC_RULE_SHARE_GOODS("growth_value_basic_rule_share_goods"),

    /**
     * 成长值基础规则类型——评论
     */
    @ApiEnumProperty("成长值基础规则类型——评论")
    GROWTH_VALUE_BASIC_RULE_COMMENT_GOODS("growth_value_basic_rule_comment_goods"),

    /**
     * 成长值基础规则类型——完善个人信息
     */
    @ApiEnumProperty("成长值基础规则类型——完善个人信息")
    GROWTH_VALUE_BASIC_RULE_COMPLETE_INFORMATION("growth_value_basic_rule_complete_information"),

    /**
     * 成长值基础获取规则类型——绑定微信
     */
    @ApiEnumProperty("成长值基础规则类型——绑定微信")
    GROWTH_VALUE_BASIC_RULE_BIND_WECHAT("growth_value_basic_rule_bind_wechat"),

    /**
     * 成长值基础获取规则类型——添加收货地址
     */
    @ApiEnumProperty("成长值基础获取规则类型——添加收货地址")
    GROWTH_VALUE_BASIC_RULE_ADD_DELIVERY_ADDRESS("growth_value_basic_rule_add_delivery_address"),

    /**
     * 成长值基础获取规则类型——关注店铺
     */
    @ApiEnumProperty("成长值基础获取规则类型——关注店铺")
    GROWTH_VALUE_BASIC_RULE_FOLLOW_STORE("growth_value_basic_rule_follow_store"),

    /**
     * 成长值基础获取规则类型——分享注册
     */
    @ApiEnumProperty("成长值基础获取规则类型——分享注册")
    GROWTH_VALUE_BASIC_RULE_SHARE_REGISTER("growth_value_basic_rule_share_register"),

    /**
     * 成长值基础获取规则类型——分享购买
     */
    @ApiEnumProperty("成长值基础获取规则类型——分享购买")
    GROWTH_VALUE_BASIC_RULE_SHARE_BUY("growth_value_basic_rule_share_buy"),

    /**
     * 积分基础获取规则类型——签到
     */
    @ApiEnumProperty("积分基础获取规则类型——签到")
    POINTS_BASIC_RULE_SIGN_IN("points_basic_rule_sign_in"),

    /**
     * 积分基础获取规则类型——注册
     */
    @ApiEnumProperty("积分基础获取规则类型——注册")
    POINTS_BASIC_RULE_REGISTER("points_basic_rule_register"),

    /**
     * 积分基础获取规则类型——分享商品
     */
    @ApiEnumProperty("积分基础获取规则类型——分享商品")
    POINTS_BASIC_RULE_SHARE_GOODS("points_basic_rule_share_goods"),

    /**
     * 积分基础获取规则类型——评论
     */
    @ApiEnumProperty("积分基础获取规则类型——评论")
    POINTS_BASIC_RULE_COMMENT_GOODS("points_basic_rule_comment_goods"),

    /**
     * 积分基础获取规则类型——关注店铺
     */
    @ApiEnumProperty("积分基础获取规则类型——关注店铺")
    POINTS_BASIC_RULE_FOLLOW_STORE("points_basic_rule_follow_store"),

    /**
     * 积分基础获取规则类型——完善个人信息
     */
    @ApiEnumProperty("积分基础获取规则类型——完善个人信息")
    POINTS_BASIC_RULE_COMPLETE_INFORMATION("points_basic_rule_complete_information"),

    /**
     * 积分基础获取规则类型——绑定微信
     */
    @ApiEnumProperty("积分基础获取规则类型——绑定微信")
    POINTS_BASIC_RULE_BIND_WECHAT("points_basic_rule_bind_wechat"),

    /**
     * 积分基础获取规则类型——添加收货地址
     */
    @ApiEnumProperty("积分基础获取规则类型——添加收货地址")
    POINTS_BASIC_RULE_ADD_DELIVERY_ADDRESS("points_basic_rule_add_delivery_address"),

    /**
     * 积分基础获取规则类型——分享注册
     */
    @ApiEnumProperty("积分基础获取规则类型——分享注册")
    POINTS_BASIC_RULE_SHARE_REGISTER("points_basic_rule_share_register"),

    /**
     * 积分基础获取规则类型——分享购买
     */
    @ApiEnumProperty("积分基础获取规则类型——分享购买")
    POINTS_BASIC_RULE_SHARE_BUY("points_basic_rule_share_buy"),

    /**
     *小程序直播
     */
    @ApiEnumProperty("小程序直播")
    LIVE("live"),
    /**
     *crm标记
     */
    @ApiEnumProperty("crm标记")
    CRM_FLAG("crm"),

    /**
     * 阿里云客服配置
     */
    @ApiEnumProperty("阿里云客服配置")
    ALIYUN_ONLINE_SERVICE("aliyun_online_service"),

    /**
     * 订单列表展示设置
     */
    @ApiEnumProperty("订单列表展示设置")
    ORDER_LIST_SHOW_TYPE("order_list_show_type"),

    /**
     * 订单倒计时设置
     **/
    @ApiEnumProperty("订单倒计时设置")
    ORDER_SETTING_COUNTDOWN("order_setting_countdown"),

    /**
     * 渠道设置-linkedMall
     */
    @ApiEnumProperty("渠道设置-linkedMall")
    THIRD_PLATFORM_LINKED_MALL("third_platform_linked_mall"),

    /**
     * 线下支付设置
     */
    @ApiEnumProperty("线下支付设置")
    OFFLINE_PAY_SETTING("offline_pay_setting"),

    @ApiEnumProperty("渠道设置-vop")
    THIRD_PLATFORM_VOP("third_platform_vop"),

    @ApiEnumProperty("o2o门店设置")
    VAS_O2O_SETTING("vas_o2o_setting"),

    @ApiEnumProperty("自营商家订单自提设置")
    SELF_MERCHANT("selfMerchant"),

    @ApiEnumProperty("第三方门店订单自提设置")
    THIRD_MERCHANT("thirdMerchant"),

    @ApiEnumProperty("门店订单自提设置")
    STORE("store"),

    @ApiEnumProperty("在途退货设置")
    ORDER_SETTING_ALONG_REFUND("order_setting_along_refund"),

    /**
     * ES商品查询权重设置
     */
    @ApiEnumProperty("ES商品查询权重设置")
    ES_QUERY_BOOST("es_query_boost"),

    /**
     * o2o营销设置
     */
    @ApiEnumProperty("o2o营销设置")
    O2O_MARKETING_SETTING("o2o_marketing_setting"),

    /**
     * 商家商品二次审核
     */
    @ApiEnumProperty("商家商品二次审核")
    SUPPLIER_GOODS_SECONDARY_AUDIT("supplier_goods_secondary_audit"),

    /**
     * 供应商商品二次审核
     */
    @ApiEnumProperty("供应商商品二次审核")
    PROVIDER_GOODS_SECONDARY_AUDIT("provider_goods_secondary_audit"),

    /**
     * 商家商品二次审核
     */
    @ApiEnumProperty("商家签约信息二次审核")
    SUPPLIER_SIGN_SECONDARY_AUDIT("supplier_sign_secondary_audit"),

    /**
     * 供应商商品二次审核
     */
    @ApiEnumProperty("供应商签约信息二次审核")
    PROVIDER_SIGN_SECONDARY_AUDIT("provider_sign_secondary_audit"),

    /**
     * 限时抢购海报设置
     */
    @ApiEnumProperty("限时抢购海报设置")
    FLASH_GOODS_SALE_POSTER("flash_goods_sale_poster"),

    /**
     * 千米数谋基本配置
     */
    @ApiEnumProperty("千米数谋基本配置")
    QM_STATISTICS_SETTING("qm_statistics_setting"),

    /**
     * 主题色设置
     */
    @ApiEnumProperty("主题色设置")
    THEME_COLOR_SETTING("theme_color_setting"),

    /**
     * 营销互斥验证标识
     */
    @ApiEnumProperty("营销互斥验证标识")
    MARKETING_MUTEX("marketing_mutex"),

    /**
     * 种草开关
     */
    @ApiEnumProperty("种草开关")
    RECOMMEND_STATUS("recommend_status"),

    /**
     * 发现页展示直播种草展示优先级
     */
    @ApiEnumProperty("发现页展示直播种草展示优先级")
    FIND_SHOW_TYPE("find_show_type"),

    /**
     * 付费会员设置
     */
    @ApiEnumProperty("付费会员设置")
    PAYING_MEMBER("paying_member"),

    /**
     * 砍价审核开关
     */
    @ApiEnumProperty("砍价审核开关")
    BARGIN_GOODS_AUDIT("bargain_goods_audit"),

    /**
     * 砍价随机语
     */
    @ApiEnumProperty("砍价随机语")
    BARGIN_GOODS_RANDOM_WORDS("bargain_goods_random_words"),

    /**
     * 砍价频道海报
     */
    @ApiEnumProperty("砍价频道海报")
    BARGIN_GOODS_SALE_POSTER("bargain_goods_sale_poster"),

    /**
     * 砍价规则
     */
    @ApiEnumProperty("砍价规则")
    BARGIN_GOODS_RULE("bargain_goods_rule"),

    @ApiEnumProperty("砍价叠加优惠券使用")
    BARGIN_USE_COUPON("bargain_use_coupon"),

    @ApiEnumProperty("砍价活动时长")
    BARGIN_ACTIVITY_TIME("bargain_activity_time"),

    @ApiEnumProperty("没人每天最多帮砍次数")
    BARGIN_MAX_NUM_EVERY_DAY("bargain_maxNum_everyDay"),

    /**
     * SEO设置
     */
    @ApiEnumProperty("SEO设置")
    SEO_SETTING("seo_setting"),

    @ApiEnumProperty("个人中心配置")
    PERSONAL_CONFIG("personal_config"),

    /**
     * 礼品卡制卡审核开关
     */
    @ApiEnumProperty("礼品卡制卡审核开关")
    GIFT_CARD_MAKE_CARD_AUDIT("gift_card_make_card_audit"),

    /**
     * 礼品卡发卡审核开关
     */
    @ApiEnumProperty("礼品卡发卡审核开关")
    GIFT_CARD_SEND_CARD_AUDIT("gift_card_send_card_audit"),

    /**
     * 限时抢购商品抢完是否可以原价购买
     */
    @ApiEnumProperty("现实抢购商品抢完是否可以原价购买")
    FLASH_PROMOTION_ORIGINAL_PRICE("flash_promotion_original_price"),

    /**
     * 整点秒杀商品库存抢完后是否允许原价购买
     */
    @ApiEnumProperty("整点秒杀商品库存抢完后是否允许原价购买")
    FLASH_SALE_ORIGINAL_PRICE("flash_sale_original_price"),

    /**
     * 限时抢购订单自动取消设置
     */
    @ApiEnumProperty("限时抢购订单自动取消设置")
    FLASH_PROMOTION_ORDER_AUTO_CANCEL("flash_promotion_order_auto_cancel"),

    /**
     * 秒杀订单自动取消设置
     */
    @ApiEnumProperty("秒杀订单自动取消设置")
    FLASH_SALE_ORDER_AUTO_CANCEL("flash_sale_order_auto_cancel"),

    /**
     * 热门城市
     */
    @ApiEnumProperty("热门城市")
    POPULAR_CITY("popular_city"),

    /**
     * 高德地图设置
     */
    @ApiEnumProperty("高德地图设置")
    WHETHER_OPEN_MAP("whether_open_map"),

    /**
     * pc快速下单设置
     */
    @ApiEnumProperty("pc快速下单设置")
    ORDER_SETTING_QUICK_ORDER("order_setting_quick_order"),

    /**
     * 社区团购设置
     */
    @ApiEnumProperty("社区团购设置")
    COMMUNITY_CONFIG("community_config");

    private final String value;

    ConfigType(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
