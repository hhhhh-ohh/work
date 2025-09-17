package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>积分商品表通用查询请求参数</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsQueryRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-积分商品idList
     */
    @Schema(description = "批量查询-积分商品idList")
    private List<String> pointsGoodsIds;

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品id")
    private String pointsGoodsId;

    /**
     * SpuId
     */
    @Schema(description = "SpuId")
    private String goodsId;

    /**
     * SpuId-批量查询
     */
    @Schema(description = "SpuId-批量查询")
    private List<String> goodsIds;

    /**
     * SkuId
     */
    @Schema(description = "SkuId")
    private String goodsInfoId;

    /**
     * SpuId-批量查询
     */
    @Schema(description = "SkuId-批量查询")
    private List<String> goodsInfoIds;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Integer cateId;

    /**
     * 库存
     */
    @Schema(description = "库存")
    private Long stock;

    /**
     * 库存最小值
     */
    @Schema(description = "库存最小值")
    private Long minStock;

    /**
     * 销量
     */
    @Schema(description = "销量")
    private Long sales;

    /**
     * 结算价格
     */
    @Schema(description = "结算价格")
    private BigDecimal settlementPrice;

    /**
     * 兑换积分
     */
    @Schema(description = "兑换积分")
    private Long points;

    /**
     * 兑换积分最大值
     */
    @Schema(description = "兑换积分最大值")
    private Long maxPoints;

    /**
     * 兑换积分区间开始
     */
    @Schema(description = "兑换积分区间开始")
    private Long pointsSectionStart;

    /**
     * 兑换积分区间结尾
     */
    @Schema(description = "兑换积分区间结尾")
    private Long pointsSectionEnd;

    /**
     * 是否启用 0：停用，1：启用
     */
    @Schema(description = "是否启用 0：停用，1：启用")
    private EnableStatus status;

    /**
     * 推荐标价, 0: 未推荐 1: 已推荐
     */
    @Schema(description = "推荐标价, 0: 未推荐 1: 已推荐")
    private BoolFlag recommendFlag;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * SPU编码
     */
    @Schema(description = "SPU编码")
    private String goodsNo;

    /**
     * SKU编码
     */
    @Schema(description = "SKU编码")
    private String goodsInfoNo;

    /**
     * SPU市场价
     */
    @Schema(description = "SPU市场价")
    private BigDecimal goodsMarketPrice;

    /**
     * 商品SKU名称
     */
    @Schema(description = "商品SKU名称")
    private String goodsInfoName;

    /**
     * SKU市场价
     */
    @Schema(description = "SKU市场价")
    private BigDecimal goodsInfoMarketPrice;

    /**
     * 搜索条件:兑换开始时间开始
     */
    @Schema(description = "搜索条件:兑换开始时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeBegin;
    /**
     * 搜索条件:兑换开始时间截止
     */
    @Schema(description = "搜索条件:兑换开始时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeEnd;

    /**
     * 搜索条件:兑换结束时间开始
     */
    @Schema(description = "搜索条件:兑换结束时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeBegin;
    /**
     * 搜索条件:兑换结束时间截止
     */
    @Schema(description = "搜索条件:兑换结束时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeEnd;

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
     * 搜索条件:修改时间开始
     */
    @Schema(description = "搜索条件:修改时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:修改时间截止
     */
    @Schema(description = "搜索条件:修改时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 删除标识,0: 未删除 1: 已删除
     */
    @Schema(description = "删除标识,0: 未删除 1: 已删除")
    private DeleteFlag delFlag;

    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 商品是否可售
     */
    @Schema(description = "商品是否可售")
    private Boolean saleFlag;

}