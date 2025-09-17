package com.wanmi.sbc.goods.api.request.livegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询直播商品请求参数</p>
 * @author zwb
 * @date 2020-06-10 11:05:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信id
	 */
	@Schema(description = "微信id")
	@NotNull
	private Long goodsId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识", hidden = true)
	private Long storeId;

}