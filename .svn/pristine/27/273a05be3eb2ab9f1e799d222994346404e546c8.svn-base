package com.wanmi.sbc.vas.api.response.channel;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 渠道订单补偿响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderCompensateResponse extends BasicResponse {

    private static final long serialVersionUID = -8015726253741444133L;

	@Schema(description = "设定支付成功的thirdTradeId")
	private List<String> paySuccessThirdTradeId = new ArrayList<>();

    @Schema(description = "设定支付错误的thirdTradeId")
    private List<String> payErrThirdTradeId = new ArrayList<>();

    @Schema(description = "设定待确认状态的thirdTradeId")
    private List<String> unconfirmedThirdTradeId = new ArrayList<>();

    @Schema(description = "设定支付错误的providerTradeId")
    private List<String> payErrProviderTradeIds = new ArrayList<>();

    @Schema(description = "设定待确认状态的providerTradeId")
    private List<String> unconfirmedProviderTradeIds = new ArrayList<>();
}
