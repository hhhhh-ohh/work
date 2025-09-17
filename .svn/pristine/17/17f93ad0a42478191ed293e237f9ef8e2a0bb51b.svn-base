package com.wanmi.sbc.communityactivity.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.response.GoodsInfoListVO;
import com.wanmi.sbc.marketing.bean.enums.CommunityLogisticsType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.bean.vo.CommunitySkuRelVO;
import com.wanmi.sbc.order.bean.vo.CommunitySimpleTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>社区团购活动表VO</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@Data
public class CommunityActivitySiteVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String activityId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 店铺Logo
	 */
	@Schema(description = "店铺Logo")
	private String storeLogo;

	/**
	 * 活动名称
	 */
	@Schema(description = "活动名称")
	private String activityName;

	/**
	 * 活动描述
	 */
	@Schema(description = "活动描述")
	private String description;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

	/**
	 * 物流方式 0:自提 1:快递 以逗号拼凑
	 */
	@Schema(description = "物流方式 0:自提 1:快递")
	private List<CommunityLogisticsType> logisticsTypes;

	/**
	 * 最大预估佣金
	 */
	@Schema(description = "最大预估佣金")
	private BigDecimal maxCommission;

	/**
	 * 批量-帮卖团长佣金
	 */
	@Schema(description = "最小预估佣金")
	private BigDecimal minCommission;

	/**
	 * 团详情
	 */
	@Schema(description = "团详情")
	private String details;

	/**
	 * 团图片
	 */
	@Schema(description = "团图片")
	private String images;

	/**
	 * 团视频
	 */
	@Schema(description = "团视频")
	private String videoUrl;

	/**
	 * 是否允许帮卖
	 */
	@Schema(description = "是否允许帮卖")
	private Boolean assistCanFlag;

	/**
	 * 跟团数
	 */
	@Schema(description = "跟团数")
	private Long payNum;

	/**
	 * 活动状态
	 */
	@Schema(description = "0:进行中 1:已结束 2:未开始")
	private CommunityTabStatus activityStatus;

	/**
	 * 商品列表
	 */
	@NotEmpty
	@Schema(description = "商品列表")
	private List<CommunitySkuRelVO> skuList;

	/**
	 * 商品列表
	 */
	@Schema(description = "商品列表")
	private List<GoodsInfoListVO> esGoodsInfoVOList;

	/**
	 * 跟团记录
	 */
	@Schema(description = "跟团记录")
	List<CommunitySimpleTradeVO> tradeList;
}