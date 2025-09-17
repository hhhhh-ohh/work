package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.enums.DefaultFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * @author: Geek Wang
 * @createDate: 2019/8/7 10:17
 * @version: 1.0
 */
@Data
public class CouponActivityConfigAndCouponInfoDTO implements Serializable {

	/**
	 * 优惠券活动配置表id
	 */
	@Schema(description = "优惠券活动配置表id")
	private String activityConfigId;

	/**
	 * 活动id
	 */
	@Schema(description = "优惠券活动id")
	private String activityId;

	/**
	 * 优惠券id
	 */
	@Schema(description = "优惠券id")
	private String couponId;


	/**
	 * 优惠券赠送张数
	 */
	@Schema(description = "优惠券赠送张数")
	private Long totalCount;

	/**
	 * 是否有剩余, 1 有，0 没有
	 */
	@Schema(description = "优惠券是否有剩余")
	private DefaultFlag hasLeft;

	@Schema(description = "优惠券信息")
	private CouponInfoDTO couponInfoDTO;
}
