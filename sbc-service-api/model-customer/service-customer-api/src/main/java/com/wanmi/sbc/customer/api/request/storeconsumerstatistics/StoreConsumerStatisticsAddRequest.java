package com.wanmi.sbc.customer.api.request.storeconsumerstatistics;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>店铺客户消费统计表新增参数</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreConsumerStatisticsAddRequest extends CustomerBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@NotBlank
	@Schema(description = "用户id")
	private String customerId;

	/**
	 * 店铺id
	 */
	@NotNull
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 会员在该店铺下单数
	 */
	@Schema(description = "会员在该店铺下单数")
	private Integer tradeCount;

	/**
	 * 会员在该店铺消费额
	 */
	@Schema(description = "会员在该店铺消费额")
	private BigDecimal tradePriceCount;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标记 0:未删除 1:已删除
	 */
	@Schema(description = "删除标记")
	private Integer delFlag;

}