package com.wanmi.sbc.crm.api.request.tagdimension;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>标签维度修改参数</p>
 * @author dyt
 * @date 2020-03-12 16:00:30
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDimensionModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 维度名
	 */
	@Schema(description = "维度名")
	@Length(max=255)
	private String name;

	/**
	 * 维度对应表明
	 */
	@Schema(description = "维度对应表明")
	@Length(max=255)
	private String tableName;

}