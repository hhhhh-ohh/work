package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description 加价购
 * @author edz
 * @date 2022/11/28 11:13
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountPriceItemForPreferentialVO extends BasicResponse {

    @Schema(description = "加价购商品ID")
    private String goodsInfoId;

    @Schema(description = "加价购价格")
    private BigDecimal preferentialAmount;

    @Schema(description = "加价购活动ID")
    private Long marketingId;

    @Schema(description = "商品详情")
    private GoodsInfoVO goodsInfoVO;

}