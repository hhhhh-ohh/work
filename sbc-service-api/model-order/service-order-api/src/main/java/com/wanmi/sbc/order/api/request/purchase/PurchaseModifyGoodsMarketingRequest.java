package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-03
 */
@Data
@Schema
public class PurchaseModifyGoodsMarketingRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品id")
    @NotBlank
    private String goodsInfoId;

    @Schema(description = "营销id")
    @NotNull
    private Long marketingId;

    @Schema(description = "客户信息")
    private CustomerDTO customer;

    @Schema(description = "营销类型")
    private PluginType pluginType;

    @Schema(description = "门店ID")
    private Long storeId;
}
