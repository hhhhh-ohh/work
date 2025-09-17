package com.wanmi.sbc.empower.apppush.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;
import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>消息推送配置实体类</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Data
@Entity
@Table(name = "app_push_setting")
public class AppPushSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 推送平台类型：0：友盟
	 */
	@Enumerated
	@Column(name = "platform_type")
	private AppPushPlatformType platformType;

	/**
	 * 安卓应用的Appkey
	 */
	@Column(name = "android_key_id")
	private String androidKeyId;

	/**
	 * 安卓应用的Message Secret
	 */
	@Column(name = "android_msg_secret")
	private String androidMsgSecret;

	/**
	 * 安卓应用的服务器秘钥
	 */
	@Column(name = "android_key_secret")
	private String androidKeySecret;

	/**
	 * IOS应用的Appkey
	 */
	@Column(name = "ios_key_id")
	private String iosKeyId;

	/**
	 * IOS应用的服务器秘钥
	 */
	@Column(name = "ios_key_secret")
	private String iosKeySecret;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}