package com.wanmi.sbc.account.finance.record.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.enums.SettleStatus;
import com.wanmi.sbc.common.enums.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description 拉卡拉分账结算单
 * @author edz
 * @date 2022/7/18 17:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lakala_settlement")
@Builder
public class LakalaSettlement {

    /**
     * 用于生成结算单号，结算单号自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settle_id")
    private Long settleId;

    @Column(name = "settle_uuid")
    private String settleUuid;

    /**
     * 结算单创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 结算单开始时间
     */
    @Column(name = "start_time")
    private String startTime;

    /**
     * 结算单结束时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 商家Id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 商家类型
     */
    @Column(name = "store_type")
    private StoreType storeType;


    /**
     * 分账总金额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 供应商分账总额 (供货额+运费)
     */
    @Column(name = "total_provider_price")
    private BigDecimal totalProviderPrice;

    /**
     * 店铺分账总额
     */
    @Column(name = "total_store_price")
    private BigDecimal totalStorePrice;

    /**
     * 分销佣金分账总额
     */
    @Column(name = "total_commission_price")
    private BigDecimal totalCommissionPrice;

    /**
     * 平台佣金分账总额
     */
    @Column(name = "total_platform_price")
    private BigDecimal totalPlatformPrice;

    /**
     * 商品实付总额
     */
    @Column(name = "total_split_Pay_price")
    private BigDecimal totalSplitPayPrice;

    /**
     * 通用券优惠总额
     */
    @Column(name = "total_common_coupon_price")
    private BigDecimal totalCommonCouponPrice;

    /**
     * 积分数量
     */
    @Column(name = "total_points")
    private Long totalPoints;

    /**
     * 积分抵扣总额
     */
    @Column(name = "point_price")
    private BigDecimal pointPrice;

    /**
     * 商品供货总额
     */
    @Column(name = "provider_goods_total_price")
    private BigDecimal providerGoodsTotalPrice;

    /**
     * 商品供货运费总额
     */
    @Column(name = "provider_delivery_total_price")
    private BigDecimal providerDeliveryTotalPrice;

    /**
     * 总运费(包含供应商运费)
     */
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    /**
     * 结算状态
     */
    @Column(name = "lakala_ledger_status")
    @Enumerated
    private LakalaLedgerStatus lakalaLedgerStatus;

    @Column(name = "gift_card_price")
    private BigDecimal giftCardPrice;

    /**
     * 代销商家店铺id
     */
    @Column(name = "supplier_store_id")
    private Long supplierStoreId;

}
