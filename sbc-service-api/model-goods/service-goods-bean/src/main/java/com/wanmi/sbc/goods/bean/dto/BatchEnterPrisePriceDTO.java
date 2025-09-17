package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品修改企业价参数
 * 增加虚拟goodsId，表示与其他商品相关类的数据关联
 * @Auth baijianzhong
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchEnterPrisePriceDTO implements Serializable {

    private static final long serialVersionUID = 9210716312588692598L;

    /**
     * skuId
     */
    @Schema(description = "skuId")
    private String goodsInfoId;

    /**
     * 企业价格
     */
    @Schema(description = "企业价")
    private BigDecimal enterPrisePrice;

}
