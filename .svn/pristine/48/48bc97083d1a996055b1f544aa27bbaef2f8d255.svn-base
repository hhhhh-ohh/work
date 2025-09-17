package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionFreightBearFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionSynPriceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 商品代销设置
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Schema
@Data
public class GoodsCommissionConfigVO extends BasicResponse {

    private static final long serialVersionUID = 7106082109163140352L;

    /**
     * 商家ID
     */
    @Schema(description = "商家ID")
    private Long storeId;

    /**
     * 设价类型：0.智能设价 1.手动设价
     */
    @Schema(description = "设价类型：0.智能设价 1.手动设价")
    private CommissionSynPriceType synPriceType;

    /**
     * 默认加价比例
     */
    @Schema(description = "默认加价比例")
    private BigDecimal addRate;

    /**
     * 低价是否自动下架：0.关 1.开
     */
    @Schema(description = "低价是否自动下架：0.关 1.开")
    private DefaultFlag underFlag;

    /**
     * 商品信息自动同步：0.关 1.开
     */
    @Schema(description = "商品信息自动同步：0.关 1.开")
    private DefaultFlag infoSynFlag;

    /**
     * 代销商品运费承担：0.买家 1.卖家
     */
    @Schema(description = "代销商品运费承担：0.买家 1.卖家")
    private CommissionFreightBearFlag freightBearFlag;
}
