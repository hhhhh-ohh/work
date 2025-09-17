package com.wanmi.sbc.marketing.api.request.giftcard;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>礼品卡批量制卡生成参数</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@Schema
@Data
public class GiftCardBatchMakeCreateRequest extends GiftCardBatchCreateRequest {

	private static final long serialVersionUID = 1L;
}