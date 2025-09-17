package com.wanmi.sbc.setting.baseconfig.model.root;


import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * <p>基本设置实体类</p>
 * @author lq
 * @date 2019-11-05 16:08:31
 */
@Data
@Entity
@Table(name = "base_config")
public class BaseConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 基本设置ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "base_config_id")
	private Integer baseConfigId;

	/**
	 * PC端商城网址
	 */
	@Column(name = "pc_website")
	private String pcWebsite;

	/**
	 * 移动端商城网址
	 */
	@Column(name = "mobile_website")
	private String mobileWebsite;

	/**
	 * PC商城logo
	 */
	@Column(name = "pc_logo")
	private String pcLogo;

	/**
	 * PC商城banner,最多可添加5个,多个图片间以'|'隔开
	 */
	@Column(name = "pc_banner")
	private String pcBanner;

	/**
	 * 移动商城banner,最多可添加5个,多个图片间以'|'隔开
	 */
	@Column(name = "mobile_banner")
	private String mobileBanner;

	/**
	 * PC商城首页banner,最多可添加5个,多个图片间以'|'隔开
	 */
	@Column(name = "pc_main_banner")
	private String pcMainBanner;

	/**
	 * 网页ico
	 */
	@Column(name = "pc_ico")
	private String pcIco;

	/**
	 * pc商城标题
	 */
	@Column(name = "pc_title")
	private String pcTitle;

	/**
	 * 商家后台登录网址
	 */
	@Column(name = "supplier_website")
	private String supplierWebsite;

	/**
	 * 会员注册协议
	 */
	@Column(name = "register_content")
	private String registerContent;

	/**
	 * 会员注销协议
	 */
	@Column(name = "cancellation_content")
	private String cancellationContent;

	/**
	 * 加载地址
	 */
	@Column(name = "loading_url")
	private String loadingUrl;

	/**
	 * 付费会员协议
	 */
	@Column(name = "paying_member_content")
	private String payingMemberContent;

	/**
	 * 版权信息
	 */
	@Column(name = "copyright")
	private String copyright;

	/**
	 * 版本号
	 */
	@Column(name = "version")
	private String version;


}