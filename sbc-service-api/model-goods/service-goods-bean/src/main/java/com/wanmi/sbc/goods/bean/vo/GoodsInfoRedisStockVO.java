package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品缓存中库存
 * @author  wur
 * @date: 2021/11/5 15:29
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoRedisStockVO extends BasicResponse {

    private static final long serialVersionUID = 7984268074056422550L;

    /**
     * 库存数
     */
    @Schema(description = "库存数")
    private Long stock;

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    private String goodsInfoId;

}

