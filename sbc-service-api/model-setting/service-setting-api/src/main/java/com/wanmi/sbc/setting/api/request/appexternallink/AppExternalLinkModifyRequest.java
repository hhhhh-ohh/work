package com.wanmi.sbc.setting.api.request.appexternallink;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>AppExternalLink修改参数</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalLinkModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 外部链接id
	 */
	@Schema(description = "外部链接id")
	@NotNull
	private Long configId;

	/**
	 * 页面名称
	 */
	@Schema(description = "页面名称")
	@NotBlank
	@Length(max=40)
	private String pageName;

	/**
	 * 页面链接
	 */
	@Schema(description = "页面链接")
	@NotBlank
	@Length(max=250)
	private String pageLink;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}
