package com.wanmi.sbc.order.api.request.payingmemberrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
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
 * <p>付费记录表新增参数</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * 等级状态：0.生效中，1.未生效，2.已过期，3.已退款
	 */
	@Schema(description = "等级状态：0.生效中，1.未生效，2.已过期，3.已退款")
	@Max(127)
	private Integer levelState;

	/**
	 * 已优惠金额
	 */
	@Schema(description = "已优惠金额")
	private BigDecimal alreadyDiscountAmount;

	/**
	 * 已领积分
	 */
	@Schema(description = "已领积分")
	@Max(9223372036854775807L)
	private Long alreadyReceivePoint;

	/**
	 * 退款金额
	 */
	@Schema(description = "退款金额")
	private BigDecimal returnAmount;


	/**
	 * 退款原因
	 */
	@Schema(description = "退款原因")
	private String returnCause;


	/**
	 * 退款回收券 0.是，1.否
	 */
	@Schema(description = "退款回收券 0.是，1.否")
	private Integer returnCoupon;

	/**
	 * 退款回收积分 0.是，1.否
	 */
	@Schema(description = "退款回收积分 0.是，1.否")
	private Integer returnPoint;

	/**
	 * 权益id集合
	 */
	@Schema(description = "权益id集合")
	@Length(max=255)
	private String rightsIds;

	/**
	 * 首次开通 0.是，1.否
	 */
	@Schema(description = "首次开通 0.是，1.否")
	private Integer firstOpen;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}