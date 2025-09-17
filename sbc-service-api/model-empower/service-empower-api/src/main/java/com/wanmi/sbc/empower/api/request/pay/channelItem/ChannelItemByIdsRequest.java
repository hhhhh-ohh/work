package com.wanmi.sbc.empower.api.request.pay.channelItem;

import com.wanmi.sbc.empower.api.request.pay.PayBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>根据id获取单个支付渠道request</p>
 * Created by of628-wenzhi on 2018-08-13-下午4:18.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelItemByIdsRequest extends PayBaseRequest {

    private static final long serialVersionUID = 529885168673888368L;
    /**
     * 支付渠道idList
     */
    @Schema(description = "支付渠道idList")
    private List<Long> channelItemIds;
}
