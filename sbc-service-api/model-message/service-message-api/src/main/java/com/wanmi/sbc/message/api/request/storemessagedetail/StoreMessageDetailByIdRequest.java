package com.wanmi.sbc.message.api.request.storemessagedetail;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询商家消息/公告请求参数</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMessageDetailByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@NotNull
	private String id;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id", hidden = true)
	@NotNull
	private Long storeId;

}