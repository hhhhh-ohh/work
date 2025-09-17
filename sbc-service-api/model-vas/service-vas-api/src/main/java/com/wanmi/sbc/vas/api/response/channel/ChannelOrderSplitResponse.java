package com.wanmi.sbc.vas.api.response.channel;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelOrderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 渠道订单拆分响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderSplitResponse extends BasicResponse {

    private static final long serialVersionUID = -8015726253741444133L;

    @Schema(description = "渠道平台订单列表")
    private List<ChannelOrderVO> channelTradeList = new ArrayList<>();
}
