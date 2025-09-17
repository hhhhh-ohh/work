package com.wanmi.sbc.setting.api.request.storeexpresscompanyrela;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>店铺快递公司关联表修改参数</p>
 * @author lq
 * @date 2019-11-05 16:12:13
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreExpressCompanyRelaModifyRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键UUID
	 */
	@Schema(description = "主键UUID")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 主键ID,自增
	 */
	@Schema(description = "主键ID,自增")
	@Max(9223372036854775807L)
	private Long expressCompanyId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 商家标识
	 */
	@Schema(description = "商家标识")
	@Max(9999999999L)
	private Integer companyInfoId;


}