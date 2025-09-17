package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.marketing.bean.dto.GiftCardSendCustomerDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.List;

/**
 * <p>礼品卡批量发卡生成参数</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@Schema
@Data
public class GiftCardBatchSendCreateRequest extends GiftCardBatchCreateRequest {

	private static final long serialVersionUID = 1L;

	@Schema(description = "发卡会员列表")
	@NotEmpty
	@Valid
	private List<GiftCardSendCustomerDTO> sendCustomerList;
}