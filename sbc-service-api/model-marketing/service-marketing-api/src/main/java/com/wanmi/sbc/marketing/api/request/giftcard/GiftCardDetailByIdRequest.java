package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询礼品卡详情请求参数</p>
 * @author 马连峰
 * @date 2022-12-10 10:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡卡号，主键
	 */
	@Schema(description = "礼品卡卡号，主键")
	@NotNull
	private String giftCardNo;

}