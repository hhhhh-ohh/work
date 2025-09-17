package com.wanmi.sbc.order.api.request.payingmemberrecordtemp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>付费记录临时表修改参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordTempModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录id
	 */
	@Schema(description = "记录id")
	@Length(max=45)
	private String recordId;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	@Max(9999999999L)
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Schema(description = "付费会员等级名称")
	@Length(max=55)
	private String levelName;

	/**
	 * 付费会员等级昵称
	 */
	@Schema(description = "付费会员等级昵称")
	private String levelNickName;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	@Length(max=32)
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	@Length(max=128)
	private String customerName;

	/**
	 * 支付数量
	 */
	@Schema(description = "支付数量")
	@Max(9999999999L)
	private Integer payNum;

	/**
	 * 支付金额
	 */
	@Schema(description = "支付金额")
	private BigDecimal payAmount;

	/**
	 * 支付时间
	 */
	@Schema(description = "支付时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime payTime;

	/**
	 * 会员到期时间
	 */
	@Schema(description = "会员到期时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate expirationDate;

	/**
	 * 支付状态：0.未支付，1.已支付
	 */
	@Schema(description = "支付状态：0.未支付，1.已支付")
	@Max(127)
	private Integer payState;

	/**
	 * 权益id集合
	 */
	@Schema(description = "权益id集合")
	@Length(max=255)
	private String rightsIds;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

}
