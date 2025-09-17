package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsMarketingDTO;
import com.wanmi.sbc.order.api.response.purchase.PurchaseResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Data
@Schema
public class ValidateAndSetGoodsMarketingsRequest extends BaseRequest {

    @Schema(description = "采购单信息")
    @NotNull
    private PurchaseResponse response;

    @Schema(description = "营销信息")
    private List<GoodsMarketingDTO> goodsMarketingDTOList;
}
