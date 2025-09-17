package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p> 根据活动ID、SKU编号更新商品销售量、订单量、交易额</p>
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsInfoModifyStatisticsNumRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 拼团活动ID
	 */
	@Schema(description = " 拼团活动ID")
	@NotBlank
	private String grouponActivityId;

	/**
	 * sku编号
	 */
	@Schema(description = "SKU编号")
	@NotBlank
	private String goodsInfoId;

	/**
	 * 商品销售量
	 */
	@Schema(description = "商品销售量")
	@NotNull
	private Integer goodsSalesNum;

	/**
	 * 订单量
	 */
	@Schema(description = "订单量")
	@NotNull
	private Integer orderSalesNum;

	/**
	 * 交易额
	 */
	@Schema(description = "交易额")
	@NotNull
	private BigDecimal tradeAmount;
}