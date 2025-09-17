package com.wanmi.sbc.order.paytimeseries.model.root;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>支付流水记录实体类</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Data
@Entity
@Table(name = "pay_time_series")
public class PayTimeSeries{
	private static final long serialVersionUID = -4805445572732921919L;

	/**
	 * 支付流水号
	 */
	@Id
	@Column(name = "pay_no")
	private String payNo;

	/**
	 * 订单id
	 */
	@Column(name = "business_id")
	private String businessId;

	/**
	 * 申请价格
	 */
	@Column(name = "apply_price")
	private BigDecimal applyPrice;

	/**
	 * 实际支付价格
	 */
	@Column(name = "practical_price")
	private BigDecimal practicalPrice;

//	/**
//	 * 返回结果
//	 */
//	@Column(name = "result")
//	private String result;

	/**
	 * 状态
	 */
	@Column(name = "status")
	@Enumerated
	private TradeStatus status;

	/**
	 * chargeId
	 */
	@Column(name = "charge_id")
	private String chargeId;

	/**
	 * 渠道id
	 */
	@Column(name = "channel_item_id")
	private Long channelItemId;

	/**
	 * 请求ip
	 */
	@Column(name = "client_ip")
	private String clientIp;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 回调时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "callback_time")
	private LocalDateTime callbackTime;

	/**
	 * 退款时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "refund_time")
	private LocalDateTime refundTime;

	/**
	 * 退款状态
	 */
	@Column(name = "refund_status")
	@Enumerated
	private TradeStatus refundStatus;

	/**
	 * 支付渠道，跟订单支付时候的渠道相对应
	 */
	@Column(name = "pay_channel_type")
	private String payChannelType;

	/**
	 * 退款流水号
	 */
	@Column(name = "refund_pay_no")
	private String refundPayNo;

	/**
	 * 第三方返回的支付流水号
	 */
	@Column(name = "trade_no")
	private String tradeNo;

}
