package com.wanmi.sbc.order.api.request.growthvalue;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

/**
 * <p>会员权益处理订单成长值 临时表通用查询请求参数</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGrowthValueTempQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键idList
	 */
	@Schema(description = "批量查询-主键idList")
	private List<Long> idList;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 退货截止时间
	 */
	@Schema(description = "退货截止时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime returnEndTime;

}