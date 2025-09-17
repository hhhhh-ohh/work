package com.wanmi.sbc.empower.api.request.miniprogramset;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>小程序配置新增参数</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}