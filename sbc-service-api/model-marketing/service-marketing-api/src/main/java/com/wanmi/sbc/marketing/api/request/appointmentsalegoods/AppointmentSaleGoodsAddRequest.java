package com.wanmi.sbc.marketing.api.request.appointmentsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * <p>预约抢购新增参数</p>
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleGoodsAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 预约id
	 */
	@Schema(description = "预约id")
	@NotNull
	@Max(9223372036854775807L)
	private Long appointmentSaleId;

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
	 * 预约价
	 */
	@Schema(description = "预约价")
	private BigDecimal price;

	/**
	 * 预约数量
	 */
	@Schema(description = "预约数量")
	@NotNull
	@Max(9999999999L)
	private Integer appointmentCount;

	/**
	 * 购买数量
	 */
	@Schema(description = "购买数量")
	@NotNull
	@Max(9999999999L)
	private Integer buyerCount;

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