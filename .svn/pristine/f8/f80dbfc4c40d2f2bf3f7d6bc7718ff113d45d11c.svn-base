package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName GoodsRelatedRecommendInfoVO
 * @Description 商品相关性推荐VO
 * @Author lvzhenwei
 * @Date 2020/11/24 16:21
 **/
@Schema
@Data
public class GoodsRelatedRecommendInfoVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品推荐管理id")
    private Long id;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String goodsId;

    /**
     * 关联商品id
     */
    @Schema(description = "关联商品id")
    private String relatedGoodsId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodsImage;

    /**
     * 关联商品名称
     */
    @Schema(description = "商品名称")
    private String relatedGoodsName;

    /**
     * 关联商品图片
     */
    @Schema(description = "关联商品图片")
    private String relatedGoodsImage;

    /**
     * 商品SPU编码
     */
    @Schema(description = "商品SPU编码")
    private String goodsNo;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    private String goodsCate;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌")
    private String goodsBrand;

    /**
     * 所属商家
     */
    @Schema(description = "所属商家")
    private String storeName;

    /**
     * 关联所属商家
     */
    @Schema(description = "关联所属商家")
    private String relatedStoreName;

    /**
     * 商品市场价
     */
    @Schema(description = "商品市场价")
    private BigDecimal marketPrice;

    /**
     * 积分价
     */
    @Schema(description = "积分价")
    private Long buyPoint;

    /**
     * 商品库存
     */
    @Schema(description = "商品库存")
    private Long stock;

    /**
     * 商品销量
     */
    @Schema(description = "商品销量")
    private Long goodsSalesNum;


    /**
     * 权重
     */
    @Schema(description = "权重")
    private BigDecimal weight;

    /**
     * 关联商品数量
     */
    @Schema(description = "关联商品数量")
    private Long relatedNum;

    /**
     * 提升度
     */
    @Schema(description = "提升度")
    private BigDecimal lift;

}
