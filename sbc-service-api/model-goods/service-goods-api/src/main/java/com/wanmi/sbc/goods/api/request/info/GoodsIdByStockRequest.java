package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsIdByStockRequest extends BaseRequest {
    private static final long serialVersionUID = -4029722441279284624L;

    /**
     * 店铺Id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 商家库存预警值
     */
    @Schema(description = "商家库存预警值")
    private Long warningStock;
}
