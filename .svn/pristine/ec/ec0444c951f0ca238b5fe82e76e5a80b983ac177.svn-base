package com.wanmi.sbc.crm.api.request.autotagpreference;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoPreferencePageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id")
	private Long tagId;

	/**
	 * 偏好类标签名称
	 */
	@Schema(description = "偏好类标签名称")
	private String detailName;

	private String TabFlag;
}