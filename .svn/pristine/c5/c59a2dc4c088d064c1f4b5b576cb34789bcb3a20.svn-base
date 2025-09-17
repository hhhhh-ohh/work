package com.wanmi.sbc.account.api.request.credit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>客户授信还款新增参数</p>
 * @author chenli
 * @date 2021-03-04 16:21:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditRepayAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 客户id
	 */
	@Schema(description = "客户id")
	private String customerId;

	/**
	 * 关联订单
	 */
	@Schema(description = "关联订单")
	@NotNull
	private List<String> orderIds;

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
	@Length(max=500)
	private String repayNotes;

	/**
	 * 还款方式 0:线上 1:线下
	 */
	@Schema(description = "还款方式 0:线上 1:线下")
	@NotNull
	private Integer repayWay;

	/**
	 * 还款附件 repay_way为1时有值
	 */
	@Schema(description = "还款附件 repay_way为1时有值")
	private String repayFile;

	/**
	 * 还款状态 0待还款 1还款成功 2 已作废
	 */
	@Schema(description = "还款状态 0待还款 1还款成功 2已作废")
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
	 * 创建人id
	 */
	@Schema(description = "创建人id", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

	@Override
	public void checkParam() {
		if (Objects.equals(Constants.ONE,repayWay) && StringUtils.isBlank(repayFile)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}