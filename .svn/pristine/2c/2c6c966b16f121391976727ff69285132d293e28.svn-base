package com.wanmi.sbc.order.api.request.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>团明细</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponInstancePageRequest  extends BaseQueryRequest {


	private static final long serialVersionUID = 208932344606542513L;
	/**
	 * 团号
	 */
	private String grouponNo;

	/**
	 * 拼团活动id
	 */
	private String grouponActivityId;

	/**
	 * 拼团状态
	 */
	private GrouponOrderStatus grouponStatus;


	/**
	 * 团长用户id
	 */
	private String customerId;

	/**
	 * 距离拼团结束还剩3小时，且未成团
	 */
	@Schema(description = "距离拼团结束还剩3小时，且未成团")
	private Boolean grouponExpiredSendFlag = false;

	@Schema(description = "距离拼团结束还剩3小时")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;

}