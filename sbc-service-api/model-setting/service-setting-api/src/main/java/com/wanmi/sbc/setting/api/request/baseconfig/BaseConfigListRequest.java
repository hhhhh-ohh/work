package com.wanmi.sbc.setting.api.request.baseconfig;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>基本设置列表查询请求参数</p>
 * @author lq
 * @date 2019-11-05 16:08:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseConfigListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-基本设置IDList
	 */
	@Schema(description = "批量查询-基本设置IDList")
	private List<Integer> baseConfigIdList;

	/**
	 * 基本设置ID
	 */
	@Schema(description = "基本设置ID")
	private Integer baseConfigId;

	/**
	 * PC端商城网址
	 */
	@Schema(description = "PC端商城网址")
	private String pcWebsite;

	/**
	 * 移动端商城网址
	 */
	@Schema(description = "移动端商城网址")
	private String mobileWebsite;

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
	private String pcTitle;

	/**
	 * 商家后台登录网址
	 */
	@Schema(description = "商家后台登录网址")
	private String supplierWebsite;

	/**
	 * 会员注册协议
	 */
	@Schema(description = "会员注册协议")
	private String registerContent;

}