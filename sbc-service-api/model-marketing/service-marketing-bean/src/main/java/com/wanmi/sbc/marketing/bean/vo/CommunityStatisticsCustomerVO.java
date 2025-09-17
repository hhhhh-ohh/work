package com.wanmi.sbc.marketing.bean.vo;

import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购活动会员信息表VO</p>
 * @author dyt
 * @date 2023-07-24 14:49:55
 */
@Schema
@Data
public class CommunityStatisticsCustomerVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 活动id
	 */
	@Schema(description = "活动id")
	private String activityId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 团长id
	 */
	@Schema(description = "团长id")
	private String leaderId;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

}