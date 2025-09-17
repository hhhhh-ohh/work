package com.wanmi.sbc.order.api.request.paytimeseries;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>支付流水记录新增参数</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesAddRequest extends BaseRequest {
	private static final long serialVersionUID = 4162926998559232206L;

	@NotBlank
	@Length(max=32)
	private String payNo;

	/**
	 * 订单id
	 */
	@Schema(description = "订单id")
	@NotBlank
	@Length(max=32)
	private String businessId;

	/**
	 * 申请价格
	 */
	@Schema(description = "申请价格")
	private BigDecimal applyPrice;

	/**
	 * 实际支付价格
	 */
	@Schema(description = "实际支付价格")
	private BigDecimal practicalPrice;

//	/**
//	 * 返回结果
//	 */
//	@Schema(description = "返回结果")
//	private String result;

	/**
	 * 状态
	 */
	@Schema(description = "状态")
	private TradeStatus status;

	/**
	 * chargeId
	 */
	@Schema(description = "chargeId")
	@Length(max=32)
	private String chargeId;

	/**
	 * 渠道id
	 */
	@Schema(description = "渠道id")
	@Max(9999999999L)
	private Long channelItemId;

	/**
	 * 请求ip
	 */
	@Schema(description = "请求ip")
	@Length(max=50)
	private String clientIp;

	/**
	 * 回调时间
	 */
	@Schema(description = "回调时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime callbackTime;

	/**
	 * 退款时间
	 */
	@Schema(description = "退款时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime refundTime;

	/**
	 * 退款状态
	 */
	@Schema(description = "退款状态")
	private TradeStatus refundStatus;

	/**
	 * 支付渠道，跟订单支付时候的渠道相对应
	 */
	@Schema(description = "支付渠道，跟订单支付时候的渠道相对应")
	@Length(max=30)
	private String payChannelType;

	/**
	 * 退款流水号
	 */
	@Schema(description = "退款流水号")
	@Length(max=32)
	private String refundPayNo;

	/**
	 * 第三方返回的支付流水号
	 */
	@Schema(description = "第三方返回的支付流水号")
	@Length(max=50)
	private String tradeNo;

}