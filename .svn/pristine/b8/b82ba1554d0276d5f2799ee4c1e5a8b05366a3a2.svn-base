package com.wanmi.sbc.elastic.api.request.pointsgoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.PointsGoodsStatus;

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
public class EsPointsGoodsPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-积分商品idList
     */
    @Schema(description = "批量查询-积分商品idList", hidden = true)
    private List<String> pointsGoodsIdList;

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品id", hidden = true)
    private String pointsGoodsId;

    /**
     * SpuId
     */
    @Schema(description = "SpuId", hidden = true)
    private String goodsId;

    /**
     * SkuId
     */
    @Schema(description = "SkuId", hidden = true)
    private String goodsInfoId;

    /**
     * 分类id
     */
    @Schema(description = "分类id")
    private Integer cateId;

    /**
     * 库存
     */
    @Schema(description = "库存", hidden = true)
    private Long stock;

    /**
     * 库存最小值
     */
    @Schema(description = "库存最小值", hidden = true)
    private Long minStock;

    /**
     * 销量
     */
    @Schema(description = "销量", hidden = true)
    private Long sales;

    /**
     * 结算价格
     */
    @Schema(description = "结算价格", hidden = true)
    private BigDecimal settlementPrice;

    /**
     * 兑换积分
     */
    @Schema(description = "兑换积分", hidden = true)
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
    @Schema(description = "是否启用 0：停用，1：启用", hidden = true)
    private EnableStatus status;

    /**
     * 推荐标价, 0: 未推荐 1: 已推荐
     */
    @Schema(description = "推荐标价, 0: 未推荐 1: 已推荐", hidden = true)
    private BoolFlag recommendFlag;

    /**
     * 活动状态 1: 进行中, 2: 暂停中,3: 未开始,4: 已结束
     */
    @Schema(description = "活动状态 1: 进行中, 2: 暂停中,3: 未开始,4: 已结束")
    private PointsGoodsStatus pointsGoodsStatus;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * SPU编码
     */
    @Schema(description = "SPU编码", hidden = true)
    private String goodsNo;

    /**
     * SKU编码
     */
    @Schema(description = "SKU编码", hidden = true)
    private String goodsInfoNo;

    /**
     * SPU市场价
     */
    @Schema(description = "SPU市场价", hidden = true)
    private BigDecimal goodsMarketPrice;

    /**
     * 商品SKU名称
     */
    @Schema(description = "商品SKU名称", hidden = true)
    private String goodsInfoName;

    /**
     * SKU市场价
     */
    @Schema(description = "SKU市场价", hidden = true)
    private BigDecimal goodsInfoMarketPrice;

    /**
     * 搜索条件:兑换开始时间开始
     */
    @Schema(description = "搜索条件:兑换开始时间开始", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeBegin;
    /**
     * 搜索条件:兑换开始时间截止
     */
    @Schema(description = "搜索条件:兑换开始时间截止", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTimeEnd;

    /**
     * 搜索条件:兑换结束时间开始
     */
    @Schema(description = "搜索条件:兑换结束时间开始", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeBegin;
    /**
     * 搜索条件:兑换结束时间截止
     */
    @Schema(description = "搜索条件:兑换结束时间截止", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTimeEnd;

    /**
     * 搜索条件:创建时间开始
     */
    @Schema(description = "搜索条件:创建时间开始", hidden = true)
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
    @Schema(description = "创建人", hidden = true)
    private String createPerson;

    /**
     * 搜索条件:修改时间开始
     */
    @Schema(description = "搜索条件:修改时间开始", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:修改时间截止
     */
    @Schema(description = "搜索条件:修改时间截止", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * 修改人
     */
    @Schema(description = "修改人", hidden = true)
    private String updatePerson;

    /**
     * 删除标识,0: 未删除 1: 已删除
     */
    @Schema(description = "删除标识,0: 未删除 1: 已删除", hidden = true)
    private DeleteFlag delFlag;

    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 商品是否可售
     */
    @Schema(description = "商品是否可售", hidden = true)
    private Boolean saleFlag;

    /**
     * 排序标识
     * 0:我能兑换
     * 1:积分价格数升序
     * 2:积分价格数倒序
     * 3:市场价升序
     * 4:市场价倒序
     */
    @Schema(description = "排序标识")
    private Integer sortFlag;

    @Schema(description = "填充积分商品分类", hidden = true)
    private Boolean fillCateFlag;

    /**
     * 库存状态
     * null:所有,1:有货,0:无货
     */
    @Schema(description = "1:有货，其他:所有")
    private Integer stockFlag;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    @Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
    private Integer goodsType;
}