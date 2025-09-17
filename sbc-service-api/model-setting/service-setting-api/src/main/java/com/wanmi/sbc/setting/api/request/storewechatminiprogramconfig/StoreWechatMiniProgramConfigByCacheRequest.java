package com.wanmi.sbc.setting.api.request.storewechatminiprogramconfig;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询门店微信小程序配置请求参数</p>
 * @author tangLian
 * @date 2020-01-16 11:47:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreWechatMiniProgramConfigByCacheRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 门店id
	 */
	@Schema(description = "门店id")
	@NotNull
	private Long storeId;
}