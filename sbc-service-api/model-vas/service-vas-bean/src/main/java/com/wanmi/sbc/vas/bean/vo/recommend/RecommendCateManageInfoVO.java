package com.wanmi.sbc.vas.bean.vo.recommend;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RecommendCateManageInfoVO
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/19 13:56
 **/
@Schema
@Data
public class RecommendCateManageInfoVO extends BasicResponse {
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
     * 商品推荐分类id
     */
    @Schema(description = "商品id")
    private Long recommendCateId;

    /**
     * 商品分类权重
     */
    @Schema(description = "商品分类权重")
    private Integer weight;

    /**
     * 商品分类是否禁推
     */
    @Schema(description = "商品分类是否禁推")
    private Integer noPushType;

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
    private List<RecommendCateManageInfoVO> children;

}
