package com.wanmi.sbc.setting.api.request.appexternallink;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>AppExternalLink新增参数</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppExternalLinkAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}