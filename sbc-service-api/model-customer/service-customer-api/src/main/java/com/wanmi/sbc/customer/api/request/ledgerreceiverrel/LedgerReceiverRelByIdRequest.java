package com.wanmi.sbc.customer.api.request.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询分账绑定关系请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:24:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerReceiverRelByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private String id;

}