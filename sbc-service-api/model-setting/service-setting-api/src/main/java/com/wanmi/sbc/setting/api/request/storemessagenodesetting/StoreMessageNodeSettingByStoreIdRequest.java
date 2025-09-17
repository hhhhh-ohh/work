package com.wanmi.sbc.setting.api.request.storemessagenodesetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

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
public class StoreMessageNodeSettingByStoreIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id", hidden = true)
	private Long storeId;

}