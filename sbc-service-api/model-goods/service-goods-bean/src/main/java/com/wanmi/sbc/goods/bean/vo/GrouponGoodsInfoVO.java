package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>拼团活动商品信息表VO</p>
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
public class GrouponGoodsInfoVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团商品ID
	 */
    @Schema(description = "拼团商品ID")
	private String grouponGoodsId;

	/**
	 * 单品Id
	 */
    @Schema(description = "SKU编号")
	private String goodsInfoId;

	/**
	 * 拼团价格
	 */
    @Schema(description = "拼团价格")
	private BigDecimal grouponPrice;

	/**
	 * 起售数量
	 */
    @Schema(description = "起售数量")
	private Integer startSellingNum;

	/**
	 * 限购数量
	 */
    @Schema(description = "限购数量")
	private Integer limitSellingNum;

	/**
	 * 拼团活动ID
	 */
    @Schema(description = "拼团活动ID")
	private String grouponActivityId;

	/**
	 * 拼团分类ID
	 */
    @Schema(description = "拼团分类ID")
	private String grouponCateId;

	/**
	 * 店铺ID
	 */
    @Schema(description = "店铺ID")
	private String storeId;

	/**
	 * SPU编号
	 */
    @Schema(description = "SPU编号")
	private String goodsId;

	/**
	 * 商品销售数量
	 */
    @Schema(description = "商品销售数量")
	private Integer goodsSalesNum;

	/**
	 * 订单数量
	 */
    @Schema(description = "订单数量")
	private Integer orderSalesNum;

	/**
	 * 交易额
	 */
    @Schema(description = "交易额")
	private BigDecimal tradeAmount;

	/**
	 * 成团后退单数量
	 */
    @Schema(description = "成团后退单数量")
	private Integer refundNum;

	/**
	 * 已成团人数
	 */
	@Schema(description = "已成团人数")
	private Integer alreadyGrouponNum;

	/**
	 * 成团后退单金额
	 */
    @Schema(description = "成团后退单金额")
	private BigDecimal refundAmount;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsInfoName;

	/**
	 * SKU编码
	 */
	@Schema(description = "SKU编码")
	private String goodsInfoNo;

	/**
	 * 市场价
	 */
	@Schema(description = "市场价")
	private BigDecimal marketPrice;

	/**
	 * 活动开始时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "活动开始时间")
	private LocalDateTime startTime;

	/**
	 * 活动结束时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "活动结束时间")
	private LocalDateTime endTime;

	/**
	 * 规格名称
	 */
	@Schema(description = "规格名称")
	private String specText;

	@Schema(description = "商品信息")
	private GoodsInfoVO goodsInfo;

	@Schema(description = "分类名称")
	private String cateName;

	@Schema(description = "活动状态 0：即将开始 1：进行中 2：已结束")
	private Integer status;

    /**
     * 拼团人数
     */
    @Schema(description = "拼团人数")
    private Integer grouponNum;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 商品主图片
	 */
	@Schema(description = "商品主图片")
	private String goodsImg;

	/**
	 * 预热开始时间
	 */
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Schema(description = "预热开始时间")
	private LocalDateTime preStartTime;

	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;
}