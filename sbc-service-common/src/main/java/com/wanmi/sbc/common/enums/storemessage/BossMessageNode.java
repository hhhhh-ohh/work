package com.wanmi.sbc.common.enums.storemessage;

import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * @description 平台消息节点枚举
 * @author malianfeng
 * @date 2022/7/11 11:13
 */
@ApiEnum
public enum BossMessageNode {

    @ApiEnumProperty("商家待审核通知")
    SUPPLIER_WAIT_AUDIT("SUPPLIER_WAIT_AUDIT","商家待审核通知"),

    @ApiEnumProperty("供应商待审核通知")
    PROVIDER_WAIT_AUDIT("PROVIDER_WAIT_AUDIT","供应商待审核通知"),

    @ApiEnumProperty("商家商品待审核通知")
    SUPPLIER_GOODS_WAIT_AUDIT("SUPPLIER_GOODS_WAIT_AUDIT","商家商品待审核通知"),

    @ApiEnumProperty("供应商商品待审核通知")
    PROVIDER_GOODS_WAIT_AUDIT("PROVIDER_GOODS_WAIT_AUDIT","供应商商品待审核通知"),

    @ApiEnumProperty("待退款订单提醒")
    TRADE_WAIT_REFUND("TRADE_WAIT_REFUND","待退款订单提醒"),

    @ApiEnumProperty("退款失败提醒")
    REFUND_FAIL("REFUND_FAIL","退款失败提醒"),

    @ApiEnumProperty("待审核客户提醒")
    CUSTOMER_WAIT_AUDIT("CUSTOMER_WAIT_AUDIT","待审核客户提醒"),

    @ApiEnumProperty("待审核企业会员提醒")
    ENTERPRISE_CUSTOMER_WAIT_AUDIT("ENTERPRISE_CUSTOMER_WAIT_AUDIT","待审核企业会员提醒"),

    @ApiEnumProperty("客户注销提醒")
    CUSTOMER_LOGOUT("CUSTOMER_LOGOUT","客户注销提醒"),

    @ApiEnumProperty("客户升级提醒")
    CUSTOMER_UPGRADE("CUSTOMER_UPGRADE","客户升级提醒"),

    @ApiEnumProperty("商家待结算单生成提醒")
    SUPPLIER_SETTLEMENT_PRODUCE("SUPPLIER_SETTLEMENT_PRODUCE","商家待结算单生成提醒"),

    @ApiEnumProperty("供应商待结算单生成提醒")
    PROVIDER_SETTLEMENT_PRODUCE("PROVIDER_SETTLEMENT_PRODUCE","供应商待结算单生成提醒"),

    @ApiEnumProperty("拉卡拉结算单结算提醒")
    LAKALA_SETTLEMENT_SETTLE("LAKALA_SETTLEMENT_SETTLE","拉卡拉结算单结算提醒"),

    @ApiEnumProperty("会员提现待审核提醒")
    CUSTOMER_WITHDRAW_WAIT_AUDIT("CUSTOMER_WITHDRAW_WAIT_AUDIT","会员提现待审核提醒"),

    @ApiEnumProperty("授信账户待审核提醒")
    CREDIT_ACCOUNT_WAIT_AUDIT("CREDIT_ACCOUNT_WAIT_AUDIT","授信账户待审核提醒"),

    @ApiEnumProperty("授信还款成功通知")
    CREDIT_REPAYMENT_SUCCESS("CREDIT_REPAYMENT_SUCCESS","授信还款成功通知");

    private String code;

    private String description;

    BossMessageNode(String code, String description) {
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
