package com.wanmi.sbc.account.api.request.credit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>客户授信还款修改参数</p>
 * @author chenli
 * @date 2021-03-15 16:21:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 客户id
	 */
	@Schema(description = "客户id")
	private String customerId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 还款单号
	 */
	@Schema(description = "还款单号")
	private String repayOrderCode;

	/**
	 * 授信额度
	 */
	@Schema(description = "授信额度")
	private BigDecimal creditAmount;

	/**
	 * 还款金额
	 */
	@Schema(description = "还款金额")
	private BigDecimal repayAmount;

	/**
	 * 还款说明
	 */
	@Schema(description = "还款说明")
	private String repayNotes;

	/**
	 * 还款状态 0待还款 1还款成功 2 已作废
	 */
	@Schema(description = "还款状态 0待还款 1还款成功")
	private CreditRepayStatus repayStatus;

	/**
	 * 还款方式 0银联，1微信，2支付宝
	 */
	@Schema(description = "还款方式 0银联，1微信，2支付宝")
	private CreditRepayTypeEnum repayType;

	/**
	 * 还款时间
	 */
	@Schema(description = "还款时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime repayTime;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}