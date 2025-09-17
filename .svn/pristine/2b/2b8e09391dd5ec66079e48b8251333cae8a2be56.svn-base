package com.wanmi.sbc.marketing.api.request.preferential;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author edz
 * @className PreferentialGoodsAddRequest
 * @description 加价购商品
 * @date 2022/11/17 14:41
 **/
@Data
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferentialGoodsRequest implements Serializable {

    @Schema(description = "加价购活动关联商品主键")
    private Long preferentialGoodsDetailId;

    @Schema(description = "营销阶梯ID")
    private Long preferentialLevelId;

    @Schema(description = "商品ID")
    private String goodsInfoId;

    @Schema(description = "加价购活动购买价格")
    private BigDecimal preferentialAmount;

    @Schema(description = "加价购活动ID")
    private Long marketingId;
}
