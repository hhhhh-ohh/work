package com.wanmi.sbc.goods.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*
 * @description   运费模板计算
 * @author  wur
 * @date: 2022/7/7 15:23
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightGoodsInfoVO implements Serializable {

    private static final long serialVersionUID = -2243723680863364216L;

    /**
     * 商品Id
     */
    @Schema(description = "商品Id")
    private String goodsInfoId;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Long num;

    /**
     * 价格
     */
    private BigDecimal price;

    public BigDecimal getBigNum() {
        return new BigDecimal(num);
    }


}
