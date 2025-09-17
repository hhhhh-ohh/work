package com.wanmi.sbc.order.api.request.payingmemberpayrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>付费会员支付记录表修改参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPayRecordModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Length(max=45)
	private String id;

	/**
	 * 业务id
	 */
	@Schema(description = "业务id")
	@Length(max=45)
	private String businessId;

	/**
	 * chargeId
	 */
	@Schema(description = "chargeId")
	@Length(max=27)
	private String chargeId;

	/**
	 * 申请价格
	 */
	@Schema(description = "申请价格")
	@NotNull
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
	@NotNull
	private Integer status;

	/**
	 * 支付渠道项id
	 */
	@Schema(description = "支付渠道项id")
	@NotNull
	@Max(9999999999L)
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

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

}
