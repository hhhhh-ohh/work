package com.wanmi.sbc.marketing.api.request.distributionrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>单个软删除DistributionRecord请求参数</p>
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionRecordDelByTradeIdAndGoodsInfoIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易订单号
	 */
	@NotNull
	private String tradeId;

	/**
	 * 商品Id
	 */
	@NotNull
	private String goodsInfoId;
}