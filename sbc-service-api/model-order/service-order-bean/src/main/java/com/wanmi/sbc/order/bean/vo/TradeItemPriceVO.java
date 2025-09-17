package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>订单商品价格，特定于编辑订单前的展示场景，只包含skuId和计算会员，区间价后的单价</p>
 * Created by of628-wenzhi on 2018-05-25-下午4:12.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class TradeItemPriceVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "skuId")
    private String skuId;

    /**
     * 商品当前最新销售单价
     */
    @Schema(description = "商品当前最新销售单价")
    private BigDecimal price;
}
