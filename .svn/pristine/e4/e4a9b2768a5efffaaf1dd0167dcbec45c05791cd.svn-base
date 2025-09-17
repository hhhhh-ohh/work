package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-07
 */
@Data
@Schema
public class PurchaseCalcMarketingRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "营销信息")
    @NotNull
    private Long marketingId;

    @Schema(description = "采购单信息")
    private PurchaseFrontRequest frontRequest;

    @Schema(description = "客户信息")
    private CustomerDTO customer;

    @Schema(description = "商品信息ids")
    private List<String> goodsInfoIds;

    @Schema(description = "是否是采购单" ,contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    @NotNull
    private Boolean isPurchase;

    @Schema(description = "插件类型")
    private PluginType pluginType;

    @Schema(description = "门店ID")
    private Long storeId;
}
