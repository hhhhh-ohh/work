package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>礼品卡批次审核参数</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class GiftCardBatchCheckForAuditRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 批次id
	 */
	@Schema(description = "批次id")
	@NotNull
	private Long giftCardBatchId;
}