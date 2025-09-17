package com.wanmi.sbc.setting.api.request.storeexpresscompanyrela;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * @description   根据物流公司ID删除门店的关联关系
 * @author  wur
 * @date: 2021/11/9 17:10
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreExpressCompanyRelaDelByExpressCompanyIdRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 物流公司ID
	 */
	@Schema(description = "物流公司ID")
	@NotNull
	private Long expressCompanyId;
}