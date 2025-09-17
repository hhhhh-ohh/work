package com.wanmi.sbc.vas.api.request.sellplatform.order;

import com.wanmi.sbc.vas.api.request.sellplatform.SellPlatformBaseRequest;
import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformDeliveryVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description SellPlatformDeliverySendRequest
 * @author wur
 * @date: 2022/4/20 9:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformDeliverySendRequest extends SellPlatformBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    @NotEmpty
    @Schema(description = "用户关联的OpenId")
    private String thirdOpenId;

    @Schema(description = "订单Id")
    private String orderId;

    @Schema(description = "代售订单号")
    private String sellOrderId;

    @NotNull
    @Schema(description = "发货完成标志位, 0: 未发完, 1:已发完")
    private Integer finishAll;

    @NotNull
    @Schema(description = "物流信息")
    private List<SellPlatformDeliveryVO> deliveryVOList;

    @Schema(description = "发货时间")
    private String deliveryTime;

}
