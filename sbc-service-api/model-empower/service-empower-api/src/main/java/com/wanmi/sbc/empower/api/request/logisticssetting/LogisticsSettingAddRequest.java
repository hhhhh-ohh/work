package com.wanmi.sbc.empower.api.request.logisticssetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * @className LogisticsSettingAddRequest
 * @description TODO
 * @author songhanlin
 * @date 2021/4/9 上午11:51
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsSettingAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
