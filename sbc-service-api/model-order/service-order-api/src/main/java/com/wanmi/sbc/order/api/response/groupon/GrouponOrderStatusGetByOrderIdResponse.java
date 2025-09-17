package com.wanmi.sbc.order.api.response.groupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.enums.GrouponOrderCheckStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>团订单状态</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponOrderStatusGetByOrderIdResponse extends BasicResponse {
	private static final long serialVersionUID = 1L;


	/**
	 * 团订单验证状态
	 */
	@Schema(description = "团订单验证状态")
	private GrouponOrderCheckStatus status;

}