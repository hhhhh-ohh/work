package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.*;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>客户与付费会员等级关联表VO</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@Schema
@Data
public class PayingMemberCustomerRelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 付费会员等级id
	 */
	@Schema(description = "付费会员等级id")
	private Integer levelId;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 开通时间
	 */
	@Schema(description = "开通时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime openTime;

	/**
	 * 会员到期时间
	 */
	@Schema(description = "会员到期时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate expirationDate;

	/**
	 * 总共优惠金额
	 */
	@Schema(description = "总共优惠金额")
	private BigDecimal discountAmount;

	/**
	 * 会员当前状态 0.生效中，1.未生效，2.已过期，3.已退款
	 */
	@Schema(description = "会员当前状态 0.生效中，1.未生效，2.已过期，3.已退款")
	private Integer levelState;


	public Integer getLevelState() {
		if (expirationDate == null) {
			return null;
		}
		LocalDate now = LocalDate.now();
		if (now.isAfter(expirationDate)) {
			return Constants.TWO;
		}
		return NumberUtils.INTEGER_ZERO;
	}

	/**
	 * 付费会员等级名称
	 */
	@Schema(description = "付费会员等级名称")
	private String levelName;

	/**
	 * 还差金额
	 */
	@Schema(description = "还差金额")
	private BigDecimal remainingAmount;
}