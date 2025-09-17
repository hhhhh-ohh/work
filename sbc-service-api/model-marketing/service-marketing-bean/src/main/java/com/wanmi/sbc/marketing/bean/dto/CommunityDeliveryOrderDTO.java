package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>社区团购发货单新增参数</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDeliveryOrderDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	@NotBlank
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
	@Schema(description = "商品内容")
	@NotEmpty
	private List<CommunityDeliveryItemDTO> goodsList;

	/**
	 * 发货状态 0:未发货 1:已发货
	 */
	@Schema(description = "发货状态 0:未发货 1:已发货")
	@NotNull
	private Integer deliveryStatus;

	/**
	 * 汇总类型 0:按团长 1:按区域
	 */
	@Schema(description = "汇总类型 0:按团长 1:按区域")
	@NotNull
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

}