package com.wanmi.sbc.empower.sms.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.empower.bean.enums.SmsPlatformType;
import lombok.Data;

import jakarta.persistence.*;

/**
 * <p>短信配置实体类</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@Data
@Entity
@Table(name = "sms_setting")
public class SmsSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 调用api参数key，或华信账号
	 */
	@Column(name = "access_key_id")
	private String accessKeyId;

	/**
	 * 调用api参数secret，或华信密码
	 */
	@Column(name = "access_key_secret")
	private String accessKeySecret;

	/**
	 * 短信平台类型：0：阿里云短信平台，1：华信短信平台
	 */
	@Column(name = "type")
	@Enumerated
	private SmsPlatformType type;

	/**
	 * 是否启用：0：未启用；1：启用
	 */
	@Column(name = "status")
	@Enumerated
	private EnableStatus status;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 华信接口地址
	 */
	@Column(name = "huaxin_api_url")
	private String huaxinApiUrl;

	/**
	 * 华信短信报备和签名
	 */
	@Column(name = "huaxin_template")
	private String huaxinTemplate;
}