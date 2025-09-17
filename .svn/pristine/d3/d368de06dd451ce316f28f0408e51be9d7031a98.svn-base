package com.wanmi.sbc.marketing.api.request.communitydeliveryorder;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>社区团购发货单修改参数</p>
 * @author dyt
 * @date 2023-08-03 16:23:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDeliveryOrderModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Length(max=32)
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	@NotBlank
	@Length(max=32)
	private String activityId;

	/**
	 * 团长Id
	 */
	@Schema(description = "团长Id")
	@Length(max=32)
	private String leaderId;

	/**
	 * 区域名称
	 */
	@Schema(description = "区域名称")
	@Length(max=32)
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
	@NotNull
	@Max(127)
	private Integer deliveryStatus;

	/**
	 * 汇总类型 0:按团长 1:按区域
	 */
	@Schema(description = "汇总类型 0:按团长 1:按区域")
	@NotNull
	@Max(127)
	private DeliveryOrderSummaryType type;

	/**
	 * 团长名称
	 */
	@Schema(description = "团长名称")
	@Length(max=20)
	private String leaderName;

	/**
	 * 自提点名称
	 */
	@Schema(description = "自提点名称")
	@Length(max=20)
	private String pickupPointName;

	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	@Length(max=50)
	private String contactNumber;

	/**
	 * 全详细地址
	 */
	@Schema(description = "全详细地址")
	@Length(max=255)
	private String fullAddress;

}
