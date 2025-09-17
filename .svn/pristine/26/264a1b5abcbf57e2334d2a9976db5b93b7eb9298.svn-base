package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>礼品卡详情查询余额</p>
 * @author 马连峰
 * @date 2022-12-10 10:58:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailQueryBalanceRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡卡号列表，主键
	 */
	@Schema(description = "礼品卡卡号列表，主键")
	@NotEmpty
	private List<String> giftCardNoList;
}
