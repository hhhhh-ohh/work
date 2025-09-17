package com.wanmi.sbc.customer.api.request.customersignrecord;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>查询用户签到记录请求参数</p>
 * @author wangtao
 * @date 2019-10-05 16:13:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignRecordGetByDaysRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private String customerId;

	/**
	 * 相差天数
	 */
	@Schema(description = "相差天数")
	private Long days;
}