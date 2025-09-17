package com.wanmi.sbc.marketing.api.request.bookingsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * <p>预售商品信息新增参数</p>
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleGoodsAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 预售id
	 */
	@Schema(description = "预售id")
	@NotNull
	@Max(9223372036854775807L)
	private Long bookingSaleId;

	/**
	 * 商户id
	 */
	@Schema(description = "商户id")
	@NotNull
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * skuID
	 */
	@Schema(description = "skuID")
	@NotBlank
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * spuID
	 */
	@Schema(description = "spuID")
	@NotBlank
	@Length(max=32)
	private String goodsId;

	/**
	 * 定金
	 */
	@Schema(description = "定金")
	private BigDecimal handSelPrice;

	/**
	 * 膨胀价格
	 */
	@Schema(description = "膨胀价格")
	private BigDecimal inflationPrice;

	/**
	 * 预售价
	 */
	@Schema(description = "预售价")
	private BigDecimal bookingPrice;

	/**
	 * 预售数量
	 */
	@Schema(description = "预售数量")
	@Max(9999999999L)
	private Integer bookingCount;

	/**
	 * 定金支付数量
	 */
	@Schema(description = "定金支付数量")
	@NotNull
	@Max(9999999999L)
	private Integer handSelCount;

	/**
	 * 尾款支付数量
	 */
	@Schema(description = "尾款支付数量")
	@NotNull
	@Max(9999999999L)
	private Integer tailCount;

	/**
	 * 全款支付数量
	 */
	@Schema(description = "全款支付数量")
	@NotNull
	@Max(9999999999L)
	private Integer payCount;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}