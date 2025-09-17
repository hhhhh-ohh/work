package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.PurchaseCalcAmountDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * <p>购物车价格计算请求参数</p>
 * author: sunkun
 * Date: 2018-11-30
 */
@Data
@Schema
public class PurchaseCalcAmountRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品信息")
    private List<String> goodsInfoIds;

    @Schema(description = "会员id")
    private String customerId;

    @Schema(description = "采购单信息")
    @NotNull
    private PurchaseCalcAmountDTO purchaseCalcAmount;

}
