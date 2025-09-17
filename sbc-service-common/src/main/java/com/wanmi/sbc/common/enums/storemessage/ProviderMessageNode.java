package com.wanmi.sbc.common.enums.storemessage;

import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 供应商消息节点枚举
 * @author malianfeng
 * @date 2022/7/11 11:13
 */
@ApiEnum
public enum ProviderMessageNode {

    @ApiEnumProperty("商品审核结果通知")
    GOODS_AUDIT_RESULT("GOODS_AUDIT_RESULT","商品审核结果通知"),

    @ApiEnumProperty("待发货订单提醒")
    TRADE_WAIT_DELIVER("TRADE_WAIT_DELIVER","待发货订单提醒"),

    @ApiEnumProperty("待供应商收货提醒")
    PROVIDER_WAIT_RECEIVE("PROVIDER_WAIT_RECEIVE","待供应商收货提醒"),

    @ApiEnumProperty("待结算单结算提醒")
    SETTLEMENT_SETTLED("SETTLEMENT_SETTLED","商家待结算单生成提醒"),

    @ApiEnumProperty("拉卡拉结算单结算提醒")
    LAKALA_SETTLEMENT_SETTLE("LAKALA_SETTLEMENT_SETTLE","拉卡拉结算单结算提醒"),

    @ApiEnumProperty("收货信息变更提醒")
    TRADE_BUYER_MODIFY_CONSIGNEE("TRADE_BUYER_MODIFY_CONSIGNEE","收货信息变更提醒"),

    @ApiEnumProperty("商品sku库存预警")
    GOODS_SKU_WARN_STOCK("GOODS_SKU_WARN_STOCK","商品库存预警");

    private String code;

    private String description;

    ProviderMessageNode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
