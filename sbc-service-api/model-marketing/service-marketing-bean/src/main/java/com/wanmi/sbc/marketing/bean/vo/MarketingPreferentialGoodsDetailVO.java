package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author edz
 * @className MarketingPreferentialGoodsDetailVO
 * @description 加价购商品信息实体
 * @date 2022/11/18 11:18
 **/
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MarketingPreferentialGoodsDetailVO implements Serializable {

    @Schema(description = "主键")
    private Long preferentialDetailId;

    @Schema(description = "加价购档次阶梯ID")
    private Long preferentialLevelId;

    @Schema(description = "商品ID")
    private String goodsInfoId;

    @Schema(description = "加价购活动金额")
    private BigDecimal preferentialAmount;

    @Schema(description = "加价购活动ID")
    private Long marketingId;

    @Schema(description = "商品详情")
    private GoodsInfoVO goodsInfoVO;
}
