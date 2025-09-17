package com.wanmi.sbc.setting.api.request.platformaddress;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>平台地址信息修改参数</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAddressModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Length(max=32)
	private String id;

	/**
	 * 地址名称
	 */
	@Schema(description = "地址名称")
	@NotBlank
	@Length(max=200)
	private String addrName;

	/**
	 * 修改之前的主键id
	 */
	@Schema(description = "主键id")
	@Length(max=32)
	private String oldAddrId;
}