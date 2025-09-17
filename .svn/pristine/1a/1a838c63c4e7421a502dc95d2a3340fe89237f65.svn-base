package com.wanmi.sbc.customer.api.request.customersignrecord;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除用户签到记录请求参数</p>
 * @author wangtao
 * @date 2019-10-05 16:13:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignRecordDelByIdRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户签到记录表id
	 */
	@Schema(description = "用户签到记录表id")
	@NotNull
	private String signRecordId;
}