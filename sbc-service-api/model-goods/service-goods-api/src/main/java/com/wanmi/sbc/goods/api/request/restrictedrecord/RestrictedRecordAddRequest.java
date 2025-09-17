package com.wanmi.sbc.goods.api.request.restrictedrecord;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import java.time.LocalDate;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import lombok.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售新增参数</p>
 * @author 限售记录
 * @date 2020-04-11 15:59:01
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestrictedRecordAddRequest extends GoodsBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 会员的主键
	 */
	@Schema(description = "会员的主键")
	@Length(max=32)
	private String customerId;

	/**
	 * 货品主键
	 */
	@Schema(description = "货品主键")
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * 购买的数量
	 */
	@Schema(description = "购买的数量")
	@Max(9223372036854775807L)
	private Long purchaseNum;

	/**
	 * 周期类型（0: 终生，1:周  2:月  3:年）
	 */
	@Schema(description = "周期类型（0: 终生，1:周  2:月  3:年）")
	@Max(127)
	private Integer restrictedCycleType;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate startDate;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate endDate;

}