package com.wanmi.sbc.order.bean.vo;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.*;

import java.time.LocalDateTime;
import java.time.LocalDate;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * <p>付费记录表VO</p>
 * @author zhanghao
 * @date 2022-05-13 15:27:53
 */
@Schema
@Data
public class PayingMemberRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录id
	 */
	@Schema(description = "记录id")
	private String recordId;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	private Integer levelId;

	/**
	 * 付费会员等级名称
	 */
	@Schema(description = "付费会员等级名称")
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
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	@SensitiveWordsField(signType = SignWordType.NAME)
	private String customerName;

	/**
	 * 支付数量
	 */
	@Schema(description = "支付数量")
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
	@JsonSerialize(using = CustomDateTimeSerializer.class)
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
	private Long alreadyReceivePoint;


	/**
	 * 已剩积分
	 */
	@Schema(description = "已剩积分")
	private Long pointsAvailable;

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
	private String rightsIds;

	/**
	 * 首次开通 0.是，1.否
	 */
	@Schema(description = "首次开通 0.是，1.否")
	private Integer firstOpen;

}