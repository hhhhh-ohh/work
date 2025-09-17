package com.wanmi.sbc.setting.api.request.expresscompany;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>物流公司新增参数</p>
 * @author lq
 * @date 2019-11-05 16:10:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressCompanyAddRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 物流公司名称
	 */
	@Schema(description = "物流公司名称")
	@Length(max=125)
	@NotBlank
	private String expressName;

	/**
	 * 物流公司代码
	 */
	@Schema(description = "物流公司代码")
	@Length(max=255)
	@NotBlank
	private String expressCode;

	/**
	 * 删除标志 默认0：未删除 1：删除
	 */
	@Schema(description = "删除标志 默认0：未删除 1：删除")
	private DeleteFlag delFlag;
}