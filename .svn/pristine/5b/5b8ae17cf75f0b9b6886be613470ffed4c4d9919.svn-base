package com.wanmi.sbc.setting.api.request.thirdaddress;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>第三方地址映射操作</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdAddressMappingRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * 第三方平台类型
	 */
	@Schema(description = "第三方平台类型")
	@NotNull
	private ThirdPlatformType thirdPlatformType;
}