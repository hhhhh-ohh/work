package com.wanmi.sbc.marketing.api.request.appointmentsalegoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class AppointmentSaleGoodsModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

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
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}