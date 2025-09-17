package com.wanmi.sbc.marketing.api.request.distributionrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>DistributionRecord修改参数</p>
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Schema
public class DistributionRecordUpdateInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 货品Id(sku)
	 */
	@Schema(description = "货品Id")
	@Length(max=32)
	@NotBlank
	private String goodsInfoId;

	/**
	 * 订单交易号
	 */
	@Schema(description = "订单交易号")
	@Length(max=32)
	@NotBlank
	private String tradeId;

	/**
	 * 订单完成时间
	 */
	@Schema(description = "订单完成时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime finishTime;

	/**
	 * 佣金入账时间
	 */
	@Schema(description = "佣金入账时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime missionReceivedTime;

	/**
	 * 商品数量
	 */
	@Max(9999999999L)
	@NotNull
	@Schema(description = "商品数量")
	private Long goodsCount;

}