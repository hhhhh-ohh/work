package com.wanmi.sbc.vas.bean.vo.sellplatform;

import com.wanmi.sbc.common.enums.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SellPlatformReturnOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 退单号
     */
    @Schema(description = "退单号")
    private String id;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @NotBlank
    private String tid;

    private SellPlatformRefundCauseVO refundCause;

    /**
     * 客户信息 买家信息
     */
    @Schema(description = "客户信息 买家信息")
    private SellPlatformBuyerVO buyer;


    /**
     * 退货商品信息
     */
    @Schema(description = "退货商品信息")
    private List<SellPlatformReturnItemVO> returnItems;

    /**
     * 退单赠品信息
     */
    @Schema(description = "退单赠品信息")
    private List<SellPlatformReturnItemVO> returnGifts = new ArrayList<>();

    /**
     * 退货总金额
     */
    @Schema(description = "退货总金额")
    private SellPlatformReturnPriceVO returnPrice;


    /**
     * 售后类型，1:退款,2:退款退货
     */
    @Schema(description = "售后类型，1:退款,2:退款退货")
    private Integer type;

    @Schema(description = "代销平台退单号")
    private String sellPlatformReturnId;

}

