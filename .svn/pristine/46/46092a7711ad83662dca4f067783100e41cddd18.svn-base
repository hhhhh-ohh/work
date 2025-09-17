package com.wanmi.sbc.setting.api.request.storemessagenodesetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询商家消息节点请求参数</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageNodeSettingByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@NotNull
	private Long id;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id", hidden = true)
	private Long storeId;

}