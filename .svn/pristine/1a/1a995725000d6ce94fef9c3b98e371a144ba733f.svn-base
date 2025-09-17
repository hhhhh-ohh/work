package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

/**
 * <p>单个查询周期购sku表请求参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoByGoodsIdRequest extends BaseRequest {

	private static final long serialVersionUID = 65177067889918044L;
	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	@NotEmpty
	private String goodsInfoId;

}