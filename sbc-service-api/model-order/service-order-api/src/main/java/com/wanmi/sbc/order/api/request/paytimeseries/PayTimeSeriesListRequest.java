package com.wanmi.sbc.order.api.request.paytimeseries;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

/**
 * <p>支付流水记录列表查询请求参数</p>
 * @author zhanggaolei
 * @date 2022-12-08 15:30:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayTimeSeriesListRequest extends BaseRequest {
	private static final long serialVersionUID = 1239859930344545314L;

	/**
	 * 批量查询-支付流水号List
	 */
	@Schema(description = "批量查询-支付流水号List")
	private List<String> payNoList;

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

	private List<String> businessIdList;

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

	/**
	 * 返回结果
	 */
	@Schema(description = "返回结果")
	private String result;

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
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:回调时间开始
	 */
	@Schema(description = "搜索条件:回调时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime callbackTimeBegin;
	/**
	 * 搜索条件:回调时间截止
	 */
	@Schema(description = "搜索条件:回调时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime callbackTimeEnd;

	/**
	 * 搜索条件:退款时间开始
	 */
	@Schema(description = "搜索条件:退款时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime refundTimeBegin;
	/**
	 * 搜索条件:退款时间截止
	 */
	@Schema(description = "搜索条件:退款时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime refundTimeEnd;

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
	@Length(max=50)
	private String tradeNo;
}