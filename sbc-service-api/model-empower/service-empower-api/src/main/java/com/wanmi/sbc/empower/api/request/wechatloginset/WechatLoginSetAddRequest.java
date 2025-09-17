package com.wanmi.sbc.empower.api.request.wechatloginset;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * <p>微信授权登录配置新增参数</p>
 * @author lq
 * @date 2019-11-05 16:15:25
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatLoginSetAddRequest extends EmpowerBaseRequest {

	private static final long serialVersionUID = 4771595133067943144L;
	/**
	 * h5-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "h5-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag mobileServerStatus;

	/**
	 * h5-AppID(应用ID)
	 */
	@Schema(description = "h5-AppID(应用ID)")
	@Length(max=60)
	private String mobileAppId;

	/**
	 * h5-AppSecret(应用密钥)
	 */
	@Schema(description = "h5-AppSecret(应用密钥)")
	@Length(max=60)
	private String mobileAppSecret;

	/**
	 * pc-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "pc-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag pcServerStatus;

	/**
	 * pc-AppID(应用ID)
	 */
	@Schema(description = "pc-AppID(应用ID)")
	@Length(max=60)
	private String pcAppId;

	/**
	 * pc-AppSecret(应用密钥)
	 */
	@Schema(description = "pc-AppSecret(应用密钥)")
	@Length(max=60)
	private String pcAppSecret;

	/**
	 * app-微信授权登录是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "app-微信授权登录是否启用 0 不启用， 1 启用")
	private DefaultFlag appServerStatus;

	/**
	 * 操作人
	 */
	@Schema(description = "操作人")
	@Length(max=45)
	private String operatePerson;

	@Schema(description = "门店id")
	private Long storeId;

}