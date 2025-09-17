package com.wanmi.sbc.account.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

import java.util.Arrays;

/**
@ClassName FailReasonEnum
@Description 转账明细失败原因
@Author qiyuanzhao
@Date 2022/8/18 11:07
 **/
@ApiEnum
public enum FailReasonEnum {

    @ApiEnumProperty("账户冻结")
    ACCOUNT_FROZEN("账户冻结"),

    @ApiEnumProperty("用户未实名")
    REAL_NAME_CHECK_FAIL("用户未实名"),

    @ApiEnumProperty("用户姓名校验失败")
    NAME_NOT_CORRECT("用户姓名校验失败"),

    @ApiEnumProperty("Openid校验失败")
    OPENID_INVALID("Openid校验失败"),

    @ApiEnumProperty("超过用户单笔收款额度")
    TRANSFER_QUOTA_EXCEED("超过用户单笔收款额度"),

    @ApiEnumProperty("超过用户单日收款额度")
    DAY_RECEIVED_QUOTA_EXCEED("超过用户单日收款额度"),

    @ApiEnumProperty("账户冻结")
    MONTH_RECEIVED_QUOTA_EXCEED("超过用户单月收款额度"),

    @ApiEnumProperty("超过用户单日收款次数")
    DAY_RECEIVED_COUNT_EXCEED("超过用户单日收款次数"),

    @ApiEnumProperty("产品权限校验失败")
    PRODUCT_AUTH_CHECK_FAIL("产品权限校验失败"),

    @ApiEnumProperty("转账关闭")
    OVERDUE_CLOSE("转账关闭"),

    @ApiEnumProperty("用户身份证校验失败")
    ID_CARD_NOT_CORRECT("用户身份证校验失败"),

    @ApiEnumProperty("用户账户不存在")
    ACCOUNT_NOT_EXIST("用户账户不存在"),

    @ApiEnumProperty("转账存在风险")
    TRANSFER_RISK("转账存在风险"),

    @ApiEnumProperty("用户账户收款受限，请引导用户在微信支付查看详情")
    REALNAME_ACCOUNT_RECEIVED_QUOTA_EXCEED("用户账户收款受限，请引导用户在微信支付查看详情"),

    @ApiEnumProperty("未配置该用户为转账收款人")
    RECEIVE_ACCOUNT_NOT_PERMMIT("未配置该用户为转账收款人"),

    @ApiEnumProperty("商户账户付款受限，可前往商户平台-违约记录获取解除功能限制指引")
    PAYER_ACCOUNT_ABNORMAL("商户账户付款受限，可前往商户平台-违约记录获取解除功能限制指引"),

    @ApiEnumProperty("用户账户收款异常，请引导用户完善其在微信支付的身份信息以继续收款")
    PAYEE_ACCOUNT_ABNORMAL("用户账户收款异常，请引导用户完善其在微信支付的身份信息以继续收款"),

    @ApiEnumProperty("转账备注设置失败，请调整对应文案后重新再试")
    TRANSFER_REMARK_SET_FAIL("转账备注设置失败，请调整对应文案后重新再试");


    private String desc;

    FailReasonEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }


}
