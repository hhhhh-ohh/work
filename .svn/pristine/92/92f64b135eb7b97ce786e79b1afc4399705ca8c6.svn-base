package com.wanmi.sbc.setting.api.request.businessconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

/**
 * <p>招商页设置修改参数</p>
 * @author lq
 * @date 2019-11-05 16:09:10
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessConfigModifyRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 招商页设置主键
	 */
	@Schema(description = "招商页设置主键")
	@Max(9999999999L)
	private Integer businessConfigId;

	/**
	 * 招商页banner
	 */
	@Schema(description = "招商页banner")
	private String businessBanner;

	/**
	 * 招商页自定义
	 */
	@Schema(description = "招商页自定义")
	private String businessCustom;

	/**
	 * 招商页注册协议
	 */
	@Schema(description = "招商页注册协议")
	private String businessRegister;

	/**
	 * 招商页入驻协议
	 */
	@Schema(description = "招商页入驻协议")
	private String businessEnter;

	/**
	 * 商家招商页banner
	 */
	@Schema(description = "商家招商页banner")
	private String supplierBanner;

	/**
	 * 商家招商页自定义
	 */
	@Schema(description = "商家招商页自定义")
	private String supplierCustom;

	/**
	 * 商家招商页注册协议
	 */
	@Schema(description = "商家招商页注册协议")
	private String supplierRegister;

	/**
	 * 商家招商页入驻协议
	 */
	@Schema(description = "商家招商页入驻协议")
	private String supplierEnter;

	/**
	 * 跨境商家招商页入驻协议
	 */
	@Schema(description = "跨境商家招商页入驻协议")
	private String crossSupplierEnter;
}