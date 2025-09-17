package com.wanmi.sbc.customer.api.request.ledgerfile;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>分账文件修改参数</p>
 * @author 许云鹏
 * @date 2022-07-01 16:43:39
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerFileModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Length(max=32)
	@NotBlank
	private String id;

	/**
	 * 文件内容
	 */
	@Schema(description = "文件内容")
	private byte[] content;

}
