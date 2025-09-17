package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName ManualRecommendGoodsInfoVO
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/23 11:20
 **/
@Schema
@Data
public class ManualRecommendGoodsInfoVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品推荐管理id")
    private Long id;

    /**
     * 商品id
     */
    @Schema(description = "商品id")
    private String goodsId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商品SPU编码
     */
    @Schema(description = "商品SPU编码")
    private String goodsNo;

    /**
     * 商品图片
     */
    @Schema(description = "商品图片")
    private String goodsImage;

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
}
