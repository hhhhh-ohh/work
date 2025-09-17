package com.wanmi.sbc.empower.api.request.customerservice;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.bean.enums.CustomerServicePlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>单个查询在线客服配置请求参数</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceSettingByStoreIdRequest extends EmpowerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private CustomerServicePlatformType platformType;

	/**
	 * 店铺ID
	 */
	@Schema(description = "店铺ID", hidden = true)
	@NotNull
	private Long storeId;

	/**
	 * 在线客服是否启用 0 不启用， 1 启用
	 */
	@Schema(description = "在线客服是否启用 0 不启用， 1 启用")
	private DefaultFlag status;

	/**
	 * 前端调用 true 后端调用 false
	 */
	@Schema(description = "前端调用 true 后端调用 false")
	private boolean bffWeb = false;

}