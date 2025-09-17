package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 *es商品查询权重配置表
 */
@Schema
@Data
public class EsGoodsBoostSettingVO extends BasicResponse {

    private static final long serialVersionUID = 4311450957791537904L;

    /**
     * 商品标题
     */
    @Schema(description = "商品标题")
    private Float goodsInfoNameBoost;

    /**
     * 商品标签
     */
    @Schema(description = "商品标签")
    private Float goodsLabelNameBoost;

    /**
     * 商品规格值
     */
    @Schema(description = "商品规格值")
    private Float specTextBoost;

    /**
     * 商品属性值
     */
    @Schema(description = "商品属性值")
    private Float goodsPropDetailNestNameBoost;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌")
    private Float brandNameBoost;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    private Float cateNameBoost;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    private Float goodsSubtitleBoost;
}
