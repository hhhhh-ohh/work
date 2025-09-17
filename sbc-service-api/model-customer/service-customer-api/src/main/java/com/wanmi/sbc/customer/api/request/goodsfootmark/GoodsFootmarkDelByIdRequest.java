package com.wanmi.sbc.customer.api.request.goodsfootmark;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotNull;

/**
 * <p>单个删除我的足迹请求参数</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsFootmarkDelByIdRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * footmarkId
	 */
	@Schema(description = "footmarkId")
	@NotNull
	private Long footmarkId;

	@Schema(description = "客户id")
	private String customerId;
}