package com.wanmi.sbc.empower.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>小程序配置VO</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@Data
public class MiniProgramSetVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 小程序配置主键
	 */
	@Schema(description = "小程序配置主键")
	private Integer id;

	/**
	 * 小程序类型 0 微信小程序
	 */
	@Schema(description = "小程序类型 0 微信小程序")
	private Integer type;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Schema(description = "状态,0:未启用1:已启用")
	private Integer status;

	/**
	 * 小程序AppID(应用ID)
	 */
	@Schema(description = "小程序AppID(应用ID)")
	private String appId;

	/**
	 * 小程序AppSecret(应用密钥)
	 */
	@Schema(description = "小程序AppSecret(应用密钥)")
	private String appSecret;

}