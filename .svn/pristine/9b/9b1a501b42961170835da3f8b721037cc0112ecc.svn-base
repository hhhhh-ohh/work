package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuitsRelationGoodsInfoVO extends BasicResponse {

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String  goodInfoId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsInfoName;


    /**
     *  商品图片 -默认主图
     */
    @Schema(description = "商品图片 -默认主图")
    private String  mainImage;

    /**
     * 原价=市场价
     */
    @Schema(description = "原价=市场价")
    private BigDecimal marketPrice;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String specDetail;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private  Long goodsInfoNum;



}
