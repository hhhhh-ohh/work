package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品信息减量库存传输对象
 * @author lipeng
 * @dateTime 2018/11/6 下午2:29
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema
@Data
@Builder
public class GoodsInfoMinusStockDTO implements Serializable {

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

    /***
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;
}

