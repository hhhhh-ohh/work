package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName CateRelatedRecommendInfoVO
 * @Description 分类相关性推荐VO
 * @Author lvzhenwei
 * @Date 2020/11/26 14:40
 **/
@Schema
@Data
public class CateRelatedRecommendInfoVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品分类id
     */
    @Schema(description = "商品分类id")
    private Long parenId;

    /**
     * 商品分类id
     */
    @Schema(description = "商品分类id")
    private Long key;

    /**
     * 商品关联分类id
     */
    @Schema(description = "商品推荐分类id")
    private Long id;

    /**
     * 关联商品分类数量
     */
    @Schema(description = "关联商品分类数量")
    private Long num;

    /**
     * 关联商品分类提升度
     */
    @Schema(description = "关联商品分类提升度")
    private BigDecimal lift;

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
     * 商品推荐分类子分类列表
     */
    @Schema(description = "商品推荐分类子分类列表")
    private List<CateRelatedRecommendInfoVO> children;
}
