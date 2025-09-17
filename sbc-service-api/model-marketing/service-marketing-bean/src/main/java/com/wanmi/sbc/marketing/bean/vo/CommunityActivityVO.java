package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.*;

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
public class CommunityActivityVO implements Serializable {
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
	 * 销售渠道 0:自主销售 1:团长帮卖 以逗号拼凑
	 */
	@Schema(description = "销售渠道 0:自主销售 1:团长帮卖")
	private List<CommunitySalesType> salesTypes;

	/**
	 * 自主销售范围 0：全部 1：地区 2：自定义
	 */
	@Schema(description = "自主销售范围 0：全部 1：地区 2：自定义")
	private CommunitySalesRangeType salesRange;

	/**
	 * 自主销售范围内容
	 */
	@Schema(description = "自主销售范围内容")
	private List<String> salesRangeContext;

	/**
	 * 自主销售范围区域名称
	 */
	@Schema(description = "自主销售范围区域名称")
	private List<String> salesRangeAreaNames;

	/**
	 * 帮卖团长范围 0：全部 1：地区 2：自定义
	 */
	@Schema(description = "帮卖团长范围 0：全部 1：地区 2：自定义")
	private CommunityLeaderRangeType leaderRange;

	/**
	 * 帮卖团长范围内容
	 */
	@Schema(description = "帮卖团长范围内容")
	private List<String> leaderRangeContext;

	/**
	 * 帮卖团长范围内容
	 */
	@Schema(description = "帮卖团长范围区域名称")
	private List<String> leaderRangeAreaNames;


	private List<CommunityLeaderRelVO> communityLeaderRelVOList;

	/**
	 * 佣金设置 0：商品 1：按团长/自提点
	 */
	@Schema(description = "佣金设置 0：商品 1：按团长/自提点")
	private CommunityCommissionFlag commissionFlag;

	/**
	 * 批量-自提服务佣金
	 */
	@Schema(description = "批量-自提服务佣金")
	private BigDecimal pickupCommission;

	/**
	 * 批量-帮卖团长佣金
	 */
	@Schema(description = "批量-帮卖团长佣金")
	private BigDecimal assistCommission;

	/**
	 * 区域佣金
	 */
	@Schema(description = "区域佣金")
	private List<CommunityCommissionAreaRelVO> commissionAreaList;

	/**
	 * 团长佣金
	 */
	@Schema(description = "团长佣金")
	private List<CommunityCommissionLeaderRelVO> commissionLeaderList;

	/**
	 * 商品列表
	 */
	@NotEmpty
	@Schema(description = "商品列表")
	private List<CommunitySkuRelVO> skuList;

	/**
	 * 团详情
	 */
	@Schema(description = "团详情")
	private String details;

	/**
	 * 是否生成 0:未生成 1:已生成
	 */
	@Schema(description = "是否生成 0:未生成 1:已生成")
	private Integer generateFlag;

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
	 * 活动状态
	 */
	@Schema(description = "0:进行中 1:已结束 2:未开始")
	private CommunityTabStatus activityStatus;

	/**
	 * 结束时间
	 */
	@Schema(description = "生成时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime generateTime;
}