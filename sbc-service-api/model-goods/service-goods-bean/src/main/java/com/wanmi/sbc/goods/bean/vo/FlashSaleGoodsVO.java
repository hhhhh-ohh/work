package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.FlashSaleGoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>抢购商品表VO</p>
 *
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Schema
@Data
public class FlashSaleGoodsVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 活动日期：2019-06-11
     */
    @Schema(description = "活动日期：2019-06-11")
    private String activityDate;

    /**
     * 活动时间：13:00
     */
    @Schema(description = "活动时间：13:00")
    private String activityTime;

    /**
     * skuID
     */
    @Schema(description = "skuID")
    private String goodsInfoId;

    /**
     * spuID
     */
    @Schema(description = "spuID")
    private String goodsId;

    /**
     * 抢购价
     */
    @Schema(description = "抢购价")
    private BigDecimal price;

    /**
     * 上限数量
     */
    @Schema(description = "上限数量")
    @Max(999999999L)
    private Integer stock;

    /**
     * 抢购销量
     */
    @Schema(description = "抢购销量")
    private Long salesVolume = 0L;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long cateId;

    /**
     * 限购数量
     */
    @Schema(description = "限购数量")
    private Integer maxNum = 0;

    /**
     * 起售数量
     */
    @Schema(description = "起售数量")
    private Integer minNum;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;
    /**
     * 包邮标志，0：不包邮 1:包邮
     */
    @Schema(description = "包邮标志，0：不包邮 1:包邮")
    private Integer postage;

    /**
     * 删除标志，0:未删除 1:已删除
     */
    @Schema(description = "删除标志，0:未删除 1:已删除")
    private DeleteFlag delFlag;

    /**
     * 分类信息
     */
    @Schema(description = "分类信息")
    private FlashSaleCateVO flashSaleCateVO;

    /**
     * SPU信息
     */
    @Schema(description = "SPU信息")
    private GoodsVO goods;

    /**
     * SKU信息
     */
    @Schema(description = "SKU信息")
    private GoodsInfoVO goodsInfo;

    /**
     * 可兑换的最大库存
     */
    @Schema(description = "可兑换的最大库存")
    private Long maxStock;

    /**
     * 规格信息
     */
    @Schema(description = "规格信息")
    private String specText;

    /**
     * 秒杀商品状态
     */
    @Schema(description = "秒杀商品状态")
    private FlashSaleGoodsStatus flashSaleGoodsStatus;

    /**
     * 是否可修改 0否 1是
     */
    @Schema(description = "是否可编辑")
    private BoolFlag modifyFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

    /**
     * 活动日期+时间
     */
    @Schema(description = "活动日期+时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activityFullTime;

    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 活动名称
     */
    @Schema(description = "activityId")
    private String activityId;

    /**
     * 活动名称
     */
    @Schema(description = "activityName")
    private String activityName;

    /**
     * 活动开始时间
     */
    @Schema(description = "startTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 活动结束时间
     */
    @Schema(description = "endTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 状态 0:开始 1:暂停
     */
    @Schema(description = "status")
    private Integer status;

    /**
     * 预热时间
     */
    @Schema(description = "预热时间")
    private Integer preTime;

    /**
     * 类型 1:限时购 0:秒杀
     */
    @Schema(description = "类型 1:限时购 0:秒杀")
    private Integer type;

    /**
     * 预热开始时间
     */
    @Schema(description = "预热开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime preStartTime;

    /**
     * 增加上限数量
     */
    @Schema(description = "增加上限数量")
    private Integer increaseStock;

    /**
     * 营销商品状态
     */
    @Schema(description = "营销商品状态")
    private MarketingGoodsStatus marketingGoodsStatus;


}