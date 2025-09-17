package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.QueryPayType;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 支付单返回对象
 * Created by zhangjin on 2017/4/27.
 */
@Data
@Schema
public class PayOrderResponseVO extends BasicResponse {

    /**
     * 支付单Id
     */
    @Schema(description = "支付单Id")
    private String payOrderId;

    /**
     *子订单列表
     */
    @Schema(description = "子订单列表")
    private List<TradeVO> tradeVOList;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 子订单id(尾款支付单展示订单编号)
     */
    @Schema(description = "订单编号")
    private String tid;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 支付类型
     */
    @Schema(description = "支付类型")
    private PayType payType;

    /**
     * 付款状态
     */
    @Schema(description = "付款状态")
    private PayOrderStatus payOrderStatus;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String comment;

    /**
     * 收款单账号
     * 收款账户前端展示 农业银行6329***7791 支付宝支付189**@163.com
     */
    @Schema(description = "收款单账号")
    private String receivableAccount;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String receivableNo;

    /**
     * 收款金额
     */
    @Schema(description = "收款金额")
    private BigDecimal payOrderPrice;

    /**
     * 支付单积分
     */
    @Schema(description = "支付单积分")
    private Long payOrderPoints;

    /**
     * 支付礼品卡
     */
    @Schema(description = "支付礼品卡")
    private BigDecimal giftCardPrice;

    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;

    /**
     * 应付金额
     */
    @Schema(description = "应付金额")
    private BigDecimal totalPrice;

    /**
     * 收款时间
     */
    @Schema(description = "收款时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime receiveTime;

    /**
     * 收款在线渠道
     */
    @Schema(description = "收款在线渠道")
    private String payChannel;

    @Schema(description = "收款在线渠道")
    private Long payChannelId;

    /**
     * 支付渠道
     */
    @Schema(description = "支付渠道")
    private String payChannelValue;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private Long companyInfoId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 附件
     */
    @Schema(description = "附件")
    private String encloses;

    /**
     * 支付单对应的订单状态
     */
    @Schema(description = "订单状态")
    private TradeStateVO tradeState;

    /**
     * 门店/店铺名称
     */
    @Schema(description = "门店/店铺名称")
    private String storeName;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    @Schema(description = "注销状态 0:正常 1:注销中 2:已注销")
    private LogOutStatus logOutStatus;

    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private QueryPayType queryPayType;
}
