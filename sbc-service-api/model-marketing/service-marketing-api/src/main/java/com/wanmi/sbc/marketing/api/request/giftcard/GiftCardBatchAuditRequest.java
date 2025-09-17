package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * <p>礼品卡批次审核参数</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class GiftCardBatchAuditRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 批次id
	 */
	@Schema(description = "批次id")
	@NotNull
	private Long giftCardBatchId;

	/**
	 * 审核状态，1：审核通过，2：审核不通过
	 */
	@Schema(description = "审核状态，1：审核通过，2：审核不通过")
	@NotNull
	private AuditStatus auditStatus;

	/**
	 * 审核未通过原因
	 */
	@Schema(description = "审核未通过原因")
	@Length(max = 100)
	private String auditReason;

	/**
	 * 批次类型
	 */
	@Schema(description = "批次类型")
	private GiftCardBatchType batchType;

	@Override
	public void checkParam() {
		if (AuditStatus.WAIT_CHECK == auditStatus) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (AuditStatus.NOT_PASS == auditStatus && StringUtils.isBlank(auditReason)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
	}
}