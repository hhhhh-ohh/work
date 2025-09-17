package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>单个查询抢购活动请求参数</p>
 * @author xufeng
 * @date 2022-02-10 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsByActivityIdRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * activityId
	 */
	@Schema(description = "activityId")
	@NotNull
	private String activityId;
}