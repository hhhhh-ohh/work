package com.wanmi.sbc.empower.customerservice.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
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
 * @date 2021-04-08 16:43:56
 */
@Data
@Entity
@Table(name = "customer_service_setting_item")
public class CustomerServiceSettingItem extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 在线客服座席id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_item_id")
	private Long serviceItemId;

	/**
	 * 店铺ID
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 在线客服主键
	 */
	@Column(name = "customer_service_id")
	private Long customerServiceId;

	/**
	 * 客服昵称
	 */
	@Column(name = "customer_service_name")
	private String customerServiceName;

	/**
	 * 客服账号
	 */
	@Column(name = "customer_service_account")
	private String customerServiceAccount;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/** 在线客服链接 */
	@Column(name = "service_url")
	private String serviceUrl;

	/** 在线客服头像 */
	@Column(name = "service_icon")
	private String serviceIcon;

}