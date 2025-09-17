package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName CateRelatedRecommendDetailVO
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/26 15:54
 **/
@Schema
@Data
public class CateRelatedRecommendDetailVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品关联分类id
     */
    @Schema(description = "商品推荐分类id")
    private Long id;

    /**
     * 商品分类id
     */
    @Schema(description = "商品分类id")
    private Long cateId;

    /**
     * 商品推荐分类id
     */
    @Schema(description = "商品id")
    private String cateName;

    /**
     * 商品分类id
     */
    @Schema(description = "商品分类id")
    private Long relatedCateId;

    /**
     * 商品推荐分类id
     */
    @Schema(description = "商品id")
    private String relatedCateName;

    /**
     * 关联商品分类提升度
     */
    @Schema(description = "关联商品分类提升度")
    private Integer lift;

    /**
     * 关联商品分类权重
     */
    @Schema(description = "关联商品分类权重")
    private BigDecimal weight;
}
