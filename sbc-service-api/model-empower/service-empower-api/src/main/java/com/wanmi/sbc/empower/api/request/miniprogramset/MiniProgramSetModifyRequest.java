package com.wanmi.sbc.empower.api.request.miniprogramset;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>小程序配置修改参数</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 小程序配置主键
	 */
	@Schema(description = "小程序配置主键")
	@Max(9999999999L)
	private Integer id;

	/**
	 * 小程序类型 0 微信小程序
	 */
	@Schema(description = "小程序类型 0 微信小程序")
	@Max(127)
	private Integer type;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	@Length(max=255)
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
	@Length(max=60)
	private String appId;

	/**
	 * 小程序AppSecret(应用密钥)
	 */
	@Schema(description = "小程序AppSecret(应用密钥)")
	@Length(max=60)
	private String appSecret;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}
