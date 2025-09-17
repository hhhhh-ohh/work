package com.wanmi.sbc.vas.api.request.recommend.manualrecommendgoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName ManualRecommendGoodsInfoListRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/23 11:40
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsInfoListRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-主键List
     */
    @Schema(description = "批量查询-主键List")
    private List<Long> idList;

    /**
     * 主键
     */
    @Schema(description = "主键")
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
     * 商品SPU编码
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
    private List<Long> goodsCateIds;

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
     * 权重
     */
    @Schema(description = "权重")
    private BigDecimal weight;

    /**
     * 搜索条件:创建时间开始
     */
    @Schema(description = "搜索条件:创建时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /**
     * 搜索条件:创建时间截止
     */
    @Schema(description = "搜索条件:创建时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 搜索条件:更新时间开始
     */
    @Schema(description = "搜索条件:更新时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:更新时间截止
     */
    @Schema(description = "搜索条件:更新时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortColumn;

    /**
     * 排序方式
     */
    @Schema(description = "排序方式")
    private String sortRole;

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
     *
     */
    @Schema(description = "商品标签")
    private Long labelId;
}
