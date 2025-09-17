package com.wanmi.sbc.marketing.api.request.appointmentsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>预约抢购修改参数</p>
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSaleGoodsCountRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * 预约id
	 */
	@Schema(description = "预约id")
	@NotNull
	@Max(9223372036854775807L)
	private Long appointmentSaleId;


	/**
	 * skuID
	 */
	@Schema(description = "skuID")
	@NotBlank
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * stock
	 */
	@Schema(description = "stock")
	private Long stock;


}