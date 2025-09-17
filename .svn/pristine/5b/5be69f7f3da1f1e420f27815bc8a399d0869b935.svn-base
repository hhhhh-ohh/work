package com.wanmi.sbc.empower.api.response.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @description 渠道订单查询响应类
 * @author daiyitian
 * @date 2021/5/10 17:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelOrderQuerySkuListResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "渠道平台订单列表")
    private List<ChannelOrder> channelOrderList;

    @Data
    public static class ChannelOrder {

        @Schema(description = "渠道平台订单id")
        private String channelOrderId;

        @Schema(description = "渠道平台订单SKU列表")
        private List<ChannelOrderSku> channelOrderSkuList;
    }

    @Data
    public static class ChannelOrderSku {

        @Schema(description = "渠道平台子订单id")
        private String ChannelSubOrderId;

        @Schema(description = "渠道平台SKU商品id")
        private String channelSkuId;

        @Schema(description = "渠道平台SPU商品id")
        private String channelSpuId;
    }
}
