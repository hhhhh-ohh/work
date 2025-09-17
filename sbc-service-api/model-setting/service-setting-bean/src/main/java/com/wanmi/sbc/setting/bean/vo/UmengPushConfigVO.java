package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>友盟push接口配置VO</p>
 * @author bob
 * @date 2020-01-07 10:34:04
 */
@Schema
@Data
public class UmengPushConfigVO extends BasicResponse {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Schema(description = "id")
	private Integer id;

	/**
	 * androidKeyId
	 */
	@Schema(description = "androidKeyId")
	private String androidKeyId;

	/**
	 * androidMsgSecret
	 */
	@Schema(description = "androidMsgSecret")
	private String androidMsgSecret;

	/**
	 * androidKeySecret
	 */
	@Schema(description = "androidKeySecret")
	private String androidKeySecret;

	/**
	 * iosKeyId
	 */
	@Schema(description = "iosKeyId")
	private String iosKeyId;

	/**
	 * iosKeySecret
	 */
	@Schema(description = "iosKeySecret")
	private String iosKeySecret;

}