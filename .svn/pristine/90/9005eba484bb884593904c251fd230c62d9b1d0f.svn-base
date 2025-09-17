package com.wanmi.sbc.empower.api.request.ledgercontent;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>拉卡拉经营内容表修改参数</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerContentModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 经营内容id
	 */
	@Schema(description = "经营内容id")
	@Max(9223372036854775807L)
	private Long contentId;

	/**
	 * 经营内容名称
	 */
	@Schema(description = "经营内容名称")
	@Length(max=128)
	private String contentName;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

}
