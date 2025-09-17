package com.wanmi.sbc.order.bean.vo;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;

import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

/**
 * <p>支付流水记录VO</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@Data
public class PayTimeSeriesVO implements Serializable {
	private static final long serialVersionUID = 8255907442580462009L;

	/**
	 * 支付流水号
	 */
	@Schema(description = "支付流水号")
	private String payNo;

	/**
	 * 订单id
	 */
	@Schema(description = "订单id")
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
	private String chargeId;

	/**
	 * 渠道id
	 */
	@Schema(description = "渠道id")
	private Long channelItemId;

	/**
	 * 请求ip
	 */
	@Schema(description = "请求ip")
	private String clientIp;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

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
	private String payChannelType;

	/**
	 * 退款流水号
	 */
	@Schema(description = "退款流水号")
	private String refundPayNo;

	/**
	 * 第三方返回的支付流水号
	 */
	@Schema(description = "第三方返回的支付流水号")
	private String tradeNo;

}