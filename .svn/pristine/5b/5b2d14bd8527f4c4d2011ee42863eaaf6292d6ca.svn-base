package com.wanmi.sbc.marketing.api.request.communitydeliveryorder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购发货单通用查询请求参数</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDeliveryOrderQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<String> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 团长Id
	 */
	@Schema(description = "团长Id")
	private String leaderId;

	/**
	 * 区域名称
	 */
	@Schema(description = "区域名称")
	private String areaName;

	/**
	 * 商品json内容
	 */
	@Schema(description = "商品json内容")
	private String goodsContext;

	/**
	 * 发货状态 0:未发货 1:已发货
	 */
	@Schema(description = "发货状态 0:未发货 1:已发货")
	private Integer deliveryStatus;

	/**
	 * 汇总类型 0:按团长 1:按区域
	 */
	@Schema(description = "汇总类型 0:按团长 1:按区域")
	private DeliveryOrderSummaryType type;

	/**
	 * 团长名称
	 */
	@Schema(description = "团长名称")
	private String leaderName;

	/**
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	private String pickupPointName;

	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	private String contactNumber;

	/**
	 * 全详细地址
	 */
	@Schema(description = "全详细地址")
	private String fullAddress;

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

}