package com.wanmi.sbc.order.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/**
 * <p>订单商品购买请求结构</p>
 * Created by of628-wenzhi on 2017-07-13-上午9:20.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class TradeItemRequest extends BaseRequest {

    private static final long serialVersionUID = 5109763872537870011L;

    /**
     * skuId
     */
    @Schema(description = "skuId")
    @NotBlank
    private String skuId;

    /**
     * 购买数量
     */
    @Schema(description = "购买数量")
    @Range(min = 1)
    private long num;

    /**
     * 购买商品的价格
     */
    @Schema(description = "购买商品的价格")
    private BigDecimal price;

    /**
     * 是否是秒杀抢购商品
     */
    private Boolean isFlashSaleGoods;

    /**
     * 秒杀抢购商品Id
     */
    private Long flashSaleGoodsId;


    /**
     * 是否是预约抢购商品
     */
    private Boolean isAppointmentSaleGoods = Boolean.FALSE;

    /**
     * 抢购活动Id
     */
    private Long appointmentSaleId;

    /**
     * 是否是预售商品
     */
    private Boolean isBookingSaleGoods = Boolean.FALSE;

    /**
     * 预售活动Id
     */
    private Long bookingSaleId;

    /**
     * 促销活动id
     */
    private Long marketingId;
}
