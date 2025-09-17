package com.wanmi.sbc.account.finance.record.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
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
 * <p>对账单结构</p>
 * Created by of628-wenzhi on 2017-12-05-下午4:02.
 */
@Data
@Entity
@Table(name = "reconciliation")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reconciliation implements Serializable {

    private static final long serialVersionUID = 7714766237836795260L;
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 商家id
     */
    @Column(name = "supplier_id")
    private Long supplierId;

    /**
     * 店铺id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 支付方式
     */
    @Column(name = "pay_way")
    @Enumerated(EnumType.STRING)
    private PayWay payWay;

    /**
     * 金额
     */
    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * 积分
     */
    @Column(name = "points")
    private Long points;

    /**
     * 积分抵现金额
     */
    @Column(name = "points_price")
    private BigDecimal pointsPrice;

    /**
     * 礼品卡抵扣金额
     */
    @Column(name = "gift_card_price")
    private BigDecimal giftCardPrice;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 客户名称
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * 订单号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 退单号
     */
    @Column(name = "return_order_code")
    private String returnOrderCode;

    /**
     * 下单时间
     */
    @Column(name = "order_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime orderTime;

    /**
     * 支付/退款时间
     */
    @Column(name = "trade_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime tradeTime;

    /**
     * 交易类型，0：收入 1：退款
     */
    @Column(name = "type")
    private Byte type;

    /**
     * 优惠金额
     */
    @Column(name = "discounts")
    private BigDecimal discounts;


    /**
     * 交易流水号
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 交易流水号
     */
    @Column(name = "gift_card_type")
    @Enumerated
    private GiftCardType giftCardType;
}
