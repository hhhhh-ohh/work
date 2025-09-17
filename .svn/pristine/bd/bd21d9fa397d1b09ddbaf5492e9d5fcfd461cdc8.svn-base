package com.wanmi.sbc.empower.customerservice.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.PlatformType;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <p>在线客服配置实体类</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Data
@Entity
@Table(name = "customer_service_setting")
public class CustomerServiceSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 店铺ID
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 推送平台类型：0：QQ客服 1：阿里云客服
	 */
	@Enumerated
	@Column(name = "platform_type")
	private CustomerServicePlatformType platformType;

	/**
	 * 在线客服是否启用 0 不启用， 1 启用
	 */
	@Enumerated
	@Column(name = "status")
	private DefaultFlag status;

	/**
	 * 客服标题
	 */
	@Column(name = "service_title")
	private String serviceTitle;

	/**
	 * 生效终端pc 0 不生效 1 生效
	 */
	@Enumerated
	@Column(name = "effective_pc")
	private DefaultFlag effectivePc;

	/**
	 * 生效终端App 0 不生效 1 生效
	 */
	@Enumerated
	@Column(name = "effective_app")
	private DefaultFlag effectiveApp;

	/**
	 * 生效终端移动版 0 不生效 1 生效
	 */
	@Enumerated
	@Column(name = "effective_mobile")
	private DefaultFlag effectiveMobile;

	/**
	 * 客服key
	 */
	@Column(name = "service_key")
	private String serviceKey;

	/**
	 * 客服链接
	 */
	@Column(name = "service_url")
	private String serviceUrl;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 企业ID
	 */
	@Column(name = "enterprise_id")
	private String enterpriseId;

	/**
	 * 在线客服分组是否启用 0 不启用， 1 启用
	 */
	@Enumerated
	@Column(name = "group_status")
	private DefaultFlag groupStatus;

	/**
	 * 在线客服类型 0 平台， 1 商家
	 */
	@Enumerated
	@Column(name = "service_type")
	private PlatformType serviceType;

}