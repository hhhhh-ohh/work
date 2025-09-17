package com.wanmi.sbc.setting.api.request.baseconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import lombok.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>基本设置修改参数</p>
 * @author lq
 * @date 2019-11-05 16:08:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigModifyRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 基本设置ID
	 */
	@Schema(description = "基本设置ID")
	@Max(9999999999L)
	private Integer baseConfigId;

	/**
	 * PC端商城网址
	 */
	@Schema(description = "PC端商城网址")
	@Length(max=128)
	private String pcWebsite;

	/**
	 * 移动端商城网址
	 */
	@Schema(description = "移动端商城网址")
	@Length(max=128)
	private String mobileWebsite;

	/**
	 * 商城加载图
	 */
	@Schema(description = "商城加载图")
	private String loadingUrl;

	/**
	 * PC商城logo
	 */
	@Schema(description = "PC商城logo")
	private String pcLogo;

	/**
	 * PC商城banner,最多可添加5个,多个图片间以'|'隔开
	 */
	@Schema(description = "PC商城banner,最多可添加5个,多个图片间以'|'隔开")
	private String pcBanner;

	/**
	 * 移动商城banner,最多可添加5个,多个图片间以'|'隔开
	 */
	@Schema(description = "移动商城banner,最多可添加5个,多个图片间以'|'隔开")
	private String mobileBanner;

	/**
	 * PC商城首页banner,最多可添加5个,多个图片间以'|'隔开
	 */
	@Schema(description = "PC商城首页banner,最多可添加5个,多个图片间以'|'隔开")
	private String pcMainBanner;

	/**
	 * 网页ico
	 */
	@Schema(description = "网页ico")
	private String pcIco;

	/**
	 * pc商城标题
	 */
	@Schema(description = "pc商城标题")
	@Length(max=128)
	private String pcTitle;

	/**
	 * 商家后台登录网址
	 */
	@Schema(description = "商家后台登录网址")
	@Length(max=128)
	private String supplierWebsite;

	/**
	 * 会员注册协议
	 */
	@Schema(description = "会员注册协议")
	private String registerContent;

	/**
	 * 版权信息
	 */
	@Schema(description = "版权信息")
	@Length(max = 40)
	@NotBlank
	private String copyright;

	/**
	 * 版本号
	 */
	@Schema(description = "版本号")
	private String version;

}