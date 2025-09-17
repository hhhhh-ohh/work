package com.wanmi.sbc.empower.bean.vo;

import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>消息推送配置VO</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Schema
@Data
public class AppPushSettingVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Integer id;

	/**
	 * 推送平台类型：0：友盟
	 */
	@Schema(description = "推送平台类型：0：友盟")
	private AppPushPlatformType platformType;

	/**
	 * 安卓应用的Appkey
	 */
	@Schema(description = "安卓应用的Appkey")
	private String androidKeyId;

	/**
	 * 安卓应用的Message Secret
	 */
	@Schema(description = "安卓应用的Message Secret")
	private String androidMsgSecret;

	/**
	 * 安卓应用的服务器秘钥
	 */
	@Schema(description = "安卓应用的服务器秘钥")
	private String androidKeySecret;

	/**
	 * IOS应用的Appkey
	 */
	@Schema(description = "IOS应用的Appkey")
	private String iosKeyId;

	/**
	 * IOS应用的服务器秘钥
	 */
	@Schema(description = "IOS应用的服务器秘钥")
	private String iosKeySecret;

}