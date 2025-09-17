package com.wanmi.sbc.goods.api.request.goodsevaluate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sbc-micro-service
 * 商品评价总数
 * @date 2019-04-04 16:22
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsEvaluateCountBySkuIdRequset implements Serializable {

    private static final long serialVersionUID = 1168651340625714204L;

    /***
     * skuId
     */
    @Schema(description = "skuId")
    private String skuId;
}
