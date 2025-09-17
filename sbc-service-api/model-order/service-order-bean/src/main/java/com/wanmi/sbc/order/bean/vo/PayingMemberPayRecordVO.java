package com.wanmi.sbc.order.bean.vo;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费会员支付记录表VO</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@Data
public class PayingMemberPayRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 业务id
	 */
	@Schema(description = "业务id")
	private String businessId;

	/**
	 * chargeId
	 */
	@Schema(description = "chargeId")
	private String chargeId;

	/**
	 * 申请价格
	 */
	@Schema(description = "申请价格")
	private BigDecimal applyPrice;

	/**
	 * 实际成功交易价格
	 */
	@Schema(description = "实际成功交易价格")
	private BigDecimal practicalPrice;

	/**
	 * 状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败
	 */
	@Schema(description = "状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败")
	private Integer status;

	/**
	 * 支付渠道项id
	 */
	@Schema(description = "支付渠道项id")
	private Integer channelItemId;

	/**
	 * 回调时间
	 */
	@Schema(description = "回调时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime callbackTime;

	/**
	 * 交易完成时间
	 */
	@Schema(description = "交易完成时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime finishTime;

}