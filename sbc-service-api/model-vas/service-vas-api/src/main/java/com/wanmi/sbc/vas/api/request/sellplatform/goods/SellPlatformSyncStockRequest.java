package com.wanmi.sbc.vas.api.request.sellplatform.goods;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  商品更新
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformSyncStockRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;


    /**
     * 商家自定义商品ID
     */
    @NotEmpty
    @Schema(description = "SPUId")
    private String out_product_id;
    /**
     * 商家自定义skuID
     */
    @Schema(description = "商家自定义skuID")
    private String out_sku_id;

    @Min(1)
    @NotNull
    @Schema(description = "库存，数字类型，最大不超过10000000（1000万")
    private Integer stock_num;


}
