package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>店铺客户消费统计表VO</p>
 */
@Data
public class StoreConsumerStatisticsVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private String id;

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private String customerId;

	/**
	 * 店铺id
	 */
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