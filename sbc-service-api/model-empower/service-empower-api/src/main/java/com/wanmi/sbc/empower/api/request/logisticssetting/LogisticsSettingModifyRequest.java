package com.wanmi.sbc.empower.api.request.logisticssetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>物流配置修改参数</p>
 * @author 宋汉林
 * @date 2021-04-01 11:23:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsSettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 配置类型 0: 快递100
	 */
	@Schema(description = "配置类型 0: 快递100")
	@NotNull
	private LogisticsType logisticsType;

	/**
	 * 用户申请授权key
	 */
	@Schema(description = "用户申请授权key")
	@NotBlank
	@Length(max=128)
	private String customerKey;

	/**
	 * 授权秘钥key
	 */
	@Schema(description = "授权秘钥key")
	@NotBlank
	@Length(max=128)
	private String deliveryKey;

	/**
	 * 实时查询是否开启 0: 否, 1: 是
	 */
	@Schema(description = "实时查询是否开启 0: 否, 1: 是")
	@NotNull
	private DefaultFlag realTimeStatus;

	/**
	 * 是否开启订阅 0: 否, 1: 是
	 */
	@Schema(description = "是否开启订阅 0: 否, 1: 是")
	@NotNull
	private DefaultFlag subscribeStatus;

	/**
	 * 回调地址
	 */
	@Schema(description = "回调地址")
	@NotBlank
	@Length(max=128)
	private String callbackUrl;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;


	/**
	 * 是否启用 0: 否, 1: 是，暂时给达达使用
	 */
	@Schema(description = "是否启用 0: 否, 1: 是，暂时给达达使用")
	private EnableStatus enableStatus;

	/**
	 * 达达商户id
	 */
	@Schema(description = "达达商户id")
	private String shopNo;

}
