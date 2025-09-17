package com.wanmi.sbc.message.api.request.storenoticesend;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>商家公告修改参数</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreNoticeSendModifyRequest extends StoreNoticeSendAddRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@NotNull
	private Long id;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;
}
