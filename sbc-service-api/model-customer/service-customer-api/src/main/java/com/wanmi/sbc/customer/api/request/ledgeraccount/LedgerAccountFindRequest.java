package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询清分账户请求参数</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountFindRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务id
	 */
	@Schema(description = "业务id")
	@NotBlank
	private String businessId;

	/**
	 * 是否填充文件内容
	 */
	@Schema(description = "业务id")
	@NotNull
	private Boolean setFileFlag;

}