package com.wanmi.sbc.setting.api.request;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询请求参数</p>
 * @author wjj
 * @date 2019-02-22 16:09:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveWordsByIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 敏感词id 主键
	 */
	@NotNull
	private Long sensitiveId;
}