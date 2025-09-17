package com.wanmi.sbc.vas.api.request.recommend.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName GoodsRelatedRecommendInfoListRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/24 17:11
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendInfoListRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

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
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String storeName;

    /**
     * 商品SPU编码
     */
    @Schema(description = "商品SPU编码")
    private String goodsNo;

    /**
     * 商品sku编码
     */
    @Schema(description = "商品SKU编码")
    private String goodsInfoNo;

    /**
     * 商品类目
     */
    @Schema(description = "商品类目")
    private String goodsCate;

    /**
     * 商品类目id
     */
    @Schema(description = "商品类目id")
    private Long goodsCateId;

    /**
     * 商品类目idList
     */
    @Schema(description = "商品类目idList")
    List<Long> goodsCateIds;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌")
    private String goodsBrand;

    /**
     * 商品品牌id
     */
    @Schema(description = "商品品牌id")
    private Long goodsBrandId;
    /**
     * 最小市场价
     */
    @Schema(description = "最小市场价")
    private BigDecimal minMarketPrice;

    /**
     * 最大市场价
     */
    @Schema(description = "最大市场价")
    private BigDecimal maxMarketPrice;

    /**
     * 最小实际销量
     */
    @Schema(description = "实际销量查询区间：最小实际销量")
    private Long minGoodsSalesNum;

    /**
     * 最大实际销量
     */
    @Schema(description = "实际销量查询区间：最大实际销量")
    private Long maxGoodsSalesNum;

    /**
     * 最小库存
     */
    @Schema(description = "库存查询区间：最小库存")
    private Long minStock;

    /**
     * 最大库存
     */
    @Schema(description = "库存查询区间：最大库存")
    private Long maxStock;

    /**
     * 标签ID
     */
    @Schema(description = "标签id labelId")
    private Long labelId;

    /**
     * 上下架状态
     */
    @Schema(description = "上下架状态 1上架")
    private Integer addedFlag;

    /**
     * 供应商是否可售
     */
    @Schema(description = "是否可售 1可售")
    private Integer vendibility;
}
