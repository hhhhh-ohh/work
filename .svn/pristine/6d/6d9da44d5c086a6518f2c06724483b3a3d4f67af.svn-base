package com.wanmi.sbc.setting.api.request.payadvertisement;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询支付广告页配置请求参数</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAdvertisementByIdRequest extends BaseRequest {

	private static final long serialVersionUID = 2886168431581184546L;
	/**
	 * 支付广告id
	 */
	@Schema(description = "支付广告id")
	@NotNull
	private Long id;

}