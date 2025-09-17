package com.wanmi.sbc.account.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 结算单
 * Created by daiyitian on 2017/12/6.
 */
@Schema
@Data
public class SettlementDTO implements Serializable {

    private static final long serialVersionUID = -8995917947597264253L;
    /**
     * 用于生成结算单号，结算单号自增
     */
    @Schema(description = "结算单id")
    private Long settleId;

    /**
     * 结算单uuid，mongodb外键
     */
    @Schema(description = "结算单uuid")
    private String settleUuid;

    /**
     * 结算单创建时间
     */
    @Schema(description = "结算单创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 结算单号
     */
    @Schema(description = "结算单号")
    private String settleCode;

    /**
     * 结算单开始时间
     */
    @Schema(description = "结算单开始时间")
    private String startTime;

    /**
     * 结算单结束时间
     */
    @Schema(description = "结算单结束时间")
    private String endTime;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long storeId;

    /**
     * 交易总额
     */
    @Schema(description = "交易总额")
    private BigDecimal salePrice;

    /**
     * 商品销售总数
     */
    @Schema(description = "商品销售总数")
    private long saleNum;

    /**
     * 退款总额
     */
    @Schema(description = "退款总额")
    private BigDecimal returnPrice;

    /**
     * 商品退货总数
     */
    @Schema(description = "商品退货总数")
    private long returnNum;

    /**
     * 平台佣金总额
     */
    @Schema(description = "平台佣金总额")
    private BigDecimal platformPrice;

    /**
     * 店铺应收
     */
    @Schema(description = "店铺应收")
    private BigDecimal storePrice;

    /**
     * 商品供货总额
     */
    @Schema(description = "商品供货总额")
    private BigDecimal providerPrice;

    /**
     * 总运费
     */
    @Schema(description = "总运费")
    private BigDecimal deliveryPrice;

    /**
     * 商品实付总额
     */
    @Schema(description = "商品实付总额")
    private BigDecimal splitPayPrice;

    /**
     * 通用券优惠总额
     */
    @Schema(description = "通用券优惠总额")
    private BigDecimal commonCouponPrice;

    /**
     * 积分抵扣总额
     */
    @Schema(description = "积分抵扣总额")
    private BigDecimal pointPrice;

    /**
     * 积分数量
     */
    @Schema(description = "积分数量")
    private Long points;

    /**
     * 分销佣金总额
     */
    @Schema(description = "分销佣金总额")
    private BigDecimal commissionPrice;

    /**
     * 结算状态
     */
    @Schema(description = "结算状态")
    private SettleStatus settleStatus;

    /**
     * 商家类型
     */
    @Schema(description = "商家类型 0 供应商 1 商家")
    private StoreType storeType;


    /**
     * 总供货运费
     */
    @Schema(description = "总供货运费")
    private BigDecimal thirdPlatFormFreight;

    /**
     * 礼品卡-现金卡抵扣金额
     */
    @Schema(description = "礼品卡-现金卡抵扣金额")
    private BigDecimal giftCardPrice;

    /**
     * 礼品卡-提货卡抵扣金额
     */
    @Schema(description = "礼品卡-提货卡抵扣金额")
    private BigDecimal pickupGiftCardPrice;

    @Schema(description = "社区团购佣金")
    private BigDecimal communityCommissionPrice;

}
