package com.wanmi.sbc.customer.growthvalue.builder;

import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.setting.bean.enums.ConfigType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * 成长值类型对应配置信息  静态MAP
 */
public final class GrowthValueTypeConfig {

    private GrowthValueTypeConfig() {
    }

    private static final Map<GrowthValueServiceType, ConfigType> CONFIG_TYPE_KEY_MAP =
            new EnumMap<>(GrowthValueServiceType.class);

    static {
        // 注册
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.REGISTER, ConfigType.GROWTH_VALUE_BASIC_RULE_REGISTER);
        // 绑定微信
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.BINDINGWECHAT, ConfigType.GROWTH_VALUE_BASIC_RULE_BIND_WECHAT);
        // 完善个人信息
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.PERFECTINFO, ConfigType.GROWTH_VALUE_BASIC_RULE_COMPLETE_INFORMATION);
        // 添加收货地址
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.ADDSHIPPINGADDRESS, ConfigType.GROWTH_VALUE_BASIC_RULE_ADD_DELIVERY_ADDRESS);
        // 收藏店铺
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.FOCUSONSTORE, ConfigType.GROWTH_VALUE_BASIC_RULE_FOLLOW_STORE);
        // 分享商城
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.SHARE, ConfigType.GROWTH_VALUE_BASIC_RULE_SHARE_GOODS);
        // 评论晒单
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.EVALUATE, ConfigType.GROWTH_VALUE_BASIC_RULE_COMMENT_GOODS);
        // 分享注册
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.SHAREREGISTER, ConfigType.GROWTH_VALUE_BASIC_RULE_SHARE_REGISTER);
        // 分享购买
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.SHAREPURCHASE, ConfigType.GROWTH_VALUE_BASIC_RULE_SHARE_BUY);
        //签到
        CONFIG_TYPE_KEY_MAP.put(GrowthValueServiceType.SIGNIN, ConfigType.GROWTH_VALUE_BASIC_RULE_SIGN_IN);
    }

    /***
     * 根据成长值获取业务类型获得对应系统Key
     * @param key
     * @return
     */
    public static ConfigType getConfigTypeByKey(GrowthValueServiceType key) {
        return CONFIG_TYPE_KEY_MAP.get(key);
    }
}
