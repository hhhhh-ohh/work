package com.wanmi.sbc.empower.api.response.channel.linkedmall;

import com.wanmi.sbc.empower.bean.vo.channel.linkedmall.LinkedMallSkuVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkedMallRenderOrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "linkedmall订单渲染详情")
    private List<RenderOrderInfo> renderOrderInfos;

    @Data
    public static class RenderOrderInfo{

        private Map<Object,Object> extInfo;

        private List<LinkedMallSkuVO> lmItemInfos;

        private List<DeliveryInfosItem> deliveryInfos;
    }

    @Data
    public static class DeliveryInfosItem {

        private String id;

        private String displayName;

        private Long postFee;

        private Long serviceType;
    }
}
