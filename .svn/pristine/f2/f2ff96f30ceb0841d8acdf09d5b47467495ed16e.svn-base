package com.wanmi.sbc.goods.api.request.goods;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
*
 * @description  查询代销商品信息
 * @author  wur
 * @date: 2022/7/28 15:07
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsImageRequest implements Serializable {

    private static final long serialVersionUID = 5621833007962215905L;
    /**
     * 代销商品Id
     */
    @Schema(description = "商品Id")
    @NotEmpty
    private String goodsId;
}
