package com.wanmi.sbc.elastic.api.request.distributionmatter;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.MatterType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author houshuai
 * 分销商品素材新增
 */
@Data
@Schema
public class EsDistributionGoodsMatteAddRequest extends BaseRequest {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 商品sku的id
     */
    @Schema(description = "商品sku的id")
    private String goodsInfoId;

    /**
     * 素材类型
     */
    @Schema(description = "素材类型")
    @NotNull
    private MatterType matterType;

    /**
     * 素材
     */
    @Schema(description = "素材")
    @NotBlank
    private String matter;

    /**
     * 推荐语
     */
    @Schema(description = "推荐语")
    @NotBlank
    private String recommend;

    /**
     * 发布者id
     */
    @Schema(description = "发布者id")
    private String operatorId;

}