package com.wanmi.sbc.setting.bean.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 系统配置KEY
 * Created by daiyitian on 2017/4/22.
 */
@ApiEnum(dataType = "java.lang.String")
public enum ConfigKey {


    /**
     * resource_server:素材服务器
     */
    RESOURCESERVER("resource_server"),

    /**
     * 小程序直播开关
     */
    LIVESWITCH("live_switch"),

    /**
     * 增值税资质审核
     */
    TICKETAUDIT("ticket_aduit"),

    /**
     * 客服开关
     */
    ONLINESERVICE("online_service"),

    /**
     * kuaidi100
     */
    @ApiEnumProperty("kuaidi100")
    KUAIDI100("kuaidi100"),

    /**
     * S2B审核管理
     */
    @ApiEnumProperty("S2B审核管理")
    S2BAUDIT("s2b_audit"),

    /**
     * 订单设置
     */
    @ApiEnumProperty("订单设置")
    ORDERSETTING("order_setting"),

    /**
     * 移动端设置
     */
    @ApiEnumProperty("移动端设置")
    MOBILE_SETTING("mobile_setting"),

    @ApiEnumProperty("商品设置")
    GOODS_SETTING("goods_setting"),

    /**
     * 小程序设置
     */
    @ApiEnumProperty("小程序设置")
    SMALL_PROGRAM_SETTING("small_program_setting"),

    /**
     * 成长值获取基础规则
     */
    @ApiEnumProperty("成长值获取基础规则")
    GROWTH_VALUE_BASIC_RULE("growth_value_basic_rule"),

    /**
     * 积分基础获取规则
     */
    @ApiEnumProperty("积分基础获取规则")
    POINTS_BASIC_RULE("points_basic_rule"),

    @ApiEnumProperty("增值服务")
    VALUE_ADDED_SERVICES("value_added_services"),

    /**
     * 订单列表展示设置
     */
    @ApiEnumProperty("订单列表展示设置")
    ORDER_LIST_SHOW_TYPE("order_list_show_type"),

    /**
     * 渠道设置类型
     */
    @ApiEnumProperty("渠道设置")
    THIRD_PLATFORM_SETTING("third_platform_setting"),

    /**
     * 支付设置
     */
    @ApiEnumProperty("支付设置")
    PAY_SETTING("pay_setting"),

    /**
     * 订单自提设置
     */
    @ApiEnumProperty("订单自提设置")
    PICKUP_SETTING("pickup_setting"),

    /**
     * 商品字段展示
     */
    @ApiEnumProperty("商品字段展示")
    GOODS_COLUMN_SHOW("goods_column_show"),

    /**
     * es查询设置
     */
    @ApiEnumProperty("es查询设置")
    ES_QUERY_SETTING("es_query_setting"),

    /**
     * o2o营销设置
     */
    @ApiEnumProperty("o2o营销设置")
    MARKETING_SETTING("marketing_setting"),

    /**
     * 限时抢购海报设置
     */
    @ApiEnumProperty("限时抢购海报设置")
    FLASH_GOODS_SALE("flash_goods_sale"),

    /**
     * 数谋基础设置
     */
    @ApiEnumProperty("数谋基础设置")
    STATISTICSSETTING("statistics_setting"),

    /**
     * 砍价设置
     */
    @ApiEnumProperty("砍价设置")
    BARGIN_GOODS_SETTING("bargain_goods_setting"),

    /**
     * 主题色设置
     */
    @ApiEnumProperty("主题色设置")
    THEME_COLOR("theme_color"),

    /**
     * 营销互斥验证标识
     */
    @ApiEnumProperty("营销互斥验证标识")
    MARKETING_MUTEX("marketing_mutex"),

    /**
     * 种草开关
     */
    @ApiEnumProperty("种草开关")
    RECOMMEND("recommend"),

    /**
     * 付费会员设置
     */
    @ApiEnumProperty("付费会员设置")
    PAYING_MEMBER("paying_member"),

    /**
     * SEO设置
     */
    @ApiEnumProperty("SEO设置")
    SEO_SETTING("seo_setting"),

    /**
     * 热门城市设置
     */
    @ApiEnumProperty("热门城市")
    PLATFORM_ADDRESS("platform_address"),

    /**
     * 社区团购设置
     */
    @ApiEnumProperty("社区团购设置")
    COMMUNITY_CONFIG("community_config");

    private final String value;

    ConfigKey(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
