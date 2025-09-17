package com.wanmi.sbc.crm.api.request.preferencetagdetail;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>偏好标签明细新增参数</p>
 * @author dyt
 * @date 2020-03-11 14:58:07
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceTagDetailAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id")
	@Max(9223372036854775807L)
	private Long tagId;

	/**
	 * 偏好类标签名称
	 */
	@Schema(description = "偏好类标签名称")
	@Length(max=255)
	private String detailName;

	/**
	 * 会员人数
	 */
	@Schema(description = "会员人数")
	@Max(9223372036854775807L)
	private Long customerCount;

}