package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wanmi.sbc.goods.bean.vo.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品SKU视图响应
 * Created by liguang on 2022-03-04 15:44:34.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeConfirmGoodsResponse extends BasicResponse {

    private static final long serialVersionUID = -193370700926139956L;

    @Schema(description = "商品SPU信息")
    @Builder.Default
    private List<GoodsVO> goodses = Lists.newArrayList();

    @Schema(description = "商品SKU信息")
    @Builder.Default
    private List<GoodsInfoVO> goodsInfos = Lists.newArrayList();

    @Schema(description = "供应商商品SKU信息")
    @Builder.Default
    private List<GoodsInfoVO> providerGoodsInfos = Lists.newArrayList();

    @Schema(description = "商品标签列表")
    @Builder.Default
    private List<GoodsLabelVO> goodsLabelList = Lists.newArrayList();

    @Schema(description = "区间价列表")
    @Builder.Default
    private List<GoodsIntervalPriceVO> goodsIntervalPriceVOList = Lists.newArrayList();

    @Schema(description = "商品规格列表")
    @Builder.Default
    private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelVOS = Lists.newArrayList();

    @Schema(description = "sku的redis库存 包含供应商商品")
    @Builder.Default
    private Map<String, Long> skuRedisStockMap = Maps.newHashMap();
}
