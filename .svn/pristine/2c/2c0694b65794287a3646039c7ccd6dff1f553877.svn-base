package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * <p>批量查询SKU市场价参数</p>
 * Created by of628-wenzhi on 2020-12-14-9:28 下午.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class GoodsInfoMarketingPriceByNosRequest extends GoodsBaseRequest {
    private static final long serialVersionUID = 9187246070612239373L;

    /**
     * SKU NO集合
     */
    @Schema(description = "SKU NO集合")
    @NotEmpty
    private List<String> goodsInfoNos;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;
}
